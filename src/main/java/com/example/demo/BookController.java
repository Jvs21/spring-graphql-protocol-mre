package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;

@Controller
public class BookController {

    private FluxSink<Book> booksStream;
    private ConnectableFlux<Book> booksPublisher;


    @PostConstruct
    public void init() {

        Flux<Book> publisher = Flux.create(emitter -> {
            booksStream = emitter;
        });
        booksPublisher = publisher.publish();
        booksPublisher.connect();
    }

    @QueryMapping
    public List<Book> getBooks() {
        return BookService.getInstance().getBooks();
    }

    @MutationMapping
    public Book publishBook(@Argument String id, @Argument String title, @Argument String author, @Argument int pageCount) {
        Book book = new Book(id, title, author, pageCount);
        booksStream.next(book);
        return BookService.getInstance().publishBook(book);

    }

    @SubscriptionMapping
    public Publisher<Book> notifyBookPublish() {
        return booksPublisher;
    }
}

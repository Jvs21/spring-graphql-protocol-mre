package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static BookService instance;
    private List<Book> bookShelf = new ArrayList<>();

    private BookService() {
        Book book = new Book("penguin123", "The Fountainhead","Ayn Rand", 100);
        bookShelf.add(book);
    }

    public static BookService getInstance() {
        if (instance == null){
            instance = new BookService();
        }
        return instance;
    }

    public Book publishBook(Book book) {
        bookShelf.add(book);
        return book;
    }

    public List<Book> getBooks() {
        return bookShelf;
    }

}

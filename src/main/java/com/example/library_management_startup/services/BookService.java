package com.example.library_management_startup.services;

import com.example.library_management_startup.entities.Book;
import com.example.library_management_startup.repositories.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setCategory(updatedBook.getCategory());
            book.setTags(updatedBook.getTags());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublicationDate(updatedBook.getPublicationDate());
            book.setLanguage(updatedBook.getLanguage());
            book.setTotalCopies(updatedBook.getTotalCopies());
            book.setAvailableCopies(updatedBook.getAvailableCopies());
            book.setCoverImageUrl(updatedBook.getCoverImageUrl());
            book.setDescription(updatedBook.getDescription());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}


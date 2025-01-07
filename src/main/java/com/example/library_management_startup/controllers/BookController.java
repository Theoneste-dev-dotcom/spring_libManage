package com.example.library_management_startup.controllers;

import com.example.library_management_startup.entities.Book;

import com.example.library_management_startup.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowerService borrowerService;

    // getting all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //get specific book
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    // add book
    @PostMapping("/{author_id}")
    public Book addBook(@PathVariable Long author_id,@RequestBody Book book) {
        return bookService.saveBook(book, author_id);
    }
    // Add a borrower to a book
    @PostMapping("/{bookId}/add-borrower")
        public ResponseEntity<?> addBorrowerToBook(@PathVariable Long bookId, @RequestBody Borrower borrower) {
            Book book = bookService.getBookById(bookId);
            Borrower existingBorrower = borrowerService.getBorrowerById(borrower.getId());
            // Add the borrower to the book's borrowers list
            book.getBorrowers().add(existingBorrower);
            return ResponseEntity.ok("Borrower added to the book successfully");
    }

    //update book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book) {
           bookService.updateBook(id, book);
         return bookService.getBookById(id);
    }

    //delete book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }

}

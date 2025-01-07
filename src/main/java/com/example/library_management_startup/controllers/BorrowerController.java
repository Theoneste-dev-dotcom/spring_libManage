package com.example.library_management_startup.controllers;

import com.example.library_management_startup.entities.Book;
import com.example.library_management_startup.repositories.BookRepository;
import com.example.library_management_startup.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {
    @Autowired
    BorrowerService borrowerService;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowerRepository borrowerRepository;

    //add borrower
    @PostMapping("/add_borrower")
    public Borrower addBorrower(@RequestBody Borrower borrower) {
        return borrowerService.saveBorrower(borrower);
    }
    @PostMapping("/{borrowerId}/borrow")
    public ResponseEntity<?> borrow(@PathVariable Long borrowerId, @RequestBody Book book) {
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);
        Book existingBook = bookService.getBookById(book.getId());

        borrower.getBorrowedBooks().add(existingBook);
        borrowerService.saveBorrower(borrower);

        return ResponseEntity.ok("Book Borrowed Successfully");
    }

    @GetMapping("/")
   public List<Borrower> getBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public Borrower getBorrower(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id);
    }

    @PutMapping("/{id}")
    public void updateBorrower(@PathVariable Long id, @RequestBody Borrower borrower) {
        borrowerService.updateBook(id, borrower);
        System.out.println("Book updated successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
    }
}


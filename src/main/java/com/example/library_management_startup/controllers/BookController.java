package com.example.library_management_startup.controllers;

import com.example.library_management_startup.entities.Book;
import com.example.library_management_startup.entities.BookFiles;
import com.example.library_management_startup.repositories.BookFilesRepository;
import com.example.library_management_startup.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookFilesRepository bookFilesRepository;

    private final String path = "B:\\learningJAVA\\library-management-startup\\uploads\\";


    @PostMapping("/{bookId}/upload")
    public ResponseEntity<String> handleFileUpload(@PathVariable Long bookId, @RequestParam("file") MultipartFile file) {
        try {
            // Validate file content type and size
            if (!file.getContentType().equals("image/png")) {
                return ResponseEntity.badRequest().body("Only PNG files are allowed.");
            }
            if (file.getSize() > 1_000_000) {
                return ResponseEntity.badRequest().body("File size exceeds the limit of 1MB.");
            }

            // Check if the book exists
            Optional<Book> optionalBook =  bookService.getBookById(bookId);
            if (optionalBook.isEmpty()) {
                return ResponseEntity.badRequest().body("Book not found.");
            }

            // Save the file to the local filesystem
            String fileName = file.getOriginalFilename();
            File destinationFile = new File(path + fileName);
            file.transferTo(destinationFile);

            // Save file metadata in the database
            BookFiles bookFile = new BookFiles();
            bookFile.setBook(optionalBook.get());
            bookFile.setImageUrl(path + fileName);
            bookFilesRepository.save(bookFile);

            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during file upload.");
        }
    }





    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/category/{category_id}")
    public ResponseEntity<?> createBook(@PathVariable("category_id") Long category_id, @RequestBody Book book) {
         bookService.createBook(book, category_id);
         return ResponseEntity.ok("Book created"+book.getTitle() +"successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

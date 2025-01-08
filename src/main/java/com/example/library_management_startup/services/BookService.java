package com.example.library_management_startup.services;

import com.example.library_management_startup.entities.Book;
import com.example.library_management_startup.entities.Category;
import com.example.library_management_startup.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public ResponseEntity<?> createBook(Book book, Long cat_id) {
      try {
          Category book_category = categoryService.getCategoryById(cat_id);
           if(book_category == null) {
              return ResponseEntity.ok("Category with that id not found");
           }
          book.setCategory(book_category);
           bookRepository.save(book);
           return ResponseEntity.ok(book);
      }catch (Exception e) {
          return ResponseEntity.badRequest().body("Failed to save the book");
      }
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setCategory(updatedBook.getCategory());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublicationDate(updatedBook.getPublicationDate());
            book.setLanguage(updatedBook.getLanguage());
            book.setTotalCopies(updatedBook.getTotalCopies());
            book.setAvailableCopies(updatedBook.getAvailableCopies());
            book.setDescription(updatedBook.getDescription());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}


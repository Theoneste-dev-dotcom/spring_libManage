package com.example.library_management_startup.services;

import com.example.library_management_startup.entities.Book;
import com.example.library_management_startup.entities.Tag;
import com.example.library_management_startup.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BookService bookService;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public ResponseEntity<?> createTag(Tag tag, Long bookId) {
        try {
            Book foundBook = bookService.getBookById(bookId).get();
            if(foundBook == null) {
                return ResponseEntity.badRequest().body("Book not found");
            }
            tag.setBook(foundBook);
            tagRepository.save(tag);
            return ResponseEntity.ok(tag);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Tag updateTag(Long id, Tag updatedTag) {
        return tagRepository.findById(id).map(tag -> {
            tag.setName(updatedTag.getName());
            return tagRepository.save(tag);
        }).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}


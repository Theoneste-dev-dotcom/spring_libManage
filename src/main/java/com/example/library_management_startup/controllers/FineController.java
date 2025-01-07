package com.example.library_management_startup.controllers;

import com.example.library_management_startup.entities.Fine;
import com.example.library_management_startup.services.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    // Get all fines
    @GetMapping
    public List<Fine> getAllFines() {
        return fineService.getAllFines();
    }

    // Get a fine by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFineById(@PathVariable Long id) {
        Optional<Fine> fine = fineService.getFineById(id);
        return fine.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new fine
    @PostMapping
    public Fine createFine(@RequestBody Fine fine) {
        return fineService.createFine(fine);
    }

    // Update an existing fine by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Fine> updateFine(@PathVariable Long id, @RequestBody Fine fine) {
        try {
            return ResponseEntity.ok(fineService.updateFine(id, fine));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a fine by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        try {
            fineService.deleteFine(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.example.library_management_startup.controllers;

import com.example.library_management_startup.entities.User;
import com.example.library_management_startup.entities.UserProfiles;
import com.example.library_management_startup.repositories.UserFilesRepository;
import com.example.library_management_startup.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final String path = "B:\\learningJAVA\\library-management-startup\\uploads\\";

    @Autowired
    private UserFilesRepository userFilesRepository;


    @PostMapping("/{userId}/upload")
    public ResponseEntity<String> handleFileUpload(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            // Validate file content type and size
            if (!file.getContentType().equals("image/png")) {
                return ResponseEntity.badRequest().body("Only PNG files are allowed.");
            }
            if (file.getSize() > 1_000_000) {
                return ResponseEntity.badRequest().body("File size exceeds the limit of 1MB.");
            }

            // Check if the book exists
            Optional<User>  optionalUser = userService.getUserById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest().body("Book not found.");
            }

            // Save the file to the local filesystem
            String fileName = file.getOriginalFilename();
            File destinationFile = new File(path + fileName);
            file.transferTo(destinationFile);

            // Save file metadata in the database
            UserProfiles userProfiles = new UserProfiles();

            userProfiles.setUser(optionalUser.get());
           userProfiles.setImageUrl(path + fileName);
            userFilesRepository.save(userProfiles);

            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during file upload.");
        }
        }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
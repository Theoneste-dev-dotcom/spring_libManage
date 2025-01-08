package com.example.library_management_startup.services;

import com.example.library_management_startup.entities.Borrowing;
import com.example.library_management_startup.repositories.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Optional<Borrowing> getBorrowingById(Long id) {
        return borrowingRepository.findById(id);
    }

    public Borrowing createBorrowing(Borrowing borrowing) {
        borrowing.setBorrowedAt(new Date());
        borrowing.setStatus(Borrowing.BorrowingStatus.BORROWED);  // Set status to BORROWED on create
        return borrowingRepository.save(borrowing);
    }

    public Borrowing updateBorrowing(Long id, Borrowing updatedBorrowing) {
        return borrowingRepository.findById(id).map(borrowing -> {
            borrowing.setReturnedAt(updatedBorrowing.getReturnedAt());
            borrowing.setStatus(updatedBorrowing.getStatus());
            return borrowingRepository.save(borrowing);
        }).orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    public void deleteBorrowing(Long id) {
        borrowingRepository.deleteById(id);
    }

    public Borrowing returnBook(Long borrowingId) {
        Optional<Borrowing> optionalBorrowing = borrowingRepository.findById(borrowingId);

        if (optionalBorrowing.isPresent()) {
            Borrowing borrowing = optionalBorrowing.get();
            Date returnedAt = new Date();  // Use current date as returned date
            borrowing.setReturnedAt(returnedAt);
            borrowing.setStatus(Borrowing.BorrowingStatus.RETURNED);

            // Check if the book is returned late and calculate fine
            long fine = calculateFine(borrowing.getDueDate(), returnedAt);

            // If fine is applicable, set it to the borrowing (you can save the fine to the Borrowing entity)
            if (fine > 0) {
                // You can set the fine here, or return it for further processing (e.g., update in database)
                System.out.println("Fine for late return: " + fine + " units");
            }

            return borrowingRepository.save(borrowing);
        } else {
            throw new RuntimeException("Borrowing record not found");
        }
    }

    private long calculateFine(Date dueDate, Date returnedAt) {
        long fine = 0;
        if (returnedAt.after(dueDate)) {
            // Calculate days overdue
            long diffInMillis = returnedAt.getTime() - dueDate.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            fine = diffInDays * 5;  // Assume a fine of 5 units per day overdue
        }
        return fine;
    }
}

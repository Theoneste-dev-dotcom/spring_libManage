package com.example.library_management_startup.services;


import com.example.library_management_startup.entities.Borrowing;
import com.example.library_management_startup.repositories.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        borrowing.setStatus(Borrowing.BorrowingStatus.PENDING);
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
}

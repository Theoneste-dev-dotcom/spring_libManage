package com.example.library_management_startup.services;

import com.example.library_management_startup.entities.Fine;
import com.example.library_management_startup.repositories.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public Optional<Fine> getFineById(Long id) {
        return fineRepository.findById(id);
    }

    public Fine createFine(Fine fine) {
        fine.setCreatedAt(new Date());
        fine.setPaid(false);
        return fineRepository.save(fine);
    }

    public Fine updateFine(Long id, Fine updatedFine) {
        return fineRepository.findById(id).map(fine -> {
            fine.setAmount(updatedFine.getAmount());
            fine.setReason(updatedFine.getReason());
            fine.setPaid(updatedFine.getPaid());
            fine.setPaidAt(updatedFine.getPaidAt());
            return fineRepository.save(fine);
        }).orElseThrow(() -> new RuntimeException("Fine not found"));
    }

    public void deleteFine(Long id) {
        fineRepository.deleteById(id);
    }
}

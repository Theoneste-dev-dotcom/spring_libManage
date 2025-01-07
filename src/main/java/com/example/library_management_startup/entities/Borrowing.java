package com.example.library_management_startup.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrowings")
@Getter
@Setter
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedAt;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedAt;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    // Enum for Borrowing Status
    public enum BorrowingStatus {
        PENDING, BORROWED, RETURNED, OVERDUE
    }
}

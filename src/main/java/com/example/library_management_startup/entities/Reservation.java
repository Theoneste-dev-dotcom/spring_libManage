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
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {

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
    private Date reservedAt;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // Enum for Reservation Status
    public enum ReservationStatus {
        ACTIVE, CANCELED, EXPIRED
    }
}

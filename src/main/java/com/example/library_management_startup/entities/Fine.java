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
@Table(name = "fines")
@Getter
@Setter
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrowing borrowing;

    private Double amount;

    private String reason;

    private Boolean paid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
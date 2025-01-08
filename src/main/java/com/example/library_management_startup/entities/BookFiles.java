package com.example.library_management_startup.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "book_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable=false)
    private Book book;

    @Column(nullable = false)
    private String imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    @PrePersist
    protected void onUpload() {
        uploadedAt = new Date();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

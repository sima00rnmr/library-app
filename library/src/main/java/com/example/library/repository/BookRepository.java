package com.example.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByQrCode(String qrCode);
}
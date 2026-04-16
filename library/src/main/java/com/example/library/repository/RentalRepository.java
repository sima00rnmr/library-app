package com.example.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
	Optional<Rental> findByBookIdAndReturnedAtIsNull(Long bookId);
}
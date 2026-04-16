package com.example.library.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalView {

    private String bookTitle;
    private String qrCode;
    private Long userId;
    private LocalDateTime rentedAt;
}
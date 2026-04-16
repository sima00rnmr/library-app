package com.example.library.dto;

import lombok.Data;

@Data
public class RentalView {

    private String bookTitle;
    private String qrCode;
    private Long userId;
}
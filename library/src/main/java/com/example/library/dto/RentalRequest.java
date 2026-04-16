package com.example.library.dto;

import lombok.Data;

@Data
public class RentalRequest {
    private String qrCode;
    private Long userId;
}
package com.oz.importingApp.dto;

import com.oz.importingApp.dto.enums.WheelPosition;

import java.util.List;

public record PrivilegedAutoDto(
        Long id,
        String brand,
        String model,
        int year,
        List<String> imageUrls,
        WheelPosition wheelPosition

) {}

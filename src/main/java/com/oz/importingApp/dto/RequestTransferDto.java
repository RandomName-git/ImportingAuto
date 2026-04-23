package com.oz.importingApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Дто для передачи расстояния между городами доставки
 *
 * @param range в км
 */
public record RequestTransferDto(
        @NotNull
        @Min(1)
        long range
) {
}

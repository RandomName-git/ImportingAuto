package com.oz.importingApp.dto;

import java.math.BigDecimal;

public record CustomsFeeRange(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        BigDecimal fee
) {}

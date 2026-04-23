package com.oz.importingApp.service;

import com.oz.importingApp.dto.CustomsFeeRange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomsFeeService {
    /**
     * Таблица тарифов на таможенное оформление
     */
    private final List<CustomsFeeRange> feeRates = List.of(
            new CustomsFeeRange(BigDecimal.ZERO, new BigDecimal("200000"), new BigDecimal("1231")),
            new CustomsFeeRange(new BigDecimal("200000"), new BigDecimal("450000"), new BigDecimal("2462")),
            new CustomsFeeRange(new BigDecimal("450000"), new BigDecimal("1200000"), new BigDecimal("4924")),
            new CustomsFeeRange(new BigDecimal("1200000"), new BigDecimal("2700000"), new BigDecimal("13541")),
            new CustomsFeeRange(new BigDecimal("2700000"), new BigDecimal("4200000"), new BigDecimal("18465")),
            new CustomsFeeRange(new BigDecimal("4200000"), new BigDecimal("5500000"), new BigDecimal("21344")),
            new CustomsFeeRange(new BigDecimal("5500000"), new BigDecimal("10000000"), new BigDecimal("49240")),
            new CustomsFeeRange(new BigDecimal("10000000"), new BigDecimal("999999999999"), new BigDecimal("73860"))
    );

    public BigDecimal getFee(BigDecimal carPrice) {
        return feeRates.stream()
                .filter(range ->
                        // carPrice > minPrice
                        carPrice.compareTo(range.minPrice()) > 0 &&
                                // carPrice <= maxPrice
                                carPrice.compareTo(range.maxPrice()) <= 0)
                .findFirst()
                .map(CustomsFeeRange::fee)
                .orElse(handleEdgeCases(carPrice));
    }

    private BigDecimal handleEdgeCases(BigDecimal carPrice) {
        if (carPrice.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;
        // Если цена выше максимального диапазона (10 млн+)
        return new BigDecimal("73860");
    }
}

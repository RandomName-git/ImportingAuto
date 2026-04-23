package com.oz.importingApp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CustomsFeeServiceTest {
    private final CustomsFeeService customsFeeService = new CustomsFeeService();


    @ParameterizedTest(name = "Для цены {0} сбор должен быть {1}")
    @CsvSource({
            "100000, 1231",    // Первый диапазон (0 - 200k)
            "200000, 1231",    // Граница включительно (в вашем коде <= 200k)
            "200001, 2462",    // Переход во второй диапазон
            "1000000, 4924",   // Средний диапазон
            "15000000, 73860"  // Больше 10 млн
    })
    void getFee(String price, String expectedResult) {
        BigDecimal result = customsFeeService.getFee(new BigDecimal(price));
        assertEquals(new BigDecimal(expectedResult),result);
    }
    @Test
    void getFeeZero(){
        BigDecimal result = customsFeeService.getFee(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO,result);
    }
}
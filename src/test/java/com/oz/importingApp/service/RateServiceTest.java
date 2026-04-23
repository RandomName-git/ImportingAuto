package com.oz.importingApp.service;

import com.oz.importingApp.dto.enums.AgeCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    @Mock
    private ValuteService valuteService; // Создаем "куклу" сервиса

    @InjectMocks
    private RateService rateService; // Создает RateService и внедряет туда наш Mock
    @Test
    void getRateTest() {
        Mockito.when(valuteService.getRatesFromCBRForCalculate("EUR"))
                .thenReturn(new BigDecimal("100"));

        BigDecimal result = rateService.getRate(new BigDecimal("10000"), 1600, AgeCategory.NEW);
        assertNotNull(result);
        assertEquals(0,result.compareTo(new BigDecimal("560000")));


    }
}
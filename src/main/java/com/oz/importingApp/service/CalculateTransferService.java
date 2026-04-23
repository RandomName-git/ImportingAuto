package com.oz.importingApp.service;

import com.oz.importingApp.dto.RequestTransferDto;
import com.oz.importingApp.dto.ResponseCostTransferDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сервис для расчета стоимости перевозки по территории РФ
 */
@Service
public class CalculateTransferService {
    @Value("${MIN_COST_TRANSFER}")
    private int minCostTransfer;
    @Value("${MAX_COST_TRANSFER}")
    private int maxCostTransfer;


    public ResponseCostTransferDTO calculateTransfer(RequestTransferDto request) {
        BigDecimal minCost = new BigDecimal(minCostTransfer * request.range());
        BigDecimal maxCost = new BigDecimal(maxCostTransfer * request.range());
        BigDecimal avgCost = minCost.add(maxCost).divide(new BigDecimal(2), RoundingMode.HALF_UP);
                ResponseCostTransferDTO response = new ResponseCostTransferDTO(
                minCost,
                maxCost,
                avgCost,
                minCostTransfer,
                maxCostTransfer
        );
        return response;

    }
}


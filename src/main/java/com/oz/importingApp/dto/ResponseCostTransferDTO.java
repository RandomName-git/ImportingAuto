package com.oz.importingApp.dto;

import java.math.BigDecimal;

public record ResponseCostTransferDTO(BigDecimal minCost,
                                      BigDecimal maxCost,
                                      BigDecimal avgCost,
                                      int minCostTransfer,
                                      int maxCostTransfer) {
}

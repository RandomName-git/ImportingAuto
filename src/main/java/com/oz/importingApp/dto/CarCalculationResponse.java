package com.oz.importingApp.dto;

import java.math.BigDecimal;

/**
 * totalPriceRub: Финальная цена «под ключ», которую заплатит клиент.
 * details (Детализация):
 * carPriceRub: Чистая стоимость авто в рублях
 * customsDuty: Сколько ушло таможне (пошлина + сбор).
 * utilizationFee: Сумма утильсбора.
 * vat: НДС (если везете на юрлицо).
 * logistics: Стоимость доставки (если заложена в расчет).
 * appliedRates (Примененные ставки):
 * Здесь вы показываете «почему расчет именно такой».
 * Какой был курс евро/доллара на момент расчета и какие коэффициенты использовал алгоритм.
 * @param totalPriceRub
 * @param details
 * @param appliedRates
 */
public record CarCalculationResponse(
        BigDecimal totalPriceRub,
        CalculationDetails details,
        AppliedRates appliedRates
) {
    public record CalculationDetails(
            BigDecimal carPriceRub,
            BigDecimal customsDuty,// пошлина (единая ставка)
            BigDecimal customsFee,// таможенное оформление
            BigDecimal utilizationFee,
            BigDecimal vat,
            BigDecimal logistics
    ) {}

    public record AppliedRates(
            BigDecimal exchangeRate,
            BigDecimal dutyRate,
            BigDecimal utilCoefficient
    ) {}
}

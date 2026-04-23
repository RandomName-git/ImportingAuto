package com.oz.importingApp.service;

import com.oz.importingApp.dto.CustomsRange;
import com.oz.importingApp.dto.enums.AgeCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateService {

    private final ValuteService valuteService;
    /**
     * Таблица тарифов для авто от 3 до 5 лет (евро * объем двигателя см3 * коэффициент из таблицы)
     */
    List<CustomsRange> freshRate = List.of(
            new CustomsRange(0, 1000, new BigDecimal("1.5")),
            new CustomsRange(1000, 1500, new BigDecimal("1.7")),
            new CustomsRange(1500, 1799, new BigDecimal("2.5")),
            new CustomsRange(1800, 2299, new BigDecimal("2.7")),
            new CustomsRange(2300, 2999, new BigDecimal("3.0")),
            new CustomsRange(3000, Integer.MAX_VALUE, new BigDecimal("3.6"))

    );
    /**
     * Таблица тарифов для авто до 3 лет (евро * объем двигателя см3 * коэффициент из таблицы)
     */
    List<CustomsRange> newRates = List.of(
            new CustomsRange(0, 8500, new BigDecimal("0.54"), new BigDecimal("2.5")),
            new CustomsRange(8500, 16700, new BigDecimal("0.48"), new BigDecimal("3.5")),
            new CustomsRange(16700, 42300, new BigDecimal("0.48"), new BigDecimal("5.5")),
            new CustomsRange(42300, 84500, new BigDecimal("0.48"), new BigDecimal("7.5")),
            new CustomsRange(84500, 169000, new BigDecimal("0.48"), new BigDecimal("15.0")),
            new CustomsRange(169000, Integer.MAX_VALUE, new BigDecimal("0.48"), new BigDecimal("20.0"))
    );
    /**
     * Таблица тарифов для авто старше 5 лет (евро * объем двигателя см3 * коэффициент из таблицы)
     */
    List<CustomsRange> oldRates = List.of(
            new CustomsRange(0, 1000, new BigDecimal("3.0")),
            new CustomsRange(1001, 1500, new BigDecimal("3.2")),
            new CustomsRange(1501, 1800, new BigDecimal("3.5")),
            new CustomsRange(1801, 2300, new BigDecimal("4.8")),
            new CustomsRange(2301, 3000, new BigDecimal("5.0")),
            new CustomsRange(3001, Integer.MAX_VALUE, new BigDecimal("5.7"))
    );

    /**
     * Метод расчета таможенной пошлины(Единая ставка)
     * @param priceInEuro цена авто в евро
     * @param weight мощность в авто в лс
     * @param category возраст авто
     * @return возвращаем таможенный платеж в рублях
     */
    public BigDecimal getRate(BigDecimal priceInEuro, int weight, AgeCategory category) {
        List<CustomsRange> findedList = switch (category) {
            case NEW -> newRates;
            case FRESH -> freshRate;
            case OLD, VINTAGE -> oldRates;
        };

        BigDecimal costOneEuro = valuteService.getRatesFromCBRForCalculate("EUR");

        return findedList.stream()
                // Используем >= и <= для точного попадания в диапазоны
                .filter(range -> {
                    int valueToCheck = (findedList == newRates) ? priceInEuro.intValue() : weight;
                    return valueToCheck >= range.minVolume() && valueToCheck <= range.maxVolume();
                })
                .findFirst()
                .map(range -> {
                    BigDecimal resultInEuro;
                    if (findedList == newRates) {
                        // Логика: % от цены vs ставка за объем
                        BigDecimal byPrice = priceInEuro.multiply(range.procent());
                        BigDecimal byVolume = range.rate().multiply(new BigDecimal(weight));
                        resultInEuro = byPrice.max(byVolume);
                    } else {
                        // Логика: просто ставка за объем
                        resultInEuro = range.rate().multiply(new BigDecimal(weight));
                    }
                    // Конвертируем итог в рубли
                    return resultInEuro.multiply(costOneEuro);
                })
                .orElse(BigDecimal.ZERO);
    }

}

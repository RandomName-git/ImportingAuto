package com.oz.importingApp.service;

import com.oz.importingApp.dto.CarCalculationResponse;
import com.oz.importingApp.dto.RequestDtoCalculate;
import com.oz.importingApp.dto.enums.AgeCategory;
import com.oz.importingApp.dto.enums.ImporterType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CalculateService {
    @Value("${UTIL_FEE_HP_LIMIT}")
    private int powersLimit;
    @Value("${NEW_MULTIPLE}")
    private double newMultiple;
    @Value("${OLD_MULTIPLE}")
    private double oldMultiple;
    @Value("${CONVERSION_HP_TO_KW}")
    private BigDecimal hpToKw;
    @Value("${CONVERSION_KW_TO_HP}")
    private BigDecimal kwToHp;
    @Value("${BASE_RATE}")
    private BigDecimal baseRate;

    private final RateService rateService;
    private final ValuteService valuteService;
    private final CustomsFeeService customsFeeService;

    /**
     * Расчет общей суммы уплаты утильсбора+единая ставка+таможенное оформление
     *
     * @param requestDtoCalculate запрос из фронт калькулятора
     * @return дто ответ
     */
    public CarCalculationResponse calculate(RequestDtoCalculate requestDtoCalculate) {
        BigDecimal carPriceRub = convertToRub(requestDtoCalculate.price(), requestDtoCalculate.currency());// Стоимость авто с аукциона
        BigDecimal totalRecyclingFee = getCostOfRecyclingFee(requestDtoCalculate.ageCategory(),
                requestDtoCalculate.importerType(),
                requestDtoCalculate.powerHp());// утильсбор
        BigDecimal totalRate = calculateRate(carPriceRub,
                requestDtoCalculate.engineVolumeCc(),
                requestDtoCalculate.ageCategory());//пошлина (самая большая сумма)
        BigDecimal totalCustomsFee = getCustomsFee(carPriceRub);// таможенное оформление
        BigDecimal vat = BigDecimal.ZERO; // НДС
        BigDecimal logistics = BigDecimal.ZERO;// логистика
        BigDecimal total = carPriceRub
                .add(totalRecyclingFee)
                .add(totalRate)
                .add(totalCustomsFee)
                .add(vat)
                .add(logistics);
        CarCalculationResponse.CalculationDetails details = new CarCalculationResponse.CalculationDetails(
                carPriceRub,//стоимость в рублях
                totalRate,//пошлина
                totalCustomsFee,//оформление
                totalRecyclingFee,//утиль
                vat,
                logistics
        );
        CarCalculationResponse.AppliedRates rates = new CarCalculationResponse.AppliedRates(
                valuteService.getRatesFromCBRForCalculate(requestDtoCalculate.currency()),
                rateService.getRate(carPriceRub,
                        requestDtoCalculate.engineVolumeCc(),
                        requestDtoCalculate.ageCategory()),
                BigDecimal.valueOf(getRate(requestDtoCalculate.ageCategory())));// ставки

        return new CarCalculationResponse(total, details, rates);
    }

    /**
     * Расчет единой ставки на основании мощности авто в см3 и возраста
     * ставка всегда считется по евро
     *
     * @return возвращается размер пошлины в рублях
     */
    private BigDecimal calculateRate(BigDecimal priceAutoInRub, int weight, AgeCategory ageCategory) {
        BigDecimal pricedCarInEur = convertToEUR(priceAutoInRub);
        BigDecimal rate = rateService.getRate(pricedCarInEur, weight, ageCategory);
        return rate;

    }

    /**
     * Расчёт таможенного сброка
     */
    private BigDecimal getCustomsFee(BigDecimal price) {
        return customsFeeService.getFee(price);
    }

    /**
     * Расчет утильсбора
     */
    private BigDecimal getCostOfRecyclingFee(AgeCategory ageCategory,
                                             ImporterType importerType,
                                             Integer powerHp) {
        if (importerType == ImporterType.INDIVIDUAL && powerHp <= powersLimit) {
            BigDecimal multiplier = (ageCategory == AgeCategory.NEW)
                    ? BigDecimal.valueOf(newMultiple)
                    : BigDecimal.valueOf(oldMultiple);

            return multiplier.multiply(baseRate).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Конвертация из Лошадиных сил в Киловатты
     */
    public BigDecimal convertHpToKw(Integer hp) {
        if (hp == null) return BigDecimal.ZERO;

        return new BigDecimal(hp)
                .multiply(hpToKw)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Конвертация из Киловатт в Лошадиные силы
     */
    public BigDecimal convertKwToHp(BigDecimal kw) {
        if (kw == null) return BigDecimal.ZERO;

        return kw.multiply(kwToHp)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Расчет коэффициента множителя базовой ставки
     *
     * @param category
     * @return
     */
    private double getRate(AgeCategory category) {
        if (category == AgeCategory.NEW) {
            return newMultiple;
        }
        if (category == AgeCategory.OLD) {
            return oldMultiple;
        }
        return 0;
    }

    /**
     * Конвертация стоимости авто в рубли по курсу валюты
     */
    private BigDecimal convertToRub(BigDecimal price, String currency) {
        if (price == null || currency == null) return BigDecimal.ZERO;
        // Если валюта уже рубли, возвращаем как есть
        if ("RUB".equalsIgnoreCase(currency)) return price;

        BigDecimal rate = valuteService.getRatesFromCBRForCalculate(currency);
        return price.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Конвертация рублей в евро для расчета размера пошлины для новых автомобилий
     */
    private BigDecimal convertToEUR(BigDecimal priceRub) {
        return priceRub.divide(valuteService.getRatesFromCBRForCalculate("EUR"), RoundingMode.HALF_UP);
    }
}

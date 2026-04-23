package com.oz.importingApp.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oz.importingApp.dto.ValCursDto;
import com.oz.importingApp.dto.ValuteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ValuteService {

    @Value("${URL_CBR}")
    private String url;

    private final XmlMapper xmlMapper = new XmlMapper();

    private static final Set<String> TARGET_CODES = Set.of("USD", "EUR", "CNY", "JPY", "KRW");

    public List<ValuteDto> getRatesFromCBR() {
        try {

            ValCursDto data = xmlMapper.readValue(new URL(url), ValCursDto.class);

            return data.getValutes().stream()
                    .filter(v -> TARGET_CODES.contains(v.getCharCode()))
                    .toList();

        } catch (IOException e) {
            log.warn("ошибка при получении данных с ЦБР");
            return List.of();
        }
    }

    /**
     * Получаем стоимость 1 единицу валюты в рублях
     * @param charCode
     * @return
     */
    public BigDecimal getRatesFromCBRForCalculate(String charCode) {
        try {


            ValCursDto data = xmlMapper.readValue(new URL(url), ValCursDto.class);

            return data.getValutes().stream()
                    .filter(v -> v.getCharCode().equalsIgnoreCase(charCode))
                    .findFirst() // Ищем конкретную валюту
                    .map(v -> {
                        // Заменяем запятую на точку, чтобы BigDecimal смог распарсить строку
                        BigDecimal value = new BigDecimal(v.getValue().replace(",", "."));
                        BigDecimal nominal = new BigDecimal(v.getNominal());
                        // Делим значение на номинал (например, 100 иен -> курс за 1 иену)
                        return value.divide(nominal, 4, RoundingMode.HALF_UP);
                    })
                    .orElse(BigDecimal.ZERO); // Если валюта не найдена

        } catch (IOException e) {
            log.warn("Ошибка при получении данных с ЦБР: {}", e.getMessage());
            return BigDecimal.ZERO; // Метод возвращает BigDecimal, поэтому List.of() тут нельзя
        }
    }
}

package com.oz.importingApp.dto;

import com.oz.importingApp.dto.enums.AgeCategory;
import com.oz.importingApp.dto.enums.ImporterType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RequestDtoCalculate(
        @NotNull(message = "Цена обязательна")
        @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
        BigDecimal price,

        @NotBlank(message = "Валюта обязательна")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Валюта должна быть из 3-х заглавных букв (напр. USD)")
        String currency,

        @NotNull(message = "Объем двигателя обязателен")
        @Min(value = 1, message = "Объем двигателя должен быть больше 0")
        Integer engineVolumeCc,

        @NotNull(message = "Мощность обязательна")
        @Positive(message = "Мощность должна быть положительным числом")
        Integer powerHp,


        @NotNull(message = "Категория возраста обязательна")
        AgeCategory ageCategory,



        @NotNull(message = "Тип импортера обязателен")
        ImporterType importerType,

        @NotBlank(message = "Страна импорта обязательна")
        @Size(min = 2, max = 2, message = "Код страны должен быть из 2-х символов (ISO)")
        String importCountry
) {}


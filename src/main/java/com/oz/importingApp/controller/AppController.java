package com.oz.importingApp.controller;

import com.oz.importingApp.dto.*;
import com.oz.importingApp.service.CalculateService;
import com.oz.importingApp.service.CalculateTransferService;
import com.oz.importingApp.service.CatalogService;
import com.oz.importingApp.service.ValuteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppController {

    private final CalculateService calculateService;
    private final ValuteService valuteService;
    private final CatalogService catalogService;
    private final CalculateTransferService calculateTransferService;

    /**
     * Калькулятор авто
     *
     * @param request данные с фронтэнда
     * @return итоговый расчет стоимости автомобиля
     */
    @PostMapping("/calculator/calculate")
    public ResponseEntity<CarCalculationResponse> calculate(@Valid @RequestBody RequestDtoCalculate request) {
        return ResponseEntity.ok(calculateService.calculate(request));
    }

    /**
     * Курсы валют на текущий день
     *
     * @return курсы "USD", "EUR", "CNY", "JPY", "KRW" в рублях на сегодня
     */
    @GetMapping("/currency/rates")
    public ResponseEntity<List<ValuteDto>> getCurrencyRates() {
        List<ValuteDto> response = valuteService.getRatesFromCBR();
        return ResponseEntity.ok(response);

    }

    /**
     * Каталог авто для фронтенда
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/catalog/privileged")
    public ResponseEntity<SliceResponse<PrivilegedAutoDto>> getAllProductsWithSlices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        SliceResponse<PrivilegedAutoDto> response = catalogService.getCatalog(size, page);
        return ResponseEntity.ok(response);

    }

    /**
     * Калькулятор для расчета доставки авто автовозом по территории РФ
     */
    @PostMapping("calculator/transit")
    public ResponseEntity<ResponseCostTransferDTO> transitCalculator(@Valid @RequestBody RequestTransferDto request) {
        ResponseCostTransferDTO response =  calculateTransferService.calculateTransfer(request);
return ResponseEntity.ok(response);
    }


}

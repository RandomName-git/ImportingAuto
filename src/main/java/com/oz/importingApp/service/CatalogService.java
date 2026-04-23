package com.oz.importingApp.service;

import com.oz.importingApp.dto.PrivilegedAutoDto;
import com.oz.importingApp.dto.SliceResponse;
import com.oz.importingApp.dto.enums.WheelPosition;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final Map<Long, PrivilegedAutoDto> carStorage = new LinkedHashMap<>();
    private void addCar(Long id, String brand, String model, int year, String imgPath, WheelPosition pos) {
        carStorage.put(id, new PrivilegedAutoDto(id, brand, model, year, List.of(imgPath), pos));
    }
    @PostConstruct
    public void init() {
        // Часть 1: Европа и Корея
        addCar(1L, "Volkswagen", "Golf (VIII)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Volkswagen+Golf+(VIII)", WheelPosition.LEFT);
        addCar(2L, "Skoda", "Octavia (A8)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Skoda+Octavia+(A8)", WheelPosition.LEFT);
        addCar(3L, "Renault", "Megane", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Renault+Megane", WheelPosition.LEFT);
        addCar(4L, "BMW", "1 series (F40)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=BMW+1+series+(F40)", WheelPosition.LEFT);
        addCar(5L, "Peugeot", "3008", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Peugeot+3008", WheelPosition.LEFT);
        addCar(6L, "Opel", "Astra (L)", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Opel+Astra+(L)", WheelPosition.LEFT);
        addCar(7L, "Hyundai", "Avante (CN7)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Hyundai+Avante+(CN7)", WheelPosition.LEFT);
        addCar(8L, "Kia", "Seltos", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Kia+Seltos", WheelPosition.LEFT);
        addCar(9L, "Hyundai", "Kona", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Hyundai+Kona", WheelPosition.LEFT);
        addCar(10L, "Kia", "K3 (Cerato)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Kia+K3+(Cerato)", WheelPosition.LEFT);
        addCar(11L, "Toyota", "Corolla (E210)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Toyota+Corolla+(E210)", WheelPosition.RIGHT);
        addCar(12L, "Honda", "Fit (GR)", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Honda+Fit+(GR)", WheelPosition.RIGHT);
        addCar(13L, "Mazda", "3 (BP)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Mazda+3+(BP)", WheelPosition.RIGHT);
        addCar(14L, "Toyota", "Raize", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Toyota+Raize", WheelPosition.RIGHT);
        addCar(15L, "Nissan", "Note (E13)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Nissan+Note+(E13)", WheelPosition.RIGHT);
        addCar(16L, "Toyota", "Aqua", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Toyota+Aqua", WheelPosition.RIGHT);
        addCar(17L, "Honda", "Freed", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Honda+Freed", WheelPosition.RIGHT);
        addCar(18L, "Subaru", "XV", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Subaru+XV", WheelPosition.RIGHT);
        addCar(19L, "Toyota", "Tank", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Toyota+Tank", WheelPosition.RIGHT);
        addCar(20L, "Suzuki", "Swift", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Suzuki+Swift", WheelPosition.RIGHT);
        addCar(21L, "Audi", "A3 (8Y)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Audi+A3+(8Y)", WheelPosition.LEFT);
        addCar(22L, "Mercedes-Benz", "A-class (W177)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Mercedes-Benz+A-class+(W177)", WheelPosition.LEFT);
        addCar(23L, "Ford", "Focus (IV)", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Ford+Focus+(IV)", WheelPosition.LEFT);
        addCar(24L, "Dacia", "Duster", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Dacia+Duster", WheelPosition.LEFT);
        addCar(25L, "Seat", "Leon", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Seat+Leon", WheelPosition.LEFT);
        addCar(26L, "Volkswagen", "T-Roc", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Volkswagen+T-Roc", WheelPosition.LEFT);
        addCar(27L, "Hyundai", "Tucson", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Hyundai+Tucson", WheelPosition.LEFT);
        addCar(28L, "Kia", "Sportage", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Kia+Sportage", WheelPosition.LEFT);
        addCar(29L, "Renault", "Captur", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Renault+Captur", WheelPosition.LEFT);
        addCar(30L, "Citroen", "C4", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Citroen+C4", WheelPosition.LEFT);
        addCar(31L, "Toyota", "Yaris Cross", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Toyota+Yaris+Cross", WheelPosition.RIGHT);
        addCar(32L, "Honda", "Vezel", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Honda+Vezel", WheelPosition.RIGHT);
        addCar(33L, "Mazda", "CX-3", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Mazda+CX-3", WheelPosition.RIGHT);
        addCar(34L, "Nissan", "Juke (F16)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Nissan+Juke+(F16)", WheelPosition.LEFT);
        addCar(35L, "Mitsubishi", "Eclipse Cross", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Mitsubishi+Eclipse+Cross", WheelPosition.RIGHT);
        addCar(36L, "Suzuki", "Jimny", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Suzuki+Jimny", WheelPosition.RIGHT);
        addCar(37L, "Daihatsu", "Rocky", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Daihatsu+Rocky", WheelPosition.RIGHT);
        addCar(38L, "Volkswagen", "Polo", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Volkswagen+Polo", WheelPosition.LEFT);
        addCar(39L, "Skoda", "Kamiq", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Skoda+Kamiq", WheelPosition.LEFT);
        addCar(40L, "Fiat", "500X", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Fiat+500X", WheelPosition.LEFT);
        addCar(41L, "Jeep", "Renegade", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Jeep+Renegade", WheelPosition.LEFT);
        addCar(42L, "BMW", "2 series Active Tourer", 2022, "https://placehold.co/300x200/1e3a8a/white?text=BMW+2+series+Active+Tourer", WheelPosition.LEFT);
        addCar(43L, "Mini", "Cooper", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Mini+Cooper", WheelPosition.LEFT);
        addCar(44L, "Volvo", "XC40", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Volvo+XC40", WheelPosition.LEFT);
        addCar(45L, "Honda", "Civic (FL1)", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Honda+Civic+(FL1)", WheelPosition.RIGHT);
        addCar(46L, "Nissan", "Qashqai (J12)", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Nissan+Qashqai+(J12)", WheelPosition.LEFT);
        addCar(47L, "Geely", "Binyue (Coolray)", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Geely+Binyue+(Coolray)", WheelPosition.LEFT);
        addCar(48L, "Changan", "CS35 Plus", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Changan+CS35+Plus", WheelPosition.LEFT);
        addCar(49L, "Hyundai", "Venue", 2022, "https://placehold.co/300x200/1e3a8a/white?text=Hyundai+Venue", WheelPosition.LEFT);
        addCar(50L, "Kia", "Ceed", 2021, "https://placehold.co/300x200/1e3a8a/white?text=Kia+Ceed", WheelPosition.LEFT);
    }


    public SliceResponse<PrivilegedAutoDto> getCatalog(int size, int page) {
List<PrivilegedAutoDto> allCars = carStorage.values().stream()
        .sorted(Comparator.comparing(PrivilegedAutoDto::id))
        .toList();
        int start = page * size;

        // 2. Пытаемся взять на один элемент БОЛЬШЕ, чем запрошено (size + 1)
        List<PrivilegedAutoDto> pagedContentPlusOne = allCars.stream()
                .skip(start)
                .limit(size + 1) // Ключевой момент здесь
                .collect(Collectors.toList());

        // 3. Проверяем, удалось ли достать этот лишний элемент
        boolean hasNext = pagedContentPlusOne.size() > size;

        // 4. В ответ отдаем только запрошенное количество (удаляем лишний, если он есть)
        List<PrivilegedAutoDto> finalContent = hasNext
                ? pagedContentPlusOne.subList(0, size)
                : pagedContentPlusOne;

        SliceResponse<PrivilegedAutoDto> response = new SliceResponse<>();
        response.setContent(finalContent);
        response.setPage(page);
        response.setSize(size);
        response.setHasNext(hasNext);

        return response;

    }

}

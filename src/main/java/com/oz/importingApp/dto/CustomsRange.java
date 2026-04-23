package com.oz.importingApp.dto;

import java.math.BigDecimal;

public record CustomsRange(int minVolume, int maxVolume, BigDecimal procent,BigDecimal rate) {

    public CustomsRange(int minVolume, int maxVolume, BigDecimal procent, BigDecimal rate) {
        this.minVolume = minVolume;
        this.maxVolume = maxVolume;
        this.procent = procent;
        this.rate = rate;
    }

    public CustomsRange(int minVolume, int maxVolume, BigDecimal rate) {
        this(minVolume, maxVolume, null, rate);
    }
}



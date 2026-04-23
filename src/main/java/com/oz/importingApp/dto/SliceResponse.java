package com.oz.importingApp.dto;

import lombok.Data;

import java.util.List;
@Data
public class SliceResponse <T>{
    private List<T> content;
    private int page;
    private int size;
    private boolean hasNext;

}

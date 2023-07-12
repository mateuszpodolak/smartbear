package com.podolak.smartbear.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}

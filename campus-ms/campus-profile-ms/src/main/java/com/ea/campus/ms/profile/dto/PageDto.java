package com.ea.campus.ms.profile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T extends BaseDto> {
    private List<T> content;
    private long totalElements;
    private int page;
    private int totalPages;

    public PageDto(List<T> content, Page<?> page) {
        this.content = content;
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
}

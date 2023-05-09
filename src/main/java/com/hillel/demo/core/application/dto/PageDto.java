package com.hillel.demo.core.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {

    private List<T> pageContent;
    private Integer contentSize;

    public PageDto() {
    }

    public PageDto(List<T> pageContent, Integer contentSize) {
        this.pageContent = pageContent;
        this.contentSize = contentSize;
    }
}

package com.ohgiraffers.lmsjpa.menu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDTO {

    private int categoryCode;

    private String categoryName;

    private Integer refCategoryCode;
}

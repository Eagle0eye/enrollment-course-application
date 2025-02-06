package com.online_shop.project.dto;

import com.online_shop.project.enums.Category;

import lombok.Getter;

public class UpdateCourseForm {
    private String name;
    private String description;
    private String category;
    private int hours;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getHours() {
        return hours;
    }
}

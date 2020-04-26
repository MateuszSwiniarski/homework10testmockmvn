package com.rodzyn.homework10testmockmvn.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Car{

    @NotNull
    private Long id;
    @NotBlank
    private String mark;
    @NotNull
    private String model;
    private String color;

    public Car() {
    }

    public Car(Long id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
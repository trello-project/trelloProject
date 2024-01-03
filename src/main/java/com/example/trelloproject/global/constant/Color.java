package com.example.trelloproject.global.constant;

public enum Color {
    RED("RED"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    WHITE("WHITE");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}

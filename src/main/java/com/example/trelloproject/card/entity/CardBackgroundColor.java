package com.example.trelloproject.card.entity;

public enum CardBackgroundColor {
    RED(Color.RED), GREEN(Color.GREEN), BLUE(Color.BLUE), WHITE(Color.WHITE);

    private final String color;

    CardBackgroundColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public static class Color {
        public static final String RED = "RED";
        public static final String GREEN = "GREEN";
        public static final String BLUE = "BLUE";
        public static final String WHITE = "WHITE";
    }
}

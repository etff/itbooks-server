package com.example.itbooks.book.domain;

public enum SortType {
    DEFAULT("accuracy"), POPULAR("salesPoint"), TITLE("title"), PRICE("price");

    private String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

package com.example.itbooks.book.domain;

/**
 * 검색 타입.
 */
public enum SearchType {
    /**
     * 판매 순위
     */
    POPULAR("/bestSeller.api"),
    /**
     * 추천
     */
    RECOMMEND("/recommend.api"),
    /**
     * 책 검색
     */
    SEARCH("/search.api"),

    /**
     * 새로운 책
     */
    NEW_BOOK("/newBook.api");


    private String url;

    SearchType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}

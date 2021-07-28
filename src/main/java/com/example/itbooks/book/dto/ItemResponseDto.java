package com.example.itbooks.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ItemResponseDto {
    private Long itemId;
    private String title;
    private String description;
    private String pubDate;
    private Integer priceStandard;
    private Integer priceSales;
    private Long discountRate;
    private String saleStatus;
    private Long mileage;
    private Long mileageRate;
    private String coverSmallUrl;
    private String coverLargeUrl;
    private Long categoryId;
    private String categoryName;
    private String publisher;
    private Long customerReviewRank;
    private String author;
    private String translator;
    private String isbn;
    private String link;
    private String mobileLink;
    private String additionalLink;
    private Long reviewCount;
    private Integer rank;

    @Builder
    public ItemResponseDto(Long itemId, String title, String description, String pubDate, Integer priceStandard, Integer priceSales, Long discountRate, String saleStatus, Long mileage, Long mileageRate, String coverSmallUrl, String coverLargeUrl, Long categoryId, String categoryName, String publisher, Long customerReviewRank, String author, String translator, String isbn, String link, String mobileLink, String additionalLink, Long reviewCount, Integer rank) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.priceStandard = priceStandard;
        this.priceSales = priceSales;
        this.discountRate = discountRate;
        this.saleStatus = saleStatus;
        this.mileage = mileage;
        this.mileageRate = mileageRate;
        this.coverSmallUrl = coverSmallUrl;
        this.coverLargeUrl = coverLargeUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publisher = publisher;
        this.customerReviewRank = customerReviewRank;
        this.author = author;
        this.translator = translator;
        this.isbn = isbn;
        this.link = link;
        this.mobileLink = mobileLink;
        this.additionalLink = additionalLink;
        this.reviewCount = reviewCount;
        this.rank = rank;
    }
}

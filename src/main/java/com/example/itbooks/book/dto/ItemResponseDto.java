package com.example.itbooks.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
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
}

package com.example.itbooks.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class InterparkResponseDto {

    private String title;
    private String link;
    private String language;
    private String copyright;
    private String pubDate;
    private String imageUrl;
    private Long totalResults;
    private Integer startIndex;
    private Integer itemsPerPage;
    private Integer maxResults;
    private String queryType;
    private Long searchCategoryId;
    private String searchCategoryName;
    private String returnCode;
    private String returnMessage;
    private List<ItemResponseDto> item;
}

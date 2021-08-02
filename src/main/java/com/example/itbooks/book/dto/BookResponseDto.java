package com.example.itbooks.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BookResponseDto {
    private static final int MAXIMUM_ITEM_SHOW = 10;

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
    private String query;
    private String returnCode;
    private String returnMessage;
    private List<ItemResponseDto> item;

    @Builder
    public BookResponseDto(String title, String link, String language, String copyright, String pubDate, String imageUrl, Long totalResults, Integer startIndex, Integer itemsPerPage, Integer maxResults, String queryType, Long searchCategoryId, String searchCategoryName, String query, String returnCode, String returnMessage, List<ItemResponseDto> item) {
        this.title = title;
        this.link = link;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
        this.totalResults = totalResults;
        this.startIndex = startIndex;
        this.itemsPerPage = itemsPerPage;
        this.maxResults = maxResults;
        this.queryType = queryType;
        this.searchCategoryId = searchCategoryId;
        this.searchCategoryName = searchCategoryName;
        this.query = query;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.item = item;
    }

    public List<ItemResponseDto> getItem() {
        return item.size() > MAXIMUM_ITEM_SHOW ? item.subList(0, 8) : item;
    }
}

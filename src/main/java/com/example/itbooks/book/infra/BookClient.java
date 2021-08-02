package com.example.itbooks.book.infra;

import com.example.itbooks.book.domain.SearchType;
import com.example.itbooks.book.dto.BookResponseDto;
import com.example.itbooks.global.properties.InterparkProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 인터파크의 책 정보를 호출한다.
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class BookClient {
    private static final int IT_CATEGORY = 122;
    private static final String PRODUCT_NUMBER = "productNumber";

    private final InterparkProperties properties;
    private final WebClient webClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 인기 책을 리턴한다.
     */
    public BookResponseDto getPopularBooks() {
        final String url = SearchType.POPULAR.getUrl();
        return convertToResponse(findBooks(url));
    }

    /**
     * 추천 책을 리턴한다.
     */
    public BookResponseDto getRecommendBooks() {
        final String url = SearchType.RECOMMEND.getUrl();
        return convertToResponse(findBooks(url));
    }

    /**
     * 찾고자 하는 책을 리턴한다.
     *
     * @param id 책의 식별자
     */
    public BookResponseDto getBook(Long id) {
        return convertToResponse(findBook(id));
    }

    private String findBooks(String searchType) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path(searchType)
                            .queryParam("categoryId", IT_CATEGORY)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }

    private String findBook(Long id) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path(SearchType.BOOK_ID.getUrl())
                            .queryParam("query", id)
                            .queryParam("queryType", PRODUCT_NUMBER)
                            .queryParam("categoryId", IT_CATEGORY)
                            .queryParam("output", "json")
                            .queryParam("key", properties.getKey()).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return items;
    }

    private BookResponseDto convertToResponse(String textData) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        try {
            bookResponseDto = objectMapper.readValue(textData, BookResponseDto.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return bookResponseDto;
    }
}

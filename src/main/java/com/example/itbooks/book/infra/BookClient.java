package com.example.itbooks.book.infra;

import com.example.itbooks.book.domain.SearchType;
import com.example.itbooks.book.domain.SortType;
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
    private static final String ISBN = "isbn";

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
     * 찾고자 하는 식별자의 책을 리턴한다.
     *
     * @param id 책의 식별자
     */
    public BookResponseDto getBook(Long id) {
        return convertToResponse(findBookById(id));
    }

    /**
     * 찾고자하는 책을 리턴한다.
     *
     * @param query     검색하고자하는 조건
     * @param index     검색 페이지 인덱스
     * @param maxResult 최대 노출 결과
     * @return 검색된 책
     */
    public BookResponseDto getSearchBooks(String query, int index, int maxResult) {
        return convertToResponse(findBookByQuery(query, index, maxResult));
    }

    /**
     * 새로 나온 책을 리턴한다.
     */
    public BookResponseDto getNewBooks() {
        final String url = SearchType.NEW_BOOK.getUrl();
        return convertToResponse(findBooks(url));
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

    private String findBookById(Long id) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path(SearchType.SEARCH.getUrl())
                            .queryParam("query", id)
                            .queryParam("queryType", ISBN)
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

    private String findBookByQuery(String query, int index, int maxResult) {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path(SearchType.SEARCH.getUrl())
                            .queryParam("query", query)
                            .queryParam("start", index)
                            .queryParam("maxResults", maxResult)
                            .queryParam("sort", SortType.POPULAR)
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

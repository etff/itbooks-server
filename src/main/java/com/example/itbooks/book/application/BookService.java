package com.example.itbooks.book.application;

import com.example.itbooks.book.dto.BookResponseDto;
import com.example.itbooks.book.infra.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 책의 정보를 다룬다.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class BookService {
    private final BookClient bookClient;

    /**
     * 인기 책을 리턴한다.
     */
    public BookResponseDto getPopularBooks() {
        return bookClient.getPopularBooks();
    }

    /**
     * 찾고자 하는 책을 리턴한다.
     *
     * @param id 책의 식별자
     */
    public BookResponseDto getBook(Long id) {
        return bookClient.getBook(id);
    }

    /**
     * 추천 책을 리턴한다.
     */
    public BookResponseDto getRecommendBooks() {
        return bookClient.getRecommendBooks();
    }

    /**
     * 검색하고자하는 책을 리턴한다.
     *
     * @param query      검색어
     * @param index      목록의 인덱스
     * @param maxResults 최대 노출 갯수
     * @return 검색하고자하는 책
     */
    public BookResponseDto getSearchBooks(String query, int index, int maxResults) {
        return bookClient.getSearchBooks(query, index, maxResults);
    }

    /**
     * 새로 발간된 책을 리턴한다.
     */
    public BookResponseDto getNewBooks() {
        return bookClient.getNewBooks();
    }
}

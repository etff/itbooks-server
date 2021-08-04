package com.example.itbooks.book.service;

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

    public BookResponseDto getSearchBooks(String query, int index, int maxResult) {
        return bookClient.getSearchBooks(query, index, maxResult);
    }
}

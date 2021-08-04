package com.example.itbooks.book.ui;

import com.example.itbooks.book.dto.BookResponseDto;
import com.example.itbooks.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 책에 대한 요청을 처리한다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    /**
     * 인기 책을 리턴한다.
     */
    @GetMapping("/popular")
    public BookResponseDto getPopularBooks() {
        return bookService.getPopularBooks();
    }

    /**
     * 찾고자하는 책을 리턴한다.
     */
    @GetMapping("/{id}")
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    /**
     * 추천 책을 리턴한다.
     */
    @GetMapping("/recommend")
    public BookResponseDto getRecommendBooks() {
        return bookService.getRecommendBooks();
    }

    /**
     * 신규 발간된 책을 리턴한다.
     */
    @GetMapping("/new")
    public BookResponseDto getNewBooks() {
        return bookService.getNewBooks();
    }
}

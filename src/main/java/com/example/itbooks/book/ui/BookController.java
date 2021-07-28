package com.example.itbooks.book.ui;

import com.example.itbooks.book.dto.InterparkResponseDto;
import com.example.itbooks.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public InterparkResponseDto popular() {
        return bookService.getPopularBooks();
    }
}

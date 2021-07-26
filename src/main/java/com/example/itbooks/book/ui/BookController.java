package com.example.itbooks.book.ui;

import com.example.itbooks.book.dto.InterparkResponseDto;
import com.example.itbooks.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/popular")
    public InterparkResponseDto popular() {
        return bookService.getPopluarBooks();
    }
}

package com.example.itbooks.book.service;

import com.example.itbooks.book.dto.InterparkResponseDto;
import com.example.itbooks.book.infra.InterparkClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService {
    private final InterparkClient interparkClient;

    public InterparkResponseDto getPopluarBooks() {
        return interparkClient.getPopularBooks();
    }
}

package com.example.itbooks.book.service;

import com.example.itbooks.book.dto.BookResponseDto;
import com.example.itbooks.book.dto.ItemResponseDto;
import com.example.itbooks.book.infra.BookClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookClient bookClient;

    private BookService bookService;

    private BookResponseDto bookResponse;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookClient);
    }


    @DisplayName("findPopularBooks 메서드는 인기 서적을 리턴한다.")
    @Test
    void getPopularBooks() {
        // given
        ItemResponseDto item1 = ItemResponseDto.builder()
                .title("clean code")
                .build();
        ItemResponseDto item2 = ItemResponseDto.builder()
                .title("effective java")
                .build();
        bookResponse = BookResponseDto.builder()
                .item(Arrays.asList(item1, item2))
                .build();
        given(bookService.getPopularBooks())
                .willReturn(bookResponse);

        // when
        BookResponseDto actual = bookService.getPopularBooks();

        // then
        assertThat(actual).isEqualTo(bookResponse);
    }
}

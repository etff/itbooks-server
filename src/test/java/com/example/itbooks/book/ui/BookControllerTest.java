package com.example.itbooks.book.ui;

import com.example.itbooks.book.dto.BookResponseDto;
import com.example.itbooks.book.dto.ItemResponseDto;
import com.example.itbooks.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("BookController 클래스")
@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /popluar 는")
    class Describe_getPoplularBooks {
        @Nested
        @DisplayName("주어진 책이 있으면")
        class Context_with_books {
            BookResponseDto bookResponse;

            @BeforeEach
            void setUp() {
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
            }

            @DisplayName("200 OK 상태와 책을 응답한다.")
            @Test
            void It_responds_ok_with_books() throws Exception {
                mockMvc.perform(get("/api/v1/books/popular"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(bookResponse)));
            }
        }

        @Nested
        @DisplayName("주어진 책이 없으면")
        class Context_without_books {

            @DisplayName("200 OK 상태와 비어있는 책을 응답한다.")
            @Test
            void It_responds_ok_with_empty_books() throws Exception {
                mockMvc.perform(get("/api/v1/books/popular"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("")));
            }
        }
    }
}

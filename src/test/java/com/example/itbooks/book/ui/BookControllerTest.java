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
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Nested
    @DisplayName("GET /id 는")
    class Describe_getBook {
        Integer bookId = 1;

        @Nested
        @DisplayName("주어진 책이 있으면")
        class Context_with_book {
            BookResponseDto bookResponse;

            @BeforeEach
            void setUp() {
                ItemResponseDto item1 = ItemResponseDto.builder()
                        .title("clean code")
                        .build();
                bookResponse = BookResponseDto.builder()
                        .item(Collections.singletonList(item1))
                        .build();
                given(bookService.getBook(anyLong()))
                        .willReturn(bookResponse);
            }

            @DisplayName("200 OK 상태와 책을 응답한다.")
            @Test
            void It_responds_ok_with_book() throws Exception {
                mockMvc.perform(get("/api/v1/books/{id}", bookId))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(bookResponse)));
            }
        }

        @Nested
        @DisplayName("주어진 책이 없으면")
        class Context_without_book {

            @DisplayName("200 OK 상태와 비어있는 책을 응답한다.")
            @Test
            void It_responds_ok_with_empty_book() throws Exception {
                mockMvc.perform(get("/api/v1/books/{id}", bookId))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("")));
            }
        }
    }

    @Nested
    @DisplayName("GET /recommend 는")
    class Describe_getRecommendBooks {
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
                given(bookService.getRecommendBooks())
                        .willReturn(bookResponse);
            }

            @DisplayName("200 OK 상태와 책을 응답한다.")
            @Test
            void It_responds_ok_with_books() throws Exception {
                mockMvc.perform(get("/api/v1/books/recommend"))
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
                mockMvc.perform(get("/api/v1/books/recommend"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("")));
            }
        }
    }

    @Nested
    @DisplayName("GET /new 는")
    class Describe_getNewBooks {
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
                given(bookService.getNewBooks())
                        .willReturn(bookResponse);
            }

            @DisplayName("200 OK 상태와 책을 응답한다.")
            @Test
            void It_responds_ok_with_books() throws Exception {
                mockMvc.perform(get("/api/v1/books/new"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(bookResponse)));
            }
        }
    }

    @Nested
    @DisplayName("GET /search 는")
    class Describe_getSearchBooks {
        @Nested
        @DisplayName("주어진 책이 있으면")
        class Context_with_books {
            BookResponseDto bookResponse;
            final String query = "파이썬";
            final int index = 1;
            final int maxResults = 10;

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
                given(bookService.getSearchBooks(query, index, maxResults))
                        .willReturn(bookResponse);
            }

            @DisplayName("200 OK 상태와 책을 응답한다.")
            @Test
            void It_responds_ok_with_books() throws Exception {
                mockMvc.perform(get("/api/v1/books/search")
                        .param("query", query)
                        .param("index", String.valueOf(index))
                        .param("maxResults", String.valueOf(maxResults))
                )
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(bookResponse)));
            }
        }

        @Nested
        @DisplayName("검색어가 주어지지 않으면")
        class Context_with_no_query {
            final int index = 1;
            final int maxResult = 10;

            @DisplayName("400 BAD REQUEST 상태를 응답한다.")
            @Test
            void It_responds_bad_request() throws Exception {
                mockMvc.perform(get("/api/v1/books/search")
                        .param("index", String.valueOf(index))
                        .param("maxResult", String.valueOf(maxResult))
                )
                        .andExpect(status().isBadRequest());
            }
        }
    }
}

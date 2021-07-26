package com.example.itbooks.book.infra;

import com.example.itbooks.book.dto.InterparkResponseDto;
import com.example.itbooks.global.properties.InterparkProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
@Component
public class InterparkClient {
    private static final int IT_CATEGORY = 122;
    private final InterparkProperties properties;
    private final WebClient webClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    public InterparkResponseDto getPopularBooks() {
        InterparkResponseDto interparkResponseDto = convertToResponse(findPopularBooks());
        return interparkResponseDto;
    }

    private String findPopularBooks() {
        String items = null;
        try {
            items = webClient.get()
                    .uri(builder -> builder.path("/bestSeller.api")
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

    private InterparkResponseDto convertToResponse(String textData) {
        InterparkResponseDto interparkResponseDto = new InterparkResponseDto();
        try {
            interparkResponseDto = objectMapper.readValue(textData, InterparkResponseDto.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return interparkResponseDto;
    }
}

package com.example.itbooks.user.ui;

import com.example.itbooks.common.BaseControllerTest;
import com.example.itbooks.user.application.UserService;
import com.example.itbooks.user.dto.UserModificationData;
import com.example.itbooks.user.dto.UserRegistrationData;
import com.example.itbooks.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {
    private static final String MY_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final String OTHER_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjJ9.TEM6MULsZeqkBbUKziCR4Dg_8kymmZkyxsCXlfNJ3g0";
    private static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjEwMDR9.3GV5ZH3flBf0cnaXQCNNZlT4mgyFyBUhn3LKzQohh1A";

    private static Long GIVEN_ID = 1L;
    private static Long NOT_EXISTED_ID = -1L;
    private static String GIVEN_NAME = "user1";
    private static String GIVEN_EMAIL = "test@test.com";
    private static String GIVEN_PASSWORD = "test";
    private static String UPDATE_NAME = GIVEN_NAME + "_UPDATED";
    private static String UPDATE_EMAIL = GIVEN_EMAIL + "_UPDATED";

    @MockBean
    private UserService userService;

    @DisplayName("사용자의 정보를 등록합니다.")
    @Test
    void registerUserWithValidAttributes() throws Exception {
        UserRegistrationData dto = UserRegistrationData.builder()
                .name(GIVEN_NAME)
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .build();
        UserResponse userResponse = UserResponse
                .builder()
                .id(GIVEN_ID)
                .build();
        given(userService.registerUser(any(UserRegistrationData.class)))
                .willReturn(userResponse);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.
                                post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andDo(print())
                .andDo(document("create-user",
                        requestFields(
                                attributes(key("user").value("Fields for user creation")),
                                fieldWithPath("email").type(STRING).description("사용자 이메일")
                                        .attributes(key("constraints").value("최소 세 글자 이상 입력해야합니다.")),
                                fieldWithPath("name").type(STRING).description("사용자 이름")
                                        .attributes(key("constraints").value("한 글자 이상 입력해야합니다.")),
                                fieldWithPath("password").type(STRING).description("사용자 비밀번호")
                                        .attributes(key("constraints").value("비밀번호는 4 ~ 1024 글자 이내 입력해야합니다."))
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("사용자 식별자"),
                                fieldWithPath("email").type(STRING).ignored(),
                                fieldWithPath("name").type(STRING).ignored(),
                                fieldWithPath("role").type(OBJECT).ignored(),
                                fieldWithPath("auth").type(BOOLEAN).ignored(),
                                fieldWithPath("admin").type(BOOLEAN).ignored()
                        ))
                );

        verify(userService).registerUser(any(UserRegistrationData.class));
    }

    @DisplayName("사용자의 정보를 갱신합니다.")
    @Test
    void updateUserWithValidAttributes() throws Exception {
        UserModificationData updateDto = UserModificationData.builder()
                .name(UPDATE_NAME)
                .build();
        UserResponse userResponse = UserResponse.builder()
                .id(GIVEN_ID)
                .name(UPDATE_NAME)
                .email(GIVEN_EMAIL)
                .build();

        given(
                userService.updateUser(
                        eq(GIVEN_ID),
                        any(UserModificationData.class)
                )
        )
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserModificationData modificationData =
                            invocation.getArgument(1);
                    return UserResponse.builder()
                            .id(id)
                            .email(GIVEN_EMAIL)
                            .name(modificationData.getName())
                            .build();
                });

        mockMvc.perform(
                        RestDocumentationRequestBuilders.
                                patch("/api/v1/users/{id}", GIVEN_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(updateDto))
                                .header("Authorization", "Bearer " + MY_TOKEN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userResponse)))
                .andDo(print())
                .andDo(document("update-user",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        pathParameters(
                                parameterWithName("id").description("사용자 식별자")
                        ),
                        requestFields(
                                attributes(key("user").value("Fields for user creation")),
                                fieldWithPath("name").type(STRING).description("사용자 이름")
                                        .attributes(key("constraints").value("한 글자 이상 입력해야합니다."))
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("사용자 식별자"),
                                fieldWithPath("email").type(STRING).description("사용자 이메일"),
                                fieldWithPath("name").type(STRING).description("사용자 이름"),
                                fieldWithPath("role").type(OBJECT).ignored(),
                                fieldWithPath("auth").type(BOOLEAN).ignored(),
                                fieldWithPath("admin").type(BOOLEAN).ignored()
                        ))
                );

        verify(userService)
                .updateUser(eq(GIVEN_ID), any(UserModificationData.class));
    }

    @DisplayName("사용자의 정보를 삭제합니다.")
    @Test
    void destroyWithExistedId() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.
                                delete("/api/v1/users/{id}", GIVEN_ID)
                                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("delete-user",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        pathParameters(
                                parameterWithName("id").description("사용자 식별자")
                        )
                ));

        verify(userService).deleteUser(1L);
    }

    @DisplayName("로그인한 사용자 정보 가져오기")
    @Test
    void detailWithLoginUser() throws Exception {
        given(userService.getUser(GIVEN_ID))
                .willReturn(
                        UserResponse.builder()
                                .id(GIVEN_ID)
                                .email(GIVEN_EMAIL)
                                .name(GIVEN_NAME)
                                .isAuth(true)
                                .build()
                );
        given(authenticationService.parseToken(MY_TOKEN))
                .willReturn(GIVEN_ID);

        mockMvc
                .perform(
                        RestDocumentationRequestBuilders
                                .get("/api/v1/users/me")
                                .header("Authorization", "Bearer " + MY_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GIVEN_NAME)))
                .andExpect(content().string(containsString(GIVEN_EMAIL)))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(content().string(containsString(GIVEN_NAME)))
                .andDo(document("myinfo",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("사용자 식별자"),
                                fieldWithPath("name").type(STRING).description("사용자 이름"),
                                fieldWithPath("email").type(STRING).description("사용자 이메일"),
                                fieldWithPath("auth").type(BOOLEAN).description("인증 여부"),
                                fieldWithPath("role").type(OBJECT).ignored(),
                                fieldWithPath("admin").type(BOOLEAN).ignored()
                        ))
                );
    }
}


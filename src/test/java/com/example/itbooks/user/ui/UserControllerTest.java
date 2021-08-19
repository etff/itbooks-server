package com.example.itbooks.user.ui;

import com.example.itbooks.common.BaseControllerTest;
import com.example.itbooks.user.application.UserService;
import com.example.itbooks.user.dto.UserRegistrationData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTY0MDk2MjgwMCwiZXh" +
            "wIjoxNjQwOTYzMTAwfQ.2siRnBJmRU2JXjZY0CkQMgnCHRJN4Dld4_wG6R7T-HQ";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD0";

    private static Long GIVEN_ID = 1L;
    private static Long NOT_EXISTED_ID = -1L;
    private static String GIVEN_NAME = "user1";
    private static String GIVEN_EMAIL = "test@test.com";
    private static String GIVEN_PASSWORD = "test";
    private static String UPDATE_NAME = GIVEN_NAME + "_UPDATED";
    private static String UPDATE_EMAIL = GIVEN_EMAIL + "_UPDATED";

    @MockBean
    private UserService userService;

    @Test
    void registerUserWithValidAttributes() throws Exception {
        UserRegistrationData dto = UserRegistrationData.builder()
                .name(GIVEN_NAME)
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .build();
        given(userService.registerUser(any(UserRegistrationData.class))).willReturn(GIVEN_ID);

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
                                fieldWithPath("name").type(STRING).ignored()
                        ))
                );

        verify(userService).registerUser(any(UserRegistrationData.class));
    }
}


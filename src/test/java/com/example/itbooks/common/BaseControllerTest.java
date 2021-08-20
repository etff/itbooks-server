package com.example.itbooks.common;

import com.example.itbooks.auth.application.AuthenticationGuard;
import com.example.itbooks.auth.application.AuthenticationService;
import com.example.itbooks.user.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
@Import(RestDocConfiguration.class)
public class BaseControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean(name = "authenticationGuard")
    protected AuthenticationGuard authenticationGuard;

    @MockBean
    protected AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        given(authenticationService.roles(anyLong()))
                .willReturn(Collections.singletonList(new Role("USER")));
        given(authenticationGuard.checkIdMatch(any(Authentication.class), anyLong()))
                .willReturn(true);
    }

}

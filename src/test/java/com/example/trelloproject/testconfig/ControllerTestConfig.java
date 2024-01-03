package com.example.trelloproject.testconfig;

import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.entity.UserRoleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.security.Principal;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class ControllerTestConfig {

    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected UserDetails testUserDetails;
    protected Principal mockPrincipal;

    protected FieldDescriptor[] failResponseFields = {
            fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지"),
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("data").type(JsonFieldType.STRING).description("현재 날짜")
    };

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        mockUserSetup();
    }


    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "robbie";
        String password = "12341234";
        String email = "robbie@test.com";
        UserRoleEnum role = UserRoleEnum.USER;
        User testUser = User.builder().username(username).password(password).email(email).role(role).build();
        testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

}
package com.example.trelloproject.user.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.trelloproject.global.config.WebSecurityConfig;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.testconfig.ControllerTestConfig;
import com.example.trelloproject.user.dto.UserLoginDto;
import com.example.trelloproject.user.dto.UserSignupDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.entity.UserRoleEnum;
import com.example.trelloproject.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
        }
)
class UserControllerTest extends ControllerTestConfig {

    private static final String DEFAULT_URL = "/v1/users";

    private Principal mockPrincipal;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void singupSuccessTest() throws Exception {

        // given
        UserSignupDto userSignupDto = new UserSignupDto();
        userSignupDto.setUsername("usertest1");
        userSignupDto.setPassword("testpassword");
        userSignupDto.setEmail("test@email.com");

        String requestDto = objectMapper.writeValueAsString(userSignupDto);

        //when - then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post(DEFAULT_URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestDto)
        ).andDo(
                MockMvcRestDocumentationWrapper.document("201_성공",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("유저 로직")
                                        .description("회원가입")
                                        .requestFields(
                                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 이름").attributes(new Attributes.Attribute("length", "4자 이상 10자 이하")),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                                fieldWithPath("admin").type(JsonFieldType.BOOLEAN).description("어드민 가입 여부, 어드민 페이지만 사용 가능").optional()
                                        )
                                        .responseFields()
                                        .build()
                        )
                )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("회원가입 실패")
    void singupFailTest() throws Exception {

        // given
        UserSignupDto userSignupDto = new UserSignupDto();
        userSignupDto.setUsername("usertest1234"); //초과
        userSignupDto.setPassword("testpassword");
        userSignupDto.setEmail("test@email.com");

        String requestDto = objectMapper.writeValueAsString(userSignupDto);

        //when - then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post(DEFAULT_URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestDto)
        )
        .andDo(
                MockMvcRestDocumentationWrapper.document("400_실패-유효성 검사 실패",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("유저 로직")
                                        .description("회원가입 실패")
                                        .requestFields(
                                                fieldWithPath("username").type(JsonFieldType.STRING).description("계정 이름"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                                fieldWithPath("admin").type(JsonFieldType.BOOLEAN).description("어드민 가입 여부, 어드민 페이지만 사용 가능").optional()
                                        )
                                        .responseFields(failResponseFields)
                                        .build()
                        )
                )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
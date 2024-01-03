package com.example.trelloproject.board.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.trelloproject.board.dto.BoardColumnCardResponseDto;
import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.testconfig.ControllerTestConfig;
import com.example.trelloproject.user.entity.User;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@WebMvcTest(value = BoardController.class)
@ContextConfiguration(classes = SecurityConfig.class)
class BoardControllerTest extends ControllerTestConfig {

    private static final String DEFAULT_URL = "/v1/boards";

    @MockBean
    BoardService boardService;

    private FieldDescriptor[] boardFields = {
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("번호").optional(),
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목").optional(),
            fieldWithPath("content").type(JsonFieldType.STRING).description("내용").optional(),
            fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("색상").optional(),
            fieldWithPath("user").type(JsonFieldType.STRING).description("생성 유저").optional(),
            fieldWithPath("columns").type(JsonFieldType.ARRAY).description("컬럼 목록").optional(),
            fieldWithPath("invitedUsers").type(JsonFieldType.ARRAY).description("소속된 유저 목록").optional(),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일시").optional(),
            fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일시").optional()
    };

    @Test
    @DisplayName("보드 생성 성공")
    void createBoardSuccessTest() throws Exception {
        // given
        BoardRequestDto requestDto = new BoardRequestDto("title", "contents");
        given(boardService.createBoard(any(BoardRequestDto.class), any(User.class)))
                .willReturn(Board.builder().title("title").build());


        //when - then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post(DEFAULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andDo(
                MockMvcRestDocumentationWrapper.document("200_성공1",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("보드 로직")
                                        .description("생성")
                                        .requestFields(
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                        )
                                        .responseFields(boardFields)
                                        .build()
                        )
                )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("보드 조회")
    void getBoardSuccessTest() throws Exception {
        // given
//        given(boardService.getBoard(anyLong()))
//                .willReturn(new BoardColumnCardResponseDto(new Board()));
        when(boardService.getBoard(anyLong()))
                .thenReturn(new BoardColumnCardResponseDto(new Board()) );



        //when - then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{boardId}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcRestDocumentationWrapper.document("200_성공2",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("보드 로직")
                                        .description("조회")
                                        .requestFields()
                                        .responseFields(boardFields)
                                        .build()
                        )
                )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("보드 선택 조회")
    void getBoardMyBoardSuccessTest() throws Exception {
        // given
        List<Board> boards = new ArrayList<>();
        boards.add(new Board());
        given(boardService.getMyBoards(new User()))
                .willReturn(new ArrayList<>());

        //when - then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(DEFAULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(
                MockMvcRestDocumentationWrapper.document("200_성공3",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("보드 로직")
                                        .description("전체 조회")
                                        .requestFields()
                                        .responseFields(
                                                fieldWithPath("data.[]").type(JsonFieldType.ARRAY).description("board 목록").optional(),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지").optional(),
                                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드").optional()
                                        )
                                        .build()
                        )
                )
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
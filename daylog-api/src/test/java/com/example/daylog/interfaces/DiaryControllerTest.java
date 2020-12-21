package com.example.daylog.interfaces;

import com.example.daylog.application.DiaryService;
import com.example.daylog.domain.Diary;
import com.example.daylog.domain.DiaryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(DiaryController.class)
public class DiaryControllerTest {

    @Autowired // 직접 만들지 않아도 스프링에서 알아서 넣어줌
    protected MockMvc mvc;

    @MockBean
    private DiaryService diaryService;

    @Test
    public void list() throws Exception {
        List<Diary> diaries = new ArrayList<>();
        diaries.add(Diary.builder()
                .id(1004L)
                .weather("sunny")
                .food("noodle")
                .mood("awesome")
                .title("nice day with Steve")
                .build());
        given(diaryService.getDiaries()).willReturn(diaries);

        mvc.perform(MockMvcRequestBuilders.get("/diary"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"weather\":\"sunny\"")
                )).andExpect(content().string(
                        containsString("\"food\":\"noodle\"")
                )).andExpect(content().string(
                        containsString("\"mood\":\"awesome\"")
                )).andExpect(content().string(
                        containsString("\"title\":\"nice day with Steve\"")
                ));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Diary diary = Diary.builder()
                .id(1004L)
                .date("2020-12-20")
                .photo("sunny.jpg")
                .title("nice day with Steve")
                .content("nice nice gooooood sunny day with Steve")
                .build();

        given(diaryService.getDiaryById(1004L)).willReturn(diary);

        mvc.perform(MockMvcRequestBuilders.get("/diary/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                )).andExpect(content().string(
                        containsString("\"date\":\"2020-12-20\"")
                )).andExpect(content().string(
                        containsString("\"photo\":\"sunny.jpg\"")
                )).andExpect(content().string(
                        containsString("\"title\":\"nice day with Steve\"")
                )).andExpect(content().string(
                        containsString("\"content\":\"nice nice gooooood sunny day with Steve\"")
                ));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(diaryService.getDiaryById(404L))
                .willThrow(new DiaryNotFoundException(404L));
        mvc.perform(MockMvcRequestBuilders.get("/diary/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"message\":\"Could not find diary\"}"));
    }

    @Test
    public void createWithValidData() throws Exception {
        Diary diary = Diary.builder()
                .id(1234L)
                .date("2020-12-21")
                .weather("sunny")
                .mood("happy")
                .food("pasta")
                .keyword("nice!!!")
                .content("nice day with Steve")
                .title("good day")
                .build();
        mvc.perform(MockMvcRequestBuilders.post("/diary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2020-12-21\", \"weather\":\"sunny\", \"mood\":\"happy\", \"food\":\"pasta\", \"keyword\":\"nice!!!\", \"content\":\"nice day with Steve\", \"title\":\"good day\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/diary"))
                .andExpect(content().string("{\"message\":\"created\"}"));

        verify(diaryService).addDiary(ArgumentMatchers.any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/diary")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
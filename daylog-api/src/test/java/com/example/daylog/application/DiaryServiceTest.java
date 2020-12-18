package com.example.daylog.application;

import com.example.daylog.domain.Diary;
import com.example.daylog.domain.DiaryNotFoundException;
import com.example.daylog.domain.DiaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class DiaryServiceTest {

    private DiaryService diaryService;

    @Mock
    private DiaryRepository diaryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockDiaryRepository();
        diaryService = new DiaryService(diaryRepository);
    }

    private void MockDiaryRepository() {
        List<Diary> diaries = new ArrayList<>();
        Diary diary = Diary.builder()
                .id(1004L)
                .title("nice day")
                .weather("sunny")
                .food("pasta")
                .mood("happy")
                .content("nice weather with my favorite food!")
                .build();
        diaries.add(diary);
        // getDiaries
        given(diaryRepository.findAll()).willReturn(diaries);
        // getDiaryById
        given(diaryRepository.findById(1004L)).willReturn(java.util.Optional.of(diary));
        // addDiary
        given(diaryRepository.save(any())).willReturn(diary);
        // getTodaysDiaryByDate
        given(diaryRepository.findDiaryByDate("2020년 12월 18일")).willReturn(diary);
    }

    @Test
    public void getDiaryWithExisted() {
        Diary diary = diaryService.getDiaryById(1004L);

        assertThat(diary.getId(), is(1004L));
    }

    @Test
    public void getDiaryWithNotExisted() {
        assertThrows(DiaryNotFoundException.class, () -> diaryService.getDiaryById(404L));
    }

    @Test
    public void getDiaries() {
        List<Diary> diaries = diaryService.getDiaries();
        Diary diary = diaries.get(0);
        assertThat(diary.getId(), is(1004L));
    }

    @Test
    public void addDiary() {
        given(diaryRepository.save(any())).will(invocation -> {
            Diary diary = invocation.getArgument(0);
            diary.setId(1234L);
            return diary;
        });

        Diary diary = Diary.builder()
                .title("goood day")
                .weather("cloudy")
                .food("buffet")
                .mood("awesome")
                .content("awesome hotel buffet's food")
                .build();

        Diary created = diaryService.addDiary(diary);
        assertThat(created.getId(), is(1234L));
    }
}
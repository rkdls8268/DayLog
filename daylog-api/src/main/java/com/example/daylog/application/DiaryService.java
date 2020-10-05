package com.example.daylog.application;

import com.example.daylog.domain.Diary;
import com.example.daylog.domain.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {

    private DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public List<Diary> getDiaries() {
        List<Diary> diaries = diaryRepository.findAll();
        return diaries;
    }
}

package com.example.daylog.application;

import com.example.daylog.domain.Diary;
import com.example.daylog.domain.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Diary addDiary(Diary diary) {
        Diary saved = diaryRepository.save(diary);
        // save() 는 미리 만들어져있음!
        return saved;
    }

    public Diary getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id).orElse(null);
        return diary;
    }
}

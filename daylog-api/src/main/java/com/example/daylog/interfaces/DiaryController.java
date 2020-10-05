package com.example.daylog.interfaces;

import com.example.daylog.domain.Diary;
import com.example.daylog.application.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin
@RestController
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/diaries")
    public List<Diary> list() {
        List<Diary> diaries = diaryService.getDiaries();
        return diaries;
    }
}

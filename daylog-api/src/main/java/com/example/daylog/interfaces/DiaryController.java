package com.example.daylog.interfaces;

import com.example.daylog.domain.Diary;
import com.example.daylog.application.DiaryService;
import com.example.daylog.domain.DiaryAlreadyExistedException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
public class DiaryController {

    private static Logger logger = LoggerFactory.getLogger(DiaryController.class);

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/diary")
    public List<Diary> list() {
        List<Diary> diaries = diaryService.getDiaries();
//        JSONObject jsonDiaries = new JSONObject();
//        JSONObject data = new JSONObject();
//        List<JSONObject> arr = new ArrayList<>();
//        List<JSONObject> list = new ArrayList<>();
//        // TODO: 다이어리에 있는 내용들 리스트에 저장
//        for (Diary d : diaries) {
//            JSONObject.
//        }
//
//        // todo: 리스트에 있는 값들을 JSONArray에 저장
//        for(Diary d : diaries) {
//            data.put("id", d.getId());
//            logger.info(d.getId().toString());
//            data.put("date", d.getDate());
//            data.put("photo", d.getPhoto());
//            data.put("content", d.getContent());
//            logger.info(data.toJSONString());
//            arr.add(data);
//            // data.put("title", d.getTitle());
//            logger.info(arr.toString());
//        }
////        arr.add(data);
//        jsonDiaries.put("data", arr);
        return diaries;
    }

    @GetMapping("/diary/{id}")
    public JSONObject detail(
            @PathVariable("id") Long id
    ) {
        Diary diary = diaryService.getDiaryById(id);
        JSONObject jsonDiary = new JSONObject();
        jsonDiary.put("id", diary.getId());
        jsonDiary.put("title", diary.getTitle());
        jsonDiary.put("date", diary.getDate());
        jsonDiary.put("photo", diary.getPhoto());
        jsonDiary.put("content", diary.getContent());
        return jsonDiary;
    }

    @PostMapping("/diary")
    public ResponseEntity<?> create(
            @RequestBody Diary resource
    ) throws URISyntaxException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");
        String title = resource.getTitle();
        String date = format1.format(System.currentTimeMillis());
        String weather = resource.getWeather();
        String mood = resource.getMood();
        String food = resource.getFood();
        String keyword = resource.getKeyword();
        String content = resource.getContent();

        Diary today = diaryService.getTodaysDiaryByDate(date);
        if (today != null) {
            throw new DiaryAlreadyExistedException(resource.getId());
        } else {
            Diary diary = Diary.builder()
                    .title(title)
                    .date(date)
                    .weather(weather)
                    .mood(mood)
                    .food(food)
                    .keyword(keyword)
                    .content(content)
                    .build();

            diaryService.addDiary(diary);

            String url = "/diary";

            return ResponseEntity.created(new URI(url)).body("{\"message\":\"created\"}");
        }
    }
}

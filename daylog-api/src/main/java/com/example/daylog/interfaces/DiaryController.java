package com.example.daylog.interfaces;

import com.example.daylog.domain.Diary;
import com.example.daylog.application.DiaryService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

//@CrossOrigin
@RestController
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/diary")
    public JSONObject list() {
        List<Diary> diaries = diaryService.getDiaries();
        JSONObject jsonDiaries = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        for(Diary d : diaries) {
            data.put("id", d.getId());
            data.put("date", d.getDate());
            data.put("photo", d.getPhoto());
            data.put("content", d.getContent());
            arr.add(data);
            jsonDiaries.put("data", arr);
        }
        return jsonDiaries;
    }

    @PostMapping("/diary")
    public ResponseEntity<?> create(
            @RequestBody Diary resource
    ) throws URISyntaxException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        String title = resource.getTitle();
        String date = format1.format(System.currentTimeMillis());
        String weather = resource.getWeather();
        String mood = resource.getMood();
        String food = resource.getFood();
        String keyword = resource.getKeyword();
        String content = resource.getContent();

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

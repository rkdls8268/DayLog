package com.example.daylog.interfaces;

import com.example.daylog.domain.DiaryAlreadyExistedException;
import com.example.daylog.domain.DiaryNotFoundException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DiaryErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DiaryNotFoundException.class)
    public JSONObject handleNotFound() {
        // 404, not found
//        "message":""
        JSONObject obj = new JSONObject();
        obj.put("message", "Could not find diary");
        return obj;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DiaryAlreadyExistedException.class)
    public JSONObject handleAlreadyExisted() {
        JSONObject obj = new JSONObject();
        obj.put("message", "Already Existed");
        return obj;
    }
}

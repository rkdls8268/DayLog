package com.example.daylog.domain;

public class DiaryAlreadyExistedException extends RuntimeException{

    public DiaryAlreadyExistedException(Long id) {
        super(id + " diary already existed.");
    }
}

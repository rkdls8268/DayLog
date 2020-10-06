package com.example.daylog.domain;

public class DiaryNotFoundException extends RuntimeException{

    public DiaryNotFoundException(Long id) {
        super("Could not find diary " + id);
    }
}

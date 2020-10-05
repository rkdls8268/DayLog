package com.example.daylog.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiaryRepository extends CrudRepository<Diary, Long> {
    List<Diary> findAll();
}

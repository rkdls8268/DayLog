package com.example.daylog.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends CrudRepository<Diary, Long> {
    List<Diary> findAll();

//    @Override
    Optional<Diary> findById(Long id);

    Diary findDiaryByDate(String date);
}

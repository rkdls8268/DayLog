package com.example.daylog.domain;

//import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotEmpty
    private String date;

    private String weather;

    private String mood;

    private String food;

    private String keyword;

    private String photo;

    private String content;
}

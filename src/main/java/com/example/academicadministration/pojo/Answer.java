package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String stuid;
    private int homeworkid;
    private int score;
    private byte[] answerfile;
    private Timestamp submittime;
}

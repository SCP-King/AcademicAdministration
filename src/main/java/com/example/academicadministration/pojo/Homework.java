package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework {
    private int homeworkid;
    private int courseid;
    private Date startline;
    private Date endline;
    private String homeworkrequest;
    private byte[] homeworkfile;

}
package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int courseid;
    private String coursename;
    private int coursenumber;
    private int courselimit;
    private String courseday;
    private String coursetime;
    private String courseroom;
    private String coursekind;
    private Teacher teacher;
}

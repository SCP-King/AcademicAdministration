package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String stuid;
    private String stupwd;
    private String stuname;
    private String stusex;
    private String sturace;
    private String stuorigin;
    private String stuenrollment;
    private String stucollege;
    private String stumajor;
    private String stuclass;
    private String stuphone;
    private byte[] stuphoto;
    private Boolean stustate;
    private Answer myAnswer;

}

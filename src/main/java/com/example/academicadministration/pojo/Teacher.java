package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String teaid;
    private String teapwd;
    private String teaname;
    private  String teasex;
    private String tearace;
    private String teaorigin;
    private  String teacollege;
    private String teapost;
    private String teagraduation;
    private String teadegree;
    private String teaphone;
    private byte[] teaphoto;
    private Boolean teastate;
}

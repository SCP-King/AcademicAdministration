package com.example.academicadministration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private int videoid;
    private int courseid;
    private String videoname;
    private String videointroducation;
    private String videofile;
    private byte[] videocover;
    private LocalDate videotime;

}

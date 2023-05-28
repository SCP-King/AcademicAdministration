package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkDao {
    int addHomework(Homework homework);
    int deleteHomework(String homeworkid);
    List<Homework> browseHomeworkAll(String courseid);
    List<Homework> browseHomework1(String courseid);
    List<Homework> browseHomework2(String courseid);

}

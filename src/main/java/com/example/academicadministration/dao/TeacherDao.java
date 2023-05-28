package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherDao {
    int addTeacher(Teacher teacher);
    int deleteTeacher(String teaid);
    int auditTeacher(String teaid);
    int inauditTeacher(String teaid);
    int reset(@Param("teaid") String teaid,@Param("teapwd") String teapwd,@Param("newpwd") String newpwd);
    List<Teacher> browseTeacher(String condition);
}

package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentDao {
    int addStudent(Student student);
    int deleteStudent(String stuid);
    int auditStudent(String stuid);
    int inauditStudent(String stuid);
    int reset(@Param("stuid") String stuid,@Param("stupwd") String stupwd,@Param("newpwd") String newpwd);
    List<Student> myStudent(String courseid);
    List<Student> browseStudent(String condition);


}

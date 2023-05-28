package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseDao {
    int addCourse(Course course);
    int deleteCourse(String courseid);
    int addTeacherCourse(@Param("teaid") String teaid,@Param("courseid") String courseid);
    int deleteTeacherCourse(@Param("teaid") String teaid,@Param("courseid") String courseid);
    int addStudentCourse(@Param("stuid") String stuid,@Param("courseid") String courseid);
    int addNum(String courseid);
    int deleteStudentCourse(@Param("stuid") String stuid,@Param("courseid") String courseid);
    int deleteNum(String courseid);
    String browseScore(@Param("stuid") String stuid,@Param("courseid") String courseid);
    int updateScore(@Param("coursescore") String coursescore,@Param("stuid") String stuid,@Param("courseid") String courseid);
    List<Course> myCourse(String stuid);
    List<Course> TmyCourse(String teaid);
    List<Course> browseCourse(String condition);
    List<String> checkTeacher(@Param("courseid") String courseid,@Param("teaid") String teaid);
}

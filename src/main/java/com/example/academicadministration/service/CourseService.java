package com.example.academicadministration.service;

import com.example.academicadministration.dao.CourseDao;
import com.example.academicadministration.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;
    public Boolean addCourse(Course course) {
        return courseDao.addCourse(course)>0;
    }

    public Boolean deleteCourse(String courseid) {


        return courseDao.deleteCourse(courseid)>0;
    }

    public List<Course> browseCourse(String condition) {
        return courseDao.browseCourse(condition);
    }
    public List<Course> myCourse(String stuid){
        return courseDao.myCourse(stuid);
    }
    public List<Course> TmyCourse(String teaid){
        return courseDao.TmyCourse(teaid);
    }
    public Boolean checkTeacher(String courseid,String teaid){return !courseDao.checkTeacher(courseid,teaid).isEmpty();}
    public Boolean addTeacherCourse(String teaid,String courseid){
        return courseDao.addTeacherCourse(teaid,courseid)>0;
    }
    public Boolean deleteTeacherCourse(String teaid,String courseid){
        return courseDao.deleteTeacherCourse(teaid,courseid)>0;
    }
    public Boolean addStudentCourse(String stuid,String courseid){
        return courseDao.addStudentCourse(stuid,courseid)>0;
    }
    public Boolean deleteStudentCourse(String stuid,String courseid){
        return courseDao.deleteStudentCourse(stuid,courseid)>0;
    }
    public Boolean addNum(String courseid){
        return courseDao.addNum(courseid)>0;
    }
    public Boolean deleteNum(String courseid){
        return courseDao.deleteNum(courseid)>0;
    }
    public String browseScore(String stuid,String courseid){
        return courseDao.browseScore(stuid,courseid);
    }
    public Boolean updateScore(String coursescore,String stuid,String courseid){
        return courseDao.updateScore(coursescore,stuid,courseid)>0;
    }
}

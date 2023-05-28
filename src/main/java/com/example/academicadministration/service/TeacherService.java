package com.example.academicadministration.service;

import com.example.academicadministration.dao.TeacherDao;
import com.example.academicadministration.pojo.Course;
import com.example.academicadministration.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private CourseService courseService;
    public Boolean addTeacher(Teacher teacher){
        return teacherDao.addTeacher(teacher)>0;
    }
    public Boolean deleteTeacher(String teaid){
        List< Course> courses=courseService.TmyCourse(teaid);
        for(Course i:courses){
            courseService.deleteTeacherCourse(teaid, String.valueOf(i.getCourseid()));
        }
        return teacherDao.deleteTeacher(teaid)>0;
    }
    public Boolean  auditTeacher(String teaid){
        return teacherDao.auditTeacher(teaid)>0;
    }
    public Boolean inauditTeacher(String teaid){
        return teacherDao.inauditTeacher(teaid)>0;
    }
    public Boolean reset(String teaid,String teapwd,String newpwd){
        return teacherDao.reset(teaid,teapwd,newpwd)>0;
    }
    public List<Teacher> browseTeacher(String condition){
        return teacherDao.browseTeacher(condition);
    }

}

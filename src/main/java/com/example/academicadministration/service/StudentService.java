package com.example.academicadministration.service;

import com.example.academicadministration.dao.StudentDao;
import com.example.academicadministration.pojo.Course;
import com.example.academicadministration.pojo.Homework;
import com.example.academicadministration.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private AnswerService answerService;
    public Boolean addStudent(Student student){
        return studentDao.addStudent(student)>0;
    }
    public Boolean deleteStudent(String stuid){
        List<Course> courses=courseService.myCourse(stuid);
        for (Course i:courses){
            courseService.deleteNum(String.valueOf(i.getCourseid()));
            courseService.deleteStudentCourse(stuid, String.valueOf(i.getCourseid()));
            List<Homework> homework=homeworkService.browseHomeworkAll(String.valueOf(i.getCourseid()));
            for (Homework j:homework){
                answerService.deleteAnswer(stuid, String.valueOf(j.getHomeworkid()));
            }
        }
        return studentDao.deleteStudent(stuid)>0;
    }
    public Boolean auditStudent(String stuid){
        return studentDao.auditStudent(stuid)>0;
    }
    public Boolean  inauditStudent(String stuid){
        return studentDao.inauditStudent(stuid)>0;
    }
    public Boolean reset(String stuid,String stupwd,String newpwd){
        return studentDao.reset(stuid,stupwd,newpwd)>0;
    }
    public  List<Student> browseStudent(String condition){
        return studentDao.browseStudent(condition);
    }
    public List<Student> myStudent(String courseid){
        return studentDao.myStudent(courseid);
    }
}

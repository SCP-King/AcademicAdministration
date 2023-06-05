package com.example.academicadministration.controller;

import com.example.academicadministration.pojo.*;
import com.example.academicadministration.service.*;
import com.example.academicadministration.utils.SendMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    private int hid;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private AnswerService answerService;
    private Boolean check(Object o){
        return o==null||o.equals("");
    }
    @SneakyThrows
    private String getImg(byte[] im){
        BufferedImage img= ImageIO.read(new ByteArrayInputStream(im));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img,"png",baos);
        byte[] encodedBytes = Base64.getEncoder().encode(baos.toByteArray());
        String encodedImage = new String(encodedBytes);
        String imgSrc="data:image/png;base64,"+encodedImage;
        return imgSrc;
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/login")
    public String login(String id, String pwd, HttpServletRequest request){
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pwd.getBytes());
        String Hpwd=new BigInteger(1,md.digest()).toString(16);
        List<Student> students=studentService.browseStudent("%%");
        for(Student i:students){
            if(i.getStuid().equals(id)){
                if(i.getStupwd().equals(Hpwd)){
                    if (i.getStustate()){
                        i.setStupwd(pwd);
                        request.getSession().setAttribute("StudentPerson",i);
                        if(i.getStuphoto()!=null&&i.getStuphoto().length>0){
                            request.getSession().setAttribute("StudentPersonImg",getImg(i.getStuphoto()));
                        }
                        else request.getSession().setAttribute("StudentPersonImg",null);
                        return "登录成功";
                    }
                    return "账号未激活";
                }
                return "密码错误";
            }
        }
        return "没有该用户";
    }
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "student/studentRegister";
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/register")
    public String register(Student student,MultipartFile photo){
        if(photo!=null&&!photo.isEmpty())
        student.setStuphoto(photo.getBytes());
        else student.setStuphoto(null);
        if(studentService.addStudent(student)){
           return "注册成功";
        }
        else{
            return "注册失败";
        }
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/getFlag")
    public String getFlag(String phone){
        String flag= SendMessage.message(phone);
        return flag;
    }
    @RequestMapping("/function")
    public String function(){
        return "/student/studentFunction";
    }
    @RequestMapping("/toChangePwd")
    public String toChangePwd(){
        return "/student/changePwd";
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/changePwd")
    public String changePwd(String oldpwd,String newpwd1,HttpServletRequest request){
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(oldpwd.getBytes());
        String Hpwd=new BigInteger(1,md.digest()).toString(16);
        if(check(newpwd1)){
            return "密码不能为空字符";
        }
        Student t=(Student) request.getSession().getAttribute("StudentPerson");
        if(studentService.reset(t.getStuid(),Hpwd,newpwd1)) {
            return "修改成功";
        }
        else {
            return "密码错误";
        }
    }
    @RequestMapping("/detail")
    public String detail(Model model,HttpServletRequest request){
        Student i=(Student)request.getSession().getAttribute("StudentPerson");
        model.addAttribute("stu",i);
        if(i.getStuphoto()!=null&&i.getStuphoto().length>0)
            model.addAttribute("Img",getImg(i.getStuphoto()));
        else model.addAttribute("Img",null);
        return "/student/studentDetail";
    }
    @SneakyThrows
    @RequestMapping("/browseCourse")
    public String browseCourse(String condition,String teaCondition,Model model){
        Boolean flag=check(teaCondition)? false:true;
        if(!check(condition)) condition="%"+condition+"%";
        else condition="%%";
        if(!check(teaCondition)) teaCondition="%"+teaCondition+"%";
        else teaCondition="%%";
        List<Course> courses=courseService.browseCourse(condition);
        List<Teacher> teachers=teacherService.browseTeacher("%%");
        List<Course> courseList=new ArrayList<>();
        for (Course i:courses){
            for(Teacher j:teachers){
                if(courseService.checkTeacher(String.valueOf(i.getCourseid()),j.getTeaid())){
                    i.setTeacher(j);
                }
            }
        }
        if(flag){
            List<Teacher> teachers1=teacherService.browseTeacher(teaCondition);
            for(Course i:courses){
                if(teachers1.contains(i.getTeacher())){
                    courseList.add(i);
                }
            }
            model.addAttribute("courseList",courseList);
        }
        else {
            model.addAttribute("courseList",courses);
        }
        model.addAttribute("teachers",teachers);
        return "/student/browseCourse";
    }
    @SneakyThrows
    @RequestMapping("/addStudentCourse")
    public void addStudentCourse(String courseid,HttpServletResponse response,HttpServletRequest request){
        Student i=(Student)request.getSession().getAttribute("StudentPerson");
        List<Course> courses=courseService.myCourse(i.getStuid());
        for(Course j:courses){
            if(String.valueOf(j.getCourseid()).equals(courseid)){
                response.getWriter().print("<script>alert('已有该课程');location.replace('/student/browseCourse')</script>");
                return;
            }
        }
        if(courseService.addStudentCourse(i.getStuid(),courseid)&&courseService.addNum(courseid)){
            response.getWriter().print("<script>alert('选课成功');location.replace('/student/browseCourse')</script>");
        }
        else {
            response.getWriter().print("<script>alert('选课失败');location.replace('/student/browseCourse')</script>");
        }

    }
    @RequestMapping("/myCourse")
    public String myCourse(Model model,HttpServletRequest request){
        Student i=(Student)request.getSession().getAttribute("StudentPerson");
        List<Course> courses=courseService.myCourse(i.getStuid());
        List<Teacher> teachers=teacherService.browseTeacher("%%");
       Map<Integer,String> score=new HashMap<>();
        for(Course j:courses) {
            String sc=courseService.browseScore(i.getStuid(), String.valueOf(j.getCourseid()));
            if(sc.equals("-1")){
                score.put(j.getCourseid(),null);
            }
            else score.put(j.getCourseid(), sc);
            for (Teacher k : teachers) {
                if (courseService.checkTeacher(String.valueOf(j.getCourseid()), k.getTeaid())) {
                    j.setTeacher(k);
                }
            }
        }
        model.addAttribute("courseList",courses);
        model.addAttribute("score",score);
        return "/student/myCourse";
    }
    @SneakyThrows
    @RequestMapping("/deleteStudentCourse")
    public void deleteSelection(String courseid,HttpServletResponse response,HttpServletRequest request){
        Student i=(Student)request.getSession().getAttribute("StudentPerson");
        if(courseService.deleteStudentCourse(i.getStuid(),courseid)&&courseService.deleteNum(courseid)){
            response.getWriter().print("<script>alert('退课成功');location.replace('/student/myCourse')</script>");
        }
        else {
            response.getWriter().print("<script>alert('退课失败');location.replace('/student/myCourse')</script>");
        }
    }

    @SneakyThrows
    @RequestMapping("/browseVideo")
    public String browseMaterial(String courseid,String condition,Model model,HttpServletRequest request){
        if(courseid==null) courseid= (String) request.getSession().getAttribute("courseid");
        else request.getSession().setAttribute("courseid",courseid);
        if(condition==null) condition="%%";
        else condition="%"+condition+"%";
        List<Video> videoList=videoService.browseVideo(condition);
        Map<Integer,String> videoImg=new HashMap<>();
        for(Video i:videoList){
            videoImg.put(i.getVideoid(),getImg(i.getVideocover()));
        }
        model.addAttribute("videoList",videoList);
        model.addAttribute("videoImg",videoImg);
        return "/student/browseVideo";
    }

    @SneakyThrows
    @RequestMapping("/browseHomework")
    public String browseHomework(String courseid,String kind,Model model,HttpServletRequest request){
        if(courseid==null) courseid=(String) request.getSession().getAttribute("courseid");
        else request.getSession().setAttribute("courseid",courseid);
        List<Homework> homeworkList=new ArrayList<>();
        if(check(kind)||kind.equals("0")){
            homeworkList=homeworkService.browseHomeworkAll(courseid);
        }
        else if(kind.equals("1")){
            homeworkList=homeworkService.browseHomework1(courseid);
        }
        else if(kind.equals("2")){
            homeworkList=homeworkService.browseHomework2(courseid);
        }

        model.addAttribute("homeworkList",homeworkList);
        return "/student/browseHomework";
    }
    @SneakyThrows
    @RequestMapping("/homeworkDetail")
    public String homeworkDetail(String homeworkid,String index,HttpServletRequest request,Model model){
        if(homeworkid==null) homeworkid=(String) request.getSession().getAttribute("homeworkid");
        else request.getSession().setAttribute("homeworkid",homeworkid);
        model.addAttribute("index",index);
        String courseid=(String) request.getSession().getAttribute("courseid");
        Student stu=(Student)request.getSession().getAttribute("StudentPerson");
        String stuid=stu.getStuid();
        List<Homework> homeworkList=homeworkService.browseHomeworkAll(courseid);
        for(Homework i:homeworkList){
            if(homeworkid.equals(String.valueOf(i.getHomeworkid()))){
                model.addAttribute("homework",i);
                model.addAttribute("homeworkImg",getImg(i.getHomeworkfile()));
                break;
            }
        }
       Answer answer=answerService.myAnswer(stuid,homeworkid);
        model.addAttribute("answer",answer);
        if (answer!=null)
        model.addAttribute("answerImg",getImg(answer.getAnswerfile()));

        return "/student/homeworkDetail";
    }
    @ResponseBody
    @SneakyThrows
    @RequestMapping("/addAnswer")
    public String addAnswer(Answer answer,MultipartFile answerImg,HttpServletRequest request){
        String homeworkid=(String) request.getSession().getAttribute("homeworkid");
        answer.setAnswerfile(answerImg.getBytes());
        Student stu=(Student)request.getSession().getAttribute("StudentPerson");
        String stuid=stu.getStuid();
        answer.setStuid(stuid);
        answer.setHomeworkid(Integer.valueOf(homeworkid));
        answer.setAnswerfile(answerImg.getBytes());
        answer.setScore(-1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        answer.setSubmittime(timestamp);
        if(answerService.addAnswer(answer)){
           return "上传成功";
        }
        else {
            return "上传失败";
        }
    }
    @ResponseBody
    @SneakyThrows
    @RequestMapping("/updateAnswer")
    public String updateAnswer(Answer answer,MultipartFile answerImg,HttpServletRequest request){
        String homeworkid=(String) request.getSession().getAttribute("homeworkid");
        answer.setAnswerfile(answerImg.getBytes());
        Student stu=(Student)request.getSession().getAttribute("StudentPerson");
        String stuid=stu.getStuid();
        answer.setStuid(stuid);
        answer.setHomeworkid(Integer.valueOf(homeworkid));
        answer.setAnswerfile(answerImg.getBytes());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        answer.setSubmittime(timestamp);
        if(answerService.updateAnswer(answer)){
            return "上传成功";
        }
        else {
            return "上传失败";
        }
    }
}


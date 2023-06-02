package com.example.academicadministration.controller;

import com.example.academicadministration.pojo.Course;
import com.example.academicadministration.pojo.Student;
import com.example.academicadministration.pojo.Teacher;
import com.example.academicadministration.service.CourseService;
import com.example.academicadministration.service.MangerService;
import com.example.academicadministration.service.StudentService;
import com.example.academicadministration.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Controller
@RequestMapping("/manger")
public class MangerController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MangerService mangerService;
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
    public String login(String id, String pwd,HttpServletRequest request){
        String res=mangerService.login(id);
        if(res==null){
            return "用户不存在";
        }
        else if(!res.equals(pwd)){
            return "密码错误";
        }
        else {
            request.getSession().setAttribute("mangerid",id);
            return "登录成功";}
    }
    @RequestMapping("/function")
    public String function(Model model){
        return "/manger/mangerFunction";
    }
    @RequestMapping("/toChangePwd")
    public String toChangePwd(){
        return "/manger/changePwd";
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/changePwd")
    public String changePwd(String oldpwd, String newpwd1, HttpServletRequest request){
        if(check(newpwd1)){
            return "密码不能为空字符";
        }
        if(mangerService.reset((String) request.getSession().getAttribute("mangerid"),oldpwd,newpwd1)) {
            return "修改成功";
        }
        else {
            return "密码错误";
        }
    }

    @SneakyThrows
    @RequestMapping("/browseStudent")
    public String browseStudent(Model model,String condition){
       if(!check(condition)) condition="%"+condition+"%";
       else condition="%%";
       List<Student> studentList=studentService.browseStudent(condition);
       Map<String,String> stuImg=new HashMap<>();
       for(Student i:studentList){
           if(i.getStuphoto()==null||i.getStuphoto().length<1){
               stuImg.put(i.getStuid(),null);
               continue;
           }
           stuImg.put(i.getStuid(),getImg(i.getStuphoto()));
       }

       model.addAttribute("stuList",studentList);
       model.addAttribute("stuImg",stuImg);
       return "/manger/browseStudent";
    }
    @SneakyThrows
    @RequestMapping("/auditStudent")
    public void auditStudent(String stuid,HttpServletResponse response){
        if(studentService.auditStudent(stuid)){
            response.getWriter().print("<script>alert('激活成功');location.replace('/manger/browseStudent')</script>");
        }
        else {
            response.getWriter().print("<script>alert('激活失败');location.replace('/manger/browseStudent')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/inauditStudent")
    public void inauditStudent(String stuid,HttpServletResponse response){
        if(studentService.inauditStudent(stuid)){
            response.getWriter().print("<script>alert('冻结成功');location.replace('/manger/browseStudent')</script>");
        }
        else {
            response.getWriter().print("<script>alert('冻结失败');location.replace('/manger/browseStudent')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/auditTeacher")
    public void auditTeacher(String teaid,HttpServletResponse response){
        if(teacherService.auditTeacher(teaid)){
            response.getWriter().print("<script>alert('激活成功');location.replace('/manger/browseTeacher')</script>");
        }
        else {
            response.getWriter().print("<script>alert('激活失败');location.replace('/manger/browseTeacher')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/inauditTeacher")
    public void inauditTeacher(String teaid,HttpServletResponse response){
        if(teacherService.inauditTeacher(teaid)){
            response.getWriter().print("<script>alert('冻结成功');location.replace('/manger/browseTeacher')</script>");
        }
        else {
            response.getWriter().print("<script>alert('冻结失败');location.replace('/manger/browseTeacher')</script>");
        }
    }

    @SneakyThrows
    @RequestMapping("/studentDetail")
    public String studentDetail(String stuid, Model model){
        List<Student> students=studentService.browseStudent("%%");
        for (Student i:students){
            if(i.getStuid().equals(stuid)){
                model.addAttribute("stu",i);
                if(i.getStuphoto()!=null&&i.getStuphoto().length>0)
                    model.addAttribute("Img",getImg(i.getStuphoto()));
                else model.addAttribute("Img",null);
                break;
            }
        }
        return "/manger/studentDetail";
    }
    @SneakyThrows
    @RequestMapping("/deleteStudent")
    public void deleteStudent(String stuid,HttpServletResponse response){
        if(studentService.deleteStudent(stuid)){
            response.getWriter().print("<script>alert('删除成功');location.replace('/manger/browseStudent')</script>");
        }
        else {
            response.getWriter().print("<script>alert('删除失败');location.replace('/manger/browseStudent')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/resetStudent")
    public void resetStudent(String stuid,String stupwd,String newpwd,HttpServletResponse response){
        if(studentService.reset(stuid,stupwd,"123456")){
            response.getWriter().print("<script>alert('重置成功');location.replace('/manger/browseStudent')</script>");
        }
        else {
            response.getWriter().print("<script>alert('重置失败');location.replace('/manger/browseStudent')</script>");
        }
    }

    @SneakyThrows
    @RequestMapping("/browseTeacher")
    public String browseTeacher(String condition,Model model){
        if(!check(condition)) condition="%"+condition+"%";
        else condition="%%";
        List<Teacher> teaList=teacherService.browseTeacher(condition);
        Map<String,String> teaImg=new HashMap<>();
        for(Teacher i:teaList){
            if(i.getTeaphoto()==null||i.getTeaphoto().length<1){
                teaImg.put(i.getTeaid(),null);
                continue;
            }
            teaImg.put(i.getTeaid(),getImg(i.getTeaphoto()));
        }
        model.addAttribute("teaList",teaList);
        model.addAttribute("teaImg",teaImg);
        return "/manger/browseTeacher";
    }
    @RequestMapping("/teacherDetail")
    public String teacherDetail(String teaid,Model model){
        List<Teacher> teachers=teacherService.browseTeacher("%%");
        for (Teacher i:teachers){
            if(i.getTeaid().equals(teaid)){
                model.addAttribute("tea",i);
                if(i.getTeaphoto()!=null&&i.getTeaphoto().length>0)
                model.addAttribute("Img",getImg(i.getTeaphoto()));
                else model.addAttribute("Img",null);
                break;
            }
        }
        return "/manger/teacherDetail";
    }
    @SneakyThrows
    @RequestMapping("/deleteTeacher")
    public void deleteTeacher(String teaid,HttpServletResponse response){
        if(teacherService.deleteTeacher(teaid)){
            response.getWriter().print("<script>alert('删除成功');location.replace('/manger/browseTeacher')</script>");
        }
        else {
            response.getWriter().print("<script>alert('删除失败');location.replace('/manger/browseTeacher')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/resetTeacher")
    public void resetTeacher(String teaid,String teapwd,String newpwd,HttpServletResponse response){
        if(teacherService.reset(teaid,teapwd,"123456")){
            response.getWriter().print("<script>alert('重置成功');location.replace('/manger/browseTeacher')</script>");
        }
        else {
            response.getWriter().print("<script>alert('重置失败');location.replace('/manger/browseTeacher')</script>");
        }
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/addCourse")
    public  String  addCourse(Course course){
        if(courseService.addCourse(course)){
           return "添加成功";
        }
        else {
            return "添加失败";
        }
    }
    @SneakyThrows
    @RequestMapping("/addTeacherCourse")
    public void addTeacherCourse(String teaid,String courseid,HttpServletResponse response){
        if(courseService.addTeacherCourse(teaid,courseid)){
            response.getWriter().print("<script>alert('添加成功');location.replace('/manger/browseCourse')</script>");
        }
        else {
            response.getWriter().print("<script>alert('添加失败');location.replace('/manger/browseCourse')</script>");
        }
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
        return "/manger/browseCourse";
    }
    @SneakyThrows
    @RequestMapping("/deleteCourse")
    public void deleteCourse(String courseid,HttpServletResponse response){
        if(courseService.deleteCourse(courseid)){
            response.getWriter().print("<script>alert('删除成功');location.replace('/manger/browseCourse')</script>");
        }
        else {
            response.getWriter().print("<script>alert('删除失败');location.replace('/manger/browseCourse')</script>");
        }
    }
}

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
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.*;



@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
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
    private String getVideo(byte[] vid,String kind){
        String filename= String.valueOf(UUID.nameUUIDFromBytes(vid));
        File file=File.createTempFile(filename,kind);
        FileOutputStream fos=new FileOutputStream((file));
        fos.write(vid);
        fos.close();
        System.out.println("文件名字:"+filename+kind);
        return filename;

    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/login")
    public String login(String id, String pwd, HttpServletRequest request){
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pwd.getBytes());
        String Hpwd=new BigInteger(1,md.digest()).toString(16);
        List<Teacher> teachers=teacherService.browseTeacher("%%");
        for(Teacher i:teachers){
            if(i.getTeaid().equals(id)) {
                if(i.getTeapwd().equals(Hpwd)){
                    if(i.getTeastate()){
                        i.setTeapwd(pwd);
                        request.getSession().setAttribute("TeacherPerson",i);
                        if(i.getTeaphoto()!=null&&i.getTeaphoto().length>0){
                            request.getSession().setAttribute("TeacherPersonImg",getImg(i.getTeaphoto()));
                        }
                        else request.getSession().setAttribute("TeacherPersonImg",null);
                        return "登录成功";
                    }
                    else return "账号未激活";
                }
                else return "密码错误";
            }
        }
        return "没用该用户";
    }
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "/teacher/teacherRegister";
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/register")
    public String register(Teacher teacher,MultipartFile photo){
        if(photo!=null&&!photo.isEmpty())
        teacher.setTeaphoto(photo.getBytes());
        else teacher.setTeaphoto(null);
        if(teacherService.addTeacher(teacher)){
            return "注册成功";
        }
        else{
           return "注册失败";
        }
    }
    @ResponseBody
    @RequestMapping("/getFlag")
    public String getFlag(String phone){
        String flag= SendMessage.message(phone);
        return flag;
    }
    @RequestMapping("/function")
    public String function(){
        return "/teacher/teacherFunction";
    }
    @RequestMapping("/toChangePwd")
    public String toChangePwd(){
        return "/teacher/changePwd";
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
        Teacher t=(Teacher) request.getSession().getAttribute("TeacherPerson");
        if(teacherService.reset(t.getTeaid(),Hpwd,newpwd1)) {
            return "修改成功";
        }
        else {
            return "密码错误";
        }
    }
    @RequestMapping("/detail")
    public String detail(Model model,HttpServletRequest request){
        Teacher i=(Teacher) request.getSession().getAttribute("TeacherPerson");
        model.addAttribute("tea",i);
        if(i.getTeaphoto()!=null&&i.getTeaphoto().length>0){
            model.addAttribute("Img",getImg(i.getTeaphoto()));
        }
        else model.addAttribute("Img",null);
        return "/teacher/teacherDetail";
    }
    @RequestMapping("/myCourse")
    public String myCourse(Model model,HttpServletRequest request){
        Teacher i=(Teacher) request.getSession().getAttribute("TeacherPerson");
        List<Course> courses=courseService.TmyCourse(i.getTeaid());
        model.addAttribute("courseList",courses);
        return "/teacher/myCourse";
    }
    @SneakyThrows
    @RequestMapping("/myStudent")
    public String myStudnet(Model model,String courseid,HttpServletRequest request){
        if(courseid==null) courseid= (String) request.getSession().getAttribute("courseid");
        else request.getSession().setAttribute("courseid",courseid);
        List<Student> studentList=studentService.myStudent(courseid);
        Map<String,String> stuImg=new HashMap<>();
        Map<String,String> score=new HashMap<>();
        for(Student i:studentList){
            String sc=courseService.browseScore(i.getStuid(),courseid);
            if(sc.equals("-1")){
                score.put(i.getStuid(),null);
            }
            else {
                score.put(i.getStuid(),sc);
            }
            if(i.getStuphoto()==null||i.getStuphoto().length<1){
                stuImg.put(i.getStuid(),null);
                continue;
            }
            stuImg.put(i.getStuid(),getImg(i.getStuphoto()));
        }

        model.addAttribute("stuList",studentList);
        model.addAttribute("stuImg",stuImg);
        model.addAttribute("score",score);
        model.addAttribute("courseid",courseid);
        return "/teacher/myStudent";
    }
    @SneakyThrows
    @RequestMapping("/updateScore")
    public  void updateGrade(String stuid,String courseid,String coursescore,HttpServletResponse response){
        response.setCharacterEncoding("GBK");
        if(courseService.updateScore(coursescore,stuid,courseid)){
            response.getWriter().print("<script>alert('登分成功');location.replace('/teacher/myStudent')</script>");
        }
        else response.getWriter().print("<script>alert('登分失败');location.replace('/teacher/myStudent')</script>");
    }
    @ResponseBody
    @SneakyThrows
    @RequestMapping("/addVideo")
    public String addVideo(Video video,MultipartFile videoImg,MultipartFile videos,HttpServletRequest request){
        int courseid=Integer.valueOf((String) request.getSession().getAttribute("courseid"));
        video.setCourseid(courseid);
        if(videoImg!=null&&!videoImg.isEmpty()){
            video.setVideocover(videoImg.getBytes());
        }
        if(videos!=null&&!videos.isEmpty()){
            String filename= String.valueOf(UUID.randomUUID())+videos.getOriginalFilename().substring(videos.getOriginalFilename().lastIndexOf("."));
            video.setVideofile(filename);
            videos.transferTo(new File("D:\\JavaProject\\AcademicAdministration\\src\\main\\resources\\static\\video\\"+filename));
        }
        video.setVideotime(LocalDate.now());
        if(videoService.addVideo(video)){
            return "添加成功";
        }
        else {
            return "添加失败";
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
        return "/teacher/browseVideo";
    }
    @SneakyThrows
    @RequestMapping("/videoDetail")
    public String videoDetail(String videoid,Model model,HttpServletResponse response){
        List<Video> videoList=videoService.browseVideo("%%");
        Video video=null;
        for(Video i:videoList) {
            if (videoid.equals(String.valueOf(i.getVideoid()))) {
                video = i;
                break;
            }
        }
        response.getWriter().print("<video id='v' src='/"+video.getVideofile()+"' controls>视频</video>");
        model.addAttribute("video",video);
        return "/teacher/videoDetail";
    }
    @SneakyThrows
    @RequestMapping("/deleteVideo")
    public void deleteVideo(String videoid,HttpServletResponse response){
        response.setCharacterEncoding("GBK");
        if(videoService.deleteVideo(videoid)){
            response.getWriter().print("<script>alert('删除成功');location.replace('/teacher/browseVideo')</script>");
        }
        else {
            response.getWriter().print("<script>alert('删除失败');location.replace('/teacher/browseVideo')</script>");
        }
    }
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/addHomework")
    public String addHomework(Homework homework,MultipartFile workfile, HttpServletRequest request){
        int courseid=Integer.valueOf((String) request.getSession().getAttribute("courseid"));
        homework.setCourseid(courseid);
        homework.setHomeworkfile(workfile.getBytes());
        if(homeworkService.addHomework(homework)){
            return "添加成功";
        }
        else {
            return "添加失败";
        }
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
            List<Student> studentList=studentService.myStudent(courseid);
            HashMap<Integer,Integer> solveNum=new HashMap<>();
            for(Homework i:homeworkList){
                int num=0;
                for(Student j:studentList){
                    if(answerService.myAnswer(j.getStuid(), String.valueOf(i.getHomeworkid()))!=null) num++;
                }
                solveNum.put(i.getHomeworkid(),num);
            }
            model.addAttribute("homeworkList",homeworkList);
            model.addAttribute("solveNum",solveNum);
            model.addAttribute("allNum",studentList.size());
            return "/teacher/browseHomework";
    }
    @SneakyThrows
    @RequestMapping("/homeworkDetail")
    public String homeworkDetail(String homeworkid,String index,Model model,HttpServletRequest request){
       String courseid=(String) request.getSession().getAttribute("courseid");
       if (check(homeworkid)) homeworkid= (String) request.getSession().getAttribute("Hid");
       else request.getSession().setAttribute("Hid",homeworkid);
       if (check(index)) index=(String) request.getSession().getAttribute("Index");
       else request.getSession().setAttribute("Index",index);
        model.addAttribute("index",index);
        List<Student> studentList=studentService.myStudent(courseid);
       List<Homework> homeworkList=homeworkService.browseHomeworkAll(courseid);
       HashMap<String,String> stuImg=new HashMap<>();
       HashMap<String,String> answerImg=new HashMap<>();
       for(Homework i:homeworkList){
           if(homeworkid.equals(String.valueOf(i.getHomeworkid()))) {
               model.addAttribute("homework", i);
               model.addAttribute("homeworkImg",getImg(i.getHomeworkfile()));
               for(Student j:studentList){
                   j.setMyAnswer(answerService.myAnswer(j.getStuid(),homeworkid));
                   if(!check(j.getStuphoto()))
                   stuImg.put(j.getStuid(),getImg(j.getStuphoto()));
                   else stuImg.put(j.getStuid(),null);
                   if(j.getMyAnswer()!=null){
                       answerImg.put(j.getStuid(),getImg(j.getMyAnswer().getAnswerfile()));
                   }
               }
               break;
           }
       }
       model.addAttribute("studentList",studentList);
       model.addAttribute("stuImg",stuImg);
       model.addAttribute("answerImg",answerImg);
       return "/teacher/homeworkDetail";
    }
    @SneakyThrows
    @RequestMapping("/deleteHomework")
    public void deleteHomework(String homeworkid,HttpServletResponse response){
        response.setCharacterEncoding("GBK");
        if(homeworkService.deleteHomework(homeworkid)){
            response.getWriter().print("<script>alert('删除成功');location.replace('/teacher/browseHomework')</script>");
        }
        else {
            response.getWriter().print("<script>alert('删除失败');location.replace('/teacher/browseHomework')</script>");
        }
    }
    @SneakyThrows
    @RequestMapping("/updateHScore")
    public void updateHScore(String homeworkid,String stuid,String score,HttpServletResponse response){
        response.setCharacterEncoding("GBK");
        if (check(score)) {
            response.getWriter().print("<script>alert('分数不能为空');location.replace('/teacher/homeworkDetail')</script>");
            return;
        }
        Answer answer=new Answer();
        answer.setScore(Integer.parseInt(score));
        answer.setStuid(stuid);
        answer.setHomeworkid(Integer.parseInt(homeworkid));
        if(answerService.updateScore(answer)){
            response.getWriter().print("<script>alert('判分成功');location.replace('/teacher/homeworkDetail')</script>");
        }
        else {
            response.getWriter().print("<script>alert('判分失败');location.replace('/teacher/homeworkDetail')</script>");
        }
    }


}

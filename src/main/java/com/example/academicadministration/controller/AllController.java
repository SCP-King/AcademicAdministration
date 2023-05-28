package com.example.academicadministration.controller;


import com.example.academicadministration.service.StudentService;
import com.example.academicadministration.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AllController {

    @SneakyThrows
    @RequestMapping("/login")
    public void login(String id, String pwd, String kind, @Param("sub") String sub, HttpServletRequest request, HttpServletResponse response){
        if (sub==null||sub.equals("注册")){
            switch (kind){
                case "student":
                    request.getRequestDispatcher("/student/toRegister").forward(request,response);
                    return;
                case "teacher":
                    request.getRequestDispatcher("/teacher/toRegister").forward(request,response);
                    return;
                default:
                   response.getWriter().print("管理员无法被注册");
                    return;
            }
        }
        else {
            switch (kind){
                case "student":
                    request.getRequestDispatcher("/student/login").forward(request,response);
                    return;
                case "teacher":
                    request.getRequestDispatcher("/teacher/login").forward(request,response);
                    return;
                case "manger":
                    request.getRequestDispatcher("/manger/login").forward(request,response);
                    return;
            }
        }
    }
    @RequestMapping("/weclome")
    public String weclome(){
        return "weclome";
    }

}

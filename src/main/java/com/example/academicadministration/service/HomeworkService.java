package com.example.academicadministration.service;

import com.example.academicadministration.dao.HomeworkDao;
import com.example.academicadministration.pojo.Homework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkDao homeworkDao;
    public Boolean addHomework(Homework homework) {
        return homeworkDao.addHomework(homework)>0;
    }

    public Boolean deleteHomework(String homeworkid) {
        return homeworkDao.deleteHomework(homeworkid)>0;
    }

    public List<Homework> browseHomeworkAll(String courseid) {
        return homeworkDao.browseHomeworkAll(courseid);
    }

    public List<Homework> browseHomework1(String courseid) {
        return homeworkDao.browseHomework1(courseid);
    }

    public List<Homework> browseHomework2(String courseid) {
        return homeworkDao.browseHomework2(courseid);
    }
}

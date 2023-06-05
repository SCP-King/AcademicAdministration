package com.example.academicadministration.service;

import com.example.academicadministration.dao.AnswerDao;
import com.example.academicadministration.pojo.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AnswerService  {
    @Autowired
    private AnswerDao answerDao;
    public Boolean addAnswer(Answer answer) {
        return answerDao.addAnswer(answer)>0;
    }

    public Boolean updateAnswer(Answer answer) {
        return answerDao.updateAnswer(answer)>0;
    }

    public Boolean updateScore(Answer answer) {
        return answerDao.updateScore(answer)>0;
    }
    public Boolean deleteAnswer(String stuid,String homeworkid){
        return answerDao.deleteAnswer(stuid,homeworkid)>0;
    }

    public Answer myAnswer(String stuid,String homeworkid) {
        return answerDao.myAnswer(stuid,homeworkid);
    }
}

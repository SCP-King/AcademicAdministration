package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnswerDao {
    int addAnswer(Answer answer);
    int updateAnswer(Answer answer);
    int updateScore(Answer answer);

    int  deleteAnswer(@Param("stuid") String stuid,@Param("homeworkid") String homeworkid);
    Answer myAnswer(@Param("stuid") String stuid,@Param("homeworkid") String homeworkid);
}

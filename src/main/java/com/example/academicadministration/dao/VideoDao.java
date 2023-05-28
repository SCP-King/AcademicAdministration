package com.example.academicadministration.dao;

import com.example.academicadministration.pojo.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDao {
    int addVideo(Video video);
    int deleteVideo(String videoid);
    List<Video> browseVideo(String condition);
}

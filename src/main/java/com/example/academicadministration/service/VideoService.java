package com.example.academicadministration.service;

import com.example.academicadministration.dao.VideoDao;
import com.example.academicadministration.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class VideoService  {
    @Autowired
    private VideoDao videoDao;
    public Boolean addVideo(Video video) {
        return videoDao.addVideo(video)>0;
    }

    public Boolean deleteVideo(String videoid) {
        List<Video> videoList=browseVideo("%%");
        for(Video i:videoList){
            if(videoid.equals(String.valueOf(i.getVideoid()))){
                new File("D:\\JavaProject\\AcademicAdministration\\src\\main\\resources\\static\\video\\"+i.getVideofile()).delete();
            }
        }
        return videoDao.deleteVideo(videoid)>0;
    }

    public List<Video> browseVideo(String condition) {
        return videoDao.browseVideo(condition);
    }

}

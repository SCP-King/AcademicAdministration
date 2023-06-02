package com.example.academicadministration.service;

import com.example.academicadministration.dao.MangerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangerService {
    @Autowired
    private MangerDao mangerDao;
    public String login(String id){
        return mangerDao.login(id);
    }

    public boolean reset(String mangerid,String oldpwd, String newpwd1) {
        return mangerDao.reset(mangerid,oldpwd,newpwd1)>0;
    }
}

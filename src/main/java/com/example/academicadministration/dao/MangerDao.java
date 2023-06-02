package com.example.academicadministration.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MangerDao {

    String login(String id);

    int reset(@Param("mangerid") String mangerid,@Param("oldpwd") String oldpwd, @Param("newpwd") String newpwd1);
}

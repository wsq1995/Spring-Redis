package com.lanou.redis.mapper;

import com.lanou.redis.domain.Stu;

import java.util.List;

/**
 * Created by dllo on 17/12/28.
 */
public interface StuMapper {
    
    List<Stu>findAll();

    void addStu(Stu stu);
    
    
    
    
}

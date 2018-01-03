package com.lanou.redis.mapper;

import com.lanou.redis.domain.Stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by dllo on 17/12/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ssm-redis.xml", "classpath:ssm-mybatis.xml"})
public class StuMapperTest {

    @Resource
    private StuMapper stuMapper;

    @Test
    public void findAll() throws Exception {
        stuMapper.findAll().forEach(System.out::println);
    }

    @Test
    public void addStu() throws Exception {
        Stu stu = new Stu();
        stu.setSname("赵六");
        stu.setSname("王八");
        stuMapper.addStu(stu);
    }
}
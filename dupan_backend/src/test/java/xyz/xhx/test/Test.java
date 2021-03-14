package xyz.xhx.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.xhx.dao.DuyunDao;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-mvc.xml")
public class Test {
    @Autowired
    private DuyunDao dao;

    @org.junit.Test
    public void Test1() {
        Map<String, Object> info = dao.getUserInfo("123456");
        System.out.println(info.toString());
    }

}
package com.pulgaspring.service;

import java.util.Date;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.pulgaspring.domain.User;
import static org.testng.Assert.*;

/**
 * Created by pulgawang on 11/05/2017.
 */
@ContextConfiguration("classpath*:/pulgaspring-context.xml")//applicationContext.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests{
    private UserService userService;

    @Autowired
    public void testSetUserService(UserService userService){
        this.userService = userService;
    }

    @Test
    public void testHasMatchUser(){
        boolean b1 = userService.hasMatchUser("涛哥","123");
        boolean b2 = userService.hasMatchUser("狗耕","123");
        boolean b3 = userService.hasMatchUser("admin","123");
        assertTrue(b1);
        assertTrue(!b2);
        assertTrue(!b3);
    }

    @Test
    public void testFindUserByUserName(){
        User user = userService.findUserByUserName("涛哥");
        assertEquals(user.getUserName(), "涛哥");
    }

    @Test
    public void testAddLoginLog(){
        User user = new User();//userService.findUserByUserName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastVisit(new Date());
        user.setLastIp("127.0.0.1");

        userService.loginSucess(user);
        System.out.println(user.toString());
    }
}

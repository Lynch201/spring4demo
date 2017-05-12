package com.pulgaspring.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pulgaspring.domain.User;
import com.pulgaspring.service.UserService;

/**
 * Created by pulgawang on 12/05/2017.
 */
@Controller
public class LoginController {
    private UserService userService;

    @RequestMapping(value = "/index.html")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand){
        boolean isValidUser = userService.hasMatchUser(loginCommand.getUserName(), loginCommand.getPassword());

        if(!isValidUser){
            return new ModelAndView("login", "error", "用户名或者密码错误。");
        }else{
            User user = userService.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSucess(user);
            request.getSession().setAttribute("user", user);
            return new ModelAndView("main");
        }

    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}

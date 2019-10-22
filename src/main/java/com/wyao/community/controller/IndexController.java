package com.wyao.community.controller;

import com.wyao.community.dto.GithubUserDTO;
import com.wyao.community.mapper.UserMapper;
import com.wyao.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String hello(HttpServletRequest httpServletRequest, Model model) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                UserModel userModel = userMapper.findByToken(token);
                if (userModel != null) {
                    httpServletRequest.getSession().setAttribute(
                            "githubUserDTO",
                            new GithubUserDTO(userModel.getName(), Long.valueOf(userModel.getAccountId()), "")
                    );
                }
                break;
            }
        }

        return "index";
    }
}

package com.wyao.community.controller;

import com.wyao.community.dto.AccessTokenDTO;
import com.wyao.community.dto.GithubUserDTO;
import com.wyao.community.mapper.UserMapper;
import com.wyao.community.model.UserModel;
import com.wyao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name="code") String code,
            @RequestParam(name="state") String state,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        if (githubUserDTO != null) {
            //登录成功 写cookie和session
            UserModel userModel = new UserModel();
            String token = UUID.randomUUID().toString();
            userModel.setToken(token);
            userModel.setName(githubUserDTO.getLogin());
            userModel.setAccountId(String.valueOf(githubUserDTO.getId()));
            userModel.setGmtCreate(System.currentTimeMillis());
            userModel.setGmtModified(userModel.getGmtCreate());
            userMapper.insert(userModel);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            // 登录失败
            return "redirect:/";
        }
    }
}

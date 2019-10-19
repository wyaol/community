package com.wyao.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wyao.community.dto.AccessTokenDTO;
import com.wyao.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            return res.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GithubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String  res = response.body().string();
            System.out.println(res);
            return JSON.parseObject(res, GithubUserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

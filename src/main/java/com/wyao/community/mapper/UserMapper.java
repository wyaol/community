package com.wyao.community.mapper;

import com.wyao.community.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified) values ( #{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified} )")
    void insert(UserModel userModel);

    @Results({
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "gmtCreate", column = "gmt_create"),
        @Result(property = "gmtModified", column = "gmtModified"),
    })
    @Select("select * from user where token = #{token}")
    UserModel findByToken(@Param("token") String token);
}

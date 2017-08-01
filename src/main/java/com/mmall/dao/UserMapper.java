package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(String username);

    int checkEmail(@Param("email") String email);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    String getQuestionByUserName(@Param("username")String username);

    int forgetCheckAnswer(@Param("username") String username,
                          @Param("question") String question,
                          @Param("answer") String answer);


    int forgetResetPassword(@Param("username") String username,@Param("password") String md5Psw);

    int checkPassword(@Param("id") Integer id,@Param("passwordOld") String passwordOld);

}
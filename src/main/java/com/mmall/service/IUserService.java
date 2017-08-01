package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse<String> getQuestionByUserName(String username);
    ServerResponse<String> forgetCheckAnswer(String username,String question,String answer);
    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String> resetPassword(User user,String passwordOld,String passwordNew);
    ServerResponse<String> updateInformation(User user);
    ServerResponse<User> getInformation(Integer userId);
}

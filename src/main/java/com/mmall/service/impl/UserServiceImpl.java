package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Service("IUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUserName(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        User user = userMapper.selectLogin(username,MD5Util.MD5EncodeUtf8(password));
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {

        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }else{
            return ServerResponse.createBySuccessMessage("注册成功");
        }
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUserName(str);
                if(resultCount > 0){
                    return ServerResponse.createByError("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByError("邮箱已存在");
                }
            }
        }else{
            return ServerResponse.createByError("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }


    @Override
    public ServerResponse<String> getQuestionByUserName(String username) {
        ServerResponse serverResponse = this.checkValid(username,Const.USERNAME);
        if(!serverResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.getQuestionByUserName(username);
        if(!StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("该用户未设置找回密码问题");
    }

    @Override
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        int resultCount = userMapper.forgetCheckAnswer(username,question,answer);
        if(resultCount > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew,String forgetToken) {
//        String token = TokenCache.getKey("token_"+username);
//        if(token == null){
//            return ServerResponse.createByErrorMessage("token已失效");
//        }
//        if(!token.equals(forgetToken)){
//            return ServerResponse.createByErrorMessage("修改密码操作失效");
//        }
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(passwordNew);
//        int resultCount = userMapper.updateByPrimaryKeySelective(user);
//        if(resultCount > 0){
//            return ServerResponse.createBySuccessMessage("修改密码成功");
//        }
//        return ServerResponse.createByErrorMessage("修改密码操作失效");
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        ServerResponse<String> response = this.checkValid(username,Const.USERNAME);
        if(!response.isSuccess()){
           return response;
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token已失效或不存在");
        }
        if (!token.equals(forgetToken)){
            return ServerResponse.createByErrorMessage("token错误");
        }
        String md5Psw = MD5Util.MD5EncodeUtf8(passwordNew);
        int resultCount = userMapper.forgetResetPassword(username,md5Psw);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("修改密码成功");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew) {
//        User user = userMapper.selectLogin(username,passwordOld);
//        if(user == null){
//            return ServerResponse.createByErrorMessage("旧密码输入错误");
//        }
//        user.setPassword(passwordNew);
//        int resultCount = userMapper.updateByPrimaryKeySelective(user);
//        if(resultCount > 0){
//            return ServerResponse.createBySuccessMessage("修改密码成功");
//        }
//        return ServerResponse.createByErrorMessage("修改密码失败");
        int resultCount = userMapper.checkPassword(user.getId(),passwordOld);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        String md5Psw = MD5Util.MD5EncodeUtf8(passwordNew);
        user.setPassword(md5Psw);
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("更改密码成功");
        }
        return ServerResponse.createByErrorMessage("更改密码失败");
    }

    @Override
    public ServerResponse<String> updateInformation(User user) {
        int resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新个人信息成功");
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未找到");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
}

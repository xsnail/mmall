package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/8/1.
 */
@Controller
@RequestMapping("/admin/user/")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession httpSession){
        ServerResponse<User> response = iUserService.login(username,password);
        if(!response.isSuccess()){
            return response;
        }
        if(Const.Role.ROLE_ADMIN != response.getData().getRole().intValue()){
            return ServerResponse.createByErrorMessage("不是管理员账号");
        }
        httpSession.setAttribute(Const.CURRENT_USER,response.getData());
        return response;
    }
}

package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession httpSession){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            httpSession.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession httpSession){
        httpSession.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
    }

    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetPassword(String username){
        return iUserService.getQuestionByUserName(username);
    }

    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        return iUserService.forgetCheckAnswer(username,question,answer);
    }

    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(HttpSession httpSession,String passwordOld,String passwordNew){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(user,passwordOld,passwordNew);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.GET)
    public ServerResponse<String> updateInformation(HttpSession httpSession,String email,String phone,String question,String answer){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setEmail(email);
        user.setPhone(phone);
        user.setQuestion(question);
        user.setAnswer(answer);
        ServerResponse<String> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            httpSession.setAttribute(Const.CURRENT_USER,user);
        }
        return response;
    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    public ServerResponse<User> getInformation(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(10,"用户未登录,无法获取当前用户信息,status=10,强制登录");
        }
        return iUserService.getInformation(user.getId());
    }
}

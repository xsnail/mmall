package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Controller
@RequestMapping("/admin/category/")
public class CategoryManagerController {

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "get_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> get_category(HttpSession httpSession,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (Const.Role.ROLE_ADMIN != user.getRole()){
            return ServerResponse.createByErrorMessage("不是管理员,请重新登陆");
        }
        return iCategoryService.getCategory(categoryId);
    }

    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> add_category(HttpSession httpSession,Integer parentId,String categoryName){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (Const.Role.ROLE_ADMIN != user.getRole()){
            return ServerResponse.createByErrorMessage("不是管理员,请重新登陆");
        }
        return iCategoryService.addCategory(parentId,categoryName);
    }

    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> set_category_name(HttpSession httpSession,Integer categoryId,String categoryName){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (Const.Role.ROLE_ADMIN != user.getRole()){
            return ServerResponse.createByErrorMessage("不是管理员,请重新登陆");
        }
        return iCategoryService.set_category_name(categoryId,categoryName);
    }

    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getDeepCategory(HttpSession httpSession,Integer categoryId){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (Const.Role.ROLE_ADMIN != user.getRole()){
            return ServerResponse.createByErrorMessage("不是管理员,请重新登陆");
        }
        return iCategoryService.getDeepCategory(categoryId);
    }
}

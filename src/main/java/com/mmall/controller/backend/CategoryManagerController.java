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
    public ServerResponse<List<Category>> get_category(HttpSession httpSession,Integer categoryId){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (Const.Role.ROLE_ADMIN != user.getRole()){
            return ServerResponse.createByErrorMessage("不是管理员,请重新登陆");
        }
        return iCategoryService.getCategory(categoryId);
    }
}

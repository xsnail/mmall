package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IProductService;
import com.mmall.service.impl.ProductServiceImpl;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/10/8.
 */
@RestController
@RequestMapping("/admin/order/")
public class OrderManagerController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "list.do")
    public ServerResponse<OrderVo> list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                        HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iOrderService.list(user.getId(),pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }
}

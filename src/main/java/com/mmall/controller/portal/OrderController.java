package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("pay.do")
    public ServerResponse pay(HttpSession httpSession, Long orderNo, HttpServletRequest httpServletRequest){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(),orderNo,path);
    }
}

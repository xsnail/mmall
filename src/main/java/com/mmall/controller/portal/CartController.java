package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@RestController
@RequestMapping("/cart/")
public class CartController {


    @Autowired
    private ICartService iCartService;

    @RequestMapping("list.do")
    public ServerResponse cartList(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }


    @RequestMapping("add.do")
    public ServerResponse add(HttpSession httpSession,Integer productId,Integer count){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(productId,count,user.getId());
    }

    @RequestMapping("update.do")
    public ServerResponse<CartVo> update(HttpSession httpSession,Integer productId,Integer count){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(productId,count,user.getId());
    }

    @RequestMapping("delete_product.do")
    public ServerResponse<CartVo> deleteProduct(HttpSession httpSession,String productIds){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(productIds,user.getId());
    }

    @RequestMapping("select.do")
    public ServerResponse<CartVo> select(Integer productId,HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(productId,user.getId(),Const.Cart.CHECKED);
    }

    @RequestMapping("un_select.do")
    public ServerResponse<CartVo> unSelect(Integer productId,HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(productId,user.getId(),Const.Cart.UN_CHECKED);
    }

    @RequestMapping("get_cart_product_count.do")
    public ServerResponse getCartProductCount(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }


    @RequestMapping("select_all.do")
    public ServerResponse selectAll(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(null,user.getId(),Const.Cart.CHECKED);
    }

    @RequestMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(null,user.getId(),Const.Cart.UN_CHECKED);

    }
}

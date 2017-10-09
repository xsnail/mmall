package com.mmall.service;

import com.mmall.common.ServerResponse;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface IOrderService {
    ServerResponse pay(Integer userId, Long orderNo, String path);
    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    ServerResponse create(Integer userId, Integer shippingId);

    ServerResponse cancel(Long orderNo, Integer userId);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse detail(Integer userId,Long orderNo);

    ServerResponse list(Integer userId,int pageNum,int pageSize);

    ServerResponse search(Long orderNo, Integer pageNum, Integer pageSize, Integer userId);
}

package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface IOrderService {
    ServerResponse pay(Integer userId, Long orderNo, String path);
}

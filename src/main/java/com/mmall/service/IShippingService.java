package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface IShippingService {
    ServerResponse add(Integer userId,Shipping shipping);

    ServerResponse del(Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse select(Integer shippingId);

    ServerResponse list(Integer userId, Integer pageNum, Integer pageSize);
}

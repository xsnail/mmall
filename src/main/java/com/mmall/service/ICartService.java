package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface ICartService {
    ServerResponse add(Integer productId,Integer count,Integer userId);

    ServerResponse<CartVo> update(Integer productId, Integer count, Integer userId);

    ServerResponse<CartVo> deleteProduct(String productIds, Integer userId);

    ServerResponse list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer productId, Integer userId,Integer checked);

    ServerResponse getCartProductCount(Integer userId);

}

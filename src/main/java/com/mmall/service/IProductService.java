package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * Created by Administrator on 2017/8/3.
 */
public interface IProductService {
    ServerResponse list(String keyword,Integer categoryId,String orderBy,Integer pageNum,Integer pageSize);

    ServerResponse<Product> detail(Integer productId);
}

package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * Created by Administrator on 2017/8/3.
 */
public interface IProductService {
    ServerResponse list(String keyword,Integer categoryId,String orderBy,Integer pageNum,Integer pageSize);

    ServerResponse<ProductDetailVo> detail(Integer productId);

    ServerResponse<ProductDetailVo> adminDetail(Integer productId);

    ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse save(Product product);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse adminList(int pageNum, int pageSize);
}

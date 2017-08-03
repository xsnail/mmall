package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
@Service("IProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse list(String keyword, Integer categoryId, String orderBy, Integer pageNum, Integer pageSize) {
        if(StringUtils.isBlank(keyword) || categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int offset = (pageNum - 1) * pageSize;
        String key = "";
        String order = "";
        String sort = "";
        if(StringUtils.isNotEmpty(orderBy)){
            String[] array = orderBy.split("_");
            if(array.length == 2){
                key = array[0];
                order = array[1];
                sort = key + " " + order;
            }
        }
        List<Product> productList = productMapper.selectPageByCategory(categoryId,offset,pageSize,sort);
        return ServerResponse.createBySuccess(productList);
    }

    @Override
    public ServerResponse<Product> detail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product != null && Const.ProductStatus.ON_SALL == product.getStatus().intValue()){
            return ServerResponse.createBySuccess(product);
        }
        return ServerResponse.createByErrorMessage("商品已下架或删除");
    }
}

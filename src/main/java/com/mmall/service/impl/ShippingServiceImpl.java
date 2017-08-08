package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@Service("IShippingService")
public class ShippingServiceImpl implements IShippingService{

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId,Shipping shipping) {
        if(shipping == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount = shippingMapper.insert(shipping);
        if(resultCount > 0){
            Shipping shippingItem = shippingMapper.getLatestShipping(userId);
            if(shippingItem != null) {
                Map resultMap = Maps.newHashMap();
                resultMap.put("shippingId",shippingItem.getId());
                return ServerResponse.createBySuccess("新建地址成功",resultMap);
            }
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse del(Integer shippingId) {
        if(shippingId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount = shippingMapper.deleteByPrimaryKey(shippingId);
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServerResponse select(Integer shippingId) {
        if(shippingId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingListByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}

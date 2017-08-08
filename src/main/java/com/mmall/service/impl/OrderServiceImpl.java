package com.mmall.service.impl;

import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Order;
import com.mmall.pojo.PayInfo;
import com.mmall.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@Service("IOrderService")
public class OrderServiceImpl implements IOrderService {

    private static AlipayTradeService tradeService;
    static {
        Configs.init("zfbinfo.properties");
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }


    @Override
    public ServerResponse pay(Integer userId, Long orderNo, String path) {
        return null;
    }
}

package com.mmall;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class Test {
    public static void main(String[] args) {

    }

    private String payModels2Json(List<PayModel> payModels){
        StringBuffer sb = new StringBuffer();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        try {
            for(PayModel payModel : payModels){
                jsonObject = new JSONObject();
                jsonObject.put("PushMachineSn",payModel.getPushMachineSn());
                jsonObject.put("OrderSn",payModel.getOrderSn());
                jsonObject.put("CreateTime",payModel.getCreateTime());
                jsonObject.put("OrderStatus",payModel.getOrderStatus());
                jsonObject.put("GoodsCode",payModel.getGoodsCode());
                jsonObject.put("GoodsNum",payModel.getGoodsNum());
                jsonObject.put("GoodsId",payModel.getGoodsId());
                jsonObject.put("GoodsBelong",payModel.getGoodsBelong());
                jsonObject.put("GoodsPrice",payModel.getGoodsPrice());
                jsonObject.put("MachineSn",payModel.getMachineSn());
                jsonObject.put("PayTime",payModel.getPayTime());
                jsonObject.put("PayType",payModel.getPayType());
                jsonObject.put("DeliveryTime",payModel.getDeliveryTime());
                jsonObject.put("MachineTradeNo",payModel.getMachineTradeNo());
                jsonObject.put("MachineRoadNo",payModel.getMachineRoadNo());
                jsonObject.put("DeliveryStatus",payModel.getDeliveryStatus());
                jsonObject.put("remark",payModel.getParam());
                jsonArray.put(jsonObject);
            }
            sb.append(jsonArray.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}

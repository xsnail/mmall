package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/3.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "categoryId",required = false) Integer categoryId,
                               @RequestParam(value = "orderBy",defaultValue = "") String orderBy,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return iProductService.list(keyword,categoryId,orderBy,pageNum,pageSize);
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.detail(productId);
    }
}

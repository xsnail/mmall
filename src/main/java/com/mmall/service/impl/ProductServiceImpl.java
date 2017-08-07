package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
@Service("IProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

//    @Override
//    public ServerResponse list(String keyword, Integer categoryId, String orderBy, Integer pageNum, Integer pageSize) {
//        if(StringUtils.isBlank(keyword) || categoryId == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
//                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
//        }
//        int offset = (pageNum - 1) * pageSize;
//        String key = "";
//        String order = "";
//        String sort = "";
//        if(StringUtils.isNotEmpty(orderBy)){
//            String[] array = orderBy.split("_");
//            if(array.length == 2){
//                key = array[0];
//                order = array[1];
//                sort = key + " " + order;
//            }
//        }
//        List<Product> productList = productMapper.selectPageByCategory(categoryId,offset,pageSize,sort);
//        return ServerResponse.createBySuccess(productList);
//    }

//    @Override
//    public ServerResponse list(String keyword, Integer categoryId, String orderBy, Integer pageNum, Integer pageSize) {
//        if(StringUtils.isBlank(keyword) && categoryId == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
//                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
//        }
//        List<Integer> categoryIdList = new ArrayList<>();
//        if(categoryId != null){
//            Category category = categoryMapper.selectByPrimaryKey(categoryId);
//            if(category == null && StringUtils.isBlank(keyword)){
//                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
//                PageHelper.startPage(pageNum,pageSize);
//
//            }
//        }
//    }

    @Override
    public ServerResponse list(String keyword, Integer categoryId, String orderBy, Integer pageNum, Integer pageSize) {
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<>();
        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.getDeepCategory(categoryId).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = convertProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product != null && Const.ProductStatus.ON_SALL == product.getStatus().intValue()){
            ProductDetailVo productDetailVo = convertProductDetailVo(product);
            return ServerResponse.createBySuccess(productDetailVo);
        }
        return ServerResponse.createByErrorMessage("商品已下架或删除");
    }

    @Override
    public ServerResponse<ProductDetailVo> adminDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product != null){
            ProductDetailVo productDetailVo = convertProductDetailVo(product);
            return ServerResponse.createBySuccess(productDetailVo);
        }
        return ServerResponse.createByErrorMessage("商品已下架或删除");
    }

    private ProductDetailVo convertProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0); // 默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = convertProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse save(Product product) {
        if(product == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int resultCount;
        if(product.getId() == null){
            resultCount = productMapper.insert(product);
            if(resultCount > 0){
                return ServerResponse.createBySuccessMessage("新增产品成功");
            }else{
                return ServerResponse.createByErrorMessage("新增产品失败");
            }
        }
        resultCount = productMapper.updateByPrimaryKeySelective(product);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新产品成功");
        }
        return ServerResponse.createByErrorMessage("更新产品失败");

    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int resultCount = productMapper.setSaleStatus(productId,status);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("修改产品状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品状态失败");
    }

    @Override
    public ServerResponse adminList(int pageNum, int pageSize) {
        //startPage--start
        // 填充自己的sql查询逻辑
        // pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = new ArrayList<>();
        for(Product productItem : productList){
            ProductListVo productListVo = convertProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo convertProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
}

package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service("ICategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<List<Category>> getCategory(Integer categoryId) {
       List<Category> categoryList = categoryMapper.getCategory(categoryId);
       return ServerResponse.createBySuccess(categoryList);
    }
}

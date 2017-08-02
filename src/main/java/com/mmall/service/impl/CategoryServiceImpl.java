package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount = categoryMapper.addCategory(parentId,categoryName);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse<String> set_category_name(Integer categoryId, String categoryName) {
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount = categoryMapper.setCategoryName(categoryId,categoryName);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {
        if(categoryId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Set<Category> categorySet = new HashSet<>();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList = new ArrayList<>();
        for(Category category : categorySet){
            categoryIdList.add(category.getId());
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.getCategory(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}

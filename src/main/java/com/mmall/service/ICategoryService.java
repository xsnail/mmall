package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface ICategoryService {
    ServerResponse<List<Category>> getCategory(Integer categoryId);
    ServerResponse<String> addCategory(Integer parentId,String categoryName);

    ServerResponse<String> set_category_name(Integer categoryId, String categoryName);

    ServerResponse<List<Integer>> getDeepCategory(Integer categoryId);
}

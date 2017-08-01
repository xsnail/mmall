package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface ICategoryService {
    ServerResponse<List<Category>> getCategory(Integer categoryId);
}

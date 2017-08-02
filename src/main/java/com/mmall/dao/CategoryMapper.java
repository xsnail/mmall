package com.mmall.dao;

import com.mmall.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> getCategory(@Param("categoryId") Integer categoryId);

    int addCategory(@Param("parentId") Integer parentId, @Param("categoryName") String categoryName);

    int setCategoryName(@Param("categoryId") Integer categoryId,@Param("categoryName") String categoryName);
}
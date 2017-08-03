package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);


    List<Product> selectPageByCategory(@Param("categoryId") Integer categoryId,
                                       @Param("offset") int offset,
                                       @Param("pageSize") Integer pageSize,
                                       @Param("sort") String sort);
}
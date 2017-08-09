package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> findCartListByUserId(@Param("userId") Integer userId);

    Cart selectCartByUserIdProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIdList")List<String> productIdList);

    int selectOrUnSelect(@Param("productId") Integer productId, @Param("userId") Integer userId,@Param("checked")Integer checked);

    int getCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}
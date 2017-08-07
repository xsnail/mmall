package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class Const {
    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 1;
        int ROLE_ADMIN = 0;
    }

    public interface ProductStatus{
        int ON_SALL = 1;
        int SALL_OUT = 2;
        int DELETE = 3;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
}

/*
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.kazuki43zoo.jpetstore.mapper

import com.kazuki43zoo.jpetstore.domain.Product
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface ProductMapper {


    @Select('''
        SELECT
            PRODUCTID,
            NAME,
            DESCN as description,
            CATEGORY as categoryId
        FROM
            PRODUCT
        WHERE
            CATEGORY = #{categoryId}
    ''')
    List<Product> getProductListByCategory(String categoryId);

    @Select('''
        SELECT
            PRODUCTID,
            NAME,
            DESCN as description,
            CATEGORY as categoryId
        FROM
            PRODUCT
        WHERE
            PRODUCTID = #{productId}
    ''')
    Product getProduct(String productId);

    @Select('''
        SELECT
            PRODUCTID,
            NAME,
            DESCN as description,
            CATEGORY as categoryId
        FROM
            PRODUCT
        WHERE
            LOWER(NAME) LIKE '%' || #{keyword} || '%'
    ''')
    List<Product> selectProductList(String keyword);

}
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

import com.kazuki43zoo.jpetstore.domain.Item
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface ItemMapper {

    @Update('''
        UPDATE INVENTORY SET
            QTY = QTY - #{increment}
        WHERE
            ITEMID = #{itemId}
    ''')
    void updateInventoryQuantity(@Param("itemId") String itemId, @Param("increment") int increment)

    @Select('''
        SELECT
            QTY AS value
        FROM
            INVENTORY
        WHERE
            ITEMID = #{itemId}
    ''')
    int getInventoryQuantity(String itemId)

    @Select('''
        SELECT
            I.ITEMID,
            LISTPRICE,
            UNITCOST,
            SUPPLIER AS supplierId,
            I.PRODUCTID AS "product.productId",
            NAME AS "product.name",
            DESCN AS "product.description",
            CATEGORY AS "product.categoryId",
            STATUS,
            ATTR1 AS attribute1,
            ATTR2 AS attribute2,
            ATTR3 AS attribute3,
            ATTR4 AS attribute4,
            ATTR5 AS attribute5
        FROM
            ITEM I, PRODUCT P
        WHERE
            P.PRODUCTID = I.PRODUCTID
            AND I.PRODUCTID = #{productId}
    ''')
    List<Item> getItemListByProduct(String productId)

    @Select('''
        SELECT
            I.ITEMID,
            LISTPRICE,
            UNITCOST,
            SUPPLIER AS supplierId,
            I.PRODUCTID AS "product.productId",
            NAME AS "product.name",
            DESCN AS "product.description",
            CATEGORY AS "product.categoryId",
            STATUS,
            ATTR1 AS attribute1,
            ATTR2 AS attribute2,
            ATTR3 AS attribute3,
            ATTR4 AS attribute4,
            ATTR5 AS attribute5,
            QTY AS quantity
        FROM
            ITEM I,
            INVENTORY V,
            PRODUCT P
        WHERE
            P.PRODUCTID = I.PRODUCTID
            AND I.ITEMID = V.ITEMID
            AND I.ITEMID = #{itemId}
    ''')
    Item getItem(String itemId)

}
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

import com.kazuki43zoo.jpetstore.domain.Category
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface CategoryMapper {

    @Select('''
        SELECT
            CATID AS categoryId,
            NAME,
            DESCN AS description
        FROM
            CATEGORY
        WHERE
            CATID = #{categoryId}
    ''')
    Category getCategory(String categoryId)

}
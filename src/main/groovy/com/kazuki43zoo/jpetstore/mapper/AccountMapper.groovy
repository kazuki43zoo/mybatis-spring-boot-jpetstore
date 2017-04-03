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

import com.kazuki43zoo.jpetstore.domain.Account
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface AccountMapper {

    @Select('''
        SELECT
            SIGNON.USERNAME,
            SIGNON.PASSWORD,
            ACCOUNT.EMAIL,
            ACCOUNT.FIRSTNAME,
            ACCOUNT.LASTNAME,
            ACCOUNT.STATUS,
            ACCOUNT.ADDR1 AS address1,
            ACCOUNT.ADDR2 AS address2,
            ACCOUNT.CITY,
            ACCOUNT.STATE,
            ACCOUNT.ZIP,
            ACCOUNT.COUNTRY,
            ACCOUNT.PHONE,
            PROFILE.LANGPREF AS languagePreference,
            PROFILE.FAVCATEGORY AS favouriteCategoryId,
            PROFILE.MYLISTOPT AS listOption,
            PROFILE.BANNEROPT AS bannerOption,
            BANNERDATA.BANNERNAME
        FROM
            ACCOUNT, PROFILE, SIGNON, BANNERDATA
        WHERE
            ACCOUNT.USERID = #{username}
            AND SIGNON.USERNAME = ACCOUNT.USERID
            AND PROFILE.USERID = ACCOUNT.USERID
            AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY
    ''')
    Account getAccountByUsername(String username)

    @Insert('''
        INSERT INTO ACCOUNT (
            EMAIL,
            FIRSTNAME,
            LASTNAME,
            STATUS,
            ADDR1,
            ADDR2,
            CITY,
            STATE,
            ZIP,
            COUNTRY,
            PHONE,
            USERID
        )
        VALUES (
            #{email},
            #{firstName},
            #{lastName},
            #{status},
            #{address1},
            #{address2},
            #{city},
            #{state},
            #{zip},
            #{country},
            #{phone},
            #{username}
        )
    ''')
    void insertAccount(Account account)

    @Insert('''
        INSERT INTO PROFILE (
            LANGPREF,
            FAVCATEGORY,
            MYLISTOPT,
            BANNEROPT,
            USERID
        )
        VALUES (
            #{languagePreference},
            #{favouriteCategoryId},
            #{listOption},
            #{bannerOption},
            #{username}
        )
    ''')
    void insertProfile(Account account)

    @Insert('''
        INSERT INTO SIGNON (
            PASSWORD,
            USERNAME
        )
        VALUES (
            #{password},
            #{username}
        )
    ''')
    void insertSignon(Account account)

    @Update('''
        UPDATE ACCOUNT SET
            EMAIL = #{email},
            FIRSTNAME = #{firstName},
            LASTNAME = #{lastName},
            STATUS = #{status},
            ADDR1 = #{address1},
            ADDR2 = #{address2},
            CITY = #{city},
            STATE = #{state},
            ZIP = #{zip},
            COUNTRY = #{country},
            PHONE = #{phone}
        WHERE
            USERID = #{username}
    ''')
    void updateAccount(Account account)

    @Update('''
        UPDATE PROFILE SET
            LANGPREF = #{languagePreference},
            FAVCATEGORY = #{favouriteCategoryId},
            MYLISTOPT = #{listOption},
            BANNEROPT = #{bannerOption}
        WHERE
            USERID = #{username}
    ''')
    void updateProfile(Account account)

    @Update('''
        UPDATE SIGNON SET
            PASSWORD = #{password}
        WHERE
            USERNAME = #{username}
    ''')
    void updateSignon(Account account)

}
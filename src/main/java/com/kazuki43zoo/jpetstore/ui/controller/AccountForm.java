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
package com.kazuki43zoo.jpetstore.ui.controller;

import com.kazuki43zoo.jpetstore.component.validation.NumericCharacters;
import com.kazuki43zoo.jpetstore.component.validation.RepeatedField;
import com.kazuki43zoo.jpetstore.component.validation.TelephoneNumber;
import com.kazuki43zoo.jpetstore.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Kazuki Shimizu
 */
@RepeatedField(field = "password")
@Getter
@Setter
public class AccountForm implements Serializable {
	private static final long serialVersionUID = 925786404136765833L;

	@NotNull(groups = Create.class)
	@Size(max = 40)
	private String username;

	@NotNull(groups = Create.class)
	@Size(min = 8)
	private String password;

	private String repeatedPassword;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(max = 40)
	private String firstName;

	@NotNull
	@Size(max = 40)
	private String lastName;

	@NotNull
	@Size(max = 40)
	private String address1;

	@Size(max = 40)
	private String address2;

	@NotNull
	@Size(max = 40)
	private String city;

	@NotNull
	@Size(max = 40)
	private String state;

	@NotNull
	@Size(max = 20)
	@NumericCharacters
	private String zip;

	@NotNull
	@Size(max = 40)
	private String country;

	@NotNull
	@Size(max = 40)
	@TelephoneNumber
	private String phone;

	@NotNull
	@Size(max = 30)
	private String favouriteCategoryId;

	@NotNull
	@Size(max = 40)
	private String languagePreference;

	private boolean listOption;

	private boolean bannerOption;

	Account toAccount() {
		Account account = new Account();
		BeanUtils.copyProperties(this, account);
		return account;
	}

	interface Create {}

}

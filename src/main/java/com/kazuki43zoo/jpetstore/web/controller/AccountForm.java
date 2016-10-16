package com.kazuki43zoo.jpetstore.web.controller;

import com.kazuki43zoo.jpetstore.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class AccountForm implements Serializable {
	private static final long serialVersionUID = 925786404136765833L;

	interface Create {}

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
	private String zip;
	@NotNull
	@Size(max = 40)
	private String country;
	@NotNull
	@Size(max = 40)
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
}

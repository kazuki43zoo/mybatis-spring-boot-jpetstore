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
import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.domain.Order;
import lombok.Getter;
import lombok.Setter;
import com.kazuki43zoo.jpetstore.ui.Cart;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Kazuki Shimizu
 */
@Getter
@Setter
public class OrderForm implements Serializable {

	private static final long serialVersionUID = 6321792448424424931L;

	@NotNull
	@Size(max = 40)
	private String cardType;

	@NotNull
	@Size(max = 80)
	@NumericCharacters
	private String creditCard;

	@NotNull
	@Pattern(regexp = "^\\d{2}/\\d{4}$")
	private String expiryDate;

	@NotNull
	@Size(max = 40)
	private String billToFirstName;

	@NotNull
	@Size(max = 40)
	private String billToLastName;

	@Size(max = 40)
	private String billAddress1;

	@Size(max = 40)
	private String billAddress2;

	@Size(max = 40)
	private String billCity;

	@Size(max = 40)
	private String billState;

	@Size(max = 20)
	@NumericCharacters
	private String billZip;

	@Size(max = 20)
	private String billCountry;

	private boolean shippingAddressRequired;

	@NotNull
	@Size(max = 40)
	private String shipToFirstName;

	@NotNull
	@Size(max = 40)
	private String shipToLastName;

	@NotNull
	@Size(max = 40)
	private String shipAddress1;

	@Size(max = 40)
	private String shipAddress2;

	@NotNull
	@Size(max = 40)
	private String shipCity;

	@Size(max = 40)
	private String shipState;

	@NotNull
	@Size(max = 20)
	@NumericCharacters
	private String shipZip;

	@NotNull
	@Size(max = 20)
	private String shipCountry;

	void initialize(Account account) {

		this.billToFirstName = account.getFirstName();
		this.billToLastName = account.getLastName();
		this.billAddress1 = account.getAddress1();
		this.billAddress2 = account.getAddress2();
		this.billCity = account.getCity();
		this.billState = account.getState();
		this.billZip = account.getZip();
		this.billCountry = account.getCountry();

		this.shipToFirstName = account.getFirstName();
		this.shipToLastName = account.getLastName();
		this.shipAddress1 = account.getAddress1();
		this.shipAddress2 = account.getAddress2();
		this.shipCity = account.getCity();
		this.shipState = account.getState();
		this.shipZip = account.getZip();
		this.shipCountry = account.getCountry();

	}

	Order toOrder(Cart cart) {
		Order order = new Order();
		BeanUtils.copyProperties(this, order);
		order.setTotalPrice(cart.getSubTotal());
		cart.getCartItems().forEach(x -> order.addLine(x.toOrderLine()));
		return order;
	}

}

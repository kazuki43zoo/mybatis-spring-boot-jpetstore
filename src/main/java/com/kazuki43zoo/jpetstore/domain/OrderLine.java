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
package com.kazuki43zoo.jpetstore.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Kazuki Shimizu
 */
@Getter
@Setter
public class OrderLine implements Serializable {

	private static final long serialVersionUID = 6804536240033522156L;

	private int orderId;
	private int lineNumber;
	private int quantity;
	private String itemId;
	private BigDecimal unitPrice;
	private Item item;
	private BigDecimal total;

	public void setItem(Item item) {
		this.item = item;
		calculateTotal();
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		calculateTotal();
	}

	private void calculateTotal() {
		this.total = null;
		Optional.ofNullable(item)
				.filter(x -> x.getListPrice() != null)
				.map(x -> x.getListPrice().multiply(new BigDecimal(quantity)))
				.ifPresent(x -> this.total = x);
	}

}

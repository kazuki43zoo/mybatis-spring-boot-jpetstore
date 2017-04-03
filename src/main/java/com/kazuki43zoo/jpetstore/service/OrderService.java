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
package com.kazuki43zoo.jpetstore.service;

import com.kazuki43zoo.jpetstore.component.exception.ResourceNotFoundException;
import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.domain.Item;
import com.kazuki43zoo.jpetstore.domain.Order;
import com.kazuki43zoo.jpetstore.domain.Sequence;
import com.kazuki43zoo.jpetstore.mapper.ItemMapper;
import com.kazuki43zoo.jpetstore.mapper.OrderMapper;
import com.kazuki43zoo.jpetstore.mapper.SequenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Kazuki Shimizu
 */
@Service
@RequiredArgsConstructor
public class OrderService {
	private static final String ORDER_ID_SEQ_NAME = "ordernum";
	private final ItemMapper itemMapper;
	private final OrderMapper orderMapper;
	private final SequenceMapper sequenceMapper;
	private final Clock clock;

	@Transactional
	public void createOrder(Order order, Account account) {

		order.setUsername(account.getUsername());

		int orderId = generateOrderId();
		order.setOrderId(orderId);
		order.setOrderDate(LocalDateTime.now(clock));
		order.setCourier("UPS");
		order.setLocale("CA");
		order.setStatus("P");

		orderMapper.insertOrder(order);
		orderMapper.insertOrderStatus(order);

		order.getLines().forEach(x -> {
			itemMapper.updateInventoryQuantity(x.getItemId(), x.getQuantity());
			x.setOrderId(orderId);
			orderMapper.insertOrderLine(x);
		});

	}

	@Transactional
	public Order getOrder(String username, int orderId) {
		Order order = Optional.ofNullable(orderMapper.getOrder(orderId))
				.filter(x -> x.getUsername().equals(username))
				.orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
		order.setLines(orderMapper.getOrderLines(orderId));
		order.getLines().forEach(x -> {
			Item item = itemMapper.getItem(x.getItemId());
			item.setQuantity(itemMapper.getInventoryQuantity(x.getItemId()));
			x.setItem(item);
		});
		return order;
	}

	public List<Order> getOrdersByUsername(String username) {
		return orderMapper.getOrdersByUsername(username);
	}

	private int generateOrderId() {
		Sequence sequence = Optional.ofNullable(sequenceMapper.getSequence(ORDER_ID_SEQ_NAME))
				.orElseThrow(() -> new RuntimeException("Error: A null sequence was returned from the database (could not get next "
						+ ORDER_ID_SEQ_NAME + " sequence)."));
		sequenceMapper.incrementSequence(sequence.getName());
		return sequence.getNextId();
	}

}

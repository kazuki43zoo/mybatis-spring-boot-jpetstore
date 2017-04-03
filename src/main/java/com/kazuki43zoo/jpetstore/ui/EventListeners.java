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
package com.kazuki43zoo.jpetstore.ui;

import com.kazuki43zoo.jpetstore.component.event.EntityChangedEvent;
import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.domain.Product;
import com.kazuki43zoo.jpetstore.service.AccountUserDetails;
import com.kazuki43zoo.jpetstore.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kazuki Shimizu
 */
@Component
@RequiredArgsConstructor
public class EventListeners {

	private final CatalogService catalogService;
	private final Favourite favourite;

	@EventListener
	public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		Account account = ((AccountUserDetails) event.getAuthentication().getPrincipal()).getAccount();
		loadFavouriteProductList(account);
	}

	@EventListener
	public void handleAccountChangedEvent(EntityChangedEvent<Account> event) {
		Account account = event.getEntity();
		loadFavouriteProductList(account);
	}

	private void loadFavouriteProductList(Account account) {
		if (account.isListOption()) {
			List<Product> productList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
			favourite.setProductList(productList);
		} else {
			favourite.setProductList(null);
		}
	}

}

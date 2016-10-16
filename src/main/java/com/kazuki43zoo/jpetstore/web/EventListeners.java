package com.kazuki43zoo.jpetstore.web;

import com.kazuki43zoo.jpetstore.component.EntityChangedEvent;
import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.domain.Product;
import com.kazuki43zoo.jpetstore.service.AccountUserDetails;
import com.kazuki43zoo.jpetstore.service.CatalogService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kazuki Shimizu
 */
@Component
public class EventListeners {

	private final CatalogService catalogService;
	private final Favourite favourite;

	public EventListeners(CatalogService catalogService, Favourite favourite) {
		this.catalogService = catalogService;
		this.favourite = favourite;
	}

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

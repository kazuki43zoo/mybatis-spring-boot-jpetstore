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

import com.kazuki43zoo.jpetstore.domain.Product;
import com.kazuki43zoo.jpetstore.ui.ProductSearchCriteria;
import com.kazuki43zoo.jpetstore.domain.Category;
import com.kazuki43zoo.jpetstore.domain.Item;
import com.kazuki43zoo.jpetstore.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Kazuki Shimizu
 */
@RequestMapping("/catalog")
@Controller
@RequiredArgsConstructor
public class CatalogController {

	private final CatalogService catalogService;
	private final ProductSearchCriteria productSearchCriteria;

	@GetMapping
	public String viewCatalog() {
		return "catalog/catalog";
	}

	@GetMapping("/categories/{categoryId}")
	public String viewCategory(@PathVariable String categoryId, Model model) {
		Category category = catalogService.getCategory(categoryId);
		List<Product> productList = catalogService.getProductListByCategory(categoryId);
		model.addAttribute(category);
		model.addAttribute(productList);
		return "catalog/category";
	}

	@GetMapping("/products")
	public String searchProducts(@RequestParam(defaultValue = "") String keywords, Model model) {
		productSearchCriteria.setKeywords(keywords);
		List<Product> productList = catalogService.searchProductList(productSearchCriteria.getKeywords());
		model.addAttribute(productList);
		return "catalog/products";
	}

	@GetMapping("/products/{productId}")
	public String viewProduct(@PathVariable String productId, Model model) {
		Product product = catalogService.getProduct(productId);
		List<Item> itemList = catalogService.getItemListByProduct(productId);
		model.addAttribute(product);
		model.addAttribute(itemList);
		return "catalog/product";
	}

	@GetMapping("/items/{itemId}")
	public String viewItem(@PathVariable String itemId, Model model) {
		Item item = catalogService.getItem(itemId);
		model.addAttribute(item);
		return "catalog/item";
	}

}

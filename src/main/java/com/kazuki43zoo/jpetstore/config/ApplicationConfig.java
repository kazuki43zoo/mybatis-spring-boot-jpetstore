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
package com.kazuki43zoo.jpetstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kazuki Shimizu
 */
@Configuration
public class ApplicationConfig {

	@Bean
	protected Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Bean
	protected List<String> clCreditCardTypes() {
		List<String> cardList = new ArrayList<>();
		cardList.add("Visa");
		cardList.add("MasterCard");
		cardList.add("American Express");
		return Collections.unmodifiableList(cardList);
	}

	@Bean
	protected List<String> clCategories() {
		List<String> categoryList = new ArrayList<>();
		categoryList.add("FISH");
		categoryList.add("DOGS");
		categoryList.add("REPTILES");
		categoryList.add("CATS");
		categoryList.add("BIRDS");
		return Collections.unmodifiableList(categoryList);
	}

	@Bean
	protected List<String> clLanguages() {
		List<String> languageList = new ArrayList<>();
		languageList.add("English");
		languageList.add("Japanese");
		return Collections.unmodifiableList(languageList);
	}

}
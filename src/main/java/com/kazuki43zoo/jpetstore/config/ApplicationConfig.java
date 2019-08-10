/*
 *    Copyright 2016-2019 the original author or authors.
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

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author Kazuki Shimizu
 */
@Configuration
public class ApplicationConfig {

  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
  }

  @Bean
  List<String> clCreditCardTypes() {
    return Arrays.asList("Visa", "MasterCard", "American Express");
  }

  @Bean
  List<String> clCategories() {
    return Arrays.asList("FISH", "DOGS", "REPTILES", "CATS", "BIRDS");
  }

  @Bean
  List<String> clLanguages() {
		return Arrays.asList("English", "Japanese");
  }

}
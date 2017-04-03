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

import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Kazuki Shimizu
 */
@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void createAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountMapper.insertAccount(account);
		accountMapper.insertProfile(account);
		accountMapper.insertSignon(account);
	}

	@Transactional
	public void updateAccount(Account account, String newPassword) {
		accountMapper.updateAccount(account);
		accountMapper.updateProfile(account);

		Optional.ofNullable(newPassword).ifPresent(x -> {
			account.setPassword(passwordEncoder.encode(x));
			accountMapper.updateSignon(account);
		});
	}

}

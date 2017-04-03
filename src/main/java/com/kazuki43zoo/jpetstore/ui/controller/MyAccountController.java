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

import com.kazuki43zoo.jpetstore.component.message.Messages;
import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.service.AccountService;
import com.kazuki43zoo.jpetstore.component.event.EntityChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Kazuki Shimizu
 */
@RequestMapping("/my/account")
@Controller
@RequiredArgsConstructor
public class MyAccountController {

	private final AccountService accountService;
	private final ApplicationEventPublisher publisher;

	@ModelAttribute
	public AccountForm setUpForm() {
		return new AccountForm();
	}

	@GetMapping(path = "/update", params = "form")
	public String updateForm(AccountForm form, @AuthenticationPrincipal(expression = "account") Account account) {
		BeanUtils.copyProperties(account, form, "password");
		return "account/updateForm";
	}

	@PostMapping("/update")
	public String update(@Validated AccountForm form, BindingResult result,
						 @AuthenticationPrincipal(expression = "account") Account account,
						 Model model, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute(
					new Messages().error("Input values are invalid. Please confirm error messages."));
			return "account/updateForm";
		}

		BeanUtils.copyProperties(form, account, "username", "password");

		accountService.updateAccount(account, form.getPassword());

		publisher.publishEvent(new EntityChangedEvent<>(this, account));

		redirectAttributes.addFlashAttribute(
				new Messages().success("Your account has been updated."));

		return "redirect:/my/account/update?form";
	}

}

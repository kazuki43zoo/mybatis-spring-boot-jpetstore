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
package com.kazuki43zoo.jpetstore.component.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author Kazuki Shimizu
 */
public class RepeatedFieldValidator implements ConstraintValidator<RepeatedField, Object> {

	private String field;
	private String repeatedField;
	private String message;

	public void initialize(RepeatedField constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.repeatedField = "repeated" + StringUtils.capitalize(field);
		this.message = constraintAnnotation.message();
	}

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object fieldValue = beanWrapper.getPropertyValue(field);
		Object repeatedFieldValue = beanWrapper.getPropertyValue(repeatedField);
		boolean matched = Objects.equals(fieldValue, repeatedFieldValue);
		if (matched) {
			return true;
		} else {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(repeatedField).addConstraintViolation();
			return false;
		}
	}
}

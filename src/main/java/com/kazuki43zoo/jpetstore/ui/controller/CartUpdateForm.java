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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kazuki Shimizu
 */
@Getter
@Setter
public class CartUpdateForm implements Serializable {
	private static final long serialVersionUID = 3518654576948095255L;

	@Valid
	private List<Line> lines = new ArrayList<>();

	public void addItemLine(String itemId, Integer quantity) {
		lines.add(new Line(itemId, quantity));
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Line implements Serializable {
		private static final long serialVersionUID = 6844663228538160416L;
		@NotNull
		private String itemId;

		@NotNull
		@Min(0)
		private Integer quantity;

	}

}

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
package com.kazuki43zoo.jpetstore.component.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Kazuki Shimizu
 */
public class Messages implements Iterable<Messages.Message> {

	private final List<Message> values = new ArrayList<>();

	@Override
	public Iterator<Message> iterator() {
		return values.iterator();
	}

	@Override
	public void forEach(Consumer<? super Message> action) {
		values.forEach(action);
	}

	@Override
	public Spliterator<Message> spliterator() {
		return values.spliterator();
	}

	public Messages error(String message) {
		values.add(new Message("error", message));
		return this;
	}

	public Messages success(String message) {
		values.add(new Message("success", message));
		return this;
	}

	public Messages info(String message) {
		values.add(new Message("info", message));
		return this;
	}

	public Messages warn(String message) {
		values.add(new Message("warn", message));
		return this;
	}

	public static class Message {
		private final String type;
		private final String text;
		private Message(String type, String text) {
			this.type = type;
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public String getText() {
			return text;
		}
	}
}

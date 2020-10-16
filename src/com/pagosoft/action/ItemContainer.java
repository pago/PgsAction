/*
 * Copyright 2005 Patrick Gotthardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pagosoft.action;

/**
 * This subclass of {@link ActionContainer} allows its items to
 * be any kind of object. If you decide to use this, make sure
 * you specified a specialized ComponentProvider.
 *
 * @author Patrick Gotthardt
 * @date 09.03.2006 14:42:14
 */
public class ItemContainer extends ActionContainer {
	public ItemContainer() {
		this(null);
	}

	public ItemContainer(String id) {
		super(id);
	}

	/**
	 * Add an object to the end of the list. Subclasses are allowed
	 * to change this behaviour.
	 * @param act
	 */
	public void add(Object act) {
		actionList.add(act);
		int index = actionList.indexOf(act);
		fireIntervalAdded(this, index, index);
	}

	/**
	 * Add the object at the specified index.
	 *
	 * Subclasses are allowed to change this behaviour to implement
	 * a sorted menu, for example.
	 * @param index
	 * @param act
	 */
	public void add(int index, Object act) {
		actionList.add(index, act);
		fireIntervalAdded(this, index, index);
	}

	public void remove(Object act) {
		remove(actionList.indexOf(act));
	}

	public int indexOf(Object act) {
		return actionList.indexOf(act);
	}
}

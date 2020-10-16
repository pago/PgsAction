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

import java.awt.event.*;

/**
 *
 * @author Patrick Gotthardt
 */
public class ContextSensitiveStateAction extends ContextSensitiveAction implements StateAction {
	private StateAction delegate;

	public ContextSensitiveStateAction(StateAction delegate, Object context) {
		super(delegate, context);
		this.delegate = delegate;
	}

	public ActionGroup getGroup() {
		return delegate.getGroup();
	}

	public boolean isSelected() {
		return delegate.isSelected();
	}

	public void setGroup(ActionGroup group) {
		delegate.setGroup(group);
	}

	public void setSelected(boolean selected) {
		delegate.setSelected(selected);
	}

	public void itemStateChanged(ItemEvent e) {
		delegate.itemStateChanged(e);
	}
}

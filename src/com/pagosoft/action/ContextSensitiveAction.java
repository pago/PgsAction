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

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.awt.event.*;

/**
 * <p>This class solves as a simple proxy that changes the source-value
 * of the ActionEvent to the context specified in the constructor.</p>
 * <p>There's no need to initialize this one yourself - in most cases.
 * The context sensitive component providers will take care of this
 * for you.</p>
 *
 * @author Patrick Gotthardt
 */
public class ContextSensitiveAction implements Action {
	private Action delegate;
	private Object context;

	public ContextSensitiveAction(Action delegate, Object context) {
		this.context = context;
		this.delegate = delegate;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	public Object getValue(String key) {
		return delegate.getValue(key);
	}

	public boolean isEnabled() {
		return delegate.isEnabled();
	}

	public void putValue(String key, Object value) {
		delegate.putValue(key, value);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

	public void setEnabled(boolean b) {
		delegate.setEnabled(b);
	}

	public void actionPerformed(ActionEvent e) {
		delegate.actionPerformed(new ActionEvent(context, e.getID(), e.getActionCommand()));
	}
}

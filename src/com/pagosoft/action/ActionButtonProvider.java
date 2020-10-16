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

import com.pagosoft.swing.ComponentProvider;

import javax.swing.*;

/**
 * <p>Default implementation of {@link ComponentProvider} for toolbars.</p>
 * <p>It is a Singleton, so don't try to create an instance of it yourself.</p>
 *
 * @author Patrick Gotthardt
 */
public class ActionButtonProvider implements ComponentProvider {
	public AbstractButton createRadioButton(StateAction stateAction) {
		return new JRadioButton(stateAction);
	}

	public AbstractButton createToggleButton(StateAction stateAction) {
		return new JToggleButton(stateAction);
	}

	public AbstractButton createButton(Action obj) {
		return new JButton(obj);
	}

	public JComponent createUnknown(Object obj) {
		throw new IllegalArgumentException("Parameter must be an Action or a JComponent or null.");
	}

	public JComponent createComponent(Object obj) {
		if(obj instanceof SeparatorAction || obj == null) {
			return new JToolBar.Separator();
		} else if(obj instanceof StateAction) {
			StateAction stateAction = (StateAction) obj;
			AbstractButton item;
			if(stateAction.getGroup() != null) {
				item = createRadioButton(stateAction);
			} else {
				item = createToggleButton(stateAction);
			}
			item.setSelected(stateAction.isSelected());
			item.addItemListener(stateAction);
			stateAction.addPropertyChangeListener(new SelectedPropertyChangeListener(item));
			return item;
		} else if(obj instanceof Action) {
			return createButton((Action)obj);
		} else if(obj instanceof JComponent) {
			return (JComponent)obj;
		}
		return createUnknown(obj);
	}

	private static ComponentProvider INSTANCE;
	public static ComponentProvider getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ActionButtonProvider();
		}
		return INSTANCE;
	}

	protected ActionButtonProvider() {}
}
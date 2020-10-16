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
import com.pagosoft.swing.ListModelBinder;

import javax.swing.*;

/**
 * <p>This is the default implementation of {@link com.pagosoft.swing.ComponentProvider}
 * to create menus. It's a singleton.</p>
 *
 * @author Patrick Gotthardt
 */
public class ActionMenuProvider implements ComponentProvider {
	protected JComponent createMenu(ListModel obj) {
		if(obj instanceof ComponentCreator) {
			return ((ComponentCreator)obj).createMenu(this);
		}
		JMenu menu = new JMenu((Action)obj);
		ListModelBinder.install(menu, obj, this);
		return menu;
	}

	protected JMenuItem createMenuItem(Action obj) {
		return new JMenuItem(obj);
	}

	protected JRadioButtonMenuItem createRadioButtonMenuItem(StateAction stateAction) {
		return new JRadioButtonMenuItem(stateAction);
	}

	protected JCheckBoxMenuItem createCheckBoxMenuItem(StateAction stateAction) {
		return new JCheckBoxMenuItem(stateAction);
	}

	protected JComponent createUnknown(Object obj) {
		throw new IllegalArgumentException("Parameter must be an Action or a JComponent or null.");
	}

	public JComponent createComponent(Object obj) {
		if(obj instanceof ListModel) {
			return createMenu((ListModel)obj);
		} else if(obj instanceof SeparatorAction || obj == null) {
			return new JPopupMenu.Separator();
		} else if(obj instanceof StateAction) {
			StateAction stateAction = (StateAction) obj;
			AbstractButton item;
			if(stateAction.getGroup() != null) {
				item = createRadioButtonMenuItem(stateAction);
			} else {
				item = createCheckBoxMenuItem(stateAction);
			}
			item.setSelected(stateAction.isSelected());
			item.addItemListener(stateAction);
			stateAction.addPropertyChangeListener(new SelectedPropertyChangeListener(item));
			return item;
		} else if(obj instanceof Action) {
			return createMenuItem((Action)obj);
		} else if(obj instanceof JComponent) {
			return (JComponent)obj;
		}
		return createUnknown(obj);
	}

	private static ComponentProvider INSTANCE;
	public static ComponentProvider getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ActionMenuProvider();
		}
		return INSTANCE;
	}

	protected ActionMenuProvider() {}
}

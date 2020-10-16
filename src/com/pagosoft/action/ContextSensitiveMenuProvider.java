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

import com.pagosoft.swing.ListModelBinder;

import javax.swing.*;

/**
 * <p>This class utilizes {@link ContextSensitiveAction} as a proxy.</p>
 * <p>Every action that is realized by this class will get an {@link java.awt.event.ActionEvent}
 * whose {@link java.awt.event.ActionEvent#getSource}-method will return
 * the specified context.</p>
 * 
 * @author Patrick Gotthardt
 */
public class ContextSensitiveMenuProvider extends ActionMenuProvider {
	private Object context;

	public ContextSensitiveMenuProvider(Object context) {
		super();
		this.context = context;
	}

	protected JComponent createMenu(ListModel obj) {
		if(obj instanceof ComponentCreator) {
			return ((ComponentCreator)obj).createMenu(this);
		}
		JMenu menu = new JMenu(new ContextSensitiveAction((Action)obj, context));
		if(obj instanceof ContextAwareAction) {
			((ContextAwareAction)obj).addContext(context, menu);
		}
		ListModelBinder.install(menu, obj, this);
		return menu;
	}

	protected JMenuItem createMenuItem(Action obj) {
		JMenuItem menu = new JMenuItem(new ContextSensitiveAction(obj, context));
		if(obj instanceof ContextAwareAction) {
			((ContextAwareAction)obj).addContext(context, menu);
		}
		return menu;
	}

	protected JRadioButtonMenuItem createRadioButtonMenuItem(StateAction stateAction) {
		JRadioButtonMenuItem menu = new JRadioButtonMenuItem(new ContextSensitiveStateAction(stateAction, context));
		if(stateAction instanceof ContextAwareAction) {
			((ContextAwareAction)stateAction).addContext(context, menu);
		}
		return menu;
	}

	protected JCheckBoxMenuItem createCheckBoxMenuItem(StateAction stateAction) {
		JCheckBoxMenuItem menu = new JCheckBoxMenuItem(new ContextSensitiveStateAction(stateAction, context));
		if(stateAction instanceof ContextAwareAction) {
			((ContextAwareAction)stateAction).addContext(context, menu);
		}
		return menu;
	}
}

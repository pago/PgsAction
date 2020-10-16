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
import java.awt.event.ItemEvent;

/**
 * <p>A simple implementation of the {@link StateAction}-interface.</p>
 * <p>The {@link #setGroup}-method will take care of registering the
 * action to the group, so you won't need to call <code>group.add(action)</code>
 * yourself.</p>
 *
 * @author Patrick Gotthardt
 */
public abstract class AbstractStateAction extends AbstractSystemAction implements StateAction {
	private ActionGroup group;
	private boolean selected;

	public AbstractStateAction() {
		this(null);
	}

	public AbstractStateAction(String id) {
		this(id, null);
	}

	public AbstractStateAction(String id, ActionGroup group) {
		this(id, group, false);
	}

	public AbstractStateAction(String id, ActionGroup group, boolean selected) {
		super(id);
		setGroup(group);
		setSelected(selected);
	}

	public ActionGroup getGroup() {
		return group;
	}

	public void setGroup(ActionGroup group) {
		ActionGroup oldGroup = this.group;
		if(oldGroup != null) {
			oldGroup.remove(this);
		}
		this.group = group;
		if(group != null) {
			group.add(this);
		}
		if(group != oldGroup) {
			firePropertyChange(GROUP, oldGroup, group);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if(selected && group != null) {
			group.unselectedOthers(this);
		}
		Boolean oldSelected = this.selected ? Boolean.TRUE : Boolean.FALSE;
		this.selected = selected;
		firePropertyChange(SELECTED, oldSelected, selected ? Boolean.TRUE : Boolean.FALSE);
	}

	public void itemStateChanged(ItemEvent e) {
		setSelected(((AbstractButton)e.getItem()).isSelected());
	}
}

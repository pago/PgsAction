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
import java.beans.PropertyChangeEvent;

/**
 * @author Patrick Gotthardt
 */
class SelectedPropertyChangeListener implements PropertyChangeListener {
	private AbstractButton button;

	public SelectedPropertyChangeListener(AbstractButton button) {
		this.button = button;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean selected = ((Boolean)evt.getNewValue()).booleanValue();
		if(button.isSelected() != selected) {
			button.setSelected(selected);
		}
	}
}

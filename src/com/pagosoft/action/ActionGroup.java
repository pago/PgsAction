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

import java.util.List;
import java.util.ArrayList;

/**
 * <p>This class is used to deal with {@link StateAction}s.</p>
 * <p>Just create an instance of it, add all StateActions to it that
 * belong to the created group and you're almost done. The StateAction
 * must know its group as well so you'll have to set it there as well.</p>
 * <p>A StateAction that owns an ActionGroup will be translated into a
 * JRadioButton or an JRadioButtonMenuItem by the default ActionProvider
 * implementations.</p>
 *
 * @author Patrick Gotthardt
 */
public class ActionGroup {
	private List actions;

	public ActionGroup() {
		this.actions = new ArrayList();
	}

	public void add(StateAction act) {
		actions.add(act);
	}

	public void setSelected(StateAction act) {
		int size = actions.size();
		for(int i = 0; i < size; i++) {
			StateAction action = (StateAction)actions.get(i);
			action.setSelected(action == act);
		}
	}

	public void unselectedOthers(StateAction act) {
		int size = actions.size();
		for(int i = 0; i < size; i++) {
			StateAction action = (StateAction)actions.get(i);
			if(action != act) {
				action.setSelected(false);
			}
		}
	}

	public void remove(StateAction action) {
		actions.remove(action);
	}
}

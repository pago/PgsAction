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
import java.util.Stack;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Simplifies creation of {@link ActionContainer}s with a fluent interface.
 *
 * Example:
 * <code>ResourceBundle bundle = ResourceBundle.getBundle("com.pagosoft.action.demobuilder.Bundle");
		JMenuBar mb = new ContainerBuilder(bundle)
				.container("main")
					.container("file")
						.add(OpenAction.class)
						.add(CloseAction.class)
						.addSeparator()
						.add(new DemoActionObject().getAction("exit"))
						.done()
					.done()
				.getContainer()
			.createMenuBar();</code>
 *
 * Do not forget to call {@link #done()} to finish the creation of one container
 * and to go to its parent.
 * You should keep a formatting the like one shown above to make your
 * code easy to read.
 *
 * @author Patrick Gotthardt
 * @date 12.03.2006 15:49:52
 */
public class ContainerBuilder {
	private Stack stack;
	private ResourceBundle bundle;
	private ActionManager manager;

	public ContainerBuilder() {
		this(null);
	}

	public ContainerBuilder(ResourceBundle bundle) {
		this(bundle, ActionManager.getDefaultInstance());
	}

	public ContainerBuilder(ResourceBundle bundle, ActionManager manager) {
		this.bundle = bundle;
		this.manager = manager;
		stack = new Stack<ActionContainer>();
	}

	public ContainerBuilder(ResourceBundle bundle, ActionContainer root) {
		this(bundle, root.getManager());
		stack.push(root);
	}

	public ContainerBuilder container(String id) {
		stack.push(new ActionBuilderContainer(id, bundle));
		return this;
	}

	public ContainerBuilder container(Class ac) {
		try {
			stack.push(manager.get(ac));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public ContainerBuilder container(ActionContainer ac) {
		stack.push(ac);
		return this;
	}

	public ContainerBuilder add(Class action) {
		((ActionContainer)stack.peek()).add(manager.get(action));
		return this;
	}

	public ContainerBuilder add(Action action) {
		((ActionContainer)stack.peek()).add(action);
		return this;
	}

	public ContainerBuilder addSeparator() {
		((ActionContainer)stack.peek()).addSeparator();
		return this;
	}

	public ContainerBuilder done() {
		if(stack.size() > 1) {
			ActionContainer child = (ActionContainer) stack.pop();
			((ActionContainer)stack.peek()).add(child);
		}
		return this;
	}

	public ActionContainer getContainer() {
		return (ActionContainer)stack.pop();
	}

	private static class ActionBuilderContainer extends ActionContainer {
		private ResourceBundle bundle;

		public ActionBuilderContainer(String id, ResourceBundle bundle) {
			super();
			this.bundle = bundle;
			setId(id);
		}

		protected String getI18NString(String id) {
			if(bundle == null) {
				Logger.getLogger("com.pagosoft.action.ContainerBuilder").warning("No bundle specified");
				return "";
			}
			try {
				return bundle.getString(id);
			} catch(Exception e) {
				Logger.getLogger("com.pagosoft.action.ContainerBuilder").warning("Could not find key for "+id);
			}
			return "";
		}
	}
}

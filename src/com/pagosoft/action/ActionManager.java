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
import java.util.Map;
import java.util.HashMap;
import java.awt.event.*;

/**
 * <p>In PgsAction, actions are supposed to be singletons.</p>
 * <p>This class supports you in managing these singletons.
 * To make things as easy as possible we're using {@link java.lang.Class}
 * as the key for the internally used Map. This removes possible
 * problems with namespace-collisions, simplifies refactorings
 * and should be smaller and more memory-friendly than the string-alternative.</p>
 * <p>It also removes the need of registering an Action to the Manager before
 * getting its instance. It'll be generated on the fly as you pass in its class-object.</p>
 *
 * <p>A simple example of how to use this class:</p>
 * <pre><code>Action demo = ActionManager.getDefaultInstance().get(DemoAction.class);</code></pre>
 *
 * <p>There is one method that should be used with extreme caution.
 * {@link #getContainer} will only type-case the result of {@link #get}.
 * So it is likely to fire a TypeCastException if the class you passed
 * in is not the key of a ActionContainer instance. Please be
 * carefull when using this method. It only made it into this
 * package to avoide ugly constructs like this one:</p>
 * <pre><code>((ActionContainer)ActionManager.getDefaultInstance().get(DemoContainer.class)).createMenu();</code></pre>
 * <p>Using the method this would look like this:</p>
 * <pre><code>ActionManager.getDefaultInstance().getContainer(DemoAction.class).createMenu();</code></pre>
 * <p>Not much smaller, but looking way better.</p>
 *
 * <p>You may want to get the instance of an Action based on a class
 * that is in fact not an Action itself. If you'd like to do this
 * you'll find the {@link #put}-method very useful.</p>
 * <p>Use {@link #remove} to remove actions you won't need anymore.</p>
 *
 * @author Patrick Gotthardt
 */
public class ActionManager {
	private static ActionManager defaultInstance;
	public static ActionManager getDefaultInstance() {
		if(defaultInstance == null) {
			defaultInstance = new ActionManager();
		}
		return defaultInstance;
	}

	public static void setDefaultInstance(ActionManager instance) {
		defaultInstance = instance;
	}

	private Map<Class<? extends Action>, Action> actionMap = new HashMap<Class<? extends Action>, Action>();

	public <K extends Action> K get(Class<K> clazz) {
		K act = (K)actionMap.get(clazz);
		if(act == null) {
			try {
				act = clazz.newInstance();
				actionMap.put(clazz, act);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return act;
	}

	public void put(Class<? extends Action> clazz, Action act) {
		actionMap.put(clazz, act);
	}

	public void remove(Class<? extends Action> clazz) {
		actionMap.remove(clazz);
	}
}

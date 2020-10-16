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
import java.util.Set;
import java.util.Collection;

/**
 * An implementation of the Map-Interface that inspects an object for
 * ActionMethod-annotations and provides access to the methods as Action-object.
 *
 * Sample:
 * <pre><code>// define an action-class
 * class Sample {
 *     @ActionMethod(icon="/some/icon.png")
 *     void handleSomething() {
 *         // do something
 *     }
 * }
 *
 * // use the object
 * ActionObject obj = new ActionObject(new Sample());
 * JButton btn = new JButton(obj.get("handleSomething"));</code></pre>
 *
 * As an alternative your class could also extend ActionObject:
 * <pre><code>class Sample extends ActionObject { ... }
 *
 * Sample obj = new Sample();
 * JButton btn = new JButton(obj.get("handleSomething"));</code></pre>
 *
 * @author Patrick Gotthardt
 * @date 06.03.2006 20:41:13
 */
public class ActionObject implements Map<String,Action> {
	protected Map<String, Action> actionMap;
	private Object actionObject;

	public ActionObject() {
		actionObject = this;
	}

	public ActionObject(Object actionObject) {
		this.actionObject = actionObject;
	}

	private void ensureActionMapExists() {
		if(actionMap == null) {
			actionMap = ActionFactory.createActionMap(actionObject);
		}
	}

	public Action getAction(String id) {
		ensureActionMapExists();
		return actionMap.get(id);
	}

	public boolean hasAction(String id) {
		ensureActionMapExists();
		return actionMap.containsKey(id);
	}

	public int size() {
		ensureActionMapExists();
		return actionMap.size();
	}

	public boolean isEmpty() {
		ensureActionMapExists();
		return actionMap.isEmpty();
	}

	public boolean containsKey(Object key) {
		ensureActionMapExists();
		return actionMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		ensureActionMapExists();
		return actionMap.containsValue(value);
	}

	public Action get(Object key) {
		ensureActionMapExists();
		return actionMap.get(key);
	}

	public Action put(String key, Action value) {
		ensureActionMapExists();
		return actionMap.put(key, value);
	}

	public Action remove(Object key) {
		ensureActionMapExists();
		return actionMap.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Action> t) {
		ensureActionMapExists();
		actionMap.putAll(t);
	}

	public void clear() {
		ensureActionMapExists();
		actionMap.clear();
	}

	public Set<String> keySet() {
		ensureActionMapExists();
		return actionMap.keySet();
	}

	public Collection<Action> values() {
		ensureActionMapExists();
		return actionMap.values();
	}

	public Set<Entry<String, Action>> entrySet() {
		ensureActionMapExists();
		return actionMap.entrySet();
	}
}

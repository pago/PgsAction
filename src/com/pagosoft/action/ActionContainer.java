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
import com.pagosoft.swing.ComponentProvider;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>This class is multiple things at once.
 * First of all it is an implementation of the {@link javax.swing.ListModel}-interface,
 * but it is also a way to create menus and toolbars without having to
 * use {@link com.pagosoft.swing.ListModelBinder} directly.</p>
 * <p>It extends {@link AbstractSystemAction} thus it offers all of its
 * features.</p>
 *
 * <p>A simple example:</p>
 * <pre><code>ActionContainer container = new ActionContainer();
 * container.add(Demo.class);
 * container.add(OtherDemo.class);</code></pre>
 *
 * @author Patrick Gotthardt
 */
public class ActionContainer extends AbstractSystemAction implements ListModel, ComponentCreator {
	protected List actionList;
	private List listenerList;

	public ActionContainer() {
		this(null);
	}

	public ActionContainer(String id) {
		super(id);

		actionList = createActionList();
		listenerList = new ArrayList();
	}

	// just in case we would like to use a different list
	// (for example: to do sorting)
	protected List createActionList() {
		return new ArrayList();
	}

	/**
	 * <p>Overwrite this to use another ActionManager than the default one.</p>
	 * @return The ActionManager which should be used to get an Action based on a class.
	 */
	protected ActionManager getManager() {
		return ActionManager.getDefaultInstance();
	}

	/**
	 * Add an separator to the end of the container.
	 *
	 * @see SeparatorAction
	 */
	public void addSeparator() {
		add(SeparatorAction.class);
	}

	/**
	 * <p>This method will use the ActionManager provided by {@link #getManager}
	 * to get an instance of the Action that should be added.</p>
	 * @param clazz
	 */
	public void add(Class clazz) {
		add(getManager().get(clazz));
	}

	/**
	 * Add an action to the container.
	 *
	 * Most likely it'll be added to the end of the list, but
	 * subclasses are allowed to change this behaviour to implement
	 * a sorted menu, for example.
	 * @param act
	 */
	public void add(Action act) {
		actionList.add(act);
		int index = actionList.indexOf(act);
		fireIntervalAdded(this, index, index);
	}

	/**
	 * Add the action at the specified index.
	 *
	 * Subclasses are allowed to change this behaviour to implement
	 * a sorted menu, for example.
	 * @param index
	 * @param act
	 */
	public void add(int index, Action act) {
		actionList.add(index, act);
		fireIntervalAdded(this, index, index);
	}

	/**
	 * Set the contained actions, all old actions will be removed.
	 * @param act
	 */
	public void setActions(Action[] act) {
		removeAll();
		for (int i = 0; i < act.length; i++) {
			actionList.add(act[i]);
		}
		fireIntervalAdded(this, 0, act.length);
	}

	/**
	 * Remove the action from the container.
	 * @param act
	 */
	public void remove(Action act) {
		remove(actionList.indexOf(act));
	}

	/**
	 * Remove the action at the specified index.
	 * @param index
	 */
	public void remove(int index) {
		actionList.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	/**
	 * Removes all actions from the container.
	 */
	public void removeAll() {
		int size = getSize();
		actionList.clear();
		fireIntervalRemoved(this, 0, size);
	}

	/**
	 * Returns the index of the specified Action.
	 * @param act
	 * @return index of action
	 */
	public int indexOf(Action act) {
		return actionList.indexOf(act);
	}

	/**
	 * Not supposed to be implemented, doesn't do anything
	 * @param e
	 */
	public final void actionPerformed(ActionEvent e) {}

	/**
	 * Returns the size of this container.
	 * @return size of container
	 */
	public int getSize() {
		return actionList.size();
	}

	/**
	 * Returns the element at the specific index.
	 * @param index
	 * @return element at index
	 */
	public Object getElementAt(int index) {
		return actionList.get(index);
	}

	/**
	 * Add a listener to this container
	 * @param l
	 */
	public void addListDataListener(ListDataListener l) {
		listenerList.add(l);
	}

	/**
	 * Remove the listener from this container
	 * @param l
	 */
	public void removeListDataListener(ListDataListener l) {
		listenerList.remove(l);
	}

	protected final void fireContentsChanged(Object source, int index0, int index1) {
		ListDataEvent event = null;
		for(int i = listenerList.size()-1; i > -1; i--) {
			if(event == null) {
				event = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1);
			}
			((ListDataListener)listenerList.get(i)).contentsChanged(event);
		}
	}

	protected final void fireIntervalAdded(Object source, int index0, int index1) {
		ListDataEvent event = null;
		for(int i = listenerList.size()-1; i > -1; i--) {
			if(event == null) {
				event = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
			}
			((ListDataListener)listenerList.get(i)).intervalAdded(event);
		}
	}

	protected final void fireIntervalRemoved(Object source, int index0, int index1) {
		ListDataEvent event = null;
		for(int i = listenerList.size()-1; i > -1; i--) {
			if(event == null) {
				event = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
			}
			((ListDataListener)listenerList.get(i)).intervalRemoved(event);
		}
	}

	/**
	 * Create a menubar using the specified context.
	 * @param context
	 * @return a menubar with the specified context
	 * @see ContextSensitiveMenuProvider
	 */
	public JMenuBar createMenuBar(Object context) {
		return createMenuBar(new ContextSensitiveMenuProvider(context));
	}

	/**
	 * Create a menubar using the specified ComponentProvider.
	 * @param prov
	 * @return a menubar with the specified context
	 * @see ComponentProvider
	 */
	public JMenuBar createMenuBar(ComponentProvider prov) {
		JMenuBar mb = new JMenuBar();
		ListModelBinder.install(mb, this, prov);
		return mb;
	}

	public JToolBar createToolBar(Object context) {
		return createToolBar(new ContextSensitiveButtonProvider(context));
	}

	public JToolBar createToolBar(ComponentProvider prov) {
		JToolBar mb = new JToolBar();
		ListModelBinder.install(mb, this, prov);
		return mb;
	}

	public JMenu createMenu(Object context) {
		return createMenu(new ContextSensitiveMenuProvider(context));
	}

	public JMenu createMenu(ComponentProvider prov) {
		JMenu menu = new JMenu(this);
		ListModelBinder.install(menu, this, prov);
		return menu;
	}

	public JPopupMenu createPopupMenu(Object context) {
		return createPopupMenu(new ContextSensitiveMenuProvider(context));
	}

	public JPopupMenu createPopupMenu(ComponentProvider prov) {
		JPopupMenu menu = new JPopupMenu();
		ListModelBinder.install(menu, this, prov);
		return menu;
	}

	public JMenuBar createMenuBar() {
		return createMenuBar(ActionMenuProvider.getInstance());
	}

	public JToolBar createToolBar() {
		return createToolBar(ActionButtonProvider.getInstance());
	}

	public JMenu createMenu() {
		return createMenu(ActionMenuProvider.getInstance());
	}

	public JPopupMenu createPopupMenu() {
		return createPopupMenu(ActionMenuProvider.getInstance());
	}
}

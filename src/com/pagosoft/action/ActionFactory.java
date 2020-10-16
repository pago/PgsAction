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
import java.util.logging.Logger;
import java.util.logging.Level;
import java.awt.event.*;
import java.lang.reflect.Method;

/**
 * @author Patrick Gotthardt
 * @date 06.03.2006 20:35:57
 */
public class ActionFactory {
	public static Map<String, Action> createActionMap(Object obj) {
		Map<String, Action> map = new HashMap<String, Action>();

		Method[] methods = null;
		try {
			methods = obj.getClass().getDeclaredMethods();
		} catch(Exception e) {
			Logger.getLogger("com.pagosoft.action.ActionFactory").warning("Could not access all members of "+obj);
			methods = obj.getClass().getMethods();
		}
		int length = methods.length;
		AnnotatedAction.currentObject = obj;
		for(int i = 0; i < length; i++) {
			if(methods[i].isAnnotationPresent(ActionMethod.class)) {
				ActionMethod annotation = methods[i].getAnnotation(ActionMethod.class);
				try {
					methods[i].setAccessible(true);
				} catch(SecurityException e) {
					Logger.getLogger("com.pagosoft.action.ActionFactory").log(Level.WARNING, "Could not change accessability", e);
					continue;
				}
				AnnotatedAction.currentMethod = methods[i];
				map.put(annotation.id().length() == 0 ? methods[i].getName() : annotation.id(),
						new AnnotatedAction(obj, methods[i], annotation));
			}
		}

		return map;
	}

	private static class AnnotatedAction extends AbstractSystemAction {
		// this is propably the worst hack I ever wrote...
		private static Method currentMethod;
		private static Object currentObject;

		private Method executedMethod;
		private Object methodObject;
		private ActionMethod annotation;

		public AnnotatedAction(Object methodObject, Method executedMethod, ActionMethod annotation) {
			super(annotation.id().length() == 0 ? executedMethod.getName() : annotation.id());

			if(!"".equals(annotation.icon())) {
				setIcon(annotation.icon());
			}

			if(!"".equals(annotation.label())) {
				MnemonicString string = getMnemonicString(annotation.label());
				putValue(Action.NAME, string.getName());
				if(string.getMnemonic() != ' ') {
					putValue(Action.MNEMONIC_KEY, new Integer(string.getMnemonic()));
				}

				if(string.getKeyStroke() != null) {
					putValue(Action.ACCELERATOR_KEY, string.getKeyStroke());
				}
			}

			this.annotation = annotation;
			this.methodObject = methodObject;
			this.executedMethod = executedMethod;
		}

		protected Object getConfig(String id) {
			if(methodObject == null) {
				methodObject = currentObject;
			}
			return getProvider().getConfig(methodObject, id);
		}

		protected String getI18NString(String id) {
			if(methodObject == null) {
				methodObject = currentObject;
			}
			return getProvider().getString(methodObject, id);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				if(annotation.event()) {
					executedMethod.invoke(methodObject, e);
				} else {
					executedMethod.invoke(methodObject);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
}

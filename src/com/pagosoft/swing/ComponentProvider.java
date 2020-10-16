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
package com.pagosoft.swing;

import javax.swing.*;

/**
 * Classes that implement this interface are supposed to
 * be able to create a component (view) based on an object.
 * In PgsAction this means it'll be able to create a component
 * for each Action within a ListModel.
 *
 * @see com.pagosoft.action.ActionButtonProvider
 * @see com.pagosoft.action.ActionMenuProvider
 * @see com.pagosoft.action.ContextSensitiveButtonProvider
 * @see com.pagosoft.action.ContextSensitiveMenuProvider
 * @author Patrick Gotthardt
 */
public interface ComponentProvider {
	/**
	 * Creates the component based on the object.
	 * As a common rule it should return some kind of
	 * separator or spacer if the parameter is <code>null</code>.
	 *
	 * @param obj
	 * @return The visual representation of the object.
	 */
	public JComponent createComponent(Object obj);
}

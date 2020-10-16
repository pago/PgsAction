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

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>This is the default implementation of an {@link ActionResourceProvider}.</p>
 * <p>See {@link AbstractSystemAction} for an explaintation of its behaviour.</p>
 *
 * @see AbstractSystemAction
 * @author Patrick Gotthardt
 */
public class DefaultActionResourceProvider implements ActionResourceProvider {
	private static Logger logger = Logger.getLogger("com.pagosoft.action.DefaultActionResourceProvider");
	private Map cache = new HashMap();

	public String getString(Object action, String key) {
		Class clazz = action.getClass();
		ResourceBundle bundle = null;
		String bundleName = clazz.getPackage().getName()+".Bundle";
		if(cache.containsKey(clazz)) {
			bundle = (ResourceBundle)cache.get(bundleName);
		} else {
			try {
				bundle = ResourceBundle.getBundle(bundleName);
			} catch(Exception e) {
				logger.warning("No resource bundle for name '"+bundleName+"' found.");
			}
			cache.put(bundleName, bundle);
		}
		if(bundle == null) {
			return "";
		}
		try {
			return bundle.getString(key);
		} catch(Exception e) {
			logger.warning("No key '"+key+"' in bundle '"+bundleName+"' found.");
			return "";
		}
	}

	public Object getConfig(Object action, String id) {
		return null;
	}
}

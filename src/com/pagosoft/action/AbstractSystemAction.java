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

/**
 * <p>AbstractSystemAction is a simple extension of AbstractAction
 * that makes it way easier to include internationalization-support
 * and things like Mnemonics and KeyStrokes.</p>
 *
 * <p>If you want to customize the way it loads those data, you'll have
 * to implement your own {@link ActionResourceProvider}.</p>
 *
 * <p>The default provider will look for a {@link java.util.ResourceBundle}
 * with the name "Bundle" in the package of the action itself. This behaviour
 * is based on what NetBeans does. It'll then try to get a value for a
 * key named like the String you gave as a parameter to the constructor
 * plus a ".name".</p>
 * <p>Here is a small example:</p>
 * <pre><code>package com.pagosoft.demo;
 *
 * import com.pagosoft.action.*;
 * import java.awt.event.*;
 * import javax.swing.*;
 *
 * public class DemoAction extends AbstractSystemAction {
 * 	public DemoAction() {
 * 		super("demo");
 * 	}
 *
 * 	public void actionPerformed(ActionEvent e) {
 * 		// do whatever you like here
 * 	}
 * }
 * </code></pre>
 * <p>And the bundle in the directory <code>com/pagosoft/demo</code>
 * named <code>"Bundle.properties"</code> would look like this:</p>
 * <pre><code>demo.name=&Demo@control D</code></pre>
 * <p>This is a good example of how easy it is to do these things
 * with PgsAction. The <code>&</code> indicates that the character
 * after it is to be used as a Mnemonic. Everything after
 * the <code>@</code> is considered to be the {@link javax.swing.KeyStroke}
 * and will be generated using {@link javax.swing.KeyStroke#getKeyStroke(String)}.</p>
 *
 * <p>You may use {@link ActionResourceProvider#getConfig(AbstractSystemAction, String)}
 * to get a KeyStroke from another place than the ID. This is useful if you want
 * to enable your users to change the keystrokes.</p>
 *
 * @author Patrick Gotthardt
 */
public abstract class AbstractSystemAction extends AbstractAction {
	public static final String ID = "__ID__";
	private static ActionResourceProvider provider;

	public AbstractSystemAction() {
	}

	public AbstractSystemAction(String id) {
		super();
		if(id != null) {
			setId(id);
		}
	}

	public void setId(String id) {
		putValue(ID, id);
		MnemonicString string = getMnemonicString(getI18NString(id+".name"));
		putValue(Action.NAME, string.name);
		if(string.mnemonic != ' ') {
			putValue(Action.MNEMONIC_KEY, new Integer(string.mnemonic));
		}

		if(string.keyStroke != null) {
			putValue(Action.ACCELERATOR_KEY, string.keyStroke);
		}

		// alternative
		String keyStroke = (String) getConfig(id+".accel");
		if(keyStroke != null) {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		}
	}

	public static ActionResourceProvider getProvider() {
		initializeResourceProvider();
		return provider;
	}

	public static void setProvider(ActionResourceProvider provider) {
		AbstractSystemAction.provider = provider;
	}

	private static void initializeResourceProvider() {
		if(provider == null) {
			provider = new DefaultActionResourceProvider();
		}
	}

	protected String getI18NString(String id) {
		initializeResourceProvider();
		return provider.getString(this, id);
	}

	protected Object getConfig(String id) {
		initializeResourceProvider();
		return provider.getConfig(this, id);
	}

	protected Icon getIcon(String icon) {
		return new ImageIcon(getClass().getResource(icon));
	}

	public void setIcon(String icon) {
		putValue(SMALL_ICON, getIcon(icon));
	}

	public static MnemonicString getMnemonicString(String name) {
		MnemonicString string = new MnemonicString();
		int index = name.indexOf('@');
		if(index != -1) {
			// special handling for mac os (use meta instead of control)
			String os = System.getProperty("os.name").toLowerCase();
			// http://developer.apple.com/technotes/tn2002/tn2110.html#PARTONE
			if(os.startsWith("mac os x")) {
				string.keyStroke = KeyStroke.getKeyStroke(name.substring(index+1).replaceAll("control", "meta"));
			} else {
				string.keyStroke = KeyStroke.getKeyStroke(name.substring(index+1));
			}
			name = name.substring(0, index);
		}
		index = name.indexOf('&');
		if(index == -1) {
			string.name = name;
		} else {
			string.mnemonic = name.charAt(index+1);
			string.name = name.substring(0, index) + name.substring(index+1);
		}
		return string;
	}

	public static class MnemonicString {
		private String name;
		private char mnemonic;
		private KeyStroke keyStroke;

		public MnemonicString() {
			name = null;
			mnemonic = ' ';
		}

		public MnemonicString(char mnemonic, String name, KeyStroke keyStroke) {
			this.mnemonic = mnemonic;
			this.name = name;
			this.keyStroke = keyStroke;
		}

		public char getMnemonic() {
			return mnemonic;
		}

		public void setMnemonic(char mnemonic) {
			this.mnemonic = mnemonic;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public KeyStroke getKeyStroke() {
			return keyStroke;
		}

		public void setKeyStroke(KeyStroke keyStroke) {
			this.keyStroke = keyStroke;
		}
	}
}

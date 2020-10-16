package com.pagosoft.action;

import javax.swing.*;

/**
 * Utility class to simplify typical Action-related tasks.
 */
public class ActionUtils {
	/**
	 * Registers an Action on a component, using the specified keystrokes
	 * <strong>Note:</strong> The Action must have an {@link AbstractSystemAction#ID}-Value
	 * @param c The component
	 * @param act The Action
	 * @param ks The keystrokes
	 */
	public static void registerAction(JComponent c, Action act, KeyStroke ... ks) {
		c.getActionMap().put(act.getValue(AbstractSystemAction.ID), act);
		for(KeyStroke k : ks) {
			c.getInputMap().put(k, act.getValue(AbstractSystemAction.ID));
		}
	}

	/**
	 * Transforms all strings into KeyStroke objects using <code>KeyStroke.getKeyStroke(String)</code>.
	 * and passes it to {@link #registerAction(javax.swing.JComponent, javax.swing.Action, javax.swing.KeyStroke[])}.
	 * 
	 * @see #registerAction(javax.swing.JComponent, javax.swing.Action, javax.swing.KeyStroke[])
	 */
	public static void registerAction(JComponent c, Action act, String ... ks) {
		KeyStroke[] strokes = new KeyStroke[ks.length];
		for(int i = 0; i < ks.length; i++) {
			strokes[i] = KeyStroke.getKeyStroke(ks[i]);
		}
		registerAction(c, act, strokes);
	}

	/**
	 * Register the action based on the KeyStroke (or String) object specified by {@link javax.swing.Action#ACCELERATOR_KEY}.
	 */
	public static void registerAction(JComponent c, Action act) {
		Object o = act.getValue(Action.ACCELERATOR_KEY);
		if(!(o instanceof KeyStroke)) {
			if(o instanceof String) {
				o = KeyStroke.getKeyStroke(o.toString());
			} else {
				return;
			}
		}
		KeyStroke ks = (KeyStroke) o;
		if(ks == null) {
			return;
		}
		registerAction(c, act, ks);
	}
}

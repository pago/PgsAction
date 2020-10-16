package com.pagosoft.action;

import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Classes that implement this interface will be realized as
 * components that might have different states
 * (JRadioButton, JToggleButton, etc.).</p>
 *
 * <p>It is very likely that a StateAction with a related
 * {@link ActionGroup} will be realized as a JRadioButton or
 * JRadioButtonMenuItem, while one without will most likely
 * become a JToggleButton or a JCheckBoxMenuItem.</p>
 *
 * <p>So, it is absolutly fine if {@link #getGroup}
 * returns <code>null</code>.</p>
 *
 * <p>Implementations must also implement the
 * <code>java.awt.event.ItemListener</code>-interface
 * and react on itemStateChanged by calling there own
 * {@link #setSelected}-method.</p>
 *
 * @author Patrick Gotthardt
 */
public interface StateAction extends Action, ItemListener {
	public static final String GROUP = "__GROUP__";
	public static final String SELECTED = "__SELECTED__";

	/**
	 * <p>Set the {@link ActionGroup} of this action.</p>
	 * <p>It should also fire an PropertyChangeEvent whose
	 * key is meant to be {@link #GROUP}.</p>
	 * <p>It is up to the implementation to decide if it
	 * registers itself to the {@link ActionGroup} or if
	 * the user has to do it by himself.</p>
	 *
	 * @param group
	 */
	public void setGroup(ActionGroup group);

	/**
	 * <p>This method is supposed to return the
	 * related {@link ActionGroup} or <code>null</code>
	 * if there the Action is in no group.</p>
	 *
	 * @return The related {@link ActionGroup}.
	 */
	public ActionGroup getGroup();

	/**
	 * <p>Sets the state of the action.</p>
	 * <p>It must update the {@link ActionGroup} (if there
	 * is any).</p>
	 *
	 * @param selected
	 */
	public void setSelected(boolean selected);

	/**
	 *
	 * @return The state of the Action.
	 */
	public boolean isSelected();
}

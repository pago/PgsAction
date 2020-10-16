package com.pagosoft.action;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Actions that implement this interface will be able to
 * listen to a model (the context) and modifiy the related component
 * (disable/enable it, change its content, etc.). The call to
 * {@link #addContext} is performed by {@link ContextSensitiveButtonProvider}
 * and {@link ContextSensitiveMenuProvider}.</p>
 * <p>Actions of this type will be wrapped within a {@link ContextSensitiveAction}
 * which means you'll get access to the current context through
 * {@link java.awt.event.ActionEvent#getSource}-method.</p>
 *
 * @author Patrick Gotthardt
 */
public interface ContextAwareAction extends Action {
	public void addContext(Object context, JComponent presentation);
}

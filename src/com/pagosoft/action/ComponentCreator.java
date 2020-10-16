package com.pagosoft.action;

import com.pagosoft.swing.ComponentProvider;

import javax.swing.*;

/**
 * @author Patrick Gotthardt
 * @date 12.03.2006 15:11:29
 */
public interface ComponentCreator {
	JMenuBar createMenuBar(Object context);

	JMenuBar createMenuBar(ComponentProvider prov);

	JToolBar createToolBar(Object context);

	JToolBar createToolBar(ComponentProvider prov);

	JMenu createMenu(Object context);

	JMenu createMenu(ComponentProvider prov);

	JPopupMenu createPopupMenu(Object context);

	JPopupMenu createPopupMenu(ComponentProvider prov);

	JMenuBar createMenuBar();

	JToolBar createToolBar();

	JMenu createMenu();

	JPopupMenu createPopupMenu();
}

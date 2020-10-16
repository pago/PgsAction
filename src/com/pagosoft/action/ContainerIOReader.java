package com.pagosoft.action;

import org.xml.sax.Attributes;

import javax.swing.*;

/**
 * @author Patrick Gotthardt
 * @date 12.03.2006 17:34:46
 */
interface ContainerIOReader {
	public Action read(Attributes attr, ActionManager manager);
}

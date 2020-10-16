package com.pagosoft.action;

import java.io.PrintWriter;

/**
 * @author Patrick Gotthardt
 * @date 12.03.2006 16:43:59
 */
interface ContainerIOWriter {
	public void print(Object o, PrintWriter out, StringBuilder indent);
	public boolean canPrint(Object o);
}

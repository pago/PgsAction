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

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Not to be used right now.
 *
 * @author Patrick Gotthardt
 * @date 12.03.2006 16:35:59
 */
class ContainerIO {
	private static List writers;
	private static List readers;
	static {
		writers = new ArrayList();
		writers.add(new ListModelWriter());
		writers.add(new SeparatorWriter());
		writers.add(new ActionWriter());
	}

	public static void registerWriter(ContainerIOWriter writer) {
		writers.add(writer);
	}

	private PrintWriter out;
	private StringBuilder indent;

	public ContainerIO(PrintWriter out) {
		this.out = out;
		indent = new StringBuilder();
		out.println("<containers>");
	}

	public void write(ActionContainer model) {
		write(out, model, indent);
	}

	public void finish() {
		out.println("</containers>");
	}

	private static void write(PrintWriter out, ActionContainer model, StringBuilder indent) {
		indent.append('	');
		out.print("<container ");
		if(model.getClass() == ActionContainer.class) {
			out.print("id=\"");
			out.print(model.getValue(AbstractSystemAction.ID));
		} else {
			out.print("class=\"");
			out.print(model.getClass().getName());
		}
		out.println("\">");
		for(int i = 0; i < model.getSize(); i++) {
			Object o = model.getElementAt(i);
			ContainerIOWriter writer = getWriter(o);
			if(writer == null) {
				Logger.getLogger("com.pagosoft.action.ContainerIO").warning("No writer found for "+o);
			} else {
				writer.print(o, out, indent);
			}
		}
		out.println("</container>");
		indent.deleteCharAt(indent.length()-1);
	}

	private static ContainerIOWriter getWriter(Object o) {
		for(int i = writers.size()-1; i > -1; i--) {
			ContainerIOWriter writer = (ContainerIOWriter) writers.get(i);
			if(writer.canPrint(o)) {
				return writer;
			}
		}
		return null;
	}

	static class ListModelWriter implements ContainerIOWriter {
		public void print(Object o, PrintWriter out, StringBuilder indent) {
			write(out, (ActionContainer)o, indent);
		}

		public boolean canPrint(Object o) {
			return o instanceof ActionContainer;
		}
	}

	static class SeparatorWriter implements ContainerIOWriter {
		public void print(Object o, PrintWriter out, StringBuilder indent) {
			out.print(indent);
			out.println("<add type=\"space\"/>");
		}

		public boolean canPrint(Object o) {
			return o instanceof SeparatorAction || o == null;
		}
	}

	static class ActionWriter implements ContainerIOWriter {
		public void print(Object o, PrintWriter out, StringBuilder indent) {
			out.print(indent);
			out.print("<add id=\"");
			out.print(o.getClass().getName());
			out.println("\"/>");
		}

		public boolean canPrint(Object o) {
			return o instanceof AbstractSystemAction;
		}
	}
}

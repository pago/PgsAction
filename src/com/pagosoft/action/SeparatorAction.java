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
import java.awt.event.*;

/**
 * <p>This class is not really required as the default
 * {@link com.pagosoft.swing.ComponentProvider}-implementations
 * will realize a <code>null</code>-value as a separator anyway, but
 * it might add a little bit of extra functionality if you change this
 * behaviour.</p>
 *
 * @author Patrick Gotthardt
 */
public final class SeparatorAction extends AbstractAction {
	public void actionPerformed(ActionEvent e) {}
}

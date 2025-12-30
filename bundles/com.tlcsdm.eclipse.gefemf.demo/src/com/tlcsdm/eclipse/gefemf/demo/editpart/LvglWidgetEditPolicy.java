/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetDeleteCommand;
import com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetRemoveFromContainerCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Edit policy for LVGL widget operations.
 */
public class LvglWidgetEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();

		if (parent instanceof LvglScreen && child instanceof LvglWidget) {
			return new LvglWidgetDeleteCommand((LvglScreen) parent, (LvglWidget) child);
		} else if (parent instanceof LvglWidget && child instanceof LvglWidget) {
			// Widget inside a container
			return new LvglWidgetRemoveFromContainerCommand((LvglWidget) parent, (LvglWidget) child);
		}
		return null;
	}
}

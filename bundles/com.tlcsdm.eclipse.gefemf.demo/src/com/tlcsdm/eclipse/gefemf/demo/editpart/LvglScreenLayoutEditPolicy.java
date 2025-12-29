/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetCreateCommand;
import com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetSetConstraintCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Layout edit policy for LVGL screen.
 */
public class LvglScreenLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		if (child.getModel() instanceof LvglWidget && constraint instanceof Rectangle) {
			return new LvglWidgetSetConstraintCommand((LvglWidget) child.getModel(), (Rectangle) constraint);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof LvglWidget) {
			LvglWidget widget = (LvglWidget) newObject;
			LvglScreen screen = (LvglScreen) getHost().getModel();

			Rectangle constraint = (Rectangle) getConstraintFor(request);
			if (constraint.width <= 0) {
				constraint.width = widget.getBounds().width;
			}
			if (constraint.height <= 0) {
				constraint.height = widget.getBounds().height;
			}

			return new LvglWidgetCreateCommand(screen, widget, constraint);
		}
		return null;
	}
}

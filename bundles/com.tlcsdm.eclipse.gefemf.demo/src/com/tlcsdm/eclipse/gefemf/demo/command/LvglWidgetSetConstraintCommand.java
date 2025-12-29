/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Command for setting the constraint (bounds) of an LVGL widget.
 */
public class LvglWidgetSetConstraintCommand extends Command {

	private final LvglWidget widget;
	private final Rectangle newBounds;
	private Rectangle oldBounds;

	public LvglWidgetSetConstraintCommand(LvglWidget widget, Rectangle newBounds) {
		this.widget = widget;
		this.newBounds = newBounds;
		setLabel("Move/Resize Widget");
	}

	@Override
	public boolean canExecute() {
		return widget != null && newBounds != null;
	}

	@Override
	public void execute() {
		oldBounds = widget.getBounds().getCopy();
		widget.setBounds(newBounds);
	}

	@Override
	public void undo() {
		widget.setBounds(oldBounds);
	}

	@Override
	public void redo() {
		widget.setBounds(newBounds);
	}
}

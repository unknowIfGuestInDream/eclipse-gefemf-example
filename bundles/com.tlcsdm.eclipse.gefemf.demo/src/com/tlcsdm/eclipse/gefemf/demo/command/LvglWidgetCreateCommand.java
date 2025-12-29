/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Command for creating an LVGL widget.
 */
public class LvglWidgetCreateCommand extends Command {

	private final LvglScreen screen;
	private final LvglWidget widget;
	private final Rectangle bounds;

	public LvglWidgetCreateCommand(LvglScreen screen, LvglWidget widget, Rectangle bounds) {
		this.screen = screen;
		this.widget = widget;
		this.bounds = bounds;
		setLabel("Create Widget");
	}

	@Override
	public boolean canExecute() {
		return screen != null && widget != null && bounds != null;
	}

	@Override
	public void execute() {
		widget.setBounds(bounds);
		screen.addWidget(widget);
	}

	@Override
	public void undo() {
		screen.removeWidget(widget);
	}

	@Override
	public void redo() {
		screen.addWidget(widget);
	}
}

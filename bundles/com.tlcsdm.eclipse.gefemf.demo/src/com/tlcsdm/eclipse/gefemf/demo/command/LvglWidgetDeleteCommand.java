/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Command for deleting an LVGL widget.
 */
public class LvglWidgetDeleteCommand extends Command {

	private final LvglScreen screen;
	private final LvglWidget widget;
	private int index;

	public LvglWidgetDeleteCommand(LvglScreen screen, LvglWidget widget) {
		this.screen = screen;
		this.widget = widget;
		setLabel("Delete Widget");
	}

	@Override
	public boolean canExecute() {
		return screen != null && widget != null;
	}

	@Override
	public void execute() {
		index = screen.getWidgets().indexOf(widget);
		screen.removeWidget(widget);
	}

	@Override
	public void undo() {
		// Insert at original index
		screen.insertWidget(index, widget);
	}

	@Override
	public void redo() {
		screen.removeWidget(widget);
	}
}

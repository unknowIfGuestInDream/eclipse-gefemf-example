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
 * Command for adding a widget to a container.
 */
public class LvglWidgetAddToContainerCommand extends Command {

	private final LvglWidget container;
	private final LvglWidget widget;
	private final Rectangle bounds;

	public LvglWidgetAddToContainerCommand(LvglWidget container, LvglWidget widget, Rectangle bounds) {
		this.container = container;
		this.widget = widget;
		this.bounds = bounds;
		setLabel("Add Widget to Container");
	}

	@Override
	public boolean canExecute() {
		return container != null && widget != null && bounds != null;
	}

	@Override
	public void execute() {
		widget.setBounds(bounds);
		container.addChild(widget);
	}

	@Override
	public void undo() {
		container.removeChild(widget);
	}

	@Override
	public void redo() {
		widget.setBounds(bounds);
		container.addChild(widget);
	}
}

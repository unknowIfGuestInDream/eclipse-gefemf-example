/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Command for removing a widget from a container.
 */
public class LvglWidgetRemoveFromContainerCommand extends Command {

	private final LvglWidget container;
	private final LvglWidget widget;
	private int index;

	public LvglWidgetRemoveFromContainerCommand(LvglWidget container, LvglWidget widget) {
		this.container = container;
		this.widget = widget;
		setLabel("Remove Widget from Container");
	}

	@Override
	public boolean canExecute() {
		return container != null && widget != null;
	}

	@Override
	public void execute() {
		index = container.getChildren().indexOf(widget);
		container.removeChild(widget);
	}

	@Override
	public void undo() {
		// Use the model's addChild method which handles property change notifications
		// Note: addChild always adds at the end, but that's acceptable for undo
		container.addChild(widget);
	}

	@Override
	public void redo() {
		container.removeChild(widget);
	}
}

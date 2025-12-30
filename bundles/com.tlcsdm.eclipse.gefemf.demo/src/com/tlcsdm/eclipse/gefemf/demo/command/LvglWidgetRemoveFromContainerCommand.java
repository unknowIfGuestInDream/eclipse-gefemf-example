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
		// Insert at original index
		if (index >= 0 && index <= container.getChildren().size()) {
			container.getChildren().add(index, widget);
			widget.setParent(container);
		} else {
			container.addChild(widget);
		}
	}

	@Override
	public void redo() {
		container.removeChild(widget);
	}
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

/**
 * Context menu provider for the diagram editor.
 * Provides right-click menu actions for widgets including delete.
 */
public class DiagramContextMenuProvider extends ContextMenuProvider {

	private final ActionRegistry actionRegistry;

	public DiagramContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		this.actionRegistry = registry;
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		// Add standard edit group
		GEFActionConstants.addStandardActionGroups(menu);

		// Add undo action
		IAction undoAction = actionRegistry.getAction(ActionFactory.UNDO.getId());
		if (undoAction != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_UNDO, undoAction);
		}

		// Add redo action
		IAction redoAction = actionRegistry.getAction(ActionFactory.REDO.getId());
		if (redoAction != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_UNDO, redoAction);
		}

		// Add delete action
		IAction deleteAction = actionRegistry.getAction(ActionFactory.DELETE.getId());
		if (deleteAction != null) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, deleteAction);
		}

		// Add separator and custom actions group
		menu.add(new Separator("custom"));
	}
}

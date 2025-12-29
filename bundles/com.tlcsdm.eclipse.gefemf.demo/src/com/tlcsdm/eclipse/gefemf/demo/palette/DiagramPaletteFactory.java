/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.palette;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.SimpleFactory;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;

/**
 * Factory for creating the palette for the diagram editor.
 */
public class DiagramPaletteFactory {

	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();

		// Tools group
		PaletteDrawer toolsDrawer = new PaletteDrawer("Tools");

		// Selection tool
		SelectionToolEntry selectionTool = new SelectionToolEntry();
		toolsDrawer.add(selectionTool);
		palette.setDefaultEntry(selectionTool);

		palette.add(toolsDrawer);

		// Shapes group
		PaletteDrawer shapesDrawer = new PaletteDrawer("Shapes");

		// Class creation tool
		@SuppressWarnings({ "rawtypes", "unchecked" })
		CreationToolEntry classCreation = new CreationToolEntry("Class", "Create a new class",
				new SimpleFactory(ClassNode.class), null, null);
		shapesDrawer.add(classCreation);

		palette.add(shapesDrawer);

		// Connections group
		PaletteDrawer connectionsDrawer = new PaletteDrawer("Connections");

		// Connection creation tool
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ConnectionCreationToolEntry connectionTool = new ConnectionCreationToolEntry("Association",
				"Create an association between classes", new SimpleFactory(Connection.class), null, null);
		connectionsDrawer.add(connectionTool);

		palette.add(connectionsDrawer);

		return palette;
	}
}

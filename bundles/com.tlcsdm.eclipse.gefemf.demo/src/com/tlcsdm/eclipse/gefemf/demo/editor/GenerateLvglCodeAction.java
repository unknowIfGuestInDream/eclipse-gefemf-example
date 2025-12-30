/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;

import com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;

/**
 * Action for generating LVGL C code from the diagram editor. Can be invoked
 * from the context menu or toolbar.
 */
public class GenerateLvglCodeAction extends Action {

	private final IWorkbenchPart workbenchPart;

	public GenerateLvglCodeAction(IWorkbenchPart part) {
		super("Generate LVGL Code");
		this.workbenchPart = part;
		setId(DiagramContextMenuProvider.GENERATE_LVGL_CODE_ACTION_ID);
		setToolTipText("Generate LVGL C Code from Diagram");
	}

	@Override
	public void run() {
		if (!(workbenchPart instanceof DiagramEditor)) {
			logToConsole("Warning: Please open an LVGL UI editor first.");
			return;
		}

		DiagramEditor diagramEditor = (DiagramEditor) workbenchPart;
		LvglScreen screen = diagramEditor.getScreen();

		if (screen == null || screen.getWidgets().isEmpty()) {
			logToConsole("Warning: The screen is empty. Please add some widgets first.");
			return;
		}

		try {
			IFile diagramFile = (IFile) diagramEditor.getEditorInput().getAdapter(IFile.class);
			if (diagramFile != null) {
				IContainer parentFolder = diagramFile.getParent();
				String baseName = diagramFile.getName();
				if (baseName.endsWith(".gefxml")) {
					baseName = baseName.substring(0, baseName.length() - 7);
				}

				LvglCodeGenerator generator = new LvglCodeGenerator(screen, baseName);
				String headerCode = generator.generateHeader();
				String sourceCode = generator.generateSource();

				IFile headerFile = parentFolder.getFile(new org.eclipse.core.runtime.Path(baseName + ".h"));
				ByteArrayInputStream headerSource = new ByteArrayInputStream(headerCode.getBytes("UTF-8"));
				if (headerFile.exists()) {
					headerFile.setContents(headerSource, true, true, new NullProgressMonitor());
				} else {
					headerFile.create(headerSource, true, new NullProgressMonitor());
				}

				IFile sourceFile = parentFolder.getFile(new org.eclipse.core.runtime.Path(baseName + ".c"));
				ByteArrayInputStream cSource = new ByteArrayInputStream(sourceCode.getBytes("UTF-8"));
				if (sourceFile.exists()) {
					sourceFile.setContents(cSource, true, true, new NullProgressMonitor());
				} else {
					sourceFile.create(cSource, true, new NullProgressMonitor());
				}

				logToConsole("Successfully generated LVGL C code:");
				logToConsole("  " + headerFile.getFullPath().toString());
				logToConsole("  " + sourceFile.getFullPath().toString());
			}
		} catch (Exception e) {
			logToConsole("Error: Failed to generate code: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void logToConsole(String message) {
		System.out.println("[LVGL Code Generator] " + message);
	}
}

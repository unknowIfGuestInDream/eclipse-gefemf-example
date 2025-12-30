/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.handler;

import java.io.ByteArrayInputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tlcsdm.eclipse.gefemf.demo.editor.DiagramEditor;
import com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.util.ConsoleLogger;

/**
 * Handler for the Generate C Code command.
 * Outputs log messages to the Eclipse platform log and console.
 */
public class GenerateCodeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (!(editor instanceof DiagramEditor)) {
			ConsoleLogger.logWarning("Please open an LVGL UI editor first.");
			ConsoleLogger.writeToConsole("Warning: Please open an LVGL UI editor first.");
			return null;
		}

		DiagramEditor diagramEditor = (DiagramEditor) editor;
		LvglScreen screen = diagramEditor.getScreen();

		if (screen == null || screen.getWidgets().isEmpty()) {
			ConsoleLogger.logWarning("The screen is empty. Please add some widgets first.");
			ConsoleLogger.writeToConsole("Warning: The screen is empty. Please add some widgets first.");
			return null;
		}

		// Save generated files in the same folder as the gefxml file with the same base name
		try {
			IFile diagramFile = (IFile) diagramEditor.getEditorInput().getAdapter(IFile.class);
			if (diagramFile != null) {
				// Get the parent folder and base name of the gefxml file
				IContainer parentFolder = diagramFile.getParent();
				String baseName = diagramFile.getName();
				// Remove the .gefxml extension to get the base name
				if (baseName.endsWith(".gefxml")) {
					baseName = baseName.substring(0, baseName.length() - 7);
				}

				// Generate code using the file base name
				LvglCodeGenerator generator = new LvglCodeGenerator(screen, baseName);
				String headerCode = generator.generateHeader();
				String sourceCode = generator.generateSource();

				// Generate header file with same name as gefxml
				IFile headerFile = parentFolder.getFile(new org.eclipse.core.runtime.Path(baseName + ".h"));
				ByteArrayInputStream headerSource = new ByteArrayInputStream(headerCode.getBytes("UTF-8"));
				if (headerFile.exists()) {
					headerFile.setContents(headerSource, true, true, new NullProgressMonitor());
				} else {
					headerFile.create(headerSource, true, new NullProgressMonitor());
				}

				// Generate source file with same name as gefxml
				IFile sourceFile = parentFolder.getFile(new org.eclipse.core.runtime.Path(baseName + ".c"));
				ByteArrayInputStream cSource = new ByteArrayInputStream(sourceCode.getBytes("UTF-8"));
				if (sourceFile.exists()) {
					sourceFile.setContents(cSource, true, true, new NullProgressMonitor());
				} else {
					sourceFile.create(cSource, true, new NullProgressMonitor());
				}

				ConsoleLogger.logInfo("Successfully generated LVGL C code: " + headerFile.getFullPath() + ", " + sourceFile.getFullPath());
				ConsoleLogger.writeToConsole("Successfully generated LVGL C code:");
				ConsoleLogger.writeToConsole("  " + headerFile.getFullPath().toString());
				ConsoleLogger.writeToConsole("  " + sourceFile.getFullPath().toString());
			}
		} catch (Exception e) {
			ConsoleLogger.logError("Failed to generate code: " + e.getMessage(), e);
			ConsoleLogger.writeToConsole("Error: Failed to generate code: " + e.getMessage());
		}

		return null;
	}

	@SuppressWarnings("unused")
	private void createFolders(IFolder folder) throws CoreException {
		if (!folder.exists()) {
			if (!folder.getParent().exists() && folder.getParent() instanceof IFolder) {
				createFolders((IFolder) folder.getParent());
			}
			folder.create(true, true, new NullProgressMonitor());
		}
	}
}

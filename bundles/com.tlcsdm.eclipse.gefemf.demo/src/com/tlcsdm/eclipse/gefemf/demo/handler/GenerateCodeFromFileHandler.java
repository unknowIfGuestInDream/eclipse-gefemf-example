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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglXmlSerializer;
import com.tlcsdm.eclipse.gefemf.demo.util.ConsoleLogger;

/**
 * Handler for generating C code from a selected gefxml file in the context menu.
 * Outputs log messages to the Eclipse platform log and console.
 */
public class GenerateCodeFromFileHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (!(selection instanceof IStructuredSelection)) {
			ConsoleLogger.logWarning("Please select a .gefxml file.");
			ConsoleLogger.writeToConsole("Warning: Please select a .gefxml file.");
			return null;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Object firstElement = structuredSelection.getFirstElement();

		if (!(firstElement instanceof IFile)) {
			ConsoleLogger.logWarning("Please select a .gefxml file.");
			ConsoleLogger.writeToConsole("Warning: Please select a .gefxml file.");
			return null;
		}

		IFile diagramFile = (IFile) firstElement;
		if (!diagramFile.getName().endsWith(".gefxml")) {
			ConsoleLogger.logWarning("Please select a .gefxml file.");
			ConsoleLogger.writeToConsole("Warning: Please select a .gefxml file.");
			return null;
		}

		try {
			// Load the screen from the file
			LvglXmlSerializer serializer = new LvglXmlSerializer();
			LvglScreen screen = serializer.load(diagramFile.getContents());

			if (screen == null || screen.getWidgets().isEmpty()) {
				ConsoleLogger.logWarning("The screen is empty. Please add some widgets first.");
				ConsoleLogger.writeToConsole("Warning: The screen is empty. Please add some widgets first.");
				return null;
			}

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

		} catch (Exception e) {
			ConsoleLogger.logError("Failed to generate code: " + e.getMessage(), e);
			ConsoleLogger.writeToConsole("Error: Failed to generate code: " + e.getMessage());
		}

		return null;
	}
}

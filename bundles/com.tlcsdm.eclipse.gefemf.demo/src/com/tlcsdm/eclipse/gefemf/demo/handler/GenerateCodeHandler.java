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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tlcsdm.eclipse.gefemf.demo.editor.DiagramEditor;
import com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;

/**
 * Handler for the Generate C Code command.
 */
public class GenerateCodeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (!(editor instanceof DiagramEditor)) {
			MessageDialog.openWarning(HandlerUtil.getActiveShell(event), "Warning",
					"Please open an LVGL UI editor first.");
			return null;
		}

		DiagramEditor diagramEditor = (DiagramEditor) editor;
		LvglScreen screen = diagramEditor.getScreen();

		if (screen == null || screen.getWidgets().isEmpty()) {
			MessageDialog.openWarning(HandlerUtil.getActiveShell(event), "Warning",
					"The screen is empty. Please add some widgets first.");
			return null;
		}

		// Generate code
		LvglCodeGenerator generator = new LvglCodeGenerator(screen);
		String headerCode = generator.generateHeader();
		String sourceCode = generator.generateSource();

		// Save generated files
		try {
			IFile diagramFile = (IFile) diagramEditor.getEditorInput().getAdapter(IFile.class);
			if (diagramFile != null) {
				IProject project = diagramFile.getProject();
				IFolder srcGenFolder = project.getFolder("src-gen");

				if (!srcGenFolder.exists()) {
					srcGenFolder.create(true, true, new NullProgressMonitor());
				}

				// Generate header file
				IFile headerFile = srcGenFolder.getFile(screen.getName() + ".h");
				ByteArrayInputStream headerSource = new ByteArrayInputStream(headerCode.getBytes("UTF-8"));
				if (headerFile.exists()) {
					headerFile.setContents(headerSource, true, true, new NullProgressMonitor());
				} else {
					headerFile.create(headerSource, true, new NullProgressMonitor());
				}

				// Generate source file
				IFile sourceFile = srcGenFolder.getFile(screen.getName() + ".c");
				ByteArrayInputStream cSource = new ByteArrayInputStream(sourceCode.getBytes("UTF-8"));
				if (sourceFile.exists()) {
					sourceFile.setContents(cSource, true, true, new NullProgressMonitor());
				} else {
					sourceFile.create(cSource, true, new NullProgressMonitor());
				}

				MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "Code Generated",
						"Successfully generated LVGL C code:\n" + srcGenFolder.getFullPath().toString() + "/"
								+ screen.getName() + ".h\n" + srcGenFolder.getFullPath().toString() + "/"
								+ screen.getName() + ".c");
			}
		} catch (Exception e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "Error",
					"Failed to generate code: " + e.getMessage());
			e.printStackTrace();
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

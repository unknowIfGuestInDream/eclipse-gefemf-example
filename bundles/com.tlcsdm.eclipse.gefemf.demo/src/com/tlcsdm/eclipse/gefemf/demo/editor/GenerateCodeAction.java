/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;

/**
 * Action to generate LVGL C code from the diagram editor.
 */
public class GenerateCodeAction extends Action {

	private final IWorkbenchWindow window;

	public GenerateCodeAction(IWorkbenchWindow window) {
		this.window = window;
		setText("Generate C Code");
		setToolTipText("Generate LVGL C Code from Diagram");
		setId("com.tlcsdm.eclipse.gefemf.demo.actions.generate");

		// Load icon from plugin
		ImageDescriptor icon = AbstractUIPlugin.imageDescriptorFromPlugin(
				"com.tlcsdm.eclipse.gefemf.demo", "icons/generate.png");
		if (icon != null) {
			setImageDescriptor(icon);
		}
	}

	@Override
	public void run() {
		if (window.getActivePage() == null) {
			return;
		}
		IEditorPart editor = window.getActivePage().getActiveEditor();

		if (!(editor instanceof DiagramEditor)) {
			MessageDialog.openWarning(window.getShell(), "Warning",
					"Please open an LVGL UI editor first.");
			return;
		}

		DiagramEditor diagramEditor = (DiagramEditor) editor;
		LvglScreen screen = diagramEditor.getScreen();

		if (screen == null || screen.getWidgets().isEmpty()) {
			MessageDialog.openWarning(window.getShell(), "Warning",
					"The screen is empty. Please add some widgets first.");
			return;
		}

		// Generate code
		LvglCodeGenerator generator = new LvglCodeGenerator(screen);
		String headerCode = generator.generateHeader();
		String sourceCode = generator.generateSource();

		// Save generated files
		try {
			IFile diagramFile = (IFile) diagramEditor.getEditorInput().getAdapter(IFile.class);
			if (diagramFile == null) {
				MessageDialog.openError(window.getShell(), "Error",
						"Cannot access the diagram file. Please save the file first.");
				return;
			}
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

			MessageDialog.openInformation(window.getShell(), "Code Generated",
					"Successfully generated LVGL C code:\n" + srcGenFolder.getFullPath().toString() + "/"
							+ screen.getName() + ".h\n" + srcGenFolder.getFullPath().toString() + "/"
							+ screen.getName() + ".c");
		} catch (Exception e) {
			MessageDialog.openError(window.getShell(), "Error",
					"Failed to generate code: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

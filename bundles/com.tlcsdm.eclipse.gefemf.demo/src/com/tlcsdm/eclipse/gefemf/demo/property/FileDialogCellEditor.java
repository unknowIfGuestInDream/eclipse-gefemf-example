/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * A cell editor that provides a file dialog for selecting image files.
 * This editor opens a file selection dialog and returns a relative path
 * to the selected file when possible.
 * <p>
 * The dialog is positioned near the cursor location for better user experience.
 * </p>
 */
public class FileDialogCellEditor extends DialogCellEditor {

	/**
	 * Filter extensions for image files.
	 */
	private static final String[] FILTER_EXTENSIONS = {
		"*.png;*.jpg;*.jpeg;*.gif;*.bmp;*.ico",
		"*.png",
		"*.jpg;*.jpeg",
		"*.gif",
		"*.bmp",
		"*.*"
	};

	/**
	 * Filter names for image files.
	 */
	private static final String[] FILTER_NAMES = {
		"All Images (*.png, *.jpg, *.gif, *.bmp, *.ico)",
		"PNG Images (*.png)",
		"JPEG Images (*.jpg, *.jpeg)",
		"GIF Images (*.gif)",
		"BMP Images (*.bmp)",
		"All Files (*.*)"
	};

	/**
	 * Creates a new file dialog cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 */
	public FileDialogCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Creates a new file dialog cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 * @param style the style bits
	 */
	public FileDialogCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		// Create a positioned shell near the cursor location
		final Display display = cellEditorWindow.getDisplay();
		final Shell centerShell = new Shell(cellEditorWindow.getShell(), SWT.NO_TRIM);
		centerShell.setLocation(display.getCursorLocation());

		// Create the file dialog
		FileDialog dialog = new FileDialog(centerShell, SWT.OPEN);
		dialog.setFilterExtensions(FILTER_EXTENSIONS);
		dialog.setFilterNames(FILTER_NAMES);
		dialog.setText("Select Image File");

		// Set initial filter path based on current project
		String filterPath = getInitialFilterPath();
		if (filterPath != null) {
			dialog.setFilterPath(filterPath);
		}

		// Open the dialog
		String selectedFile = dialog.open();
		centerShell.dispose();

		if (selectedFile != null) {
			// Try to compute relative path
			return computeRelativePath(selectedFile);
		}
		return null;
	}

	/**
	 * Get the initial filter path based on the current editor's project.
	 *
	 * @return the filter path, or null if not available
	 */
	private String getInitialFilterPath() {
		try {
			IEditorInput editorInput = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.getActiveEditor()
					.getEditorInput();
			
			if (editorInput instanceof IFileEditorInput fileInput) {
				IFile file = fileInput.getFile();
				IContainer container = file.getParent();
				if (container != null) {
					IPath location = container.getLocation();
					if (location != null) {
						return location.toOSString();
					}
				}
			}
		} catch (Exception e) {
			// Fall back to workspace root
		}
		
		// Fall back to workspace root
		IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		if (workspaceLocation != null) {
			return workspaceLocation.toOSString();
		}
		return null;
	}

	/**
	 * Compute a relative path from the current project to the selected file.
	 * If a relative path cannot be computed, returns the absolute path.
	 *
	 * @param absolutePath the absolute path of the selected file
	 * @return the relative or absolute path
	 */
	private String computeRelativePath(String absolutePath) {
		try {
			IEditorInput editorInput = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.getActiveEditor()
					.getEditorInput();
			
			if (editorInput instanceof IFileEditorInput fileInput) {
				IFile editorFile = fileInput.getFile();
				IProject project = editorFile.getProject();
				IPath projectLocation = project.getLocation();
				
				if (projectLocation != null) {
					File selectedFile = new File(absolutePath);
					File projectDir = projectLocation.toFile();
					
					// Check if selected file is within the project
					String projectPath = projectDir.getAbsolutePath();
					String filePath = selectedFile.getAbsolutePath();
					
					if (filePath.startsWith(projectPath)) {
						// Compute relative path from project root
						String relativePath = filePath.substring(projectPath.length());
						if (relativePath.startsWith(File.separator)) {
							relativePath = relativePath.substring(1);
						}
						// Convert to forward slashes for consistency
						return relativePath.replace(File.separatorChar, '/');
					}
					
					// Try to compute relative path from editor file's parent folder
					IContainer container = editorFile.getParent();
					if (container != null) {
						IPath containerLocation = container.getLocation();
						if (containerLocation != null) {
							String containerPath = containerLocation.toFile().getAbsolutePath();
							if (filePath.startsWith(containerPath)) {
								String relativePath = filePath.substring(containerPath.length());
								if (relativePath.startsWith(File.separator)) {
									relativePath = relativePath.substring(1);
								}
								return relativePath.replace(File.separatorChar, '/');
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// Fall back to absolute path
		}
		
		// Return the filename only if we can't compute relative path
		return new File(absolutePath).getName();
	}
}

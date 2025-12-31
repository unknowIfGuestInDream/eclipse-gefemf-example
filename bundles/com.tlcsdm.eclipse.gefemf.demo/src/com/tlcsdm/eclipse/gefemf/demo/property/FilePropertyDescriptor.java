/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Property descriptor for file path properties that provides a file dialog for editing.
 * This descriptor uses a FileDialogCellEditor to allow users to select image files
 * through the standard file selection dialog.
 * <p>
 * The dialog is positioned near the cursor location for better user experience.
 * </p>
 */
public class FilePropertyDescriptor extends PropertyDescriptor {

	/**
	 * Creates a new file property descriptor.
	 *
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public FilePropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		FileDialogCellEditor editor = new FileDialogCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}
}

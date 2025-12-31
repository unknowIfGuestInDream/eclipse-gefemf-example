/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Property descriptor for boolean/checkbox properties that provides a checkbox widget for editing.
 * This provides a more intuitive UI compared to using a dropdown with "true"/"false" options.
 * <p>
 * The label provider is configured to show no text, so only the checkbox is visible.
 * </p>
 */
public class CheckboxPropertyDescriptor extends PropertyDescriptor {

	/**
	 * Label provider that returns empty string to hide the "true"/"false" text.
	 * Users will only see the checkbox control.
	 */
	private static final ILabelProvider CHECKBOX_LABEL_PROVIDER = new LabelProvider() {
		@Override
		public String getText(Object element) {
			return "";
		}
	};

	/**
	 * Creates a new checkbox property descriptor.
	 *
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public CheckboxPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		setLabelProvider(CHECKBOX_LABEL_PROVIDER);
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new CheckboxCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A cell editor that provides a checkbox for boolean properties.
 * This provides a more intuitive UI for editing boolean/checked properties
 * compared to using a dropdown with "true"/"false" options.
 */
public class CheckboxCellEditor extends CellEditor {

	/**
	 * The checkbox button widget.
	 */
	private Button checkbox;

	/**
	 * Creates a new checkbox cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 */
	public CheckboxCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Creates a new checkbox cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 * @param style the style bits
	 */
	public CheckboxCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected Control createControl(Composite parent) {
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setBackground(parent.getBackground());
		checkbox.addListener(SWT.Selection, e -> {
			boolean newValue = checkbox.getSelection();
			boolean oldValue = doGetValue() != null ? (Boolean) doGetValue() : false;
			if (newValue != oldValue) {
				markDirty();
				doSetValue(Boolean.valueOf(newValue));
				fireApplyEditorValue();
			}
		});
		return checkbox;
	}

	@Override
	protected Object doGetValue() {
		return Boolean.valueOf(checkbox.getSelection());
	}

	@Override
	protected void doSetFocus() {
		if (checkbox != null && !checkbox.isDisposed()) {
			checkbox.setFocus();
		}
	}

	@Override
	protected void doSetValue(Object value) {
		if (checkbox != null && !checkbox.isDisposed()) {
			boolean checked = false;
			if (value instanceof Boolean) {
				checked = ((Boolean) value).booleanValue();
			} else if (value instanceof Integer) {
				checked = ((Integer) value).intValue() != 0;
			}
			checkbox.setSelection(checked);
		}
	}
}

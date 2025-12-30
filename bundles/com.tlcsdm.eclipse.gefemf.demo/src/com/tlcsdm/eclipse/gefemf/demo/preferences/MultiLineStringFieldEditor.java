/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.preferences;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for multi-line string preferences.
 * Extends StringFieldEditor to use a multi-line text widget.
 */
public class MultiLineStringFieldEditor extends StringFieldEditor {

	private static final int TEXT_HEIGHT = 150;

	public MultiLineStringFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		super.doFillIntoGrid(parent, numColumns);
		// Configure the text widget for multi-line editing
		Text text = getTextControl();
		if (text != null) {
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.heightHint = TEXT_HEIGHT;
			gd.horizontalSpan = numColumns - 1;
			text.setLayoutData(gd);
		}
	}

	@Override
	public Text getTextControl(Composite parent) {
		Text text = super.getTextControl(parent);
		// We need to replace the single-line text with multi-line
		return text;
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		GridData gd = (GridData) getTextControl().getLayoutData();
		gd.horizontalSpan = numColumns - 1;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
	}

	@Override
	protected Text createTextWidget(Composite parent) {
		Text text = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		return text;
	}
}

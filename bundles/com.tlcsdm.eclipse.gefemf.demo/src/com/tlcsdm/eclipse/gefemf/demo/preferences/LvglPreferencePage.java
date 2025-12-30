/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tlcsdm.eclipse.gefemf.demo.Activator;

/**
 * Preference page for LVGL UI Editor settings.
 * <p>
 * This page allows users to configure the license header text that will be
 * added to generated C/H files.
 * </p>
 */
public class LvglPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public LvglPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("LVGL UI Editor preferences");
	}

	@Override
	public void createFieldEditors() {
		addField(new MultiLineStringFieldEditor(
				LvglPreferenceConstants.P_LICENSE_HEADER,
				"License Header:",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		// Nothing to initialize
	}
}

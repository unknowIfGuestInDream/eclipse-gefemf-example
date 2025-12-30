/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Property descriptor for color properties that provides a ColorDialog for editing.
 * This descriptor uses a custom PositionedColorCellEditor to allow users to select colors
 * through the Eclipse color dialog, which is positioned near the cell for better UX.
 */
public class ColorPropertyDescriptor extends PropertyDescriptor {

	/**
	 * Creates a new color property descriptor.
	 *
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
	public ColorPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		PositionedColorCellEditor editor = new PositionedColorCellEditor(parent);
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
		return editor;
	}

	/**
	 * Convert an integer color value (0xRRGGBB) to an RGB object.
	 *
	 * @param color the integer color value
	 * @return the RGB object
	 */
	public static RGB intToRgb(int color) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		return new RGB(r, g, b);
	}

	/**
	 * Convert an RGB object to an integer color value (0xRRGGBB).
	 *
	 * @param rgb the RGB object
	 * @return the integer color value
	 */
	public static int rgbToInt(RGB rgb) {
		return (rgb.red << 16) | (rgb.green << 8) | rgb.blue;
	}
}

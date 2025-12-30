/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * Property descriptor for color properties that provides a ColorDialog for editing.
 * This descriptor uses a custom ColorCellEditor to allow users to select colors through
 * the Eclipse color dialog, positioned near the cell being edited.
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

	/**
	 * Custom ColorCellEditor that positions the ColorDialog near the cell being edited.
	 */
	private static class PositionedColorCellEditor extends DialogCellEditor {

		private static final int DEFAULT_EXTENT = 16;
		private Composite composite;
		private Label colorLabel;
		private Image image;

		public PositionedColorCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected Control createContents(Composite cell) {
			Color bg = cell.getBackground();
			composite = new Composite(cell, SWT.NONE);
			composite.setBackground(bg);
			composite.setLayout(new ColorCellLayout());
			colorLabel = new Label(composite, SWT.LEFT);
			colorLabel.setBackground(bg);
			return composite;
		}

		@Override
		protected void updateContents(Object value) {
			RGB rgb = (RGB) value;
			if (colorLabel == null) {
				return;
			}
			if (image != null) {
				image.dispose();
			}
			if (rgb != null) {
				image = makeImage(colorLabel.getDisplay(), rgb);
				colorLabel.setImage(image);
			} else {
				colorLabel.setImage(null);
			}
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			// Calculate position near the cell
			Point cellLocation = cellEditorWindow.toDisplay(0, 0);
			Point cellSize = cellEditorWindow.getSize();
			
			// Create a positioned shell for the dialog
			Shell parentShell = cellEditorWindow.getShell();
			Shell positionedShell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			positionedShell.setLocation(cellLocation.x, cellLocation.y + cellSize.y);
			
			ColorDialog dialog = new ColorDialog(positionedShell, SWT.NONE);
			
			Object value = getValue();
			if (value != null) {
				dialog.setRGB((RGB) value);
			}
			
			RGB result = dialog.open();
			positionedShell.dispose();
			
			return result;
		}

		@Override
		public void dispose() {
			if (image != null) {
				image.dispose();
				image = null;
			}
			super.dispose();
		}

		private Image makeImage(Display display, RGB rgb) {
			Image result = new Image(display, DEFAULT_EXTENT, DEFAULT_EXTENT);
			GC gc = new GC(result);
			gc.setBackground(new Color(display, rgb));
			gc.fillRectangle(0, 0, DEFAULT_EXTENT, DEFAULT_EXTENT);
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(0, 0, DEFAULT_EXTENT - 1, DEFAULT_EXTENT - 1);
			gc.dispose();
			return result;
		}

		/**
		 * Internal layout for the color cell.
		 */
		private class ColorCellLayout extends Layout {
			@Override
			public void layout(Composite editor, boolean force) {
				Rectangle bounds = editor.getClientArea();
				Point size = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
				colorLabel.setBounds(2, (bounds.height - size.y) / 2, size.x, size.y);
			}

			@Override
			public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
				if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
					return new Point(wHint, hHint);
				}
				Point size = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
				return new Point(size.x + 4, size.y);
			}
		}
	}
}

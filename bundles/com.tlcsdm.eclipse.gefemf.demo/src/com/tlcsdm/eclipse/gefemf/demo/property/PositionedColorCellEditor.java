/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

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

/**
 * A custom color cell editor that positions the color dialog near the cell.
 * This provides a better user experience as the dialog appears close to where
 * the user clicked, rather than at a system-determined location.
 */
public class PositionedColorCellEditor extends DialogCellEditor {

	/**
	 * Gap between the cell swatch and the dialog.
	 */
	private static final int GAP = 6;

	/**
	 * Width of the color swatch.
	 */
	private static final int SWATCH_WIDTH = 16;

	/**
	 * Height of the color swatch.
	 */
	private static final int SWATCH_HEIGHT = 16;

	/**
	 * The composite widget containing the color label and button.
	 */
	private Composite composite;

	/**
	 * The label widget showing the color swatch.
	 */
	private Label colorLabel;

	/**
	 * The image displayed in the color swatch.
	 */
	private Image image;

	/**
	 * Internal class for laying out the dialog.
	 */
	private class ColorCellLayout extends Layout {
		@Override
		public void layout(Composite editor, boolean force) {
			Rectangle bounds = editor.getClientArea();
			Point size = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
			colorLabel.setBounds(GAP, 0, size.x, bounds.height);
		}

		@Override
		public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}
			Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
			return new Point(GAP + colorSize.x, colorSize.y);
		}
	}

	/**
	 * Creates a new color cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 */
	public PositionedColorCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Creates a new color cell editor parented under the given control.
	 *
	 * @param parent the parent control
	 * @param style the style bits
	 */
	public PositionedColorCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue(new RGB(0, 0, 0));
	}

	@Override
	protected Control createContents(Composite cell) {
		Color bg = cell.getBackground();
		composite = new Composite(cell, getStyle());
		composite.setBackground(bg);
		composite.setLayout(new ColorCellLayout());
		colorLabel = new Label(composite, SWT.LEFT);
		colorLabel.setBackground(bg);
		return composite;
	}

	@Override
	public void dispose() {
		if (image != null) {
			image.dispose();
			image = null;
		}
		super.dispose();
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		// Create the color dialog
		Shell parentShell = cellEditorWindow.getShell();
		ColorDialog dialog = new ColorDialog(parentShell, SWT.NONE);

		// Set the current RGB value
		Object value = getValue();
		if (value != null) {
			dialog.setRGB((RGB) value);
		}

		// Position the dialog near the cell
		Shell dialogShell = dialog.getParent();
		if (cellEditorWindow != null) {
			Rectangle cellBounds = cellEditorWindow.getBounds();
			Point displayLocation = cellEditorWindow.toDisplay(cellBounds.x, cellBounds.y + cellBounds.height);
			
			// Adjust position to ensure dialog is visible on screen
			Rectangle screenBounds = Display.getCurrent().getPrimaryMonitor().getBounds();
			int x = displayLocation.x;
			int y = displayLocation.y;
			
			// Estimate dialog size (ColorDialog size varies by platform)
			int estimatedWidth = 400;
			int estimatedHeight = 300;
			
			// Adjust if dialog would go off screen
			if (x + estimatedWidth > screenBounds.x + screenBounds.width) {
				x = screenBounds.x + screenBounds.width - estimatedWidth;
			}
			if (y + estimatedHeight > screenBounds.y + screenBounds.height) {
				// Show above the cell instead
				y = displayLocation.y - cellBounds.height - estimatedHeight;
			}
			
			// Ensure minimum bounds
			x = Math.max(screenBounds.x, x);
			y = Math.max(screenBounds.y, y);
			
			// Set the dialog location (note: ColorDialog doesn't support setLocation directly,
			// but we can try to influence it through the parent shell)
			dialogShell.setLocation(x, y);
		}

		// Open the dialog and return the result
		RGB result = dialog.open();
		return result;
	}

	@Override
	protected void updateContents(Object value) {
		RGB rgb = (RGB) value;
		// Dispose old image
		if (image != null) {
			image.dispose();
		}
		
		// Create a new image with the color
		if (rgb != null) {
			image = new Image(colorLabel.getDisplay(), SWATCH_WIDTH, SWATCH_HEIGHT);
			GC gc = new GC(image);
			gc.setBackground(new Color(colorLabel.getDisplay(), rgb));
			gc.fillRectangle(0, 0, SWATCH_WIDTH, SWATCH_HEIGHT);
			gc.setForeground(colorLabel.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(0, 0, SWATCH_WIDTH - 1, SWATCH_HEIGHT - 1);
			gc.dispose();
			colorLabel.setImage(image);
		} else {
			colorLabel.setImage(null);
		}
	}
}

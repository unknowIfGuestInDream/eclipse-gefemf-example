/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Figure representing an LVGL widget in the editor.
 * <p>
 * The figure renders widgets to look like actual LVGL components without 
 * displaying the widget type label, providing a more realistic preview.
 * </p>
 */
public class LvglWidgetFigure extends Figure {

	private final Label nameLabel;
	private LvglWidget.WidgetType widgetType = LvglWidget.WidgetType.BUTTON;
	private String text = "";
	private int bgColor = 0xFFFFFF;
	private Color bgColorInstance;
	// New widget-specific visual properties
	private boolean checked = false;
	private int value = 0;
	private int minValue = 0;
	private int maxValue = 100;
	private int rowCount = 3;
	private int columnCount = 3;

	public LvglWidgetFigure() {
		setLayoutManager(new XYLayout());
		setBorder(new LineBorder(ColorConstants.black, 1));
		setOpaque(true);

		nameLabel = new Label();
		add(nameLabel);
	}

	public void setWidgetType(LvglWidget.WidgetType type) {
		this.widgetType = type;
		repaint();
	}

	public void setWidgetName(String name) {
		nameLabel.setText(name);
	}

	public void setWidgetText(String text) {
		this.text = text;
		repaint();
	}

	public void setWidgetBgColor(int color) {
		this.bgColor = color;
		// Dispose old color if we created one
		if (bgColorInstance != null && !bgColorInstance.isDisposed()) {
			bgColorInstance.dispose();
		}
		// Create new color from hex value
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		bgColorInstance = new Color(Display.getCurrent(), r, g, b);
		repaint();
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		repaint();
	}

	public void setValue(int value) {
		this.value = value;
		repaint();
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
		repaint();
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
		repaint();
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		repaint();
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
		repaint();
	}

	@Override
	protected void paintFigure(Graphics g) {
		Rectangle r = getBounds().getCopy();

		// Draw background using custom bgColor if set, otherwise use type-based color
		Color bg = getBackgroundColorForType();
		g.setBackgroundColor(bg);
		g.fillRectangle(r);

		// Draw widget-specific representation (LVGL-like appearance without type label)
		drawWidgetRepresentation(g, r);
	}

	private Color getBackgroundColorForType() {
		// If custom bgColor is set (not default white), use it
		if (bgColorInstance != null && !bgColorInstance.isDisposed() && bgColor != 0xFFFFFF) {
			return bgColorInstance;
		}
		// Use a light color variation based on widget type
		switch (widgetType) {
		case BUTTON:
			return ColorConstants.lightBlue;
		case LABEL:
			return ColorConstants.white;
		case SLIDER:
		case BAR:
			return ColorConstants.lightGray;
		case SWITCH:
		case CHECKBOX:
			return ColorConstants.white;
		case DROPDOWN:
		case TEXTAREA:
			return ColorConstants.white;
		case ARC:
			return ColorConstants.white;
		case IMAGE:
			return ColorConstants.lightGray;
		case CONTAINER:
			return ColorConstants.white;
		default:
			return ColorConstants.white;
		}
	}

	private void drawWidgetRepresentation(Graphics g, Rectangle r) {
		g.setForegroundColor(ColorConstants.black);

		switch (widgetType) {
		case BUTTON:
			drawButton(g, r);
			break;
		case LABEL:
			drawLabel(g, r);
			break;
		case SLIDER:
			drawSlider(g, r);
			break;
		case SWITCH:
			drawSwitch(g, r);
			break;
		case CHECKBOX:
			drawCheckbox(g, r);
			break;
		case DROPDOWN:
			drawDropdown(g, r);
			break;
		case TEXTAREA:
			drawTextarea(g, r);
			break;
		case ARC:
			drawArc(g, r);
			break;
		case BAR:
			drawBar(g, r);
			break;
		case IMAGE:
			drawImage(g, r);
			break;
		case CONTAINER:
			drawContainer(g, r);
			break;
		case TABLE:
			drawTable(g, r);
			break;
		default:
			drawDefault(g, r);
			break;
		}
		// Widget type label is intentionally not drawn to match actual LVGL appearance
	}

	private void drawButton(Graphics g, Rectangle r) {
		// Draw button with 3D effect (full area, no type label space)
		g.setBackgroundColor(ColorConstants.button);
		g.fillRectangle(r.x + 2, r.y + 2, r.width - 4, r.height - 4);
		g.setForegroundColor(ColorConstants.buttonDarker);
		g.drawRectangle(r.x + 2, r.y + 2, r.width - 5, r.height - 5);

		// Draw text centered
		if (!text.isEmpty()) {
			g.setForegroundColor(ColorConstants.black);
			int textWidth = g.getFontMetrics().getAverageCharWidth() * text.length();
			int textX = r.x + (r.width - textWidth) / 2;
			int textY = r.y + (r.height - g.getFontMetrics().getHeight()) / 2;
			g.drawString(text, textX, textY);
		}
	}

	private void drawLabel(Graphics g, Rectangle r) {
		// Draw label text
		if (!text.isEmpty()) {
			g.setForegroundColor(ColorConstants.black);
			g.drawString(text, r.x + 5, r.y + (r.height - g.getFontMetrics().getHeight()) / 2);
		}
	}

	private void drawSlider(Graphics g, Rectangle r) {
		int trackY = r.y + r.height / 2;
		int trackHeight = 6;

		// Draw track background
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 5, trackY - trackHeight / 2, r.width - 10, trackHeight);

		// Calculate knob position based on value (using long arithmetic to avoid overflow)
		int trackWidth = r.width - 10;
		long range = (long) maxValue - minValue;
		int normalizedValue = range > 0 ? (int) (((long) (value - minValue) * trackWidth) / range) : 0;
		normalizedValue = Math.max(0, Math.min(normalizedValue, trackWidth));
		
		// Draw filled portion
		g.setBackgroundColor(ColorConstants.blue);
		g.fillRectangle(r.x + 5, trackY - trackHeight / 2, normalizedValue, trackHeight);

		// Draw knob at value position
		int knobX = r.x + 5 + normalizedValue;
		g.setBackgroundColor(ColorConstants.blue);
		g.fillOval(knobX - 8, trackY - 8, 16, 16);
	}

	private void drawSwitch(Graphics g, Rectangle r) {
		int centerY = r.y + r.height / 2;
		int switchWidth = Math.min(40, r.width - 10);
		int switchHeight = Math.min(20, r.height - 4);
		int switchX = r.x + (r.width - switchWidth) / 2;

		// Draw track - green when checked, gray when unchecked
		g.setBackgroundColor(checked ? ColorConstants.green : ColorConstants.lightGray);
		g.fillRoundRectangle(new Rectangle(switchX, centerY - switchHeight / 2, switchWidth, switchHeight), switchHeight, switchHeight);

		// Draw knob - position based on checked state
		g.setBackgroundColor(ColorConstants.white);
		if (checked) {
			g.fillOval(switchX + switchWidth - switchHeight + 2, centerY - switchHeight / 2 + 2, switchHeight - 4, switchHeight - 4);
		} else {
			g.fillOval(switchX + 2, centerY - switchHeight / 2 + 2, switchHeight - 4, switchHeight - 4);
		}
	}

	private void drawCheckbox(Graphics g, Rectangle r) {
		int boxSize = Math.min(16, Math.min(r.width - 10, r.height - 4));
		int boxY = r.y + (r.height - boxSize) / 2;

		// Draw checkbox background - blue when checked, white when unchecked
		g.setBackgroundColor(checked ? ColorConstants.blue : ColorConstants.white);
		g.fillRectangle(r.x + 5, boxY, boxSize, boxSize);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 5, boxY, boxSize, boxSize);

		// Draw checkmark only if checked
		if (checked) {
			g.setForegroundColor(ColorConstants.white);
			g.setLineStyle(SWT.LINE_SOLID);
			g.setLineWidth(2);
			g.drawLine(r.x + 8, boxY + boxSize / 2, r.x + 5 + boxSize / 2, boxY + boxSize - 4);
			g.drawLine(r.x + 5 + boxSize / 2, boxY + boxSize - 4, r.x + 5 + boxSize - 2, boxY + 4);
			g.setLineWidth(1);
		}

		// Draw text
		g.setForegroundColor(ColorConstants.black);
		if (!text.isEmpty()) {
			g.drawString(text, r.x + boxSize + 10, boxY);
		}
	}

	private void drawDropdown(Graphics g, Rectangle r) {
		// Draw dropdown box
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 3, r.y + 3, r.width - 6, r.height - 6);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);

		// Draw arrow
		int arrowX = r.x + r.width - 15;
		int arrowY = r.y + r.height / 2;
		g.drawLine(arrowX, arrowY - 2, arrowX + 5, arrowY + 3);
		g.drawLine(arrowX + 5, arrowY + 3, arrowX + 10, arrowY - 2);
	}

	private void drawTextarea(Graphics g, Rectangle r) {
		// Draw text area
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 3, r.y + 3, r.width - 6, r.height - 6);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);

		// Draw placeholder lines
		g.setForegroundColor(ColorConstants.lightGray);
		for (int i = 0; i < 3 && (r.y + 10 + i * 15) < (r.y + r.height - 10); i++) {
			g.drawLine(r.x + 8, r.y + 10 + i * 15, r.x + r.width - 12, r.y + 10 + i * 15);
		}
	}

	private void drawArc(Graphics g, Rectangle r) {
		int centerX = r.x + r.width / 2;
		int centerY = r.y + r.height / 2;
		int radius = Math.min(r.width, r.height) / 2 - 8;

		// Draw arc background (270 degree arc)
		g.setForegroundColor(ColorConstants.lightGray);
		g.setLineWidth(8);
		g.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 135, 270);

		// Calculate arc value angle based on value (using long arithmetic to avoid overflow)
		long range = (long) maxValue - minValue;
		int normalizedValue = range > 0 ? (int) (((long) (value - minValue) * 270) / range) : 0;
		normalizedValue = Math.max(0, Math.min(normalizedValue, 270));

		// Draw arc value portion
		g.setForegroundColor(ColorConstants.blue);
		g.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 135, normalizedValue);
		g.setLineWidth(1);
	}

	private void drawBar(Graphics g, Rectangle r) {
		int barHeight = Math.min(10, r.height - 4);
		int barY = r.y + (r.height - barHeight) / 2;

		// Draw background
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 5, barY, r.width - 10, barHeight);

		// Calculate progress width based on value (using long arithmetic to avoid overflow)
		long range = (long) maxValue - minValue;
		int barWidth = r.width - 10;
		int progressWidth = range > 0 ? (int) (((long) (value - minValue) * barWidth) / range) : 0;
		progressWidth = Math.max(0, Math.min(progressWidth, barWidth));

		// Draw progress
		g.setBackgroundColor(ColorConstants.blue);
		g.fillRectangle(r.x + 5, barY, progressWidth, barHeight);
	}

	private void drawImage(Graphics g, Rectangle r) {
		// Draw image placeholder
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 3, r.y + 3, r.width - 6, r.height - 6);
		g.setForegroundColor(ColorConstants.gray);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);

		// Draw image icon (mountain landscape)
		int iconSize = Math.min(r.width - 20, r.height - 20);
		int iconX = r.x + (r.width - iconSize) / 2;
		int iconY = r.y + (r.height - iconSize) / 2;

		g.drawLine(iconX, iconY + iconSize, iconX + iconSize / 3, iconY + iconSize / 2);
		g.drawLine(iconX + iconSize / 3, iconY + iconSize / 2, iconX + iconSize * 2 / 3, iconY + iconSize * 2 / 3);
		g.drawLine(iconX + iconSize * 2 / 3, iconY + iconSize * 2 / 3, iconX + iconSize, iconY);
	}

	private void drawContainer(Graphics g, Rectangle r) {
		// Draw dashed border for container
		g.setForegroundColor(ColorConstants.gray);
		g.setLineStyle(SWT.LINE_DASH);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);
		g.setLineStyle(SWT.LINE_SOLID);
	}

	private void drawTable(Graphics g, Rectangle r) {
		// Draw table background
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 3, r.y + 3, r.width - 6, r.height - 6);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);

		// Draw grid lines based on row and column count
		int tableWidth = r.width - 6;
		int tableHeight = r.height - 6;
		int colWidth = columnCount > 0 ? tableWidth / columnCount : tableWidth;
		int rowHeight = rowCount > 0 ? tableHeight / rowCount : tableHeight;

		g.setForegroundColor(ColorConstants.lightGray);

		// Draw vertical lines
		for (int i = 1; i < columnCount; i++) {
			int x = r.x + 3 + i * colWidth;
			g.drawLine(x, r.y + 3, x, r.y + r.height - 4);
		}

		// Draw horizontal lines
		for (int i = 1; i < rowCount; i++) {
			int y = r.y + 3 + i * rowHeight;
			g.drawLine(r.x + 3, y, r.x + r.width - 4, y);
		}
	}

	private void drawDefault(Graphics g, Rectangle r) {
		// Draw a simple box for unknown widget types
		g.setForegroundColor(ColorConstants.gray);
		g.drawRectangle(r.x + 3, r.y + 3, r.width - 7, r.height - 7);
	}

	/**
	 * Dispose of any resources created by this figure.
	 */
	public void dispose() {
		if (bgColorInstance != null && !bgColorInstance.isDisposed()) {
			bgColorInstance.dispose();
			bgColorInstance = null;
		}
	}
}

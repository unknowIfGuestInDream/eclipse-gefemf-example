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

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Figure representing an LVGL widget in the editor.
 */
public class LvglWidgetFigure extends Figure {

	private final Label nameLabel;
	private final Label typeLabel;
	private LvglWidget.WidgetType widgetType = LvglWidget.WidgetType.BUTTON;
	private String text = "";
	private int bgColor = 0xFFFFFF;

	public LvglWidgetFigure() {
		setLayoutManager(new XYLayout());
		setBorder(new LineBorder(ColorConstants.black, 1));
		setOpaque(true);

		typeLabel = new Label();
		typeLabel.setForegroundColor(ColorConstants.gray);
		add(typeLabel);

		nameLabel = new Label();
		add(nameLabel);
	}

	public void setWidgetType(LvglWidget.WidgetType type) {
		this.widgetType = type;
		typeLabel.setText("[" + type.getDisplayName() + "]");
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
		repaint();
	}

	@Override
	protected void paintFigure(Graphics g) {
		Rectangle r = getBounds().getCopy();

		// Draw background based on widget type
		Color bg = getBackgroundColorForType();
		g.setBackgroundColor(bg);
		g.fillRectangle(r);

		// Draw widget-specific representation
		drawWidgetRepresentation(g, r);
	}

	private Color getBackgroundColorForType() {
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
		}

		// Draw widget type label at top
		g.setForegroundColor(ColorConstants.darkGray);
		String typeStr = "[" + widgetType.getDisplayName() + "]";
		g.drawString(typeStr, r.x + 3, r.y + 2);
	}

	private void drawButton(Graphics g, Rectangle r) {
		// Draw button with 3D effect
		g.setBackgroundColor(ColorConstants.button);
		g.fillRectangle(r.x + 2, r.y + 15, r.width - 4, r.height - 17);
		g.setForegroundColor(ColorConstants.buttonDarker);
		g.drawRectangle(r.x + 2, r.y + 15, r.width - 5, r.height - 18);

		// Draw text
		if (!text.isEmpty()) {
			g.setForegroundColor(ColorConstants.black);
			int textWidth = g.getFontMetrics().getAverageCharWidth() * text.length();
			int textX = r.x + (r.width - textWidth) / 2;
			int textY = r.y + r.height / 2;
			g.drawString(text, textX, textY);
		}
	}

	private void drawLabel(Graphics g, Rectangle r) {
		if (!text.isEmpty()) {
			g.setForegroundColor(ColorConstants.black);
			g.drawString(text, r.x + 5, r.y + 18);
		}
	}

	private void drawSlider(Graphics g, Rectangle r) {
		int trackY = r.y + r.height / 2;
		int trackHeight = 6;

		// Draw track
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 5, trackY - trackHeight / 2, r.width - 10, trackHeight);

		// Draw knob
		int knobX = r.x + r.width / 2;
		g.setBackgroundColor(ColorConstants.blue);
		g.fillOval(knobX - 8, trackY - 8, 16, 16);
	}

	private void drawSwitch(Graphics g, Rectangle r) {
		int centerY = r.y + r.height / 2 + 5;
		int switchWidth = 40;
		int switchHeight = 20;
		int switchX = r.x + (r.width - switchWidth) / 2;

		// Draw track
		g.setBackgroundColor(ColorConstants.green);
		g.fillRoundRectangle(switchX, centerY - switchHeight / 2, switchWidth, switchHeight, switchHeight, switchHeight);

		// Draw knob
		g.setBackgroundColor(ColorConstants.white);
		g.fillOval(switchX + switchWidth - switchHeight + 2, centerY - switchHeight / 2 + 2, switchHeight - 4, switchHeight - 4);
	}

	private void drawCheckbox(Graphics g, Rectangle r) {
		int boxSize = 16;
		int boxY = r.y + 18;

		// Draw checkbox
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 5, boxY, boxSize, boxSize);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 5, boxY, boxSize, boxSize);

		// Draw checkmark
		g.setLineStyle(SWT.LINE_SOLID);
		g.setLineWidth(2);
		g.drawLine(r.x + 8, boxY + 8, r.x + 12, boxY + 12);
		g.drawLine(r.x + 12, boxY + 12, r.x + 18, boxY + 4);
		g.setLineWidth(1);

		// Draw text
		if (!text.isEmpty()) {
			g.drawString(text, r.x + 25, boxY);
		}
	}

	private void drawDropdown(Graphics g, Rectangle r) {
		// Draw dropdown box
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 3, r.y + 16, r.width - 6, r.height - 19);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 3, r.y + 16, r.width - 7, r.height - 20);

		// Draw arrow
		int arrowX = r.x + r.width - 15;
		int arrowY = r.y + r.height / 2 + 5;
		g.drawLine(arrowX, arrowY, arrowX + 5, arrowY + 5);
		g.drawLine(arrowX + 5, arrowY + 5, arrowX + 10, arrowY);
	}

	private void drawTextarea(Graphics g, Rectangle r) {
		// Draw text area
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r.x + 3, r.y + 16, r.width - 6, r.height - 19);
		g.setForegroundColor(ColorConstants.black);
		g.drawRectangle(r.x + 3, r.y + 16, r.width - 7, r.height - 20);

		// Draw placeholder lines
		g.setForegroundColor(ColorConstants.lightGray);
		for (int i = 0; i < 3 && (r.y + 25 + i * 15) < (r.y + r.height - 10); i++) {
			g.drawLine(r.x + 8, r.y + 25 + i * 15, r.x + r.width - 20, r.y + 25 + i * 15);
		}
	}

	private void drawArc(Graphics g, Rectangle r) {
		int centerX = r.x + r.width / 2;
		int centerY = r.y + r.height / 2 + 5;
		int radius = Math.min(r.width, r.height - 15) / 2 - 5;

		// Draw arc background
		g.setForegroundColor(ColorConstants.lightGray);
		g.setLineWidth(8);
		g.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 135, 270);

		// Draw arc value
		g.setForegroundColor(ColorConstants.blue);
		g.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 135, 180);
		g.setLineWidth(1);
	}

	private void drawBar(Graphics g, Rectangle r) {
		int barHeight = 10;
		int barY = r.y + r.height / 2 + 3;

		// Draw background
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 5, barY, r.width - 10, barHeight);

		// Draw progress
		g.setBackgroundColor(ColorConstants.blue);
		g.fillRectangle(r.x + 5, barY, (r.width - 10) * 60 / 100, barHeight);
	}

	private void drawImage(Graphics g, Rectangle r) {
		// Draw image placeholder
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRectangle(r.x + 3, r.y + 16, r.width - 6, r.height - 19);
		g.setForegroundColor(ColorConstants.gray);
		g.drawRectangle(r.x + 3, r.y + 16, r.width - 7, r.height - 20);

		// Draw image icon
		int iconSize = Math.min(r.width - 20, r.height - 30);
		int iconX = r.x + (r.width - iconSize) / 2;
		int iconY = r.y + 16 + (r.height - 19 - iconSize) / 2;

		g.drawLine(iconX, iconY + iconSize, iconX + iconSize / 3, iconY + iconSize / 2);
		g.drawLine(iconX + iconSize / 3, iconY + iconSize / 2, iconX + iconSize * 2 / 3, iconY + iconSize * 2 / 3);
		g.drawLine(iconX + iconSize * 2 / 3, iconY + iconSize * 2 / 3, iconX + iconSize, iconY);
	}

	private void drawContainer(Graphics g, Rectangle r) {
		// Draw dashed border for container
		g.setForegroundColor(ColorConstants.gray);
		g.setLineStyle(SWT.LINE_DASH);
		g.drawRectangle(r.x + 3, r.y + 16, r.width - 7, r.height - 20);
		g.setLineStyle(SWT.LINE_SOLID);
	}
}

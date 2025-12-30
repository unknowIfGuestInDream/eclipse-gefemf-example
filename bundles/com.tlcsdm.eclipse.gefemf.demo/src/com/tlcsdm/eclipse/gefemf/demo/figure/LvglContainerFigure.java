/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Figure representing an LVGL container widget that can contain child widgets.
 */
public class LvglContainerFigure extends Figure {

	private final Label nameLabel;
	private final Label typeLabel;
	private LvglWidget.WidgetType widgetType = LvglWidget.WidgetType.CONTAINER;
	private String text = "";
	private int bgColor = 0xFFFFFF;

	public LvglContainerFigure() {
		setLayoutManager(new FreeformLayout());
		setBorder(new LineBorder(ColorConstants.gray, 1, SWT.LINE_DASH));
		setOpaque(true);
		setBackgroundColor(ColorConstants.white);

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

		// Draw background
		g.setBackgroundColor(ColorConstants.white);
		g.fillRectangle(r);

		// Draw dashed border
		g.setForegroundColor(ColorConstants.gray);
		g.setLineStyle(SWT.LINE_DASH);
		g.drawRectangle(r.x, r.y, r.width - 1, r.height - 1);
		g.setLineStyle(SWT.LINE_SOLID);

		// Draw container label at top
		g.setForegroundColor(ColorConstants.darkGray);
		String typeStr = "[" + widgetType.getDisplayName() + "]";
		g.drawString(typeStr, r.x + 3, r.y + 2);

		// Draw widget name
		g.setForegroundColor(ColorConstants.black);
		g.drawString(nameLabel.getText(), r.x + 3, r.y + 15);
	}

	@Override
	public boolean containsPoint(int x, int y) {
		return getBounds().contains(x, y);
	}
}

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
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * Figure representing a class node in UML style.
 */
public class ClassNodeFigure extends Figure {

    private static final Color CLASS_HEADER_BG = new Color(Display.getDefault(), 200, 220, 255);
    private static final Color CLASS_BODY_BG = new Color(Display.getDefault(), 240, 245, 255);

    private Label nameLabel;
    private Figure attributeCompartment;
    private Figure methodCompartment;

    public ClassNodeFigure() {
        ToolbarLayout layout = new ToolbarLayout();
        layout.setSpacing(2);
        setLayoutManager(layout);

        setBorder(new LineBorder(ColorConstants.black, 1));
        setBackgroundColor(CLASS_BODY_BG);
        setOpaque(true);

        // Class name header
        nameLabel = new Label("Class");
        nameLabel.setFont(getBoldFont());
        nameLabel.setBackgroundColor(CLASS_HEADER_BG);
        nameLabel.setOpaque(true);
        nameLabel.setBorder(new LineBorder(ColorConstants.black, 0, 0, 1, 0));
        add(nameLabel);

        // Attributes compartment
        attributeCompartment = new Figure();
        attributeCompartment.setLayoutManager(new ToolbarLayout());
        attributeCompartment.setBorder(new LineBorder(ColorConstants.black, 0, 0, 1, 0));
        add(attributeCompartment);

        // Methods compartment
        methodCompartment = new Figure();
        methodCompartment.setLayoutManager(new ToolbarLayout());
        add(methodCompartment);
    }

    private Font getBoldFont() {
        return new Font(Display.getDefault(), "Arial", 10, SWT.BOLD);
    }

    public void setClassName(String name) {
        nameLabel.setText(name);
    }

    public void addAttribute(String attribute) {
        Label attrLabel = new Label(" - " + attribute);
        attributeCompartment.add(attrLabel);
    }

    public void clearAttributes() {
        attributeCompartment.removeAll();
    }

    public void addMethod(String method) {
        Label methodLabel = new Label(" + " + method);
        methodCompartment.add(methodLabel);
    }

    public void clearMethods() {
        methodCompartment.removeAll();
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy();

        // Draw header background
        graphics.setBackgroundColor(CLASS_HEADER_BG);
        int headerHeight = nameLabel.getPreferredSize().height + 4;
        graphics.fillRectangle(r.x, r.y, r.width, headerHeight);

        // Draw body background
        graphics.setBackgroundColor(CLASS_BODY_BG);
        graphics.fillRectangle(r.x, r.y + headerHeight, r.width, r.height - headerHeight);
    }

    /**
     * Custom LineBorder that supports individual border sides.
     */
    private static class LineBorder extends org.eclipse.draw2d.LineBorder {
        private int top, left, bottom, right;

        public LineBorder(Color color, int width) {
            super(color, width);
            this.top = this.left = this.bottom = this.right = width;
        }

        public LineBorder(Color color, int top, int left, int bottom, int right) {
            super(color, Math.max(Math.max(top, left), Math.max(bottom, right)));
            this.top = top;
            this.left = left;
            this.bottom = bottom;
            this.right = right;
        }
    }
}

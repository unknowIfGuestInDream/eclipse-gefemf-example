/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * Represents an LVGL screen containing UI widgets.
 */
public class LvglScreen extends ModelElement {

	private static final long serialVersionUID = 1L;

	private String name = "screen";
	private int width = 480;
	private int height = 320;
	private int bgColor = 0xFFFFFF;
	private final List<LvglWidget> widgets = new ArrayList<>();

	public LvglScreen() {
		// Default constructor
	}

	public LvglScreen(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROPERTY_NAME, oldValue, name);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		int oldValue = this.width;
		this.width = width;
		firePropertyChange("width", oldValue, width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		int oldValue = this.height;
		this.height = height;
		firePropertyChange("height", oldValue, height);
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		int oldValue = this.bgColor;
		this.bgColor = bgColor;
		firePropertyChange("bgColor", oldValue, bgColor);
	}

	public List<LvglWidget> getWidgets() {
		return widgets;
	}

	public void addWidget(LvglWidget widget) {
		widgets.add(widget);
		firePropertyChange(PROPERTY_ADD, null, widget);
	}

	public void removeWidget(LvglWidget widget) {
		widgets.remove(widget);
		firePropertyChange(PROPERTY_REMOVE, widget, null);
	}

	@Override
	public EObject toEObject() {
		// Not used for LVGL - we use XML serialization instead
		return null;
	}
}

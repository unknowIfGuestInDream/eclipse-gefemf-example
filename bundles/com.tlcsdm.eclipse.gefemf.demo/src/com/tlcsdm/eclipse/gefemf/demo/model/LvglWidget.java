/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;

/**
 * Base class for LVGL widgets.
 */
public class LvglWidget extends ModelElement {

	private static final long serialVersionUID = 1L;

	public enum WidgetType {
		BUTTON("lv_btn", "Button"),
		LABEL("lv_label", "Label"),
		SLIDER("lv_slider", "Slider"),
		SWITCH("lv_switch", "Switch"),
		CHECKBOX("lv_checkbox", "Checkbox"),
		DROPDOWN("lv_dropdown", "Dropdown"),
		TEXTAREA("lv_textarea", "Textarea"),
		IMAGE("lv_img", "Image"),
		ARC("lv_arc", "Arc"),
		BAR("lv_bar", "Bar"),
		CONTAINER("lv_obj", "Container");

		private final String lvglType;
		private final String displayName;

		WidgetType(String lvglType, String displayName) {
			this.lvglType = lvglType;
			this.displayName = displayName;
		}

		public String getLvglType() {
			return lvglType;
		}

		public String getDisplayName() {
			return displayName;
		}
	}

	private String name = "widget";
	private WidgetType widgetType = WidgetType.BUTTON;
	private Rectangle bounds = new Rectangle(0, 0, 100, 40);
	private String text = "";
	private final List<LvglWidget> children = new ArrayList<>();
	private LvglWidget parent;
	private final List<Connection> sourceConnections = new ArrayList<>();
	private final List<Connection> targetConnections = new ArrayList<>();

	// Style properties
	private int bgColor = 0xFFFFFF;
	private int textColor = 0x000000;
	private int borderWidth = 0;
	private int borderColor = 0x000000;
	private int radius = 0;

	public LvglWidget() {
		// Default constructor
	}

	public LvglWidget(String name, WidgetType widgetType) {
		this.name = name;
		this.widgetType = widgetType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROPERTY_NAME, oldValue, name);
	}

	public WidgetType getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(WidgetType widgetType) {
		WidgetType oldValue = this.widgetType;
		this.widgetType = widgetType;
		firePropertyChange("widgetType", oldValue, widgetType);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		Rectangle oldValue = this.bounds;
		this.bounds = bounds;
		firePropertyChange(PROPERTY_LAYOUT, oldValue, bounds);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldValue = this.text;
		this.text = text;
		firePropertyChange("text", oldValue, text);
	}

	public List<LvglWidget> getChildren() {
		return children;
	}

	public void addChild(LvglWidget child) {
		children.add(child);
		child.setParent(this);
		firePropertyChange(PROPERTY_ADD, null, child);
	}

	public void removeChild(LvglWidget child) {
		children.remove(child);
		child.setParent(null);
		firePropertyChange(PROPERTY_REMOVE, child, null);
	}

	public LvglWidget getParent() {
		return parent;
	}

	public void setParent(LvglWidget parent) {
		this.parent = parent;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		int oldValue = this.bgColor;
		this.bgColor = bgColor;
		firePropertyChange("bgColor", oldValue, bgColor);
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		int oldValue = this.textColor;
		this.textColor = textColor;
		firePropertyChange("textColor", oldValue, textColor);
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		int oldValue = this.borderWidth;
		this.borderWidth = borderWidth;
		firePropertyChange("borderWidth", oldValue, borderWidth);
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		int oldValue = this.borderColor;
		this.borderColor = borderColor;
		firePropertyChange("borderColor", oldValue, borderColor);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		int oldValue = this.radius;
		this.radius = radius;
		firePropertyChange("radius", oldValue, radius);
	}

	public List<Connection> getSourceConnections() {
		return sourceConnections;
	}

	public void addSourceConnection(Connection connection) {
		sourceConnections.add(connection);
		firePropertyChange(PROPERTY_CONNECTION, null, connection);
	}

	public void removeSourceConnection(Connection connection) {
		sourceConnections.remove(connection);
		firePropertyChange(PROPERTY_CONNECTION, connection, null);
	}

	public List<Connection> getTargetConnections() {
		return targetConnections;
	}

	public void addTargetConnection(Connection connection) {
		targetConnections.add(connection);
		firePropertyChange(PROPERTY_CONNECTION, null, connection);
	}

	public void removeTargetConnection(Connection connection) {
		targetConnections.remove(connection);
		firePropertyChange(PROPERTY_CONNECTION, connection, null);
	}

	@Override
	public EObject toEObject() {
		// Not used for LVGL - we use XML serialization instead
		return null;
	}

	/**
	 * Generate variable name for this widget in C code.
	 */
	public String getVariableName() {
		return name.replaceAll("[^a-zA-Z0-9_]", "_");
	}
}

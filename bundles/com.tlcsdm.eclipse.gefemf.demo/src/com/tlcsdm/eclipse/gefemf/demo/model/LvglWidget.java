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

	/**
	 * Layout types supported by LVGL containers.
	 */
	public enum LayoutType {
		NONE("None", "LV_LAYOUT_NONE"),
		FLEX("Flex", "LV_LAYOUT_FLEX"),
		GRID("Grid", "LV_LAYOUT_GRID");

		private final String displayName;
		private final String lvglConstant;

		LayoutType(String displayName, String lvglConstant) {
			this.displayName = displayName;
			this.lvglConstant = lvglConstant;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getLvglConstant() {
			return lvglConstant;
		}
	}

	/**
	 * Flex flow direction for flex layout.
	 */
	public enum FlexFlow {
		ROW("Row", "LV_FLEX_FLOW_ROW"),
		COLUMN("Column", "LV_FLEX_FLOW_COLUMN"),
		ROW_WRAP("Row Wrap", "LV_FLEX_FLOW_ROW_WRAP"),
		COLUMN_WRAP("Column Wrap", "LV_FLEX_FLOW_COLUMN_WRAP"),
		ROW_REVERSE("Row Reverse", "LV_FLEX_FLOW_ROW_REVERSE"),
		COLUMN_REVERSE("Column Reverse", "LV_FLEX_FLOW_COLUMN_REVERSE");

		private final String displayName;
		private final String lvglConstant;

		FlexFlow(String displayName, String lvglConstant) {
			this.displayName = displayName;
			this.lvglConstant = lvglConstant;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getLvglConstant() {
			return lvglConstant;
		}
	}

	/**
	 * Flex alignment options.
	 */
	public enum FlexAlign {
		START("Start", "LV_FLEX_ALIGN_START"),
		END("End", "LV_FLEX_ALIGN_END"),
		CENTER("Center", "LV_FLEX_ALIGN_CENTER"),
		SPACE_EVENLY("Space Evenly", "LV_FLEX_ALIGN_SPACE_EVENLY"),
		SPACE_AROUND("Space Around", "LV_FLEX_ALIGN_SPACE_AROUND"),
		SPACE_BETWEEN("Space Between", "LV_FLEX_ALIGN_SPACE_BETWEEN");

		private final String displayName;
		private final String lvglConstant;

		FlexAlign(String displayName, String lvglConstant) {
			this.displayName = displayName;
			this.lvglConstant = lvglConstant;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getLvglConstant() {
			return lvglConstant;
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

	// Image-specific properties
	private String imageSource = "";

	// Layout properties (for containers)
	private LayoutType layoutType = LayoutType.NONE;
	private FlexFlow flexFlow = FlexFlow.ROW;
	private FlexAlign flexMainAlign = FlexAlign.START;
	private FlexAlign flexCrossAlign = FlexAlign.START;
	private FlexAlign flexTrackAlign = FlexAlign.START;
	private int padRow = 0;
	private int padColumn = 0;

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

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		String oldValue = this.imageSource;
		this.imageSource = imageSource != null ? imageSource : "";
		firePropertyChange("imageSource", oldValue, this.imageSource);
	}

	// Layout properties getters and setters

	public LayoutType getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(LayoutType layoutType) {
		LayoutType oldValue = this.layoutType;
		this.layoutType = layoutType != null ? layoutType : LayoutType.NONE;
		firePropertyChange("layoutType", oldValue, this.layoutType);
	}

	public FlexFlow getFlexFlow() {
		return flexFlow;
	}

	public void setFlexFlow(FlexFlow flexFlow) {
		FlexFlow oldValue = this.flexFlow;
		this.flexFlow = flexFlow != null ? flexFlow : FlexFlow.ROW;
		firePropertyChange("flexFlow", oldValue, this.flexFlow);
	}

	public FlexAlign getFlexMainAlign() {
		return flexMainAlign;
	}

	public void setFlexMainAlign(FlexAlign flexMainAlign) {
		FlexAlign oldValue = this.flexMainAlign;
		this.flexMainAlign = flexMainAlign != null ? flexMainAlign : FlexAlign.START;
		firePropertyChange("flexMainAlign", oldValue, this.flexMainAlign);
	}

	public FlexAlign getFlexCrossAlign() {
		return flexCrossAlign;
	}

	public void setFlexCrossAlign(FlexAlign flexCrossAlign) {
		FlexAlign oldValue = this.flexCrossAlign;
		this.flexCrossAlign = flexCrossAlign != null ? flexCrossAlign : FlexAlign.START;
		firePropertyChange("flexCrossAlign", oldValue, this.flexCrossAlign);
	}

	public FlexAlign getFlexTrackAlign() {
		return flexTrackAlign;
	}

	public void setFlexTrackAlign(FlexAlign flexTrackAlign) {
		FlexAlign oldValue = this.flexTrackAlign;
		this.flexTrackAlign = flexTrackAlign != null ? flexTrackAlign : FlexAlign.START;
		firePropertyChange("flexTrackAlign", oldValue, this.flexTrackAlign);
	}

	public int getPadRow() {
		return padRow;
	}

	public void setPadRow(int padRow) {
		int oldValue = this.padRow;
		this.padRow = padRow;
		firePropertyChange("padRow", oldValue, padRow);
	}

	public int getPadColumn() {
		return padColumn;
	}

	public void setPadColumn(int padColumn) {
		int oldValue = this.padColumn;
		this.padColumn = padColumn;
		firePropertyChange("padColumn", oldValue, padColumn);
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
	 * Ensures the name is a valid C identifier.
	 */
	public String getVariableName() {
		String varName = name.replaceAll("[^a-zA-Z0-9_]", "_");
		// C identifiers cannot start with a digit
		if (!varName.isEmpty() && Character.isDigit(varName.charAt(0))) {
			varName = "_" + varName;
		}
		// Handle empty name
		if (varName.isEmpty()) {
			varName = "_widget";
		}
		return varName;
	}
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Property source for LVGL widget properties.
 * This enables editing widget properties in the Eclipse Properties view.
 */
public class LvglWidgetPropertySource implements IPropertySource {

	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_TEXT = "text";
	private static final String PROPERTY_BG_COLOR = "bgColor";
	private static final String PROPERTY_TEXT_COLOR = "textColor";
	private static final String PROPERTY_BORDER_WIDTH = "borderWidth";
	private static final String PROPERTY_BORDER_COLOR = "borderColor";
	private static final String PROPERTY_RADIUS = "radius";
	private static final String PROPERTY_X = "x";
	private static final String PROPERTY_Y = "y";
	private static final String PROPERTY_WIDTH = "width";
	private static final String PROPERTY_HEIGHT = "height";
	private static final String PROPERTY_TYPE = "type";
	private static final String PROPERTY_IMAGE_SOURCE = "imageSource";
	private static final String PROPERTY_LAYOUT_TYPE = "layoutType";
	private static final String PROPERTY_FLEX_FLOW = "flexFlow";
	private static final String PROPERTY_FLEX_MAIN_ALIGN = "flexMainAlign";
	private static final String PROPERTY_FLEX_CROSS_ALIGN = "flexCrossAlign";
	private static final String PROPERTY_FLEX_TRACK_ALIGN = "flexTrackAlign";
	private static final String PROPERTY_PAD_ROW = "padRow";
	private static final String PROPERTY_PAD_COLUMN = "padColumn";

	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_POSITION = "Position";
	private static final String CATEGORY_SIZE = "Size";
	private static final String CATEGORY_STYLE = "Style";
	private static final String CATEGORY_IMAGE = "Image";
	private static final String CATEGORY_LAYOUT = "Layout";

	private final LvglWidget widget;

	public LvglWidgetPropertySource(LvglWidget widget) {
		this.widget = widget;
	}

	@Override
	public Object getEditableValue() {
		return widget;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		// General properties
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, "Name");
		nameDescriptor.setCategory(CATEGORY_GENERAL);
		descriptors.add(nameDescriptor);

		PropertyDescriptor typeDescriptor = new PropertyDescriptor(PROPERTY_TYPE, "Widget Type");
		typeDescriptor.setCategory(CATEGORY_GENERAL);
		descriptors.add(typeDescriptor);

		TextPropertyDescriptor textDescriptor = new TextPropertyDescriptor(PROPERTY_TEXT, "Text");
		textDescriptor.setCategory(CATEGORY_GENERAL);
		descriptors.add(textDescriptor);

		// Position properties
		TextPropertyDescriptor xDescriptor = new TextPropertyDescriptor(PROPERTY_X, "X");
		xDescriptor.setCategory(CATEGORY_POSITION);
		descriptors.add(xDescriptor);

		TextPropertyDescriptor yDescriptor = new TextPropertyDescriptor(PROPERTY_Y, "Y");
		yDescriptor.setCategory(CATEGORY_POSITION);
		descriptors.add(yDescriptor);

		// Size properties
		TextPropertyDescriptor widthDescriptor = new TextPropertyDescriptor(PROPERTY_WIDTH, "Width");
		widthDescriptor.setCategory(CATEGORY_SIZE);
		descriptors.add(widthDescriptor);

		TextPropertyDescriptor heightDescriptor = new TextPropertyDescriptor(PROPERTY_HEIGHT, "Height");
		heightDescriptor.setCategory(CATEGORY_SIZE);
		descriptors.add(heightDescriptor);

		// Style properties
		TextPropertyDescriptor bgColorDescriptor = new TextPropertyDescriptor(PROPERTY_BG_COLOR, "Background Color");
		bgColorDescriptor.setCategory(CATEGORY_STYLE);
		bgColorDescriptor.setDescription("Background color in hex format (e.g., #FFFFFF)");
		descriptors.add(bgColorDescriptor);

		TextPropertyDescriptor textColorDescriptor = new TextPropertyDescriptor(PROPERTY_TEXT_COLOR, "Text Color");
		textColorDescriptor.setCategory(CATEGORY_STYLE);
		textColorDescriptor.setDescription("Text color in hex format (e.g., #000000)");
		descriptors.add(textColorDescriptor);

		TextPropertyDescriptor borderWidthDescriptor = new TextPropertyDescriptor(PROPERTY_BORDER_WIDTH, "Border Width");
		borderWidthDescriptor.setCategory(CATEGORY_STYLE);
		descriptors.add(borderWidthDescriptor);

		TextPropertyDescriptor borderColorDescriptor = new TextPropertyDescriptor(PROPERTY_BORDER_COLOR, "Border Color");
		borderColorDescriptor.setCategory(CATEGORY_STYLE);
		borderColorDescriptor.setDescription("Border color in hex format (e.g., #000000)");
		descriptors.add(borderColorDescriptor);

		TextPropertyDescriptor radiusDescriptor = new TextPropertyDescriptor(PROPERTY_RADIUS, "Corner Radius");
		radiusDescriptor.setCategory(CATEGORY_STYLE);
		descriptors.add(radiusDescriptor);

		// Image-specific properties (only show for Image widgets)
		if (widget.getWidgetType() == LvglWidget.WidgetType.IMAGE) {
			TextPropertyDescriptor imageSourceDescriptor = new TextPropertyDescriptor(PROPERTY_IMAGE_SOURCE, "Image Source");
			imageSourceDescriptor.setCategory(CATEGORY_IMAGE);
			imageSourceDescriptor.setDescription("URL or path to the image resource (e.g., &image_name or path/to/image.png)");
			descriptors.add(imageSourceDescriptor);
		}

		// Layout properties (only show for Container widgets)
		if (widget.getWidgetType() == LvglWidget.WidgetType.CONTAINER) {
			// Layout Type dropdown
			String[] layoutTypeLabels = new String[LvglWidget.LayoutType.values().length];
			for (int i = 0; i < LvglWidget.LayoutType.values().length; i++) {
				layoutTypeLabels[i] = LvglWidget.LayoutType.values()[i].getDisplayName();
			}
			ComboBoxPropertyDescriptor layoutTypeDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_LAYOUT_TYPE, "Layout Type", layoutTypeLabels);
			layoutTypeDescriptor.setCategory(CATEGORY_LAYOUT);
			layoutTypeDescriptor.setDescription("Layout type for the container");
			descriptors.add(layoutTypeDescriptor);

			// Flex Flow dropdown
			String[] flexFlowLabels = new String[LvglWidget.FlexFlow.values().length];
			for (int i = 0; i < LvglWidget.FlexFlow.values().length; i++) {
				flexFlowLabels[i] = LvglWidget.FlexFlow.values()[i].getDisplayName();
			}
			ComboBoxPropertyDescriptor flexFlowDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_FLOW, "Flex Flow", flexFlowLabels);
			flexFlowDescriptor.setCategory(CATEGORY_LAYOUT);
			flexFlowDescriptor.setDescription("Flex flow direction");
			descriptors.add(flexFlowDescriptor);

			// Flex Main Align dropdown
			String[] flexAlignLabels = new String[LvglWidget.FlexAlign.values().length];
			for (int i = 0; i < LvglWidget.FlexAlign.values().length; i++) {
				flexAlignLabels[i] = LvglWidget.FlexAlign.values()[i].getDisplayName();
			}
			ComboBoxPropertyDescriptor flexMainAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_MAIN_ALIGN, "Flex Main Align", flexAlignLabels);
			flexMainAlignDescriptor.setCategory(CATEGORY_LAYOUT);
			flexMainAlignDescriptor.setDescription("Main axis alignment");
			descriptors.add(flexMainAlignDescriptor);

			// Flex Cross Align dropdown
			ComboBoxPropertyDescriptor flexCrossAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_CROSS_ALIGN, "Flex Cross Align", flexAlignLabels);
			flexCrossAlignDescriptor.setCategory(CATEGORY_LAYOUT);
			flexCrossAlignDescriptor.setDescription("Cross axis alignment");
			descriptors.add(flexCrossAlignDescriptor);

			// Flex Track Align dropdown
			ComboBoxPropertyDescriptor flexTrackAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_TRACK_ALIGN, "Flex Track Align", flexAlignLabels);
			flexTrackAlignDescriptor.setCategory(CATEGORY_LAYOUT);
			flexTrackAlignDescriptor.setDescription("Track alignment for wrapped content");
			descriptors.add(flexTrackAlignDescriptor);

			TextPropertyDescriptor padRowDescriptor = new TextPropertyDescriptor(PROPERTY_PAD_ROW, "Padding Row");
			padRowDescriptor.setCategory(CATEGORY_LAYOUT);
			padRowDescriptor.setDescription("Row padding between children (in pixels)");
			descriptors.add(padRowDescriptor);

			TextPropertyDescriptor padColumnDescriptor = new TextPropertyDescriptor(PROPERTY_PAD_COLUMN, "Padding Column");
			padColumnDescriptor.setCategory(CATEGORY_LAYOUT);
			padColumnDescriptor.setDescription("Column padding between children (in pixels)");
			descriptors.add(padColumnDescriptor);
		}

		return descriptors.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		switch ((String) id) {
		case PROPERTY_NAME:
			return widget.getName();
		case PROPERTY_TEXT:
			return widget.getText();
		case PROPERTY_TYPE:
			return widget.getWidgetType().getDisplayName();
		case PROPERTY_X:
			return String.valueOf(widget.getBounds().x);
		case PROPERTY_Y:
			return String.valueOf(widget.getBounds().y);
		case PROPERTY_WIDTH:
			return String.valueOf(widget.getBounds().width);
		case PROPERTY_HEIGHT:
			return String.valueOf(widget.getBounds().height);
		case PROPERTY_BG_COLOR:
			return PropertyUtils.formatColor(widget.getBgColor());
		case PROPERTY_TEXT_COLOR:
			return PropertyUtils.formatColor(widget.getTextColor());
		case PROPERTY_BORDER_WIDTH:
			return String.valueOf(widget.getBorderWidth());
		case PROPERTY_BORDER_COLOR:
			return PropertyUtils.formatColor(widget.getBorderColor());
		case PROPERTY_RADIUS:
			return String.valueOf(widget.getRadius());
		case PROPERTY_IMAGE_SOURCE:
			return widget.getImageSource();
		case PROPERTY_LAYOUT_TYPE:
			return Integer.valueOf(widget.getLayoutType().ordinal());
		case PROPERTY_FLEX_FLOW:
			return Integer.valueOf(widget.getFlexFlow().ordinal());
		case PROPERTY_FLEX_MAIN_ALIGN:
			return Integer.valueOf(widget.getFlexMainAlign().ordinal());
		case PROPERTY_FLEX_CROSS_ALIGN:
			return Integer.valueOf(widget.getFlexCrossAlign().ordinal());
		case PROPERTY_FLEX_TRACK_ALIGN:
			return Integer.valueOf(widget.getFlexTrackAlign().ordinal());
		case PROPERTY_PAD_ROW:
			return String.valueOf(widget.getPadRow());
		case PROPERTY_PAD_COLUMN:
			return String.valueOf(widget.getPadColumn());
		default:
			return null;
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		return true;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// Reset to defaults
		switch ((String) id) {
		case PROPERTY_NAME:
			widget.setName("widget");
			break;
		case PROPERTY_TEXT:
			widget.setText("");
			break;
		case PROPERTY_BG_COLOR:
			widget.setBgColor(0xFFFFFF);
			break;
		case PROPERTY_TEXT_COLOR:
			widget.setTextColor(0x000000);
			break;
		case PROPERTY_BORDER_WIDTH:
			widget.setBorderWidth(0);
			break;
		case PROPERTY_BORDER_COLOR:
			widget.setBorderColor(0x000000);
			break;
		case PROPERTY_RADIUS:
			widget.setRadius(0);
			break;
		case PROPERTY_IMAGE_SOURCE:
			widget.setImageSource("");
			break;
		case PROPERTY_LAYOUT_TYPE:
			widget.setLayoutType(LvglWidget.LayoutType.NONE);
			break;
		case PROPERTY_FLEX_FLOW:
			widget.setFlexFlow(LvglWidget.FlexFlow.ROW);
			break;
		case PROPERTY_FLEX_MAIN_ALIGN:
			widget.setFlexMainAlign(LvglWidget.FlexAlign.START);
			break;
		case PROPERTY_FLEX_CROSS_ALIGN:
			widget.setFlexCrossAlign(LvglWidget.FlexAlign.START);
			break;
		case PROPERTY_FLEX_TRACK_ALIGN:
			widget.setFlexTrackAlign(LvglWidget.FlexAlign.START);
			break;
		case PROPERTY_PAD_ROW:
			widget.setPadRow(0);
			break;
		case PROPERTY_PAD_COLUMN:
			widget.setPadColumn(0);
			break;
		default:
			break;
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		switch ((String) id) {
		case PROPERTY_NAME:
			widget.setName((String) value);
			break;
		case PROPERTY_TEXT:
			widget.setText((String) value);
			break;
		case PROPERTY_X:
			setX((String) value);
			break;
		case PROPERTY_Y:
			setY((String) value);
			break;
		case PROPERTY_WIDTH:
			setWidth((String) value);
			break;
		case PROPERTY_HEIGHT:
			setHeight((String) value);
			break;
		case PROPERTY_BG_COLOR:
			widget.setBgColor(PropertyUtils.parseColor((String) value));
			break;
		case PROPERTY_TEXT_COLOR:
			widget.setTextColor(PropertyUtils.parseColor((String) value));
			break;
		case PROPERTY_BORDER_WIDTH:
			widget.setBorderWidth(PropertyUtils.parseInt((String) value, 0));
			break;
		case PROPERTY_BORDER_COLOR:
			widget.setBorderColor(PropertyUtils.parseColor((String) value));
			break;
		case PROPERTY_RADIUS:
			widget.setRadius(PropertyUtils.parseInt((String) value, 0));
			break;
		case PROPERTY_IMAGE_SOURCE:
			widget.setImageSource((String) value);
			break;
		case PROPERTY_LAYOUT_TYPE:
			int layoutIndex = ((Integer) value).intValue();
			LvglWidget.LayoutType[] layoutTypes = LvglWidget.LayoutType.values();
			if (layoutIndex >= 0 && layoutIndex < layoutTypes.length) {
				widget.setLayoutType(layoutTypes[layoutIndex]);
			}
			break;
		case PROPERTY_FLEX_FLOW:
			int flowIndex = ((Integer) value).intValue();
			LvglWidget.FlexFlow[] flexFlows = LvglWidget.FlexFlow.values();
			if (flowIndex >= 0 && flowIndex < flexFlows.length) {
				widget.setFlexFlow(flexFlows[flowIndex]);
			}
			break;
		case PROPERTY_FLEX_MAIN_ALIGN:
			int mainAlignIndex = ((Integer) value).intValue();
			LvglWidget.FlexAlign[] flexAligns = LvglWidget.FlexAlign.values();
			if (mainAlignIndex >= 0 && mainAlignIndex < flexAligns.length) {
				widget.setFlexMainAlign(flexAligns[mainAlignIndex]);
			}
			break;
		case PROPERTY_FLEX_CROSS_ALIGN:
			int crossAlignIndex = ((Integer) value).intValue();
			LvglWidget.FlexAlign[] crossAligns = LvglWidget.FlexAlign.values();
			if (crossAlignIndex >= 0 && crossAlignIndex < crossAligns.length) {
				widget.setFlexCrossAlign(crossAligns[crossAlignIndex]);
			}
			break;
		case PROPERTY_FLEX_TRACK_ALIGN:
			int trackAlignIndex = ((Integer) value).intValue();
			LvglWidget.FlexAlign[] trackAligns = LvglWidget.FlexAlign.values();
			if (trackAlignIndex >= 0 && trackAlignIndex < trackAligns.length) {
				widget.setFlexTrackAlign(trackAligns[trackAlignIndex]);
			}
			break;
		case PROPERTY_PAD_ROW:
			widget.setPadRow(PropertyUtils.parseInt((String) value, 0));
			break;
		case PROPERTY_PAD_COLUMN:
			widget.setPadColumn(PropertyUtils.parseInt((String) value, 0));
			break;
		default:
			break;
		}
	}

	private void setX(String value) {
		int x = PropertyUtils.parseInt(value, widget.getBounds().x);
		updateBounds(bounds -> bounds.x = x);
	}

	private void setY(String value) {
		int y = PropertyUtils.parseInt(value, widget.getBounds().y);
		updateBounds(bounds -> bounds.y = y);
	}

	private void setWidth(String value) {
		int width = PropertyUtils.parseInt(value, widget.getBounds().width);
		if (width > 0) {
			updateBounds(bounds -> bounds.width = width);
		}
	}

	private void setHeight(String value) {
		int height = PropertyUtils.parseInt(value, widget.getBounds().height);
		if (height > 0) {
			updateBounds(bounds -> bounds.height = height);
		}
	}

	private void updateBounds(java.util.function.Consumer<org.eclipse.draw2d.geometry.Rectangle> modifier) {
		var bounds = widget.getBounds().getCopy();
		modifier.accept(bounds);
		widget.setBounds(bounds);
	}
}

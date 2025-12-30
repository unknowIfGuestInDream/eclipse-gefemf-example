/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

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

	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_POSITION = "Position";
	private static final String CATEGORY_SIZE = "Size";
	private static final String CATEGORY_STYLE = "Style";

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
		// General properties
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, "Name");
		nameDescriptor.setCategory(CATEGORY_GENERAL);

		PropertyDescriptor typeDescriptor = new PropertyDescriptor(PROPERTY_TYPE, "Widget Type");
		typeDescriptor.setCategory(CATEGORY_GENERAL);

		TextPropertyDescriptor textDescriptor = new TextPropertyDescriptor(PROPERTY_TEXT, "Text");
		textDescriptor.setCategory(CATEGORY_GENERAL);

		// Position properties
		TextPropertyDescriptor xDescriptor = new TextPropertyDescriptor(PROPERTY_X, "X");
		xDescriptor.setCategory(CATEGORY_POSITION);

		TextPropertyDescriptor yDescriptor = new TextPropertyDescriptor(PROPERTY_Y, "Y");
		yDescriptor.setCategory(CATEGORY_POSITION);

		// Size properties
		TextPropertyDescriptor widthDescriptor = new TextPropertyDescriptor(PROPERTY_WIDTH, "Width");
		widthDescriptor.setCategory(CATEGORY_SIZE);

		TextPropertyDescriptor heightDescriptor = new TextPropertyDescriptor(PROPERTY_HEIGHT, "Height");
		heightDescriptor.setCategory(CATEGORY_SIZE);

		// Style properties
		TextPropertyDescriptor bgColorDescriptor = new TextPropertyDescriptor(PROPERTY_BG_COLOR, "Background Color");
		bgColorDescriptor.setCategory(CATEGORY_STYLE);
		bgColorDescriptor.setDescription("Background color in hex format (e.g., #FFFFFF)");

		TextPropertyDescriptor textColorDescriptor = new TextPropertyDescriptor(PROPERTY_TEXT_COLOR, "Text Color");
		textColorDescriptor.setCategory(CATEGORY_STYLE);
		textColorDescriptor.setDescription("Text color in hex format (e.g., #000000)");

		TextPropertyDescriptor borderWidthDescriptor = new TextPropertyDescriptor(PROPERTY_BORDER_WIDTH, "Border Width");
		borderWidthDescriptor.setCategory(CATEGORY_STYLE);

		TextPropertyDescriptor borderColorDescriptor = new TextPropertyDescriptor(PROPERTY_BORDER_COLOR, "Border Color");
		borderColorDescriptor.setCategory(CATEGORY_STYLE);
		borderColorDescriptor.setDescription("Border color in hex format (e.g., #000000)");

		TextPropertyDescriptor radiusDescriptor = new TextPropertyDescriptor(PROPERTY_RADIUS, "Corner Radius");
		radiusDescriptor.setCategory(CATEGORY_STYLE);

		return new IPropertyDescriptor[] {
			nameDescriptor,
			typeDescriptor,
			textDescriptor,
			xDescriptor,
			yDescriptor,
			widthDescriptor,
			heightDescriptor,
			bgColorDescriptor,
			textColorDescriptor,
			borderWidthDescriptor,
			borderColorDescriptor,
			radiusDescriptor
		};
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
			return formatColor(widget.getBgColor());
		case PROPERTY_TEXT_COLOR:
			return formatColor(widget.getTextColor());
		case PROPERTY_BORDER_WIDTH:
			return String.valueOf(widget.getBorderWidth());
		case PROPERTY_BORDER_COLOR:
			return formatColor(widget.getBorderColor());
		case PROPERTY_RADIUS:
			return String.valueOf(widget.getRadius());
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
		default:
			break;
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		String strValue = (String) value;
		switch ((String) id) {
		case PROPERTY_NAME:
			widget.setName(strValue);
			break;
		case PROPERTY_TEXT:
			widget.setText(strValue);
			break;
		case PROPERTY_X:
			setX(strValue);
			break;
		case PROPERTY_Y:
			setY(strValue);
			break;
		case PROPERTY_WIDTH:
			setWidth(strValue);
			break;
		case PROPERTY_HEIGHT:
			setHeight(strValue);
			break;
		case PROPERTY_BG_COLOR:
			widget.setBgColor(parseColor(strValue));
			break;
		case PROPERTY_TEXT_COLOR:
			widget.setTextColor(parseColor(strValue));
			break;
		case PROPERTY_BORDER_WIDTH:
			widget.setBorderWidth(parseInt(strValue, 0));
			break;
		case PROPERTY_BORDER_COLOR:
			widget.setBorderColor(parseColor(strValue));
			break;
		case PROPERTY_RADIUS:
			widget.setRadius(parseInt(strValue, 0));
			break;
		default:
			break;
		}
	}

	private void setX(String value) {
		int x = parseInt(value, widget.getBounds().x);
		var bounds = widget.getBounds().getCopy();
		bounds.x = x;
		widget.setBounds(bounds);
	}

	private void setY(String value) {
		int y = parseInt(value, widget.getBounds().y);
		var bounds = widget.getBounds().getCopy();
		bounds.y = y;
		widget.setBounds(bounds);
	}

	private void setWidth(String value) {
		int width = parseInt(value, widget.getBounds().width);
		if (width > 0) {
			var bounds = widget.getBounds().getCopy();
			bounds.width = width;
			widget.setBounds(bounds);
		}
	}

	private void setHeight(String value) {
		int height = parseInt(value, widget.getBounds().height);
		if (height > 0) {
			var bounds = widget.getBounds().getCopy();
			bounds.height = height;
			widget.setBounds(bounds);
		}
	}

	private String formatColor(int color) {
		return String.format("#%06X", color & 0xFFFFFF);
	}

	private int parseColor(String colorStr) {
		if (colorStr == null || colorStr.isEmpty()) {
			return 0xFFFFFF;
		}
		if (colorStr.startsWith("#")) {
			colorStr = colorStr.substring(1);
		}
		try {
			return Integer.parseInt(colorStr, 16);
		} catch (NumberFormatException e) {
			return 0xFFFFFF;
		}
	}

	private int parseInt(String str, int defaultValue) {
		if (str == null || str.isEmpty()) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}

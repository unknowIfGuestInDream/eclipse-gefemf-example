/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;

/**
 * Property source for LVGL screen properties.
 * This enables editing screen properties in the Eclipse Properties view.
 */
public class LvglScreenPropertySource implements IPropertySource {

	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_WIDTH = "width";
	private static final String PROPERTY_HEIGHT = "height";
	private static final String PROPERTY_BG_COLOR = "bgColor";

	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_SIZE = "Size";
	private static final String CATEGORY_STYLE = "Style";

	private final LvglScreen screen;

	public LvglScreenPropertySource(LvglScreen screen) {
		this.screen = screen;
	}

	@Override
	public Object getEditableValue() {
		return screen;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// General properties
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, "Name");
		nameDescriptor.setCategory(CATEGORY_GENERAL);

		// Size properties
		TextPropertyDescriptor widthDescriptor = new TextPropertyDescriptor(PROPERTY_WIDTH, "Width");
		widthDescriptor.setCategory(CATEGORY_SIZE);

		TextPropertyDescriptor heightDescriptor = new TextPropertyDescriptor(PROPERTY_HEIGHT, "Height");
		heightDescriptor.setCategory(CATEGORY_SIZE);

		// Style properties
		TextPropertyDescriptor bgColorDescriptor = new TextPropertyDescriptor(PROPERTY_BG_COLOR, "Background Color");
		bgColorDescriptor.setCategory(CATEGORY_STYLE);
		bgColorDescriptor.setDescription("Background color in hex format (e.g., #FFFFFF)");

		return new IPropertyDescriptor[] {
			nameDescriptor,
			widthDescriptor,
			heightDescriptor,
			bgColorDescriptor
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		switch ((String) id) {
		case PROPERTY_NAME:
			return screen.getName();
		case PROPERTY_WIDTH:
			return String.valueOf(screen.getWidth());
		case PROPERTY_HEIGHT:
			return String.valueOf(screen.getHeight());
		case PROPERTY_BG_COLOR:
			return formatColor(screen.getBgColor());
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
		switch ((String) id) {
		case PROPERTY_NAME:
			screen.setName("screen");
			break;
		case PROPERTY_WIDTH:
			screen.setWidth(480);
			break;
		case PROPERTY_HEIGHT:
			screen.setHeight(320);
			break;
		case PROPERTY_BG_COLOR:
			screen.setBgColor(0xFFFFFF);
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
			screen.setName(strValue);
			break;
		case PROPERTY_WIDTH:
			int width = parseInt(strValue, screen.getWidth());
			if (width > 0) {
				screen.setWidth(width);
			}
			break;
		case PROPERTY_HEIGHT:
			int height = parseInt(strValue, screen.getHeight());
			if (height > 0) {
				screen.setHeight(height);
			}
			break;
		case PROPERTY_BG_COLOR:
			screen.setBgColor(parseColor(strValue));
			break;
		default:
			break;
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

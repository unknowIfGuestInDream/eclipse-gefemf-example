/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProvider;
import com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProviderRegistry;

/**
 * Property source for LVGL widget properties.
 * This enables editing widget properties in the Eclipse Properties view.
 * <p>
 * Common properties are defined here, while widget-specific properties
 * are delegated to {@link WidgetPropertyProvider} implementations registered
 * in the {@link WidgetPropertyProviderRegistry}.
 * </p>
 */
public class LvglWidgetPropertySource implements IPropertySource {

	// Common property IDs
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

	// Category names
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_POSITION = "Position";
	private static final String CATEGORY_SIZE = "Size";
	private static final String CATEGORY_STYLE = "Style";

	private final LvglWidget widget;
	private final List<WidgetPropertyProvider> providers;

	public LvglWidgetPropertySource(LvglWidget widget) {
		this.widget = widget;
		this.providers = WidgetPropertyProviderRegistry.getInstance().getProviders(widget.getWidgetType());
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

		// Style properties - Use ColorPropertyDescriptor for color fields
		ColorPropertyDescriptor bgColorDescriptor = new ColorPropertyDescriptor(PROPERTY_BG_COLOR, "Background Color");
		bgColorDescriptor.setCategory(CATEGORY_STYLE);
		bgColorDescriptor.setDescription("Background color (click to open color dialog)");
		descriptors.add(bgColorDescriptor);

		ColorPropertyDescriptor textColorDescriptor = new ColorPropertyDescriptor(PROPERTY_TEXT_COLOR, "Text Color");
		textColorDescriptor.setCategory(CATEGORY_STYLE);
		textColorDescriptor.setDescription("Text color (click to open color dialog)");
		descriptors.add(textColorDescriptor);

		TextPropertyDescriptor borderWidthDescriptor = new TextPropertyDescriptor(PROPERTY_BORDER_WIDTH, "Border Width");
		borderWidthDescriptor.setCategory(CATEGORY_STYLE);
		descriptors.add(borderWidthDescriptor);

		ColorPropertyDescriptor borderColorDescriptor = new ColorPropertyDescriptor(PROPERTY_BORDER_COLOR, "Border Color");
		borderColorDescriptor.setCategory(CATEGORY_STYLE);
		borderColorDescriptor.setDescription("Border color (click to open color dialog)");
		descriptors.add(borderColorDescriptor);

		TextPropertyDescriptor radiusDescriptor = new TextPropertyDescriptor(PROPERTY_RADIUS, "Corner Radius");
		radiusDescriptor.setCategory(CATEGORY_STYLE);
		descriptors.add(radiusDescriptor);

		// Add widget-specific properties from registered providers
		for (WidgetPropertyProvider provider : providers) {
			descriptors.addAll(provider.getPropertyDescriptors(widget));
		}

		return descriptors.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		String propertyId = (String) id;
		
		// Handle common properties
		switch (propertyId) {
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
			return ColorPropertyDescriptor.intToRgb(widget.getBgColor());
		case PROPERTY_TEXT_COLOR:
			return ColorPropertyDescriptor.intToRgb(widget.getTextColor());
		case PROPERTY_BORDER_WIDTH:
			return String.valueOf(widget.getBorderWidth());
		case PROPERTY_BORDER_COLOR:
			return ColorPropertyDescriptor.intToRgb(widget.getBorderColor());
		case PROPERTY_RADIUS:
			return String.valueOf(widget.getRadius());
		default:
			// Delegate to widget-specific providers
			for (WidgetPropertyProvider provider : providers) {
				Object value = provider.getPropertyValue(widget, propertyId);
				if (value != null) {
					return value;
				}
			}
			return null;
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		return true;
	}

	@Override
	public void resetPropertyValue(Object id) {
		String propertyId = (String) id;
		
		// Handle common property resets
		switch (propertyId) {
		case PROPERTY_NAME:
			widget.setName("widget");
			return;
		case PROPERTY_TEXT:
			widget.setText("");
			return;
		case PROPERTY_BG_COLOR:
			widget.setBgColor(0xFFFFFF);
			return;
		case PROPERTY_TEXT_COLOR:
			widget.setTextColor(0x000000);
			return;
		case PROPERTY_BORDER_WIDTH:
			widget.setBorderWidth(0);
			return;
		case PROPERTY_BORDER_COLOR:
			widget.setBorderColor(0x000000);
			return;
		case PROPERTY_RADIUS:
			widget.setRadius(0);
			return;
		default:
			// Delegate to widget-specific providers
			for (WidgetPropertyProvider provider : providers) {
				if (provider.resetPropertyValue(widget, propertyId)) {
					return;
				}
			}
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		String propertyId = (String) id;
		
		// Handle common properties
		switch (propertyId) {
		case PROPERTY_NAME:
			widget.setName((String) value);
			return;
		case PROPERTY_TEXT:
			widget.setText((String) value);
			return;
		case PROPERTY_X:
			setX((String) value);
			return;
		case PROPERTY_Y:
			setY((String) value);
			return;
		case PROPERTY_WIDTH:
			setWidth((String) value);
			return;
		case PROPERTY_HEIGHT:
			setHeight((String) value);
			return;
		case PROPERTY_BG_COLOR:
			if (value instanceof RGB) {
				widget.setBgColor(ColorPropertyDescriptor.rgbToInt((RGB) value));
			}
			return;
		case PROPERTY_TEXT_COLOR:
			if (value instanceof RGB) {
				widget.setTextColor(ColorPropertyDescriptor.rgbToInt((RGB) value));
			}
			return;
		case PROPERTY_BORDER_WIDTH:
			widget.setBorderWidth(PropertyUtils.parseInt((String) value, 0));
			return;
		case PROPERTY_BORDER_COLOR:
			if (value instanceof RGB) {
				widget.setBorderColor(ColorPropertyDescriptor.rgbToInt((RGB) value));
			}
			return;
		case PROPERTY_RADIUS:
			widget.setRadius(PropertyUtils.parseInt((String) value, 0));
			return;
		default:
			// Delegate to widget-specific providers
			for (WidgetPropertyProvider provider : providers) {
				if (provider.setPropertyValue(widget, propertyId, value)) {
					return;
				}
			}
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

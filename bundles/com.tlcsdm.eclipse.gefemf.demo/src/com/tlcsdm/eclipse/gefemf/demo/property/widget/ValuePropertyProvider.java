/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.property.PropertyUtils;

/**
 * Property provider for value-based widgets (Slider, Arc, Bar).
 * Handles the value, minValue, and maxValue properties.
 */
public class ValuePropertyProvider implements WidgetPropertyProvider {

	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_MIN_VALUE = "minValue";
	public static final String PROPERTY_MAX_VALUE = "maxValue";
	private static final String CATEGORY_VALUE = "Value";

	private final LvglWidget.WidgetType widgetType;

	/**
	 * Creates a property provider for the specified widget type.
	 * 
	 * @param widgetType the widget type (SLIDER, ARC, or BAR)
	 */
	public ValuePropertyProvider(LvglWidget.WidgetType widgetType) {
		this.widgetType = widgetType;
	}

	@Override
	public LvglWidget.WidgetType getWidgetType() {
		return widgetType;
	}

	@Override
	public List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget) {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		TextPropertyDescriptor valueDescriptor = new TextPropertyDescriptor(PROPERTY_VALUE, "Value");
		valueDescriptor.setCategory(CATEGORY_VALUE);
		valueDescriptor.setDescription("Current value of the widget");
		descriptors.add(valueDescriptor);

		TextPropertyDescriptor minValueDescriptor = new TextPropertyDescriptor(PROPERTY_MIN_VALUE, "Min Value");
		minValueDescriptor.setCategory(CATEGORY_VALUE);
		minValueDescriptor.setDescription("Minimum value");
		descriptors.add(minValueDescriptor);

		TextPropertyDescriptor maxValueDescriptor = new TextPropertyDescriptor(PROPERTY_MAX_VALUE, "Max Value");
		maxValueDescriptor.setCategory(CATEGORY_VALUE);
		maxValueDescriptor.setDescription("Maximum value");
		descriptors.add(maxValueDescriptor);

		return descriptors;
	}

	@Override
	public Object getPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
		case PROPERTY_VALUE:
			return String.valueOf(widget.getValue());
		case PROPERTY_MIN_VALUE:
			return String.valueOf(widget.getMinValue());
		case PROPERTY_MAX_VALUE:
			return String.valueOf(widget.getMaxValue());
		default:
			return null;
		}
	}

	@Override
	public boolean setPropertyValue(LvglWidget widget, String propertyId, Object value) {
		switch (propertyId) {
		case PROPERTY_VALUE:
			widget.setValue(PropertyUtils.parseInt((String) value, 0));
			return true;
		case PROPERTY_MIN_VALUE:
			widget.setMinValue(PropertyUtils.parseInt((String) value, 0));
			return true;
		case PROPERTY_MAX_VALUE:
			widget.setMaxValue(PropertyUtils.parseInt((String) value, 100));
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean resetPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
		case PROPERTY_VALUE:
			widget.setValue(0);
			return true;
		case PROPERTY_MIN_VALUE:
			widget.setMinValue(0);
			return true;
		case PROPERTY_MAX_VALUE:
			widget.setMaxValue(100);
			return true;
		default:
			return false;
		}
	}
}

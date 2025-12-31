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

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.property.CheckboxPropertyDescriptor;

/**
 * Property provider for Checkbox and Switch widgets.
 * Handles the 'checked' state property for these toggle-style widgets.
 */
public class CheckboxPropertyProvider implements WidgetPropertyProvider {

	public static final String PROPERTY_CHECKED = "checked";
	private static final String CATEGORY_STATE = "State";

	private final LvglWidget.WidgetType widgetType;

	/**
	 * Creates a property provider for the specified widget type.
	 * 
	 * @param widgetType the widget type (CHECKBOX or SWITCH)
	 */
	public CheckboxPropertyProvider(LvglWidget.WidgetType widgetType) {
		this.widgetType = widgetType;
	}

	@Override
	public LvglWidget.WidgetType getWidgetType() {
		return widgetType;
	}

	@Override
	public List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget) {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		CheckboxPropertyDescriptor checkedDescriptor = new CheckboxPropertyDescriptor(PROPERTY_CHECKED, "Checked");
		checkedDescriptor.setCategory(CATEGORY_STATE);
		checkedDescriptor.setDescription("Whether the widget is in checked/on state");
		descriptors.add(checkedDescriptor);

		return descriptors;
	}

	@Override
	public Object getPropertyValue(LvglWidget widget, String propertyId) {
		if (PROPERTY_CHECKED.equals(propertyId)) {
			return Boolean.valueOf(widget.isChecked());
		}
		return null;
	}

	@Override
	public boolean setPropertyValue(LvglWidget widget, String propertyId, Object value) {
		if (PROPERTY_CHECKED.equals(propertyId)) {
			if (value instanceof Boolean) {
				widget.setChecked(((Boolean) value).booleanValue());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean resetPropertyValue(LvglWidget widget, String propertyId) {
		if (PROPERTY_CHECKED.equals(propertyId)) {
			widget.setChecked(false);
			return true;
		}
		return false;
	}
}

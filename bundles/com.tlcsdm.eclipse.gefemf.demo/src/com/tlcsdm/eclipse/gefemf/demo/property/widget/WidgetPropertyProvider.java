/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property.widget;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Interface for widget-specific property providers.
 * <p>
 * Each widget type can have its own property provider implementation
 * that defines the specific properties available for that widget type.
 * This allows for better separation of concerns and easier extension
 * with custom widgets.
 * </p>
 */
public interface WidgetPropertyProvider {

	/**
	 * Get the widget type this provider handles.
	 * 
	 * @return the widget type
	 */
	LvglWidget.WidgetType getWidgetType();

	/**
	 * Get the property descriptors specific to this widget type.
	 * These descriptors are added in addition to the common properties.
	 * 
	 * @param widget the widget instance
	 * @return list of property descriptors
	 */
	List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget);

	/**
	 * Get the value for a widget-specific property.
	 * 
	 * @param widget the widget instance
	 * @param propertyId the property ID
	 * @return the property value, or null if not handled
	 */
	Object getPropertyValue(LvglWidget widget, String propertyId);

	/**
	 * Set the value for a widget-specific property.
	 * 
	 * @param widget the widget instance
	 * @param propertyId the property ID
	 * @param value the new value
	 * @return true if the property was handled, false otherwise
	 */
	boolean setPropertyValue(LvglWidget widget, String propertyId, Object value);

	/**
	 * Reset a widget-specific property to its default value.
	 * 
	 * @param widget the widget instance
	 * @param propertyId the property ID
	 * @return true if the property was handled, false otherwise
	 */
	boolean resetPropertyValue(LvglWidget widget, String propertyId);
}

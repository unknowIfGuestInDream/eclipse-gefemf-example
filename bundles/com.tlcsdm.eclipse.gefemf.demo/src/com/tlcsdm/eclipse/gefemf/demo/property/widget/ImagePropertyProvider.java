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
import com.tlcsdm.eclipse.gefemf.demo.property.FilePropertyDescriptor;

/**
 * Property provider for Image widgets.
 * Handles the imageSource property with a file dialog for selecting images.
 */
public class ImagePropertyProvider implements WidgetPropertyProvider {

	public static final String PROPERTY_IMAGE_SOURCE = "imageSource";
	private static final String CATEGORY_IMAGE = "Image";

	@Override
	public LvglWidget.WidgetType getWidgetType() {
		return LvglWidget.WidgetType.IMAGE;
	}

	@Override
	public List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget) {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		FilePropertyDescriptor imageSourceDescriptor = new FilePropertyDescriptor(PROPERTY_IMAGE_SOURCE, "Image Source");
		imageSourceDescriptor.setCategory(CATEGORY_IMAGE);
		imageSourceDescriptor.setDescription("Click to open file dialog and select an image (relative path will be used)");
		descriptors.add(imageSourceDescriptor);

		return descriptors;
	}

	@Override
	public Object getPropertyValue(LvglWidget widget, String propertyId) {
		if (PROPERTY_IMAGE_SOURCE.equals(propertyId)) {
			return widget.getImageSource();
		}
		return null;
	}

	@Override
	public boolean setPropertyValue(LvglWidget widget, String propertyId, Object value) {
		if (PROPERTY_IMAGE_SOURCE.equals(propertyId)) {
			widget.setImageSource((String) value);
			return true;
		}
		return false;
	}

	@Override
	public boolean resetPropertyValue(LvglWidget widget, String propertyId) {
		if (PROPERTY_IMAGE_SOURCE.equals(propertyId)) {
			widget.setImageSource("");
			return true;
		}
		return false;
	}
}

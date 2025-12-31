/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.property.PropertyUtils;

/**
 * Property provider for Container widgets.
 * Handles layout-related properties such as layoutType, flexFlow, and alignment.
 */
public class ContainerPropertyProvider implements WidgetPropertyProvider {

	public static final String PROPERTY_LAYOUT_TYPE = "layoutType";
	public static final String PROPERTY_FLEX_FLOW = "flexFlow";
	public static final String PROPERTY_FLEX_MAIN_ALIGN = "flexMainAlign";
	public static final String PROPERTY_FLEX_CROSS_ALIGN = "flexCrossAlign";
	public static final String PROPERTY_FLEX_TRACK_ALIGN = "flexTrackAlign";
	public static final String PROPERTY_PAD_ROW = "padRow";
	public static final String PROPERTY_PAD_COLUMN = "padColumn";
	private static final String CATEGORY_LAYOUT = "Layout";

	// Cached enum values arrays for dropdown properties
	private static final LvglWidget.LayoutType[] LAYOUT_TYPES = LvglWidget.LayoutType.values();
	private static final LvglWidget.FlexFlow[] FLEX_FLOWS = LvglWidget.FlexFlow.values();
	private static final LvglWidget.FlexAlign[] FLEX_ALIGNS = LvglWidget.FlexAlign.values();

	// Cached label arrays for dropdown properties
	private static final String[] LAYOUT_TYPE_LABELS;
	private static final String[] FLEX_FLOW_LABELS;
	private static final String[] FLEX_ALIGN_LABELS;

	static {
		LAYOUT_TYPE_LABELS = new String[LAYOUT_TYPES.length];
		for (int i = 0; i < LAYOUT_TYPES.length; i++) {
			LAYOUT_TYPE_LABELS[i] = LAYOUT_TYPES[i].getDisplayName();
		}

		FLEX_FLOW_LABELS = new String[FLEX_FLOWS.length];
		for (int i = 0; i < FLEX_FLOWS.length; i++) {
			FLEX_FLOW_LABELS[i] = FLEX_FLOWS[i].getDisplayName();
		}

		FLEX_ALIGN_LABELS = new String[FLEX_ALIGNS.length];
		for (int i = 0; i < FLEX_ALIGNS.length; i++) {
			FLEX_ALIGN_LABELS[i] = FLEX_ALIGNS[i].getDisplayName();
		}
	}

	@Override
	public LvglWidget.WidgetType getWidgetType() {
		return LvglWidget.WidgetType.CONTAINER;
	}

	@Override
	public List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget) {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		// Layout Type dropdown
		ComboBoxPropertyDescriptor layoutTypeDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_LAYOUT_TYPE, "Layout Type", LAYOUT_TYPE_LABELS);
		layoutTypeDescriptor.setCategory(CATEGORY_LAYOUT);
		layoutTypeDescriptor.setDescription("Layout type for the container");
		descriptors.add(layoutTypeDescriptor);

		// Flex Flow dropdown
		ComboBoxPropertyDescriptor flexFlowDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_FLOW, "Flex Flow", FLEX_FLOW_LABELS);
		flexFlowDescriptor.setCategory(CATEGORY_LAYOUT);
		flexFlowDescriptor.setDescription("Flex flow direction");
		descriptors.add(flexFlowDescriptor);

		// Flex Main Align dropdown
		ComboBoxPropertyDescriptor flexMainAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_MAIN_ALIGN, "Flex Main Align", FLEX_ALIGN_LABELS);
		flexMainAlignDescriptor.setCategory(CATEGORY_LAYOUT);
		flexMainAlignDescriptor.setDescription("Main axis alignment");
		descriptors.add(flexMainAlignDescriptor);

		// Flex Cross Align dropdown
		ComboBoxPropertyDescriptor flexCrossAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_CROSS_ALIGN, "Flex Cross Align", FLEX_ALIGN_LABELS);
		flexCrossAlignDescriptor.setCategory(CATEGORY_LAYOUT);
		flexCrossAlignDescriptor.setDescription("Cross axis alignment");
		descriptors.add(flexCrossAlignDescriptor);

		// Flex Track Align dropdown
		ComboBoxPropertyDescriptor flexTrackAlignDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_FLEX_TRACK_ALIGN, "Flex Track Align", FLEX_ALIGN_LABELS);
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

		return descriptors;
	}

	@Override
	public Object getPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
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
	public boolean setPropertyValue(LvglWidget widget, String propertyId, Object value) {
		switch (propertyId) {
		case PROPERTY_LAYOUT_TYPE:
			int layoutIndex = ((Integer) value).intValue();
			if (layoutIndex >= 0 && layoutIndex < LAYOUT_TYPES.length) {
				widget.setLayoutType(LAYOUT_TYPES[layoutIndex]);
			}
			return true;
		case PROPERTY_FLEX_FLOW:
			int flowIndex = ((Integer) value).intValue();
			if (flowIndex >= 0 && flowIndex < FLEX_FLOWS.length) {
				widget.setFlexFlow(FLEX_FLOWS[flowIndex]);
			}
			return true;
		case PROPERTY_FLEX_MAIN_ALIGN:
			int mainAlignIndex = ((Integer) value).intValue();
			if (mainAlignIndex >= 0 && mainAlignIndex < FLEX_ALIGNS.length) {
				widget.setFlexMainAlign(FLEX_ALIGNS[mainAlignIndex]);
			}
			return true;
		case PROPERTY_FLEX_CROSS_ALIGN:
			int crossAlignIndex = ((Integer) value).intValue();
			if (crossAlignIndex >= 0 && crossAlignIndex < FLEX_ALIGNS.length) {
				widget.setFlexCrossAlign(FLEX_ALIGNS[crossAlignIndex]);
			}
			return true;
		case PROPERTY_FLEX_TRACK_ALIGN:
			int trackAlignIndex = ((Integer) value).intValue();
			if (trackAlignIndex >= 0 && trackAlignIndex < FLEX_ALIGNS.length) {
				widget.setFlexTrackAlign(FLEX_ALIGNS[trackAlignIndex]);
			}
			return true;
		case PROPERTY_PAD_ROW:
			widget.setPadRow(PropertyUtils.parseInt((String) value, 0));
			return true;
		case PROPERTY_PAD_COLUMN:
			widget.setPadColumn(PropertyUtils.parseInt((String) value, 0));
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean resetPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
		case PROPERTY_LAYOUT_TYPE:
			widget.setLayoutType(LvglWidget.LayoutType.NONE);
			return true;
		case PROPERTY_FLEX_FLOW:
			widget.setFlexFlow(LvglWidget.FlexFlow.ROW);
			return true;
		case PROPERTY_FLEX_MAIN_ALIGN:
			widget.setFlexMainAlign(LvglWidget.FlexAlign.START);
			return true;
		case PROPERTY_FLEX_CROSS_ALIGN:
			widget.setFlexCrossAlign(LvglWidget.FlexAlign.START);
			return true;
		case PROPERTY_FLEX_TRACK_ALIGN:
			widget.setFlexTrackAlign(LvglWidget.FlexAlign.START);
			return true;
		case PROPERTY_PAD_ROW:
			widget.setPadRow(0);
			return true;
		case PROPERTY_PAD_COLUMN:
			widget.setPadColumn(0);
			return true;
		default:
			return false;
		}
	}
}

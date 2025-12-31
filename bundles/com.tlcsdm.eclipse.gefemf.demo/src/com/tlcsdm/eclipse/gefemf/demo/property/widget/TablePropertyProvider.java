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
 * Property provider for Table widgets.
 * Handles the rowCount, columnCount, and tableData properties.
 */
public class TablePropertyProvider implements WidgetPropertyProvider {

	public static final String PROPERTY_ROW_COUNT = "rowCount";
	public static final String PROPERTY_COLUMN_COUNT = "columnCount";
	public static final String PROPERTY_TABLE_DATA = "tableData";
	private static final String CATEGORY_TABLE = "Table";

	@Override
	public LvglWidget.WidgetType getWidgetType() {
		return LvglWidget.WidgetType.TABLE;
	}

	@Override
	public List<IPropertyDescriptor> getPropertyDescriptors(LvglWidget widget) {
		List<IPropertyDescriptor> descriptors = new ArrayList<>();

		TextPropertyDescriptor rowCountDescriptor = new TextPropertyDescriptor(PROPERTY_ROW_COUNT, "Row Count");
		rowCountDescriptor.setCategory(CATEGORY_TABLE);
		rowCountDescriptor.setDescription("Number of rows in the table");
		descriptors.add(rowCountDescriptor);

		TextPropertyDescriptor columnCountDescriptor = new TextPropertyDescriptor(PROPERTY_COLUMN_COUNT, "Column Count");
		columnCountDescriptor.setCategory(CATEGORY_TABLE);
		columnCountDescriptor.setDescription("Number of columns in the table");
		descriptors.add(columnCountDescriptor);

		TextPropertyDescriptor tableDataDescriptor = new TextPropertyDescriptor(PROPERTY_TABLE_DATA, "Table Data");
		tableDataDescriptor.setCategory(CATEGORY_TABLE);
		tableDataDescriptor.setDescription("Table cell data in format: row1col1,row1col2;row2col1,row2col2 (semicolon separates rows, comma separates columns)");
		descriptors.add(tableDataDescriptor);

		return descriptors;
	}

	@Override
	public Object getPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
		case PROPERTY_ROW_COUNT:
			return String.valueOf(widget.getRowCount());
		case PROPERTY_COLUMN_COUNT:
			return String.valueOf(widget.getColumnCount());
		case PROPERTY_TABLE_DATA:
			return widget.getTableData();
		default:
			return null;
		}
	}

	@Override
	public boolean setPropertyValue(LvglWidget widget, String propertyId, Object value) {
		switch (propertyId) {
		case PROPERTY_ROW_COUNT:
			widget.setRowCount(PropertyUtils.parseInt((String) value, 3));
			return true;
		case PROPERTY_COLUMN_COUNT:
			widget.setColumnCount(PropertyUtils.parseInt((String) value, 3));
			return true;
		case PROPERTY_TABLE_DATA:
			widget.setTableData((String) value);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean resetPropertyValue(LvglWidget widget, String propertyId) {
		switch (propertyId) {
		case PROPERTY_ROW_COUNT:
			widget.setRowCount(3);
			return true;
		case PROPERTY_COLUMN_COUNT:
			widget.setColumnCount(3);
			return true;
		case PROPERTY_TABLE_DATA:
			widget.setTableData("");
			return true;
		default:
			return false;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * XML serializer for LVGL screen models.
 */
public class LvglXmlSerializer {

	/**
	 * Save an LVGL screen to XML.
	 */
	public void save(LvglScreen screen, OutputStream outputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		// Root element
		Element root = doc.createElement("lvgl-screen");
		root.setAttribute("name", screen.getName());
		root.setAttribute("width", String.valueOf(screen.getWidth()));
		root.setAttribute("height", String.valueOf(screen.getHeight()));
		root.setAttribute("bgColor", formatColor(screen.getBgColor()));
		doc.appendChild(root);

		// Add widgets
		for (LvglWidget widget : screen.getWidgets()) {
			Element widgetElement = createWidgetElement(doc, widget);
			root.appendChild(widgetElement);
		}

		// Write to output stream
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
	}

	/**
	 * Load an LVGL screen from XML.
	 */
	public LvglScreen load(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inputStream);

		Element root = doc.getDocumentElement();
		LvglScreen screen = new LvglScreen();
		screen.setName(root.getAttribute("name"));
		screen.setWidth(parseInt(root.getAttribute("width"), 480));
		screen.setHeight(parseInt(root.getAttribute("height"), 320));
		screen.setBgColor(parseColor(root.getAttribute("bgColor")));

		// Load widgets
		NodeList widgetNodes = root.getElementsByTagName("widget");
		for (int i = 0; i < widgetNodes.getLength(); i++) {
			Element widgetElement = (Element) widgetNodes.item(i);
			// Only process direct children
			if (widgetElement.getParentNode() == root) {
				LvglWidget widget = parseWidget(widgetElement);
				screen.addWidget(widget);
			}
		}

		return screen;
	}

	private Element createWidgetElement(Document doc, LvglWidget widget) {
		Element element = doc.createElement("widget");
		element.setAttribute("name", widget.getName());
		element.setAttribute("type", widget.getWidgetType().name());
		element.setAttribute("text", widget.getText());
		element.setAttribute("x", String.valueOf(widget.getBounds().x));
		element.setAttribute("y", String.valueOf(widget.getBounds().y));
		element.setAttribute("width", String.valueOf(widget.getBounds().width));
		element.setAttribute("height", String.valueOf(widget.getBounds().height));
		element.setAttribute("bgColor", formatColor(widget.getBgColor()));
		element.setAttribute("textColor", formatColor(widget.getTextColor()));
		element.setAttribute("borderWidth", String.valueOf(widget.getBorderWidth()));
		element.setAttribute("borderColor", formatColor(widget.getBorderColor()));
		element.setAttribute("radius", String.valueOf(widget.getRadius()));

		// Image-specific property
		if (widget.getWidgetType() == LvglWidget.WidgetType.IMAGE && widget.getImageSource() != null && !widget.getImageSource().isEmpty()) {
			element.setAttribute("imageSource", widget.getImageSource());
		}

		// Checkbox/Switch checked state
		if (widget.getWidgetType() == LvglWidget.WidgetType.CHECKBOX 
				|| widget.getWidgetType() == LvglWidget.WidgetType.SWITCH) {
			element.setAttribute("checked", String.valueOf(widget.isChecked()));
		}

		// Value properties (for Slider, Arc, Bar)
		if (widget.getWidgetType() == LvglWidget.WidgetType.SLIDER 
				|| widget.getWidgetType() == LvglWidget.WidgetType.ARC 
				|| widget.getWidgetType() == LvglWidget.WidgetType.BAR) {
			element.setAttribute("value", String.valueOf(widget.getValue()));
			element.setAttribute("minValue", String.valueOf(widget.getMinValue()));
			element.setAttribute("maxValue", String.valueOf(widget.getMaxValue()));
		}

		// Table properties
		if (widget.getWidgetType() == LvglWidget.WidgetType.TABLE) {
			element.setAttribute("rowCount", String.valueOf(widget.getRowCount()));
			element.setAttribute("columnCount", String.valueOf(widget.getColumnCount()));
			if (widget.getTableData() != null && !widget.getTableData().isEmpty()) {
				element.setAttribute("tableData", widget.getTableData());
			}
		}

		// Layout properties (for containers)
		if (widget.getWidgetType() == LvglWidget.WidgetType.CONTAINER) {
			element.setAttribute("layoutType", widget.getLayoutType().name());
			if (widget.getLayoutType() == LvglWidget.LayoutType.FLEX) {
				element.setAttribute("flexFlow", widget.getFlexFlow().name());
				element.setAttribute("flexMainAlign", widget.getFlexMainAlign().name());
				element.setAttribute("flexCrossAlign", widget.getFlexCrossAlign().name());
				element.setAttribute("flexTrackAlign", widget.getFlexTrackAlign().name());
			}
			element.setAttribute("padRow", String.valueOf(widget.getPadRow()));
			element.setAttribute("padColumn", String.valueOf(widget.getPadColumn()));
		}

		// Add child widgets
		for (LvglWidget child : widget.getChildren()) {
			Element childElement = createWidgetElement(doc, child);
			element.appendChild(childElement);
		}

		return element;
	}

	private LvglWidget parseWidget(Element element) {
		LvglWidget widget = new LvglWidget();
		widget.setName(element.getAttribute("name"));

		String typeStr = element.getAttribute("type");
		if (typeStr != null && !typeStr.isEmpty()) {
			try {
				widget.setWidgetType(LvglWidget.WidgetType.valueOf(typeStr));
			} catch (IllegalArgumentException e) {
				widget.setWidgetType(LvglWidget.WidgetType.BUTTON);
			}
		}

		widget.setText(element.getAttribute("text"));

		int x = parseInt(element.getAttribute("x"), 0);
		int y = parseInt(element.getAttribute("y"), 0);
		int width = parseInt(element.getAttribute("width"), 100);
		int height = parseInt(element.getAttribute("height"), 40);
		widget.setBounds(new Rectangle(x, y, width, height));

		widget.setBgColor(parseColor(element.getAttribute("bgColor")));
		widget.setTextColor(parseColor(element.getAttribute("textColor")));
		widget.setBorderWidth(parseInt(element.getAttribute("borderWidth"), 0));
		widget.setBorderColor(parseColor(element.getAttribute("borderColor")));
		widget.setRadius(parseInt(element.getAttribute("radius"), 0));

		// Image-specific property
		String imageSource = element.getAttribute("imageSource");
		if (imageSource != null && !imageSource.isEmpty()) {
			widget.setImageSource(imageSource);
		}

		// Checkbox/Switch checked state
		String checkedStr = element.getAttribute("checked");
		if (checkedStr != null && !checkedStr.isEmpty()) {
			widget.setChecked(Boolean.parseBoolean(checkedStr));
		}

		// Value properties (for Slider, Arc, Bar)
		widget.setValue(parseInt(element.getAttribute("value"), 0));
		widget.setMinValue(parseInt(element.getAttribute("minValue"), 0));
		widget.setMaxValue(parseInt(element.getAttribute("maxValue"), 100));

		// Table properties
		widget.setRowCount(parseInt(element.getAttribute("rowCount"), 3));
		widget.setColumnCount(parseInt(element.getAttribute("columnCount"), 3));
		String tableData = element.getAttribute("tableData");
		if (tableData != null && !tableData.isEmpty()) {
			widget.setTableData(tableData);
		}

		// Layout properties
		String layoutTypeStr = element.getAttribute("layoutType");
		if (layoutTypeStr != null && !layoutTypeStr.isEmpty()) {
			try {
				widget.setLayoutType(LvglWidget.LayoutType.valueOf(layoutTypeStr));
			} catch (IllegalArgumentException e) {
				widget.setLayoutType(LvglWidget.LayoutType.NONE);
			}
		}
		String flexFlowStr = element.getAttribute("flexFlow");
		if (flexFlowStr != null && !flexFlowStr.isEmpty()) {
			try {
				widget.setFlexFlow(LvglWidget.FlexFlow.valueOf(flexFlowStr));
			} catch (IllegalArgumentException e) {
				widget.setFlexFlow(LvglWidget.FlexFlow.ROW);
			}
		}
		String flexMainAlignStr = element.getAttribute("flexMainAlign");
		if (flexMainAlignStr != null && !flexMainAlignStr.isEmpty()) {
			try {
				widget.setFlexMainAlign(LvglWidget.FlexAlign.valueOf(flexMainAlignStr));
			} catch (IllegalArgumentException e) {
				widget.setFlexMainAlign(LvglWidget.FlexAlign.START);
			}
		}
		String flexCrossAlignStr = element.getAttribute("flexCrossAlign");
		if (flexCrossAlignStr != null && !flexCrossAlignStr.isEmpty()) {
			try {
				widget.setFlexCrossAlign(LvglWidget.FlexAlign.valueOf(flexCrossAlignStr));
			} catch (IllegalArgumentException e) {
				widget.setFlexCrossAlign(LvglWidget.FlexAlign.START);
			}
		}
		String flexTrackAlignStr = element.getAttribute("flexTrackAlign");
		if (flexTrackAlignStr != null && !flexTrackAlignStr.isEmpty()) {
			try {
				widget.setFlexTrackAlign(LvglWidget.FlexAlign.valueOf(flexTrackAlignStr));
			} catch (IllegalArgumentException e) {
				widget.setFlexTrackAlign(LvglWidget.FlexAlign.START);
			}
		}
		widget.setPadRow(parseInt(element.getAttribute("padRow"), 0));
		widget.setPadColumn(parseInt(element.getAttribute("padColumn"), 0));

		// Parse child widgets
		NodeList children = element.getElementsByTagName("widget");
		for (int i = 0; i < children.getLength(); i++) {
			Element childElement = (Element) children.item(i);
			// Only process direct children
			if (childElement.getParentNode() == element) {
				LvglWidget child = parseWidget(childElement);
				widget.addChild(child);
			}
		}

		return widget;
	}

	/**
	 * Format color as hex string (RGB only, alpha channel is not preserved).
	 * LVGL basic widgets primarily use RGB colors.
	 */
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

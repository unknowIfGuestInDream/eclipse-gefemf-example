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

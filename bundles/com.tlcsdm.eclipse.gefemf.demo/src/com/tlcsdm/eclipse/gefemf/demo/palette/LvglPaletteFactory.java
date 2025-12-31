/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.palette;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Factory for creating the LVGL palette for the diagram editor.
 */
public class LvglPaletteFactory {

	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();

		// Tools group
		PaletteDrawer toolsDrawer = new PaletteDrawer("Tools");

		// Selection tool
		SelectionToolEntry selectionTool = new SelectionToolEntry();
		toolsDrawer.add(selectionTool);
		palette.setDefaultEntry(selectionTool);

		palette.add(toolsDrawer);

		// Basic Widgets group - core LVGL widgets for layout and display
		PaletteDrawer basicDrawer = new PaletteDrawer("Basic");

		// Container and layout
		basicDrawer.add(createWidgetEntry("Container", "Create an LVGL container", LvglWidget.WidgetType.CONTAINER));
		// Button
		basicDrawer.add(createWidgetEntry("Button", "Create an LVGL button", LvglWidget.WidgetType.BUTTON));
		// Label
		basicDrawer.add(createWidgetEntry("Label", "Create an LVGL label", LvglWidget.WidgetType.LABEL));
		// Image
		basicDrawer.add(createWidgetEntry("Image", "Create an LVGL image", LvglWidget.WidgetType.IMAGE));
		// Line
		basicDrawer.add(createWidgetEntry("Line", "Create an LVGL line", LvglWidget.WidgetType.LINE));
		// Arc
		basicDrawer.add(createWidgetEntry("Arc", "Create an LVGL arc", LvglWidget.WidgetType.ARC));
		// Bar
		basicDrawer.add(createWidgetEntry("Bar", "Create an LVGL progress bar", LvglWidget.WidgetType.BAR));

		palette.add(basicDrawer);

		// Input Widgets group - user input components
		PaletteDrawer inputDrawer = new PaletteDrawer("Input");

		// Slider
		inputDrawer.add(createWidgetEntry("Slider", "Create an LVGL slider", LvglWidget.WidgetType.SLIDER));
		// Switch
		inputDrawer.add(createWidgetEntry("Switch", "Create an LVGL switch", LvglWidget.WidgetType.SWITCH));
		// Checkbox
		inputDrawer.add(createWidgetEntry("Checkbox", "Create an LVGL checkbox", LvglWidget.WidgetType.CHECKBOX));
		// Dropdown
		inputDrawer.add(createWidgetEntry("Dropdown", "Create an LVGL dropdown", LvglWidget.WidgetType.DROPDOWN));
		// Textarea
		inputDrawer.add(createWidgetEntry("Textarea", "Create an LVGL textarea", LvglWidget.WidgetType.TEXTAREA));
		// Roller
		inputDrawer.add(createWidgetEntry("Roller", "Create an LVGL roller", LvglWidget.WidgetType.ROLLER));
		// Spinbox
		inputDrawer.add(createWidgetEntry("Spinbox", "Create an LVGL spinbox", LvglWidget.WidgetType.SPINBOX));
		// Keyboard
		inputDrawer.add(createWidgetEntry("Keyboard", "Create an LVGL keyboard", LvglWidget.WidgetType.KEYBOARD));
		// Button Matrix
		inputDrawer.add(createWidgetEntry("Button Matrix", "Create an LVGL button matrix", LvglWidget.WidgetType.BUTTONMATRIX));

		palette.add(inputDrawer);

		// Advanced Widgets group - complex display and container widgets
		PaletteDrawer advancedDrawer = new PaletteDrawer("Advanced");

		// LED
		advancedDrawer.add(createWidgetEntry("LED", "Create an LVGL LED", LvglWidget.WidgetType.LED));
		// Scale
		advancedDrawer.add(createWidgetEntry("Scale", "Create an LVGL scale", LvglWidget.WidgetType.SCALE));
		// Spinner
		advancedDrawer.add(createWidgetEntry("Spinner", "Create an LVGL spinner", LvglWidget.WidgetType.SPINNER));
		// Animation Image
		advancedDrawer.add(createWidgetEntry("Animation Image", "Create an LVGL animation image", LvglWidget.WidgetType.ANIMIMG));
		// Arc Label
		advancedDrawer.add(createWidgetEntry("Arc Label", "Create an LVGL arc label", LvglWidget.WidgetType.ARCLABEL));
		// Chart
		advancedDrawer.add(createWidgetEntry("Chart", "Create an LVGL chart", LvglWidget.WidgetType.CHART));
		// Table
		advancedDrawer.add(createWidgetEntry("Table", "Create an LVGL table", LvglWidget.WidgetType.TABLE));
		// Calendar
		advancedDrawer.add(createWidgetEntry("Calendar", "Create an LVGL calendar", LvglWidget.WidgetType.CALENDAR));
		// Spangroup
		advancedDrawer.add(createWidgetEntry("Spangroup", "Create an LVGL spangroup", LvglWidget.WidgetType.SPANGROUP));
		// List
		advancedDrawer.add(createWidgetEntry("List", "Create an LVGL list", LvglWidget.WidgetType.LIST));
		// Menu
		advancedDrawer.add(createWidgetEntry("Menu", "Create an LVGL menu", LvglWidget.WidgetType.MENU));
		// Tab View
		advancedDrawer.add(createWidgetEntry("Tab View", "Create an LVGL tab view", LvglWidget.WidgetType.TABVIEW));
		// Tile View
		advancedDrawer.add(createWidgetEntry("Tile View", "Create an LVGL tile view", LvglWidget.WidgetType.TILEVIEW));
		// Window
		advancedDrawer.add(createWidgetEntry("Window", "Create an LVGL window", LvglWidget.WidgetType.WIN));
		// Message Box
		advancedDrawer.add(createWidgetEntry("Message Box", "Create an LVGL message box", LvglWidget.WidgetType.MSGBOX));
		// Image Button
		advancedDrawer.add(createWidgetEntry("Image Button", "Create an LVGL image button", LvglWidget.WidgetType.IMAGEBUTTON));
		// Canvas
		advancedDrawer.add(createWidgetEntry("Canvas", "Create an LVGL canvas", LvglWidget.WidgetType.CANVAS));

		palette.add(advancedDrawer);

		return palette;
	}

	/**
	 * Create a palette entry for a widget type with icon.
	 */
	private static CreationToolEntry createWidgetEntry(String label, String description, LvglWidget.WidgetType type) {
		ImageDescriptor icon = LvglWidgetIcons.getIcon(type);
		return new CreationToolEntry(label, description, new LvglWidgetFactory(type), icon, icon);
	}

	/**
	 * Factory for creating LVGL widgets.
	 */
	private static class LvglWidgetFactory implements CreationFactory {
		private final LvglWidget.WidgetType widgetType;
		private static final AtomicInteger counter = new AtomicInteger(0);

		public LvglWidgetFactory(LvglWidget.WidgetType widgetType) {
			this.widgetType = widgetType;
		}

		@Override
		public Object getNewObject() {
			int count = counter.incrementAndGet();
			String name = widgetType.name().toLowerCase() + "_" + count;
			LvglWidget widget = new LvglWidget(name, widgetType);

			// Set default text and size based on widget type
			switch (widgetType) {
			case BUTTON:
				widget.setText("Button");
				widget.setBounds(widget.getBounds().setSize(100, 40));
				break;
			case LABEL:
				widget.setText("Label");
				widget.setBounds(widget.getBounds().setSize(80, 30));
				break;
			case CHECKBOX:
				widget.setText("Checkbox");
				widget.setBounds(widget.getBounds().setSize(120, 30));
				break;
			case DROPDOWN:
				widget.setText("Option 1\\nOption 2\\nOption 3");
				widget.setBounds(widget.getBounds().setSize(120, 40));
				break;
			case TEXTAREA:
				widget.setText("");
				widget.setBounds(widget.getBounds().setSize(150, 80));
				break;
			case SLIDER:
				widget.setBounds(widget.getBounds().setSize(150, 20));
				break;
			case SWITCH:
				widget.setBounds(widget.getBounds().setSize(50, 25));
				break;
			case ARC:
				widget.setBounds(widget.getBounds().setSize(100, 100));
				break;
			case BAR:
				widget.setBounds(widget.getBounds().setSize(150, 20));
				break;
			case IMAGE:
				widget.setBounds(widget.getBounds().setSize(64, 64));
				break;
			case CONTAINER:
				widget.setBounds(widget.getBounds().setSize(200, 150));
				break;
			// New widget types with default sizes
			case ANIMIMG:
				widget.setBounds(widget.getBounds().setSize(64, 64));
				break;
			case ARCLABEL:
				widget.setText("Arc Label");
				widget.setBounds(widget.getBounds().setSize(100, 100));
				break;
			case BUTTONMATRIX:
				widget.setText("Btn1\\nBtn2\\nBtn3");
				widget.setBounds(widget.getBounds().setSize(200, 100));
				break;
			case CALENDAR:
				widget.setBounds(widget.getBounds().setSize(230, 230));
				break;
			case CANVAS:
				widget.setBounds(widget.getBounds().setSize(200, 150));
				break;
			case CHART:
				widget.setBounds(widget.getBounds().setSize(200, 150));
				break;
			case IMAGEBUTTON:
				widget.setBounds(widget.getBounds().setSize(64, 64));
				break;
			case KEYBOARD:
				widget.setBounds(widget.getBounds().setSize(300, 150));
				break;
			case LED:
				widget.setBounds(widget.getBounds().setSize(30, 30));
				break;
			case LINE:
				widget.setBounds(widget.getBounds().setSize(100, 10));
				break;
			case LIST:
				widget.setBounds(widget.getBounds().setSize(150, 200));
				break;
			case MENU:
				widget.setBounds(widget.getBounds().setSize(200, 250));
				break;
			case MSGBOX:
				widget.setText("Message");
				widget.setBounds(widget.getBounds().setSize(200, 120));
				break;
			case ROLLER:
				widget.setText("Option 1\\nOption 2\\nOption 3");
				widget.setBounds(widget.getBounds().setSize(100, 100));
				break;
			case SCALE:
				widget.setBounds(widget.getBounds().setSize(150, 30));
				break;
			case SPANGROUP:
				widget.setText("Spangroup");
				widget.setBounds(widget.getBounds().setSize(150, 50));
				break;
			case SPINBOX:
				widget.setBounds(widget.getBounds().setSize(100, 40));
				break;
			case SPINNER:
				widget.setBounds(widget.getBounds().setSize(50, 50));
				break;
			case TABLE:
				widget.setBounds(widget.getBounds().setSize(200, 150));
				break;
			case TABVIEW:
				widget.setBounds(widget.getBounds().setSize(250, 200));
				break;
			case TILEVIEW:
				widget.setBounds(widget.getBounds().setSize(200, 200));
				break;
			case WIN:
				widget.setText("Window");
				widget.setBounds(widget.getBounds().setSize(300, 250));
				break;
			}

			return widget;
		}

		@Override
		public Object getObjectType() {
			return LvglWidget.class;
		}
	}
}

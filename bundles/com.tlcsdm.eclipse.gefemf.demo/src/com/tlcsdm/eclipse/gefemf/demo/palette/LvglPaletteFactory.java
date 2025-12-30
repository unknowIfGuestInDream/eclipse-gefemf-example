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

		// Basic Widgets group
		PaletteDrawer basicDrawer = new PaletteDrawer("Basic Widgets");

		// Button
		CreationToolEntry buttonTool = new CreationToolEntry("Button", "Create an LVGL button",
				new LvglWidgetFactory(LvglWidget.WidgetType.BUTTON), null, null);
		basicDrawer.add(buttonTool);

		// Label
		CreationToolEntry labelTool = new CreationToolEntry("Label", "Create an LVGL label",
				new LvglWidgetFactory(LvglWidget.WidgetType.LABEL), null, null);
		basicDrawer.add(labelTool);

		// Container
		CreationToolEntry containerTool = new CreationToolEntry("Container", "Create an LVGL container",
				new LvglWidgetFactory(LvglWidget.WidgetType.CONTAINER), null, null);
		basicDrawer.add(containerTool);

		// Image
		CreationToolEntry imageTool = new CreationToolEntry("Image", "Create an LVGL image",
				new LvglWidgetFactory(LvglWidget.WidgetType.IMAGE), null, null);
		basicDrawer.add(imageTool);

		// Line
		CreationToolEntry lineTool = new CreationToolEntry("Line", "Create an LVGL line",
				new LvglWidgetFactory(LvglWidget.WidgetType.LINE), null, null);
		basicDrawer.add(lineTool);

		palette.add(basicDrawer);

		// Input Widgets group
		PaletteDrawer inputDrawer = new PaletteDrawer("Input Widgets");

		// Slider
		CreationToolEntry sliderTool = new CreationToolEntry("Slider", "Create an LVGL slider",
				new LvglWidgetFactory(LvglWidget.WidgetType.SLIDER), null, null);
		inputDrawer.add(sliderTool);

		// Switch
		CreationToolEntry switchTool = new CreationToolEntry("Switch", "Create an LVGL switch",
				new LvglWidgetFactory(LvglWidget.WidgetType.SWITCH), null, null);
		inputDrawer.add(switchTool);

		// Checkbox
		CreationToolEntry checkboxTool = new CreationToolEntry("Checkbox", "Create an LVGL checkbox",
				new LvglWidgetFactory(LvglWidget.WidgetType.CHECKBOX), null, null);
		inputDrawer.add(checkboxTool);

		// Dropdown
		CreationToolEntry dropdownTool = new CreationToolEntry("Dropdown", "Create an LVGL dropdown",
				new LvglWidgetFactory(LvglWidget.WidgetType.DROPDOWN), null, null);
		inputDrawer.add(dropdownTool);

		// Textarea
		CreationToolEntry textareaTool = new CreationToolEntry("Textarea", "Create an LVGL textarea",
				new LvglWidgetFactory(LvglWidget.WidgetType.TEXTAREA), null, null);
		inputDrawer.add(textareaTool);

		// Roller
		CreationToolEntry rollerTool = new CreationToolEntry("Roller", "Create an LVGL roller",
				new LvglWidgetFactory(LvglWidget.WidgetType.ROLLER), null, null);
		inputDrawer.add(rollerTool);

		// Spinbox
		CreationToolEntry spinboxTool = new CreationToolEntry("Spinbox", "Create an LVGL spinbox",
				new LvglWidgetFactory(LvglWidget.WidgetType.SPINBOX), null, null);
		inputDrawer.add(spinboxTool);

		// Keyboard
		CreationToolEntry keyboardTool = new CreationToolEntry("Keyboard", "Create an LVGL keyboard",
				new LvglWidgetFactory(LvglWidget.WidgetType.KEYBOARD), null, null);
		inputDrawer.add(keyboardTool);

		// Button Matrix
		CreationToolEntry btnMatrixTool = new CreationToolEntry("Button Matrix", "Create an LVGL button matrix",
				new LvglWidgetFactory(LvglWidget.WidgetType.BUTTONMATRIX), null, null);
		inputDrawer.add(btnMatrixTool);

		palette.add(inputDrawer);

		// Display Widgets group
		PaletteDrawer displayDrawer = new PaletteDrawer("Display Widgets");

		// Arc
		CreationToolEntry arcTool = new CreationToolEntry("Arc", "Create an LVGL arc",
				new LvglWidgetFactory(LvglWidget.WidgetType.ARC), null, null);
		displayDrawer.add(arcTool);

		// Bar
		CreationToolEntry barTool = new CreationToolEntry("Bar", "Create an LVGL progress bar",
				new LvglWidgetFactory(LvglWidget.WidgetType.BAR), null, null);
		displayDrawer.add(barTool);

		// LED
		CreationToolEntry ledTool = new CreationToolEntry("LED", "Create an LVGL LED",
				new LvglWidgetFactory(LvglWidget.WidgetType.LED), null, null);
		displayDrawer.add(ledTool);

		// Scale
		CreationToolEntry scaleTool = new CreationToolEntry("Scale", "Create an LVGL scale",
				new LvglWidgetFactory(LvglWidget.WidgetType.SCALE), null, null);
		displayDrawer.add(scaleTool);

		// Spinner
		CreationToolEntry spinnerTool = new CreationToolEntry("Spinner", "Create an LVGL spinner",
				new LvglWidgetFactory(LvglWidget.WidgetType.SPINNER), null, null);
		displayDrawer.add(spinnerTool);

		// Animation Image
		CreationToolEntry animImgTool = new CreationToolEntry("Animation Image", "Create an LVGL animation image",
				new LvglWidgetFactory(LvglWidget.WidgetType.ANIMIMG), null, null);
		displayDrawer.add(animImgTool);

		// Arc Label
		CreationToolEntry arcLabelTool = new CreationToolEntry("Arc Label", "Create an LVGL arc label",
				new LvglWidgetFactory(LvglWidget.WidgetType.ARCLABEL), null, null);
		displayDrawer.add(arcLabelTool);

		palette.add(displayDrawer);

		// Data Widgets group
		PaletteDrawer dataDrawer = new PaletteDrawer("Data Widgets");

		// Chart
		CreationToolEntry chartTool = new CreationToolEntry("Chart", "Create an LVGL chart",
				new LvglWidgetFactory(LvglWidget.WidgetType.CHART), null, null);
		dataDrawer.add(chartTool);

		// Table
		CreationToolEntry tableTool = new CreationToolEntry("Table", "Create an LVGL table",
				new LvglWidgetFactory(LvglWidget.WidgetType.TABLE), null, null);
		dataDrawer.add(tableTool);

		// Calendar
		CreationToolEntry calendarTool = new CreationToolEntry("Calendar", "Create an LVGL calendar",
				new LvglWidgetFactory(LvglWidget.WidgetType.CALENDAR), null, null);
		dataDrawer.add(calendarTool);

		// Spangroup
		CreationToolEntry spangroupTool = new CreationToolEntry("Spangroup", "Create an LVGL spangroup",
				new LvglWidgetFactory(LvglWidget.WidgetType.SPANGROUP), null, null);
		dataDrawer.add(spangroupTool);

		palette.add(dataDrawer);

		// Container Widgets group
		PaletteDrawer containerDrawer = new PaletteDrawer("Container Widgets");

		// List
		CreationToolEntry listTool = new CreationToolEntry("List", "Create an LVGL list",
				new LvglWidgetFactory(LvglWidget.WidgetType.LIST), null, null);
		containerDrawer.add(listTool);

		// Menu
		CreationToolEntry menuTool = new CreationToolEntry("Menu", "Create an LVGL menu",
				new LvglWidgetFactory(LvglWidget.WidgetType.MENU), null, null);
		containerDrawer.add(menuTool);

		// Tab View
		CreationToolEntry tabViewTool = new CreationToolEntry("Tab View", "Create an LVGL tab view",
				new LvglWidgetFactory(LvglWidget.WidgetType.TABVIEW), null, null);
		containerDrawer.add(tabViewTool);

		// Tile View
		CreationToolEntry tileViewTool = new CreationToolEntry("Tile View", "Create an LVGL tile view",
				new LvglWidgetFactory(LvglWidget.WidgetType.TILEVIEW), null, null);
		containerDrawer.add(tileViewTool);

		// Window
		CreationToolEntry winTool = new CreationToolEntry("Window", "Create an LVGL window",
				new LvglWidgetFactory(LvglWidget.WidgetType.WIN), null, null);
		containerDrawer.add(winTool);

		// Message Box
		CreationToolEntry msgboxTool = new CreationToolEntry("Message Box", "Create an LVGL message box",
				new LvglWidgetFactory(LvglWidget.WidgetType.MSGBOX), null, null);
		containerDrawer.add(msgboxTool);

		palette.add(containerDrawer);

		// Special Widgets group
		PaletteDrawer specialDrawer = new PaletteDrawer("Special Widgets");

		// Image Button
		CreationToolEntry imgBtnTool = new CreationToolEntry("Image Button", "Create an LVGL image button",
				new LvglWidgetFactory(LvglWidget.WidgetType.IMAGEBUTTON), null, null);
		specialDrawer.add(imgBtnTool);

		// Canvas
		CreationToolEntry canvasTool = new CreationToolEntry("Canvas", "Create an LVGL canvas",
				new LvglWidgetFactory(LvglWidget.WidgetType.CANVAS), null, null);
		specialDrawer.add(canvasTool);

		palette.add(specialDrawer);

		return palette;
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

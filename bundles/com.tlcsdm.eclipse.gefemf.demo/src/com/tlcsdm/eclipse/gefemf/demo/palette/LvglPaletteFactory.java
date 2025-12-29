/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.palette;

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

		palette.add(displayDrawer);

		return palette;
	}

	/**
	 * Factory for creating LVGL widgets.
	 */
	private static class LvglWidgetFactory implements CreationFactory {
		private final LvglWidget.WidgetType widgetType;
		private static int counter = 0;

		public LvglWidgetFactory(LvglWidget.WidgetType widgetType) {
			this.widgetType = widgetType;
		}

		@Override
		public Object getNewObject() {
			counter++;
			String name = widgetType.name().toLowerCase() + "_" + counter;
			LvglWidget widget = new LvglWidget(name, widgetType);

			// Set default text based on widget type
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
			}

			return widget;
		}

		@Override
		public Object getObjectType() {
			return LvglWidget.class;
		}
	}
}

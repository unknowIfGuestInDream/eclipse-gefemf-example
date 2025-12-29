/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.generator;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Code generator that generates LVGL C code from the screen model.
 */
public class LvglCodeGenerator {

	private final LvglScreen screen;

	public LvglCodeGenerator(LvglScreen screen) {
		this.screen = screen;
	}

	/**
	 * Generate the header file content.
	 */
	public String generateHeader() {
		StringBuilder sb = new StringBuilder();
		String guardName = screen.getName().toUpperCase() + "_H";

		sb.append("/**\n");
		sb.append(" * @file ").append(screen.getName()).append(".h\n");
		sb.append(" * @brief LVGL UI screen header - auto-generated\n");
		sb.append(" */\n\n");

		sb.append("#ifndef ").append(guardName).append("\n");
		sb.append("#define ").append(guardName).append("\n\n");

		sb.append("#include \"lvgl.h\"\n\n");

		sb.append("#ifdef __cplusplus\n");
		sb.append("extern \"C\" {\n");
		sb.append("#endif\n\n");

		// Declare screen object
		sb.append("extern lv_obj_t *").append(screen.getName()).append(";\n\n");

		// Declare widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDeclarations(sb, widget);
		}

		sb.append("\n");

		// Function declarations
		sb.append("void ").append(screen.getName()).append("_create(void);\n");
		sb.append("void ").append(screen.getName()).append("_delete(void);\n");

		sb.append("\n#ifdef __cplusplus\n");
		sb.append("}\n");
		sb.append("#endif\n\n");

		sb.append("#endif /* ").append(guardName).append(" */\n");

		return sb.toString();
	}

	/**
	 * Generate the source file content.
	 */
	public String generateSource() {
		StringBuilder sb = new StringBuilder();

		sb.append("/**\n");
		sb.append(" * @file ").append(screen.getName()).append(".c\n");
		sb.append(" * @brief LVGL UI screen implementation - auto-generated\n");
		sb.append(" */\n\n");

		sb.append("#include \"").append(screen.getName()).append(".h\"\n\n");

		// Define screen object
		sb.append("lv_obj_t *").append(screen.getName()).append(" = NULL;\n\n");

		// Define widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDefinitions(sb, widget);
		}

		sb.append("\n");

		// Create function
		sb.append("void ").append(screen.getName()).append("_create(void) {\n");
		sb.append("    ").append(screen.getName()).append(" = lv_obj_create(NULL);\n");
		sb.append("    lv_obj_set_size(").append(screen.getName()).append(", ");
		sb.append(screen.getWidth()).append(", ").append(screen.getHeight()).append(");\n");
		sb.append("    lv_obj_set_style_bg_color(").append(screen.getName());
		sb.append(", lv_color_hex(0x").append(String.format("%06X", screen.getBgColor()));
		sb.append("), LV_PART_MAIN);\n\n");

		// Create widgets
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetCreation(sb, widget, screen.getName(), "    ");
		}

		sb.append("}\n\n");

		// Delete function
		sb.append("void ").append(screen.getName()).append("_delete(void) {\n");
		sb.append("    if (").append(screen.getName()).append(" != NULL) {\n");
		sb.append("        lv_obj_del(").append(screen.getName()).append(");\n");
		sb.append("        ").append(screen.getName()).append(" = NULL;\n");
		sb.append("    }\n");
		sb.append("}\n");

		return sb.toString();
	}

	private void generateWidgetDeclarations(StringBuilder sb, LvglWidget widget) {
		sb.append("extern lv_obj_t *").append(widget.getVariableName()).append(";\n");
		for (LvglWidget child : widget.getChildren()) {
			generateWidgetDeclarations(sb, child);
		}
	}

	private void generateWidgetDefinitions(StringBuilder sb, LvglWidget widget) {
		sb.append("lv_obj_t *").append(widget.getVariableName()).append(" = NULL;\n");
		for (LvglWidget child : widget.getChildren()) {
			generateWidgetDefinitions(sb, child);
		}
	}

	private void generateWidgetCreation(StringBuilder sb, LvglWidget widget, String parent, String indent) {
		String varName = widget.getVariableName();
		String createFunc = getCreateFunction(widget.getWidgetType());

		// Create widget
		sb.append(indent).append(varName).append(" = ").append(createFunc);
		sb.append("(").append(parent).append(");\n");

		// Set position and size
		sb.append(indent).append("lv_obj_set_pos(").append(varName).append(", ");
		sb.append(widget.getBounds().x).append(", ").append(widget.getBounds().y).append(");\n");

		sb.append(indent).append("lv_obj_set_size(").append(varName).append(", ");
		sb.append(widget.getBounds().width).append(", ").append(widget.getBounds().height).append(");\n");

		// Set text if applicable
		String text = widget.getText();
		if (text != null && !text.isEmpty()) {
			switch (widget.getWidgetType()) {
			case LABEL:
				sb.append(indent).append("lv_label_set_text(").append(varName);
				sb.append(", \"").append(escapeString(text)).append("\");\n");
				break;
			case BUTTON:
				// Create a label inside the button
				sb.append(indent).append("{\n");
				sb.append(indent).append("    lv_obj_t *label = lv_label_create(").append(varName).append(");\n");
				sb.append(indent).append("    lv_label_set_text(label, \"").append(escapeString(text)).append("\");\n");
				sb.append(indent).append("    lv_obj_center(label);\n");
				sb.append(indent).append("}\n");
				break;
			case CHECKBOX:
				sb.append(indent).append("lv_checkbox_set_text(").append(varName);
				sb.append(", \"").append(escapeString(text)).append("\");\n");
				break;
			case DROPDOWN:
				sb.append(indent).append("lv_dropdown_set_options(").append(varName);
				sb.append(", \"").append(escapeString(text)).append("\");\n");
				break;
			case TEXTAREA:
				sb.append(indent).append("lv_textarea_set_text(").append(varName);
				sb.append(", \"").append(escapeString(text)).append("\");\n");
				break;
			default:
				break;
			}
		}

		// Set background color
		if (widget.getBgColor() != 0xFFFFFF) {
			sb.append(indent).append("lv_obj_set_style_bg_color(").append(varName);
			sb.append(", lv_color_hex(0x").append(String.format("%06X", widget.getBgColor()));
			sb.append("), LV_PART_MAIN);\n");
		}

		// Set text color
		if (widget.getTextColor() != 0x000000) {
			sb.append(indent).append("lv_obj_set_style_text_color(").append(varName);
			sb.append(", lv_color_hex(0x").append(String.format("%06X", widget.getTextColor()));
			sb.append("), LV_PART_MAIN);\n");
		}

		// Set border
		if (widget.getBorderWidth() > 0) {
			sb.append(indent).append("lv_obj_set_style_border_width(").append(varName);
			sb.append(", ").append(widget.getBorderWidth()).append(", LV_PART_MAIN);\n");

			sb.append(indent).append("lv_obj_set_style_border_color(").append(varName);
			sb.append(", lv_color_hex(0x").append(String.format("%06X", widget.getBorderColor()));
			sb.append("), LV_PART_MAIN);\n");
		}

		// Set radius
		if (widget.getRadius() > 0) {
			sb.append(indent).append("lv_obj_set_style_radius(").append(varName);
			sb.append(", ").append(widget.getRadius()).append(", LV_PART_MAIN);\n");
		}

		sb.append("\n");

		// Create child widgets
		for (LvglWidget child : widget.getChildren()) {
			generateWidgetCreation(sb, child, varName, indent);
		}
	}

	private String getCreateFunction(LvglWidget.WidgetType type) {
		switch (type) {
		case BUTTON:
			return "lv_btn_create";
		case LABEL:
			return "lv_label_create";
		case SLIDER:
			return "lv_slider_create";
		case SWITCH:
			return "lv_switch_create";
		case CHECKBOX:
			return "lv_checkbox_create";
		case DROPDOWN:
			return "lv_dropdown_create";
		case TEXTAREA:
			return "lv_textarea_create";
		case IMAGE:
			return "lv_img_create";
		case ARC:
			return "lv_arc_create";
		case BAR:
			return "lv_bar_create";
		case CONTAINER:
		default:
			return "lv_obj_create";
		}
	}

	private String escapeString(String str) {
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}
}

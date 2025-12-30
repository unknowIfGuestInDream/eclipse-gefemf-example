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
	private final String baseName;

	public LvglCodeGenerator(LvglScreen screen) {
		this(screen, null);
	}

	public LvglCodeGenerator(LvglScreen screen, String baseName) {
		this.screen = screen;
		this.baseName = baseName;
	}

	/**
	 * Get the name to use for file generation (file base name if provided, otherwise screen name).
	 */
	private String getGenerationName() {
		return (baseName != null && !baseName.isEmpty()) ? baseName : screen.getName();
	}

	/**
	 * Generate the header file content.
	 */
	public String generateHeader() {
		StringBuilder sb = new StringBuilder();
		String generationName = getGenerationName();
		String guardName = generationName.toUpperCase() + "_H";

		sb.append("/**\n");
		sb.append(" * @file ").append(generationName).append(".h\n");
		sb.append(" * @brief LVGL UI screen header - auto-generated\n");
		sb.append(" */\n\n");

		sb.append("#ifndef ").append(guardName).append("\n");
		sb.append("#define ").append(guardName).append("\n\n");

		sb.append("#include \"lvgl.h\"\n\n");

		sb.append("#ifdef __cplusplus\n");
		sb.append("extern \"C\" {\n");
		sb.append("#endif\n\n");

		// Declare screen object
		sb.append("extern lv_obj_t *").append(generationName).append(";\n\n");

		// Declare widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDeclarations(sb, widget);
		}

		sb.append("\n");

		// Function declarations
		sb.append("void ").append(generationName).append("_create(void);\n");
		sb.append("void ").append(generationName).append("_delete(void);\n");

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
		String generationName = getGenerationName();

		sb.append("/**\n");
		sb.append(" * @file ").append(generationName).append(".c\n");
		sb.append(" * @brief LVGL UI screen implementation - auto-generated\n");
		sb.append(" */\n\n");

		sb.append("#include \"").append(generationName).append(".h\"\n\n");

		// Define screen object
		sb.append("lv_obj_t *").append(generationName).append(" = NULL;\n\n");

		// Define widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDefinitions(sb, widget);
		}

		sb.append("\n");

		// Create function
		sb.append("void ").append(generationName).append("_create(void) {\n");
		sb.append("    ").append(generationName).append(" = lv_obj_create(NULL);\n");
		sb.append("    lv_obj_set_size(").append(generationName).append(", ");
		sb.append(screen.getWidth()).append(", ").append(screen.getHeight()).append(");\n");
		sb.append("    lv_obj_set_style_bg_color(").append(generationName);
		sb.append(", lv_color_hex(0x").append(String.format("%06X", screen.getBgColor()));
		sb.append("), LV_PART_MAIN);\n\n");

		// Create widgets
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetCreation(sb, widget, generationName, "    ");
		}

		sb.append("}\n\n");

		// Delete function
		sb.append("void ").append(generationName).append("_delete(void) {\n");
		sb.append("    if (").append(generationName).append(" != NULL) {\n");
		sb.append("        lv_obj_del(").append(generationName).append(");\n");
		sb.append("        ").append(generationName).append(" = NULL;\n");
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

		// Set image source if applicable
		if (widget.getWidgetType() == LvglWidget.WidgetType.IMAGE) {
			String imageSource = widget.getImageSource();
			if (imageSource != null && !imageSource.isEmpty()) {
				sb.append(indent).append("lv_img_set_src(").append(varName);
				sb.append(", ").append(imageSource).append(");\n");
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

		// Set layout for containers
		if (widget.getWidgetType() == LvglWidget.WidgetType.CONTAINER && widget.getLayoutType() != LvglWidget.LayoutType.NONE) {
			generateLayoutCode(sb, widget, varName, indent);
		}

		sb.append("\n");

		// Create child widgets
		for (LvglWidget child : widget.getChildren()) {
			generateWidgetCreation(sb, child, varName, indent);
		}
	}

	private void generateLayoutCode(StringBuilder sb, LvglWidget widget, String varName, String indent) {
		LvglWidget.LayoutType layoutType = widget.getLayoutType();

		if (layoutType == LvglWidget.LayoutType.FLEX) {
			// Set flex layout
			sb.append(indent).append("lv_obj_set_layout(").append(varName).append(", LV_LAYOUT_FLEX);\n");
			
			// Set flex flow
			sb.append(indent).append("lv_obj_set_flex_flow(").append(varName).append(", ");
			sb.append(widget.getFlexFlow().getLvglConstant()).append(");\n");
			
			// Set flex alignment
			sb.append(indent).append("lv_obj_set_flex_align(").append(varName).append(", ");
			sb.append(widget.getFlexMainAlign().getLvglConstant()).append(", ");
			sb.append(widget.getFlexCrossAlign().getLvglConstant()).append(", ");
			sb.append(widget.getFlexTrackAlign().getLvglConstant()).append(");\n");
		} else if (layoutType == LvglWidget.LayoutType.GRID) {
			// Set grid layout
			sb.append(indent).append("lv_obj_set_layout(").append(varName).append(", LV_LAYOUT_GRID);\n");
		}

		// Set padding for layout
		if (widget.getPadRow() > 0 || widget.getPadColumn() > 0) {
			sb.append(indent).append("lv_obj_set_style_pad_row(").append(varName);
			sb.append(", ").append(widget.getPadRow()).append(", LV_PART_MAIN);\n");
			sb.append(indent).append("lv_obj_set_style_pad_column(").append(varName);
			sb.append(", ").append(widget.getPadColumn()).append(", LV_PART_MAIN);\n");
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

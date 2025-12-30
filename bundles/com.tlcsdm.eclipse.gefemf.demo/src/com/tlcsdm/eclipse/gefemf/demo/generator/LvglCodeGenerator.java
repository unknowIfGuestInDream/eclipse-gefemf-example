/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.generator;

import com.tlcsdm.eclipse.gefemf.demo.Activator;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.preferences.LvglPreferenceConstants;

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
	 * Get the license header from preferences.
	 * 
	 * @return the license header text, or empty string if not configured
	 */
	private String getLicenseHeader() {
		if (Activator.getDefault() != null) {
			String header = Activator.getDefault().getPreferenceStore()
					.getString(LvglPreferenceConstants.P_LICENSE_HEADER);
			if (header != null && !header.isEmpty()) {
				// Ensure the header ends with a newline
				if (!header.endsWith("\n")) {
					header = header + "\n";
				}
				return header + "\n";
			}
		}
		return "";
	}

	/**
	 * Generate the header file content.
	 */
	public String generateHeader() {
		StringBuilder sb = new StringBuilder();
		String generationName = getGenerationName();
		// Sanitize identifiers used in C code
		String identifier = sanitizeIdentifier(generationName);
		String guardName = identifier.toUpperCase() + "_H";

		// Add license header from preferences
		sb.append(getLicenseHeader());

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
		sb.append("extern lv_obj_t *").append(identifier).append(";\n\n");

		// Declare widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDeclarations(sb, widget);
		}

		sb.append("\n");

		// Function declarations
		sb.append("void ").append(identifier).append("_create(void);\n");
		sb.append("void ").append(identifier).append("_delete(void);\n");

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
		// Sanitize the identifier used in C code
		String identifier = sanitizeIdentifier(generationName);

		// Add license header from preferences
		sb.append(getLicenseHeader());

		sb.append("/**\n");
		sb.append(" * @file ").append(generationName).append(".c\n");
		sb.append(" * @brief LVGL UI screen implementation - auto-generated\n");
		sb.append(" */\n\n");

		sb.append("#include \"").append(generationName).append(".h\"\n\n");

		// Define screen object
		sb.append("lv_obj_t *").append(identifier).append(" = NULL;\n\n");

		// Define widget objects
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetDefinitions(sb, widget);
		}

		sb.append("\n");

		// Create function
		sb.append("void ").append(identifier).append("_create(void) {\n");
		sb.append("    ").append(identifier).append(" = lv_obj_create(NULL);\n");
		sb.append("    lv_obj_set_size(").append(identifier).append(", ");
		sb.append(screen.getWidth()).append(", ").append(screen.getHeight()).append(");\n");
		sb.append("    lv_obj_set_style_bg_color(").append(identifier);
		sb.append(", lv_color_hex(0x").append(String.format("%06X", screen.getBgColor()));
		sb.append("), LV_PART_MAIN);\n\n");

		// Create widgets
		for (LvglWidget widget : screen.getWidgets()) {
			generateWidgetCreation(sb, widget, identifier, "    ");
		}

		sb.append("}\n\n");

		// Delete function
		sb.append("void ").append(identifier).append("_delete(void) {\n");
		sb.append("    if (").append(identifier).append(" != NULL) {\n");
		sb.append("        lv_obj_del(").append(identifier).append(");\n");
		sb.append("        ").append(identifier).append(" = NULL;\n");
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
				// Validate image source - should be either:
				// 1. A pointer reference like &image_name (alphanumeric + underscore after &)
				// 2. A string path like "path/to/image.png" (must be properly quoted)
				String validatedSource = validateImageSource(imageSource);
				if (validatedSource != null) {
					sb.append(indent).append("lv_img_set_src(").append(varName);
					sb.append(", ").append(validatedSource).append(");\n");
				}
			}
		}

		// Set checked state for checkbox/switch
		if (widget.getWidgetType() == LvglWidget.WidgetType.CHECKBOX && widget.isChecked()) {
			sb.append(indent).append("lv_obj_add_state(").append(varName).append(", LV_STATE_CHECKED);\n");
		}
		if (widget.getWidgetType() == LvglWidget.WidgetType.SWITCH && widget.isChecked()) {
			sb.append(indent).append("lv_obj_add_state(").append(varName).append(", LV_STATE_CHECKED);\n");
		}

		// Set value for slider/arc/bar
		if (widget.getWidgetType() == LvglWidget.WidgetType.SLIDER) {
			sb.append(indent).append("lv_slider_set_range(").append(varName).append(", ");
			sb.append(widget.getMinValue()).append(", ").append(widget.getMaxValue()).append(");\n");
			sb.append(indent).append("lv_slider_set_value(").append(varName).append(", ");
			sb.append(widget.getValue()).append(", LV_ANIM_OFF);\n");
		}
		if (widget.getWidgetType() == LvglWidget.WidgetType.ARC) {
			sb.append(indent).append("lv_arc_set_range(").append(varName).append(", ");
			sb.append(widget.getMinValue()).append(", ").append(widget.getMaxValue()).append(");\n");
			sb.append(indent).append("lv_arc_set_value(").append(varName).append(", ");
			sb.append(widget.getValue()).append(");\n");
		}
		if (widget.getWidgetType() == LvglWidget.WidgetType.BAR) {
			sb.append(indent).append("lv_bar_set_range(").append(varName).append(", ");
			sb.append(widget.getMinValue()).append(", ").append(widget.getMaxValue()).append(");\n");
			sb.append(indent).append("lv_bar_set_value(").append(varName).append(", ");
			sb.append(widget.getValue()).append(", LV_ANIM_OFF);\n");
		}

		// Set table properties
		if (widget.getWidgetType() == LvglWidget.WidgetType.TABLE) {
			sb.append(indent).append("lv_table_set_row_cnt(").append(varName).append(", ");
			sb.append(widget.getRowCount()).append(");\n");
			sb.append(indent).append("lv_table_set_col_cnt(").append(varName).append(", ");
			sb.append(widget.getColumnCount()).append(");\n");
			
			// Parse and set table data if available
			String tableData = widget.getTableData();
			if (tableData != null && !tableData.isEmpty()) {
				String[] rows = tableData.split(";");
				for (int row = 0; row < rows.length && row < widget.getRowCount(); row++) {
					String[] cols = rows[row].split(",");
					for (int col = 0; col < cols.length && col < widget.getColumnCount(); col++) {
						String cellText = cols[col].trim();
						sb.append(indent).append("lv_table_set_cell_value(").append(varName).append(", ");
						sb.append(row).append(", ").append(col).append(", \"").append(escapeString(cellText)).append("\");\n");
					}
				}
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
		// New widget types
		case ANIMIMG:
			return "lv_animimg_create";
		case ARCLABEL:
			return "lv_arclabel_create";
		case BUTTONMATRIX:
			return "lv_btnmatrix_create";
		case CALENDAR:
			return "lv_calendar_create";
		case CANVAS:
			return "lv_canvas_create";
		case CHART:
			return "lv_chart_create";
		case IMAGEBUTTON:
			return "lv_imgbtn_create";
		case KEYBOARD:
			return "lv_keyboard_create";
		case LED:
			return "lv_led_create";
		case LINE:
			return "lv_line_create";
		case LIST:
			return "lv_list_create";
		case MENU:
			return "lv_menu_create";
		case MSGBOX:
			return "lv_msgbox_create";
		case ROLLER:
			return "lv_roller_create";
		case SCALE:
			return "lv_scale_create";
		case SPANGROUP:
			return "lv_spangroup_create";
		case SPINBOX:
			return "lv_spinbox_create";
		case SPINNER:
			return "lv_spinner_create";
		case TABLE:
			return "lv_table_create";
		case TABVIEW:
			return "lv_tabview_create";
		case TILEVIEW:
			return "lv_tileview_create";
		case WIN:
			return "lv_win_create";
		case CONTAINER:
		default:
			return "lv_obj_create";
		}
	}

	private String escapeString(String str) {
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}

	/**
	 * Sanitize a string to be a valid C identifier.
	 * Replaces invalid characters with underscores.
	 */
	private String sanitizeIdentifier(String str) {
		if (str == null || str.isEmpty()) {
			return "_";
		}
		String sanitized = str.replaceAll("[^a-zA-Z0-9_]", "_");
		// C identifiers cannot start with a digit
		if (Character.isDigit(sanitized.charAt(0))) {
			sanitized = "_" + sanitized;
		}
		return sanitized;
	}

	/**
	 * Validate and sanitize image source for safe inclusion in C code.
	 * Returns null if the source is invalid.
	 * 
	 * Valid formats:
	 * - Pointer reference: &identifier (e.g., &my_image)
	 * - String path: "path/to/image" (will be quoted if not already)
	 * - LV_SYMBOL constants: LV_SYMBOL_* macros
	 */
	private String validateImageSource(String source) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		
		String trimmed = source.trim();
		
		// Check for pointer reference (e.g., &image_name)
		if (trimmed.startsWith("&")) {
			String identifier = trimmed.substring(1);
			// Validate the identifier part
			if (identifier.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
				return trimmed;
			}
			return null;
		}
		
		// Check for LV_SYMBOL macro
		if (trimmed.startsWith("LV_SYMBOL_")) {
			if (trimmed.matches("LV_SYMBOL_[A-Z_]+")) {
				return trimmed;
			}
			return null;
		}
		
		// For string paths, ensure it's properly quoted
		if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
			// Already quoted - validate and escape the content
			String content = trimmed.substring(1, trimmed.length() - 1);
			// Basic path validation - no code injection characters
			if (!content.matches(".*[;{}()\\[\\]].*")) {
				return "\"" + escapeString(content) + "\"";
			}
			return null;
		}
		
		// Unquoted string - treat as path and add quotes
		// Basic path validation - no code injection characters
		if (!trimmed.matches(".*[;{}()\\[\\]&].*")) {
			return "\"" + escapeString(trimmed) + "\"";
		}
		
		return null;
	}
}

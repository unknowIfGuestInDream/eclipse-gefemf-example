/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.palette;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Provides icons for LVGL widget palette entries.
 * <p>
 * Since custom icons are not bundled with this plugin, we create simple
 * programmatic icons for each widget type using different colors and shapes.
 * </p>
 */
public class LvglWidgetIcons {

	private static final int ICON_SIZE = 16;

	/**
	 * Get an image descriptor for the given widget type.
	 * 
	 * @param type the widget type
	 * @return an image descriptor for the palette icon
	 */
	public static ImageDescriptor getIcon(LvglWidget.WidgetType type) {
		return ImageDescriptor.createFromImageDataProvider(zoom -> createIconData(type, zoom));
	}

	/**
	 * Create icon image data for a widget type.
	 */
	private static ImageData createIconData(LvglWidget.WidgetType type, int zoom) {
		int size = ICON_SIZE * zoom / 100;
		if (size < ICON_SIZE) {
			size = ICON_SIZE;
		}

		// Create a simple 16x16 (or scaled) icon with different colors/patterns
		RGB[] rgbs = new RGB[256];
		for (int i = 0; i < 256; i++) {
			rgbs[i] = new RGB(i, i, i);
		}
		
		PaletteData palette = new PaletteData(0xFF0000, 0x00FF00, 0x0000FF);
		ImageData data = new ImageData(size, size, 24, palette);
		
		// Get colors for this widget type
		int bgColor = getBackgroundColor(type);
		int fgColor = getForegroundColor(type);
		
		// Fill background
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				data.setPixel(x, y, 0xFFFFFF); // White background
			}
		}
		
		// Draw widget-specific shape
		drawWidgetShape(data, type, bgColor, fgColor, size);
		
		return data;
	}

	private static int getBackgroundColor(LvglWidget.WidgetType type) {
		return switch (type) {
			case BUTTON -> 0x4A90D9;
			case LABEL -> 0xFFFFFF;
			case SLIDER -> 0x7EC8E3;
			case SWITCH -> 0x50C878;
			case CHECKBOX -> 0xFFFFFF;
			case DROPDOWN -> 0xFFFFFF;
			case TEXTAREA -> 0xFFFFFF;
			case IMAGE -> 0xC0C0C0;
			case ARC -> 0xFFFFFF;
			case BAR -> 0x7EC8E3;
			case CONTAINER -> 0xF5F5F5;
			case LED -> 0x50C878;
			case CHART -> 0xFFFFFF;
			case TABLE -> 0xFFFFFF;
			case CALENDAR -> 0xFFFFFF;
			case KEYBOARD -> 0xE0E0E0;
			case ROLLER -> 0xFFFFFF;
			case SPINBOX -> 0xFFFFFF;
			case SPINNER -> 0x4A90D9;
			case TABVIEW -> 0xE0E0E0;
			case TILEVIEW -> 0xF0F0F0;
			case WIN -> 0xD0D0D0;
			case MSGBOX -> 0xFFFFFF;
			case LIST -> 0xFFFFFF;
			case MENU -> 0xFFFFFF;
			case BUTTONMATRIX -> 0xE0E0E0;
			case ANIMIMG -> 0xD0D0F0;
			case ARCLABEL -> 0xFFFFFF;
			case CANVAS -> 0xFFFFFF;
			case IMAGEBUTTON -> 0xC0C0C0;
			case LINE -> 0xFFFFFF;
			case SCALE -> 0xF0F0F0;
			case SPANGROUP -> 0xFFFFFF;
			default -> 0xFFFFFF;
		};
	}

	private static int getForegroundColor(LvglWidget.WidgetType type) {
		return switch (type) {
			case BUTTON -> 0xFFFFFF;
			case LABEL -> 0x000000;
			case SLIDER -> 0x2060A0;
			case SWITCH -> 0xFFFFFF;
			case CHECKBOX -> 0x000000;
			case DROPDOWN -> 0x000000;
			case TEXTAREA -> 0x808080;
			case IMAGE -> 0x606060;
			case ARC -> 0x4A90D9;
			case BAR -> 0x2060A0;
			case CONTAINER -> 0x808080;
			case LED -> 0xFFFFFF;
			default -> 0x000000;
		};
	}

	private static void drawWidgetShape(ImageData data, LvglWidget.WidgetType type, 
			int bgColor, int fgColor, int size) {
		switch (type) {
			case BUTTON -> drawButton(data, bgColor, fgColor, size);
			case LABEL -> drawLabel(data, fgColor, size);
			case SLIDER -> drawSlider(data, bgColor, fgColor, size);
			case SWITCH -> drawSwitch(data, bgColor, size);
			case CHECKBOX -> drawCheckbox(data, fgColor, size);
			case DROPDOWN -> drawDropdown(data, fgColor, size);
			case TEXTAREA -> drawTextarea(data, fgColor, size);
			case IMAGE -> drawImage(data, bgColor, fgColor, size);
			case ARC -> drawArc(data, fgColor, size);
			case BAR -> drawBar(data, bgColor, fgColor, size);
			case CONTAINER -> drawContainer(data, fgColor, size);
			case LED -> drawLed(data, bgColor, size);
			case CHART -> drawChart(data, fgColor, size);
			case TABLE -> drawTable(data, fgColor, size);
			case CALENDAR -> drawCalendar(data, fgColor, size);
			case KEYBOARD -> drawKeyboard(data, bgColor, fgColor, size);
			case ROLLER -> drawRoller(data, fgColor, size);
			case SPINBOX -> drawSpinbox(data, fgColor, size);
			case SPINNER -> drawSpinner(data, bgColor, size);
			case TABVIEW -> drawTabview(data, bgColor, fgColor, size);
			case TILEVIEW -> drawTileview(data, fgColor, size);
			case WIN -> drawWindow(data, bgColor, fgColor, size);
			case MSGBOX -> drawMsgbox(data, fgColor, size);
			case LIST -> drawList(data, fgColor, size);
			case MENU -> drawMenu(data, fgColor, size);
			case BUTTONMATRIX -> drawButtonMatrix(data, bgColor, fgColor, size);
			case ANIMIMG -> drawAnimImg(data, bgColor, fgColor, size);
			case ARCLABEL -> drawArcLabel(data, fgColor, size);
			case CANVAS -> drawCanvas(data, fgColor, size);
			case IMAGEBUTTON -> drawImageButton(data, bgColor, fgColor, size);
			case LINE -> drawLine(data, fgColor, size);
			case SCALE -> drawScale(data, fgColor, size);
			case SPANGROUP -> drawSpangroup(data, fgColor, size);
			default -> drawDefault(data, fgColor, size);
		}
	}

	// Draw methods for each widget type
	private static void drawButton(ImageData data, int bg, int fg, int s) {
		// Filled rounded rectangle
		for (int y = 2; y < s - 2; y++) {
			for (int x = 2; x < s - 2; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// "OK" text simulation - just a line
		for (int x = s/3; x < 2*s/3; x++) {
			data.setPixel(x, s/2, fg);
		}
	}

	private static void drawLabel(ImageData data, int fg, int s) {
		// Draw "Aa" text simulation
		for (int y = s/3; y < 2*s/3; y++) {
			data.setPixel(s/4, y, fg);
			data.setPixel(3*s/4, y, fg);
		}
		for (int x = s/4; x < s/2; x++) {
			data.setPixel(x, s/3, fg);
			data.setPixel(x, s/2, fg);
		}
	}

	private static void drawSlider(ImageData data, int bg, int fg, int s) {
		// Track
		for (int x = 2; x < s - 2; x++) {
			data.setPixel(x, s/2, bg);
			data.setPixel(x, s/2 + 1, bg);
		}
		// Knob
		int knobX = s/2;
		for (int y = s/2 - 3; y <= s/2 + 3; y++) {
			for (int x = knobX - 2; x <= knobX + 2; x++) {
				if (x >= 0 && x < s && y >= 0 && y < s) {
					data.setPixel(x, y, fg);
				}
			}
		}
	}

	private static void drawSwitch(ImageData data, int bg, int s) {
		// Track
		for (int y = s/2 - 3; y <= s/2 + 3; y++) {
			for (int x = 3; x < s - 3; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// Knob (white)
		for (int y = s/2 - 2; y <= s/2 + 2; y++) {
			for (int x = s - 7; x < s - 4; x++) {
				if (x >= 0 && x < s) {
					data.setPixel(x, y, 0xFFFFFF);
				}
			}
		}
	}

	private static void drawCheckbox(ImageData data, int fg, int s) {
		// Box
		for (int i = 3; i < s - 3; i++) {
			data.setPixel(3, i, fg);
			data.setPixel(s - 4, i, fg);
			data.setPixel(i, 3, fg);
			data.setPixel(i, s - 4, fg);
		}
		// Checkmark
		for (int i = 0; i < 3; i++) {
			data.setPixel(5 + i, s/2 + i, fg);
			data.setPixel(8 + i, s/2 - i, fg);
		}
	}

	private static void drawDropdown(ImageData data, int fg, int s) {
		// Box
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Arrow
		for (int i = 0; i < 3; i++) {
			data.setPixel(s - 6 - i, s/2 - 1, fg);
			data.setPixel(s - 6 + i, s/2 - 1, fg);
			data.setPixel(s - 6, s/2 + i - 1, fg);
		}
	}

	private static void drawTextarea(ImageData data, int fg, int s) {
		// Box
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Text lines
		for (int x = 4; x < s - 4; x++) {
			data.setPixel(x, 5, 0xC0C0C0);
			data.setPixel(x, 8, 0xC0C0C0);
		}
	}

	private static void drawImage(ImageData data, int bg, int fg, int s) {
		// Frame
		for (int y = 2; y < s - 2; y++) {
			for (int x = 2; x < s - 2; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// Mountain shape
		for (int i = 0; i < 5; i++) {
			data.setPixel(s/2 - i, s - 4 - i, fg);
			data.setPixel(s/2 + i, s - 4 - i, fg);
		}
	}

	private static void drawArc(ImageData data, int fg, int s) {
		// Draw arc approximation
		int cx = s/2;
		int cy = s/2;
		int r = s/2 - 2;
		for (int angle = 45; angle < 315; angle += 15) {
			double rad = Math.toRadians(angle);
			int x = cx + (int)(r * Math.cos(rad));
			int y = cy + (int)(r * Math.sin(rad));
			if (x >= 0 && x < s && y >= 0 && y < s) {
				data.setPixel(x, y, fg);
			}
		}
	}

	private static void drawBar(ImageData data, int bg, int fg, int s) {
		// Background track
		for (int y = s/2 - 2; y <= s/2 + 2; y++) {
			for (int x = 2; x < s - 2; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// Progress
		for (int y = s/2 - 2; y <= s/2 + 2; y++) {
			for (int x = 2; x < s/2 + 2; x++) {
				data.setPixel(x, y, fg);
			}
		}
	}

	private static void drawContainer(ImageData data, int fg, int s) {
		// Dashed border simulation
		for (int i = 2; i < s - 2; i += 2) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
	}

	private static void drawLed(ImageData data, int bg, int s) {
		// Circle
		int cx = s/2;
		int cy = s/2;
		int r = s/2 - 3;
		for (int y = 0; y < s; y++) {
			for (int x = 0; x < s; x++) {
				if ((x - cx) * (x - cx) + (y - cy) * (y - cy) <= r * r) {
					data.setPixel(x, y, bg);
				}
			}
		}
	}

	private static void drawChart(ImageData data, int fg, int s) {
		// Axes
		for (int y = 2; y < s - 2; y++) {
			data.setPixel(3, y, fg);
		}
		for (int x = 3; x < s - 2; x++) {
			data.setPixel(x, s - 3, fg);
		}
		// Data points
		data.setPixel(5, 8, fg);
		data.setPixel(7, 5, fg);
		data.setPixel(9, 7, fg);
		data.setPixel(11, 4, fg);
	}

	private static void drawTable(ImageData data, int fg, int s) {
		// Grid
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s/2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s/2, fg);
			data.setPixel(i, s - 3, fg);
		}
	}

	private static void drawCalendar(ImageData data, int fg, int s) {
		// Box
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Header line
		for (int x = 2; x < s - 2; x++) {
			data.setPixel(x, 5, fg);
		}
	}

	private static void drawKeyboard(ImageData data, int bg, int fg, int s) {
		// Keys
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 4; col++) {
				int x = 2 + col * 3;
				int y = 4 + row * 5;
				if (x < s - 2 && y < s - 2) {
					data.setPixel(x, y, bg);
					data.setPixel(x + 1, y, bg);
					data.setPixel(x, y + 1, bg);
					data.setPixel(x + 1, y + 1, bg);
				}
			}
		}
	}

	private static void drawRoller(ImageData data, int fg, int s) {
		// Lines representing options
		for (int y = 3; y < s - 3; y += 3) {
			for (int x = 4; x < s - 4; x++) {
				data.setPixel(x, y, fg);
			}
		}
		// Selection highlight
		for (int x = 2; x < s - 2; x++) {
			data.setPixel(x, s/2 - 2, 0x4A90D9);
			data.setPixel(x, s/2 + 2, 0x4A90D9);
		}
	}

	private static void drawSpinbox(ImageData data, int fg, int s) {
		// Box
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Up/down arrows
		data.setPixel(s - 5, 5, fg);
		data.setPixel(s - 6, 6, fg);
		data.setPixel(s - 4, 6, fg);
		data.setPixel(s - 5, s - 6, fg);
		data.setPixel(s - 6, s - 7, fg);
		data.setPixel(s - 4, s - 7, fg);
	}

	private static void drawSpinner(ImageData data, int bg, int s) {
		// Partial circle
		int cx = s/2;
		int cy = s/2;
		int r = s/2 - 3;
		for (int angle = 0; angle < 270; angle += 20) {
			double rad = Math.toRadians(angle);
			int x = cx + (int)(r * Math.cos(rad));
			int y = cy + (int)(r * Math.sin(rad));
			if (x >= 0 && x < s && y >= 0 && y < s) {
				data.setPixel(x, y, bg);
			}
		}
	}

	private static void drawTabview(ImageData data, int bg, int fg, int s) {
		// Tabs
		for (int x = 2; x < s/2 - 1; x++) {
			data.setPixel(x, 2, fg);
			data.setPixel(x, 5, fg);
		}
		for (int y = 2; y < 6; y++) {
			data.setPixel(2, y, fg);
			data.setPixel(s/2 - 1, y, fg);
		}
		// Content area
		for (int i = 5; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, s - 3, fg);
		}
	}

	private static void drawTileview(ImageData data, int fg, int s) {
		// Grid of tiles
		int tileSize = (s - 4) / 2;
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 2; col++) {
				int x = 2 + col * (tileSize + 1);
				int y = 2 + row * (tileSize + 1);
				for (int i = 0; i < tileSize; i++) {
					data.setPixel(x + i, y, fg);
					data.setPixel(x + i, y + tileSize - 1, fg);
					data.setPixel(x, y + i, fg);
					data.setPixel(x + tileSize - 1, y + i, fg);
				}
			}
		}
	}

	private static void drawWindow(ImageData data, int bg, int fg, int s) {
		// Window frame
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Title bar
		for (int y = 2; y < 6; y++) {
			for (int x = 2; x < s - 2; x++) {
				data.setPixel(x, y, bg);
			}
		}
	}

	private static void drawMsgbox(ImageData data, int fg, int s) {
		// Box
		for (int i = 3; i < s - 3; i++) {
			data.setPixel(3, i, fg);
			data.setPixel(s - 4, i, fg);
			data.setPixel(i, 3, fg);
			data.setPixel(i, s - 4, fg);
		}
		// Exclamation mark
		for (int y = 5; y < 9; y++) {
			data.setPixel(s/2, y, fg);
		}
		data.setPixel(s/2, 10, fg);
	}

	private static void drawList(ImageData data, int fg, int s) {
		// List items
		for (int y = 3; y < s - 3; y += 3) {
			data.setPixel(3, y, fg);
			for (int x = 5; x < s - 3; x++) {
				data.setPixel(x, y, 0xC0C0C0);
			}
		}
	}

	private static void drawMenu(ImageData data, int fg, int s) {
		// Menu items (hamburger style)
		for (int y = 4; y < s - 3; y += 3) {
			for (int x = 3; x < s - 3; x++) {
				data.setPixel(x, y, fg);
			}
		}
	}

	private static void drawButtonMatrix(ImageData data, int bg, int fg, int s) {
		// Grid of buttons
		int btnSize = (s - 6) / 3;
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 3; col++) {
				int x = 2 + col * (btnSize + 1);
				int y = 3 + row * (btnSize + 2);
				for (int dy = 0; dy < btnSize; dy++) {
					for (int dx = 0; dx < btnSize; dx++) {
						if (x + dx < s && y + dy < s) {
							data.setPixel(x + dx, y + dy, bg);
						}
					}
				}
			}
		}
	}

	private static void drawAnimImg(ImageData data, int bg, int fg, int s) {
		// Image with play symbol
		for (int y = 3; y < s - 3; y++) {
			for (int x = 3; x < s - 3; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// Play triangle
		for (int i = 0; i < 5; i++) {
			data.setPixel(5 + i, s/2 - i, fg);
			data.setPixel(5 + i, s/2 + i, fg);
			data.setPixel(5 + i, s/2, fg);
		}
	}

	private static void drawArcLabel(ImageData data, int fg, int s) {
		// Arc with text
		int cx = s/2;
		int cy = s/2 + 2;
		int r = s/2 - 2;
		for (int angle = 200; angle < 340; angle += 15) {
			double rad = Math.toRadians(angle);
			int x = cx + (int)(r * Math.cos(rad));
			int y = cy + (int)(r * Math.sin(rad));
			if (x >= 0 && x < s && y >= 0 && y < s) {
				data.setPixel(x, y, fg);
			}
		}
	}

	private static void drawCanvas(ImageData data, int fg, int s) {
		// Canvas frame with diagonal
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
		// Brush stroke
		for (int i = 0; i < 6; i++) {
			data.setPixel(4 + i, s - 5 - i, 0x4A90D9);
		}
	}

	private static void drawImageButton(ImageData data, int bg, int fg, int s) {
		// Button with image
		for (int y = 2; y < s - 2; y++) {
			for (int x = 2; x < s - 2; x++) {
				data.setPixel(x, y, bg);
			}
		}
		// Small image icon
		for (int i = 0; i < 3; i++) {
			data.setPixel(s/2 - i, s/2 + i, fg);
			data.setPixel(s/2 + i, s/2 + i, fg);
		}
	}

	private static void drawLine(ImageData data, int fg, int s) {
		// Diagonal line
		for (int i = 0; i < s - 4; i++) {
			data.setPixel(2 + i, 2 + i, fg);
		}
	}

	private static void drawScale(ImageData data, int fg, int s) {
		// Scale with ticks
		for (int x = 2; x < s - 2; x++) {
			data.setPixel(x, s/2, fg);
		}
		// Ticks
		for (int x = 4; x < s - 2; x += 3) {
			data.setPixel(x, s/2 - 2, fg);
			data.setPixel(x, s/2 + 2, fg);
		}
	}

	private static void drawSpangroup(ImageData data, int fg, int s) {
		// Text spans
		for (int x = 3; x < 7; x++) {
			data.setPixel(x, s/2, fg);
		}
		for (int x = 8; x < s - 3; x++) {
			data.setPixel(x, s/2, 0x4A90D9);
		}
	}

	private static void drawDefault(ImageData data, int fg, int s) {
		// Simple box
		for (int i = 2; i < s - 2; i++) {
			data.setPixel(2, i, fg);
			data.setPixel(s - 3, i, fg);
			data.setPixel(i, 2, fg);
			data.setPixel(i, s - 3, fg);
		}
	}
}

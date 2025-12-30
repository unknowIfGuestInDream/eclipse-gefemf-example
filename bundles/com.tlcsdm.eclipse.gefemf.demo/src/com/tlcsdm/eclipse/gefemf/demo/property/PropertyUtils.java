/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for property-related helper methods.
 */
public final class PropertyUtils {

	/**
	 * Pattern to match RGB format: RGB(r,g,b) or rgb(r,g,b)
	 * Supports optional spaces around the values.
	 */
	private static final Pattern RGB_PATTERN = Pattern.compile(
			"^[Rr][Gg][Bb]\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)$");

	private PropertyUtils() {
		// Utility class, prevent instantiation
	}

	/**
	 * Format color as hex string (e.g., "#FFFFFF").
	 */
	public static String formatColor(int color) {
		return String.format("#%06X", color & 0xFFFFFF);
	}

	/**
	 * Parse a color string to an integer.
	 * Supports the following formats:
	 * <ul>
	 *   <li>Hex format: "#FFFFFF" or "FFFFFF"</li>
	 *   <li>RGB format: "RGB(255,255,255)" or "rgb(255,255,255)"</li>
	 * </ul>
	 * Returns 0xFFFFFF (white) if parsing fails.
	 *
	 * @param colorStr the color string to parse
	 * @return the integer color value
	 */
	public static int parseColor(String colorStr) {
		if (colorStr == null || colorStr.isEmpty()) {
			return 0xFFFFFF;
		}
		
		String trimmed = colorStr.trim();
		
		// Try RGB format first: RGB(r,g,b)
		Matcher rgbMatcher = RGB_PATTERN.matcher(trimmed);
		if (rgbMatcher.matches()) {
			try {
				int r = Integer.parseInt(rgbMatcher.group(1));
				int g = Integer.parseInt(rgbMatcher.group(2));
				int b = Integer.parseInt(rgbMatcher.group(3));
				// Clamp values to valid range
				r = Math.max(0, Math.min(255, r));
				g = Math.max(0, Math.min(255, g));
				b = Math.max(0, Math.min(255, b));
				return (r << 16) | (g << 8) | b;
			} catch (NumberFormatException e) {
				return 0xFFFFFF;
			}
		}
		
		// Try hex format: #FFFFFF or FFFFFF
		String hexStr = trimmed;
		if (hexStr.startsWith("#")) {
			hexStr = hexStr.substring(1);
		}
		try {
			return Integer.parseInt(hexStr, 16);
		} catch (NumberFormatException e) {
			return 0xFFFFFF;
		}
	}

	/**
	 * Parse an integer string, returning a default value if parsing fails.
	 */
	public static int parseInt(String str, int defaultValue) {
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

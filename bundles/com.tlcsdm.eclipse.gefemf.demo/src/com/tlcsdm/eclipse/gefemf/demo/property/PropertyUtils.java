/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property;

/**
 * Utility class for property-related helper methods.
 */
public final class PropertyUtils {

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
	 * Parse a hex color string (e.g., "#FFFFFF" or "FFFFFF") to an integer.
	 * Returns 0xFFFFFF (white) if parsing fails.
	 */
	public static int parseColor(String colorStr) {
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

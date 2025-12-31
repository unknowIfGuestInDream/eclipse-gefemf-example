/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.property.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;

/**
 * Registry for widget-specific property providers.
 * <p>
 * This class manages the mapping between widget types and their property providers,
 * allowing for easy extension with custom widget types.
 * </p>
 */
public class WidgetPropertyProviderRegistry {

	private static WidgetPropertyProviderRegistry instance;
	
	private final Map<LvglWidget.WidgetType, List<WidgetPropertyProvider>> providers = new HashMap<>();

	private WidgetPropertyProviderRegistry() {
		// Register default providers
		registerDefaultProviders();
	}

	/**
	 * Get the singleton instance of the registry.
	 * 
	 * @return the registry instance
	 */
	public static synchronized WidgetPropertyProviderRegistry getInstance() {
		if (instance == null) {
			instance = new WidgetPropertyProviderRegistry();
		}
		return instance;
	}

	/**
	 * Register a property provider for a widget type.
	 * 
	 * @param provider the property provider to register
	 */
	public void registerProvider(WidgetPropertyProvider provider) {
		LvglWidget.WidgetType type = provider.getWidgetType();
		providers.computeIfAbsent(type, k -> new ArrayList<>()).add(provider);
	}

	/**
	 * Get all property providers for a widget type.
	 * 
	 * @param type the widget type
	 * @return list of property providers for the type, or empty list if none
	 */
	public List<WidgetPropertyProvider> getProviders(LvglWidget.WidgetType type) {
		return providers.getOrDefault(type, new ArrayList<>());
	}

	/**
	 * Unregister all providers for a widget type.
	 * 
	 * @param type the widget type
	 */
	public void unregisterProviders(LvglWidget.WidgetType type) {
		providers.remove(type);
	}

	/**
	 * Register the default widget property providers.
	 */
	private void registerDefaultProviders() {
		// Register checkbox/switch/LED providers (widgets with on/off state)
		registerProvider(new CheckboxPropertyProvider(LvglWidget.WidgetType.CHECKBOX));
		registerProvider(new CheckboxPropertyProvider(LvglWidget.WidgetType.SWITCH));
		registerProvider(new CheckboxPropertyProvider(LvglWidget.WidgetType.LED));

		// Register value-based providers (Slider, Arc, Bar)
		registerProvider(new ValuePropertyProvider(LvglWidget.WidgetType.SLIDER));
		registerProvider(new ValuePropertyProvider(LvglWidget.WidgetType.ARC));
		registerProvider(new ValuePropertyProvider(LvglWidget.WidgetType.BAR));

		// Register image provider
		registerProvider(new ImagePropertyProvider());

		// Register table provider
		registerProvider(new TablePropertyProvider());

		// Register container provider
		registerProvider(new ContainerPropertyProvider());
	}
}

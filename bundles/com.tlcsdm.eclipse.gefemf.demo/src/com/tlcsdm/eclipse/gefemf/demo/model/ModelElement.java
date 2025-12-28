/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.emf.ecore.EObject;

/**
 * Base class for all model elements with property change support.
 */
public abstract class ModelElement implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_LAYOUT = "layout";
    public static final String PROPERTY_ADD = "add";
    public static final String PROPERTY_REMOVE = "remove";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_CONNECTION = "connection";

    private final transient PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        listeners.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Convert this model element to EMF EObject for serialization.
     * @return the EMF EObject representation
     */
    public abstract EObject toEObject();
}

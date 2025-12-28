/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;

/**
 * Represents a connection between two class nodes.
 */
public class Connection extends ModelElement {

    private static final long serialVersionUID = 1L;

    public enum ConnectionType {
        ASSOCIATION("Association"),
        INHERITANCE("Inheritance"),
        COMPOSITION("Composition"),
        AGGREGATION("Aggregation");

        private final String displayName;

        ConnectionType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private String name = "";
    private ClassNode source;
    private ClassNode target;
    private ConnectionType type = ConnectionType.ASSOCIATION;

    public Connection() {
        // Default constructor
    }

    public Connection(ClassNode source, ClassNode target) {
        this.source = source;
        this.target = target;
        reconnect();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        firePropertyChange(PROPERTY_NAME, oldValue, name);
    }

    public ClassNode getSource() {
        return source;
    }

    public void setSource(ClassNode source) {
        this.source = source;
    }

    public ClassNode getTarget() {
        return target;
    }

    public void setTarget(ClassNode target) {
        this.target = target;
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        ConnectionType oldValue = this.type;
        this.type = type;
        firePropertyChange("type", oldValue, type);
    }

    public void disconnect() {
        if (source != null) {
            source.removeSourceConnection(this);
        }
        if (target != null) {
            target.removeTargetConnection(this);
        }
    }

    public void reconnect() {
        if (source != null) {
            source.addSourceConnection(this);
        }
        if (target != null) {
            target.addTargetConnection(this);
        }
    }

    public void reconnect(ClassNode newSource, ClassNode newTarget) {
        disconnect();
        this.source = newSource;
        this.target = newTarget;
        reconnect();
    }

    @Override
    public EObject toEObject() {
        EReference ref = EcoreFactory.eINSTANCE.createEReference();
        ref.setName(name.isEmpty() ? "ref_" + target.getClassName() : name);
        ref.setContainment(type == ConnectionType.COMPOSITION);
        return ref;
    }
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

/**
 * Represents the diagram containing all model elements.
 */
public class Diagram extends ModelElement {

    private static final long serialVersionUID = 1L;

    private String name = "Diagram";
    private String packageName = "com.example.generated";
    private final List<ClassNode> children = new ArrayList<>();

    public Diagram() {
        // Default constructor
    }

    public Diagram(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        firePropertyChange(PROPERTY_NAME, oldValue, name);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        String oldValue = this.packageName;
        this.packageName = packageName;
        firePropertyChange("packageName", oldValue, packageName);
    }

    public List<ClassNode> getChildren() {
        return children;
    }

    public void addChild(ClassNode child) {
        children.add(child);
        firePropertyChange(PROPERTY_ADD, null, child);
    }

    public void removeChild(ClassNode child) {
        children.remove(child);
        firePropertyChange(PROPERTY_REMOVE, child, null);
    }

    @Override
    public EObject toEObject() {
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName(name);
        ePackage.setNsPrefix(name.toLowerCase());
        ePackage.setNsURI("http://example.com/" + name.toLowerCase());

        for (ClassNode child : children) {
            ePackage.getEClassifiers().add((EClass) child.toEObject());
        }

        return ePackage;
    }
}

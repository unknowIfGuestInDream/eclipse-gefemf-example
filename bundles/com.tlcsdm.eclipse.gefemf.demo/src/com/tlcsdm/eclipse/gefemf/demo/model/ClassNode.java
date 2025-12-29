/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Represents a class node in the diagram.
 */
public class ClassNode extends ModelElement {

	private static final long serialVersionUID = 1L;

	private String className = "NewClass";
	private Rectangle bounds = new Rectangle(0, 0, 150, 100);
	private final List<String> attributes = new ArrayList<>();
	private final List<String> methods = new ArrayList<>();
	private final List<Connection> sourceConnections = new ArrayList<>();
	private final List<Connection> targetConnections = new ArrayList<>();

	public ClassNode() {
		// Default constructor
	}

	public ClassNode(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		String oldValue = this.className;
		this.className = className;
		firePropertyChange(PROPERTY_NAME, oldValue, className);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		Rectangle oldValue = this.bounds;
		this.bounds = bounds;
		firePropertyChange(PROPERTY_LAYOUT, oldValue, bounds);
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void addAttribute(String attribute) {
		attributes.add(attribute);
		firePropertyChange(PROPERTY_ADD, null, attribute);
	}

	public void removeAttribute(String attribute) {
		attributes.remove(attribute);
		firePropertyChange(PROPERTY_REMOVE, attribute, null);
	}

	public List<String> getMethods() {
		return methods;
	}

	public void addMethod(String method) {
		methods.add(method);
		firePropertyChange(PROPERTY_ADD, null, method);
	}

	public void removeMethod(String method) {
		methods.remove(method);
		firePropertyChange(PROPERTY_REMOVE, method, null);
	}

	public List<Connection> getSourceConnections() {
		return sourceConnections;
	}

	public void addSourceConnection(Connection connection) {
		sourceConnections.add(connection);
		firePropertyChange(PROPERTY_CONNECTION, null, connection);
	}

	public void removeSourceConnection(Connection connection) {
		sourceConnections.remove(connection);
		firePropertyChange(PROPERTY_CONNECTION, connection, null);
	}

	public List<Connection> getTargetConnections() {
		return targetConnections;
	}

	public void addTargetConnection(Connection connection) {
		targetConnections.add(connection);
		firePropertyChange(PROPERTY_CONNECTION, null, connection);
	}

	public void removeTargetConnection(Connection connection) {
		targetConnections.remove(connection);
		firePropertyChange(PROPERTY_CONNECTION, connection, null);
	}

	@Override
	public EObject toEObject() {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(className);

		// Add attributes as EAttributes
		for (String attr : attributes) {
			EAttribute eAttr = EcoreFactory.eINSTANCE.createEAttribute();
			String[] parts = attr.split(":");
			if (parts.length >= 1) {
				eAttr.setName(parts[0].trim());
			}
			if (parts.length >= 2) {
				String type = parts[1].trim();
				switch (type.toLowerCase()) {
				case "int":
				case "integer":
					eAttr.setEType(EcorePackage.eINSTANCE.getEInt());
					break;
				case "boolean":
					eAttr.setEType(EcorePackage.eINSTANCE.getEBoolean());
					break;
				case "double":
					eAttr.setEType(EcorePackage.eINSTANCE.getEDouble());
					break;
				default:
					eAttr.setEType(EcorePackage.eINSTANCE.getEString());
					break;
				}
			} else {
				eAttr.setEType(EcorePackage.eINSTANCE.getEString());
			}
			eClass.getEStructuralFeatures().add(eAttr);
		}

		return eClass;
	}

	/**
	 * Create a ClassNode from an EMF EClass.
	 */
	public static ClassNode fromEObject(EObject eObject, Rectangle bounds) {
		if (!(eObject instanceof EClass eClass)) {
			return null;
		}
		ClassNode node = new ClassNode(eClass.getName());
		node.setBounds(bounds);

		// Add attributes
		for (EAttribute attr : eClass.getEAttributes()) {
			String type = attr.getEType() != null ? attr.getEType().getName() : "String";
			node.addAttribute(attr.getName() + ": " + type);
		}

		return node;
	}
}

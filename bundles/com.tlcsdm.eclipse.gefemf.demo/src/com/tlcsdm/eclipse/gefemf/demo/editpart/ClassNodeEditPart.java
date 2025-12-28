/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.tlcsdm.eclipse.gefemf.demo.figure.ClassNodeFigure;
import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;
import com.tlcsdm.eclipse.gefemf.demo.model.ModelElement;

/**
 * EditPart for ClassNode model.
 */
public class ClassNodeEditPart extends AbstractGraphicalEditPart
        implements PropertyChangeListener, NodeEditPart {

    private ConnectionAnchor anchor;

    @Override
    protected IFigure createFigure() {
        return new ClassNodeFigure();
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ClassNodeEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ClassNodeGraphicalNodeEditPolicy());
    }

    @Override
    protected void refreshVisuals() {
        ClassNodeFigure figure = (ClassNodeFigure) getFigure();
        ClassNode model = getClassNode();

        figure.setClassName(model.getClassName());

        // Refresh attributes
        figure.clearAttributes();
        for (String attr : model.getAttributes()) {
            figure.addAttribute(attr);
        }

        // Refresh methods
        figure.clearMethods();
        for (String method : model.getMethods()) {
            figure.addMethod(method);
        }

        // Set bounds
        Rectangle bounds = model.getBounds();
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
    }

    private ClassNode getClassNode() {
        return (ClassNode) getModel();
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getClassNode().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            super.deactivate();
            getClassNode().removePropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if (ModelElement.PROPERTY_LAYOUT.equals(prop) ||
            ModelElement.PROPERTY_NAME.equals(prop) ||
            ModelElement.PROPERTY_ADD.equals(prop) ||
            ModelElement.PROPERTY_REMOVE.equals(prop)) {
            refreshVisuals();
        }
        if (ModelElement.PROPERTY_CONNECTION.equals(prop)) {
            refreshSourceConnections();
            refreshTargetConnections();
        }
    }

    @Override
    protected List<Connection> getModelSourceConnections() {
        return getClassNode().getSourceConnections();
    }

    @Override
    protected List<Connection> getModelTargetConnections() {
        return getClassNode().getTargetConnections();
    }

    protected ConnectionAnchor getConnectionAnchor() {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return getConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return getConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return getConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return getConnectionAnchor();
    }
}

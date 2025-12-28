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

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;
import com.tlcsdm.eclipse.gefemf.demo.model.ModelElement;

/**
 * EditPart for the Diagram model.
 */
public class DiagramEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

    @Override
    protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());

        // Set up connection routing
        ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        connLayer.setConnectionRouter(new ShortestPathConnectionRouter(figure));

        return figure;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutEditPolicy());
    }

    @Override
    protected List<ClassNode> getModelChildren() {
        return getDiagram().getChildren();
    }

    private Diagram getDiagram() {
        return (Diagram) getModel();
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getDiagram().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            super.deactivate();
            getDiagram().removePropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if (ModelElement.PROPERTY_ADD.equals(prop) || ModelElement.PROPERTY_REMOVE.equals(prop)) {
            refreshChildren();
        }
    }
}

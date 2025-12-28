/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import com.tlcsdm.eclipse.gefemf.demo.figure.ConnectionFigure;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;

/**
 * EditPart for Connection model.
 */
public class ConnectionEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {

    @Override
    protected IFigure createFigure() {
        ConnectionFigure figure = new ConnectionFigure();
        figure.setConnectionType(getConnection().getType());
        return figure;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy());
    }

    @Override
    protected void refreshVisuals() {
        ConnectionFigure figure = (ConnectionFigure) getFigure();
        figure.setConnectionType(getConnection().getType());
    }

    private Connection getConnection() {
        return (Connection) getModel();
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getConnection().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            super.deactivate();
            getConnection().removePropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshVisuals();
    }
}

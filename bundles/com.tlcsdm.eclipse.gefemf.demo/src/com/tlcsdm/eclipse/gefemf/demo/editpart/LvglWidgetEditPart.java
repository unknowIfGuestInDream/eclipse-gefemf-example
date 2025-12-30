/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import com.tlcsdm.eclipse.gefemf.demo.figure.LvglWidgetFigure;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.model.ModelElement;
import com.tlcsdm.eclipse.gefemf.demo.property.LvglWidgetPropertySource;

/**
 * EditPart for LVGL widgets.
 */
public class LvglWidgetEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {

	private ConnectionAnchor anchor;

	@Override
	protected IFigure createFigure() {
		return new LvglWidgetFigure();
	}

	@Override
	protected void createEditPolicies() {
		// Install edit policies
		installEditPolicy(org.eclipse.gef.EditPolicy.COMPONENT_ROLE, new LvglWidgetEditPolicy());
		installEditPolicy(org.eclipse.gef.EditPolicy.GRAPHICAL_NODE_ROLE, new ClassNodeGraphicalNodeEditPolicy());
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((LvglWidget) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((LvglWidget) getModel()).removePropertyChangeListener(this);
			// Dispose figure resources
			LvglWidgetFigure figure = (LvglWidgetFigure) getFigure();
			if (figure != null) {
				figure.dispose();
			}
		}
	}

	@Override
	protected void refreshVisuals() {
		LvglWidgetFigure figure = (LvglWidgetFigure) getFigure();
		LvglWidget widget = (LvglWidget) getModel();

		figure.setWidgetType(widget.getWidgetType());
		figure.setWidgetName(widget.getName());
		figure.setWidgetText(widget.getText());
		figure.setWidgetBgColor(widget.getBgColor());

		Rectangle bounds = widget.getBounds();
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (ModelElement.PROPERTY_LAYOUT.equals(prop) || ModelElement.PROPERTY_NAME.equals(prop)
				|| "widgetType".equals(prop) || "text".equals(prop) || "bgColor".equals(prop)) {
			refreshVisuals();
		} else if (ModelElement.PROPERTY_CONNECTION.equals(prop)) {
			refreshSourceConnections();
			refreshTargetConnections();
		}
	}

	@Override
	protected java.util.List<?> getModelSourceConnections() {
		return ((LvglWidget) getModel()).getSourceConnections();
	}

	@Override
	protected java.util.List<?> getModelTargetConnections() {
		return ((LvglWidget) getModel()).getTargetConnections();
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

	protected ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new ChopboxAnchor(getFigure());
		}
		return anchor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IPropertySource.class) {
			return (T) new LvglWidgetPropertySource((LvglWidget) getModel());
		}
		return super.getAdapter(adapter);
	}
}

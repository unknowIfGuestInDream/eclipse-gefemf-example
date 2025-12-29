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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.ModelElement;

/**
 * EditPart for LVGL screen.
 */
public class LvglScreenEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setLayoutManager(new FreeformLayout());
		figure.setBorder(new LineBorder(ColorConstants.lightGray, 1));
		figure.setBackgroundColor(ColorConstants.white);
		figure.setOpaque(true);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, new LvglScreenLayoutEditPolicy());
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((LvglScreen) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((LvglScreen) getModel()).removePropertyChangeListener(this);
		}
	}

	@Override
	protected List<?> getModelChildren() {
		return ((LvglScreen) getModel()).getWidgets();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (ModelElement.PROPERTY_ADD.equals(prop) || ModelElement.PROPERTY_REMOVE.equals(prop)) {
			refreshChildren();
		}
	}
}

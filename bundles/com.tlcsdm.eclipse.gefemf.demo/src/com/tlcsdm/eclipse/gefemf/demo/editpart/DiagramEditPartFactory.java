/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Factory for creating EditParts from model objects.
 */
public class DiagramEditPartFactory implements EditPartFactory {

    @Override
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart part = null;

        if (model instanceof Diagram) {
            part = new DiagramEditPart();
        } else if (model instanceof ClassNode) {
            part = new ClassNodeEditPart();
        } else if (model instanceof Connection) {
            part = new ConnectionEditPart();
        }

        if (part != null) {
            part.setModel(model);
        }

        return part;
    }
}

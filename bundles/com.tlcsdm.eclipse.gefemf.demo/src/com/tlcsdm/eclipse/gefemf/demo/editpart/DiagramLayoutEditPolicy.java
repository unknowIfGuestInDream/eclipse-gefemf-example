/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeCreateCommand;
import com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeSetConstraintCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Edit policy for layout in the diagram.
 */
public class DiagramLayoutEditPolicy extends XYLayoutEditPolicy {

    @Override
    protected Command createChangeConstraintCommand(ChangeBoundsRequest request,
            EditPart child, Object constraint) {
        if (child.getModel() instanceof ClassNode classNode && constraint instanceof Rectangle newBounds) {
            return new ClassNodeSetConstraintCommand(classNode, newBounds);
        }
        return null;
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        Object newObject = request.getNewObject();
        if (newObject instanceof ClassNode) {
            Diagram diagram = (Diagram) getHost().getModel();
            ClassNode newNode = (ClassNode) newObject;
            Rectangle bounds = (Rectangle) getConstraintFor(request);
            return new ClassNodeCreateCommand(diagram, newNode, bounds);
        }
        return null;
    }
}

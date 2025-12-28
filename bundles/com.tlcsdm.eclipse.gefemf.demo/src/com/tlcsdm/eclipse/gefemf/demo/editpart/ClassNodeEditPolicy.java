/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeDeleteCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Edit policy for ClassNode component operations.
 */
public class ClassNodeEditPolicy extends ComponentEditPolicy {

    @Override
    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        ClassNode node = (ClassNode) getHost().getModel();
        Diagram diagram = (Diagram) getHost().getParent().getModel();
        return new ClassNodeDeleteCommand(diagram, node);
    }
}

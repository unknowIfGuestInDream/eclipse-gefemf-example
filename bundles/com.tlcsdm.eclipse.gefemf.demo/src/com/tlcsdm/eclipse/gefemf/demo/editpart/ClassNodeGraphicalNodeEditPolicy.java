/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.ConnectionCreateCommand;
import com.tlcsdm.eclipse.gefemf.demo.command.ConnectionReconnectCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;

/**
 * Edit policy for connection handling on ClassNode.
 */
public class ClassNodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

    @Override
    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        ConnectionCreateCommand cmd = (ConnectionCreateCommand) request.getStartCommand();
        ClassNode target = (ClassNode) getHost().getModel();
        cmd.setTarget(target);
        return cmd;
    }

    @Override
    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        ClassNode source = (ClassNode) getHost().getModel();
        ConnectionCreateCommand cmd = new ConnectionCreateCommand(source);
        request.setStartCommand(cmd);
        return cmd;
    }

    @Override
    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        Connection conn = (Connection) request.getConnectionEditPart().getModel();
        ClassNode newSource = (ClassNode) getHost().getModel();
        return new ConnectionReconnectCommand(conn, newSource, conn.getTarget());
    }

    @Override
    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        Connection conn = (Connection) request.getConnectionEditPart().getModel();
        ClassNode newTarget = (ClassNode) getHost().getModel();
        return new ConnectionReconnectCommand(conn, conn.getSource(), newTarget);
    }
}

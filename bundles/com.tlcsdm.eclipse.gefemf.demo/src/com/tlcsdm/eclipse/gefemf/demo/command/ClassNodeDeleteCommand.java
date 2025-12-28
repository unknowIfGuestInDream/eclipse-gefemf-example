/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Command to delete a ClassNode from the diagram.
 */
public class ClassNodeDeleteCommand extends Command {

    private final Diagram diagram;
    private final ClassNode node;
    private java.util.List<Connection> sourceConnections;
    private java.util.List<Connection> targetConnections;

    public ClassNodeDeleteCommand(Diagram diagram, ClassNode node) {
        this.diagram = diagram;
        this.node = node;
        setLabel("Delete Class");
    }

    @Override
    public boolean canExecute() {
        return diagram != null && node != null;
    }

    @Override
    public void execute() {
        // Store connections for undo
        sourceConnections = new java.util.ArrayList<>(node.getSourceConnections());
        targetConnections = new java.util.ArrayList<>(node.getTargetConnections());

        // Remove all connections
        for (Connection conn : sourceConnections) {
            conn.disconnect();
        }
        for (Connection conn : targetConnections) {
            conn.disconnect();
        }

        diagram.removeChild(node);
    }

    @Override
    public void undo() {
        diagram.addChild(node);

        // Restore connections
        for (Connection conn : sourceConnections) {
            conn.reconnect();
        }
        for (Connection conn : targetConnections) {
            conn.reconnect();
        }
    }
}

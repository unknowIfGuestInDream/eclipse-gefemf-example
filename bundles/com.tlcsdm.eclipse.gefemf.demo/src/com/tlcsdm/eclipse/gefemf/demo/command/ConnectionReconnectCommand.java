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

/**
 * Command to reconnect a Connection to different nodes.
 */
public class ConnectionReconnectCommand extends Command {

    private final Connection connection;
    private final ClassNode newSource;
    private final ClassNode newTarget;
    private ClassNode oldSource;
    private ClassNode oldTarget;

    public ConnectionReconnectCommand(Connection connection, ClassNode newSource, ClassNode newTarget) {
        this.connection = connection;
        this.newSource = newSource;
        this.newTarget = newTarget;
        setLabel("Reconnect Connection");
    }

    @Override
    public boolean canExecute() {
        if (connection == null || newSource == null || newTarget == null) {
            return false;
        }
        // Don't allow self-connections
        if (newSource == newTarget) {
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        oldSource = connection.getSource();
        oldTarget = connection.getTarget();
        connection.reconnect(newSource, newTarget);
    }

    @Override
    public void undo() {
        connection.reconnect(oldSource, oldTarget);
    }
}

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
 * Command to create a new Connection between two ClassNodes.
 */
public class ConnectionCreateCommand extends Command {

    private final ClassNode source;
    private ClassNode target;
    private Connection connection;

    public ConnectionCreateCommand(ClassNode source) {
        this.source = source;
        setLabel("Create Connection");
    }

    public void setTarget(ClassNode target) {
        this.target = target;
    }

    @Override
    public boolean canExecute() {
        if (source == null || target == null) {
            return false;
        }
        // Don't allow self-connections
        if (source == target) {
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        connection = new Connection(source, target);
    }

    @Override
    public void undo() {
        connection.disconnect();
    }

    @Override
    public void redo() {
        connection.reconnect();
    }
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.Connection;

/**
 * Command to delete a Connection.
 */
public class ConnectionDeleteCommand extends Command {

    private final Connection connection;

    public ConnectionDeleteCommand(Connection connection) {
        this.connection = connection;
        setLabel("Delete Connection");
    }

    @Override
    public boolean canExecute() {
        return connection != null;
    }

    @Override
    public void execute() {
        connection.disconnect();
    }

    @Override
    public void undo() {
        connection.reconnect();
    }
}

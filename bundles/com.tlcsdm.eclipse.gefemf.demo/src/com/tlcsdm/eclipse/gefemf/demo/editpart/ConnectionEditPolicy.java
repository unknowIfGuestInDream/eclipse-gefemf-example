/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editpart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import com.tlcsdm.eclipse.gefemf.demo.command.ConnectionDeleteCommand;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;

/**
 * Edit policy for Connection operations.
 */
public class ConnectionEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {

    @Override
    protected Command getDeleteCommand(GroupRequest request) {
        Connection conn = (Connection) getHost().getModel();
        return new ConnectionDeleteCommand(conn);
    }
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;

/**
 * Command to set the constraint (bounds) of a ClassNode.
 */
public class ClassNodeSetConstraintCommand extends Command {

    private final ClassNode node;
    private final Rectangle newBounds;
    private Rectangle oldBounds;

    public ClassNodeSetConstraintCommand(ClassNode node, Rectangle newBounds) {
        this.node = node;
        this.newBounds = newBounds;
        setLabel("Move/Resize Class");
    }

    @Override
    public boolean canExecute() {
        return node != null && newBounds != null;
    }

    @Override
    public void execute() {
        oldBounds = node.getBounds();
        node.setBounds(newBounds);
    }

    @Override
    public void undo() {
        node.setBounds(oldBounds);
    }
}

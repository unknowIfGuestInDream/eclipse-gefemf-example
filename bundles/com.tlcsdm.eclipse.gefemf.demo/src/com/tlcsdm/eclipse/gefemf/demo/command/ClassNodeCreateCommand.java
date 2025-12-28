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
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Command to create a new ClassNode in the diagram.
 */
public class ClassNodeCreateCommand extends Command {

    private final Diagram diagram;
    private final ClassNode node;
    private Rectangle bounds;

    public ClassNodeCreateCommand(Diagram diagram, ClassNode node, Rectangle bounds) {
        this.diagram = diagram;
        this.node = node;
        this.bounds = bounds;
        setLabel("Create Class");
    }

    @Override
    public boolean canExecute() {
        return diagram != null && node != null && bounds != null;
    }

    @Override
    public void execute() {
        if (bounds.width < 100) {
            bounds.width = 150;
        }
        if (bounds.height < 60) {
            bounds.height = 100;
        }
        node.setBounds(bounds);
        diagram.addChild(node);
    }

    @Override
    public void undo() {
        diagram.removeChild(node);
    }
}

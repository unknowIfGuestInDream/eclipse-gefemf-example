/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

import com.tlcsdm.eclipse.gefemf.demo.editpart.DiagramEditPartFactory;
import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;
import com.tlcsdm.eclipse.gefemf.demo.palette.DiagramPaletteFactory;

/**
 * GEF Editor for the diagram.
 */
public class DiagramEditor extends GraphicalEditorWithPalette {

	public static final String ID = "com.tlcsdm.eclipse.gefemf.demo.editor";

	private Diagram diagram;
	private boolean isDirty = false;

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
		loadDiagram();
	}

	private void loadDiagram() {
		IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput fileInput) {
			IFile file = fileInput.getFile();
			try {
				if (file.exists() && file.getContents().available() > 0) {
					try (ObjectInputStream ois = new ObjectInputStream(file.getContents())) {
						diagram = (Diagram) ois.readObject();
					} catch (Exception e) {
						// File exists but couldn't read, create new diagram
						diagram = createDefaultDiagram();
					}
				} else {
					diagram = createDefaultDiagram();
				}
			} catch (Exception e) {
				diagram = createDefaultDiagram();
			}
		} else {
			diagram = createDefaultDiagram();
		}
	}

	private Diagram createDefaultDiagram() {
		Diagram d = new Diagram("MyDiagram");

		// Add a sample class node
		ClassNode node = new ClassNode("SampleClass");
		node.setBounds(new Rectangle(100, 100, 150, 120));
		node.addAttribute("id: Integer");
		node.addAttribute("name: String");
		node.addMethod("getId()");
		node.addMethod("setName(name)");
		d.addChild(node);

		return d;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new DiagramEditPartFactory());
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(diagram);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return DiagramPaletteFactory.createPalette();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput fileInput) {
			IFile file = fileInput.getFile();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(diagram);
				oos.close();

				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				if (file.exists()) {
					file.setContents(bais, true, true, monitor);
				} else {
					file.create(bais, true, monitor);
				}
				isDirty = false;
				firePropertyChange(PROP_DIRTY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doSaveAs() {
		// Not implemented
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return isDirty || getCommandStack().isDirty();
	}

	@Override
	public void commandStackChanged(EventObject event) {
		isDirty = true;
		firePropertyChange(PROP_DIRTY);
		super.updateActions(getStackActions());
	}

	public Diagram getDiagram() {
		return diagram;
	}
}

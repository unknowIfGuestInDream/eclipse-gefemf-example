/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

import com.tlcsdm.eclipse.gefemf.demo.editpart.LvglEditPartFactory;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglXmlSerializer;
import com.tlcsdm.eclipse.gefemf.demo.palette.LvglPaletteFactory;

/**
 * GEF Editor for LVGL UI design.
 */
public class DiagramEditor extends GraphicalEditorWithPalette {

	public static final String ID = "com.tlcsdm.eclipse.gefemf.demo.editor";

	private LvglScreen screen;

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
		loadScreen();
	}

	private void loadScreen() {
		IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput fileInput) {
			IFile file = fileInput.getFile();
			try {
				if (file.exists() && file.getContents().available() > 0) {
					try {
						LvglXmlSerializer serializer = new LvglXmlSerializer();
						screen = serializer.load(file.getContents());
					} catch (Exception e) {
						// File exists but couldn't read, create new screen
						screen = createDefaultScreen();
					}
				} else {
					screen = createDefaultScreen();
				}
			} catch (Exception e) {
				screen = createDefaultScreen();
			}
		} else {
			screen = createDefaultScreen();
		}
	}

	private LvglScreen createDefaultScreen() {
		LvglScreen s = new LvglScreen("main_screen");
		s.setWidth(480);
		s.setHeight(320);
		s.setBgColor(0xFFFFFF);

		// Add a sample button widget
		LvglWidget button = new LvglWidget("btn_ok", LvglWidget.WidgetType.BUTTON);
		button.setBounds(new Rectangle(100, 100, 120, 50));
		button.setText("OK");
		s.addWidget(button);

		// Add a sample label widget
		LvglWidget label = new LvglWidget("lbl_title", LvglWidget.WidgetType.LABEL);
		label.setBounds(new Rectangle(100, 30, 200, 40));
		label.setText("LVGL UI Designer");
		s.addWidget(label);

		return s;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new LvglEditPartFactory());
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		
		// Register as command stack listener to properly track dirty state
		getCommandStack().addCommandStackListener(new CommandStackListener() {
			@Override
			public void commandStackChanged(EventObject event) {
				DiagramEditor.this.commandStackChanged(event);
			}
		});
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(screen);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return LvglPaletteFactory.createPalette();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput fileInput) {
			IFile file = fileInput.getFile();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				LvglXmlSerializer serializer = new LvglXmlSerializer();
				serializer.save(screen, baos);

				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				if (file.exists()) {
					file.setContents(bais, true, true, monitor);
				} else {
					file.create(bais, true, monitor);
				}
				getCommandStack().markSaveLocation();
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
		return getCommandStack().isDirty();
	}

	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(PROP_DIRTY);
		super.updateActions(getStackActions());
	}

	public LvglScreen getScreen() {
		return screen;
	}
}

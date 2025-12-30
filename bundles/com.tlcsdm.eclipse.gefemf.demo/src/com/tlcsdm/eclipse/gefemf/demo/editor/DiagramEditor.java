/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
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
import com.tlcsdm.eclipse.gefemf.demo.util.ConsoleLogger;

/**
 * GEF Editor for LVGL UI design.
 */
public class DiagramEditor extends GraphicalEditorWithPalette implements CommandStackEventListener {

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
		
		// Register the Generate LVGL Code action
		GenerateLvglCodeAction generateAction = new GenerateLvglCodeAction(this);
		getActionRegistry().registerAction(generateAction);
		
		// Add context menu for right-click delete and other actions
		// Note: We intentionally do NOT call getSite().registerContextMenu() to avoid
		// Eclipse adding standard IDE contributions like "Run As", "Debug As", etc.
		// This keeps the context menu clean with only editor-relevant actions.
		ContextMenuProvider contextMenuProvider = new DiagramContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(contextMenuProvider);
		
		// Register as command stack listener to properly track dirty state
		// Use CommandStackEventListener (the non-deprecated API) for command stack changes
		getCommandStack().addCommandStackEventListener(this);
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

	/**
	 * Get the initial width of the palette.
	 * Increased from default to ensure widget names are fully visible.
	 */
	@Override
	protected int getInitialPaletteSize() {
		return 180; // Wider than default (125) to show full widget names
	}

	/**
	 * Configure the palette viewer with vertical scrollbar support.
	 * This enables scrolling when there are many palette entries.
	 */
	@Override
	protected void configurePaletteViewer() {
		super.configurePaletteViewer();
		PaletteViewer viewer = getPaletteViewer();
		if (viewer != null) {
			viewer.enableVerticalScrollbar(true);
		}
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
				ConsoleLogger.logError("Failed to save diagram", e);
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
	public void stackChanged(CommandStackEvent event) {
		firePropertyChange(PROP_DIRTY);
		super.updateActions(getStackActions());
	}

	public LvglScreen getScreen() {
		return screen;
	}

	/**
	 * Set the screen model and refresh the graphical viewer.
	 * Used by the multi-page editor when XML content changes.
	 */
	public void setScreen(LvglScreen newScreen) {
		this.screen = newScreen;
		GraphicalViewer viewer = getGraphicalViewer();
		if (viewer != null) {
			viewer.setContents(screen);
		}
	}
}

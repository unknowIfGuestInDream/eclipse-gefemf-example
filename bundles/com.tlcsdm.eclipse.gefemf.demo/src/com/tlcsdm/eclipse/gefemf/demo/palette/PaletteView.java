/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.palette;

import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * View that displays the palette for the diagram editor.
 */
public class PaletteView extends ViewPart {

    public static final String ID = "com.tlcsdm.eclipse.gefemf.demo.paletteView";

    private PaletteViewer paletteViewer;

    @Override
    public void createPartControl(Composite parent) {
        PaletteViewerProvider provider = new PaletteViewerProvider(getSite().getWorkbenchWindow().getActivePage());
        paletteViewer = provider.createPaletteViewer(parent);
        paletteViewer.setPaletteRoot(DiagramPaletteFactory.createPalette());
    }

    @Override
    public void setFocus() {
        if (paletteViewer != null && paletteViewer.getControl() != null) {
            paletteViewer.getControl().setFocus();
        }
    }

    public PaletteViewer getPaletteViewer() {
        return paletteViewer;
    }
}

/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Perspective factory for the GEF EMF Demo perspective.
 */
public class Perspective implements IPerspectiveFactory {

    public static final String ID = "com.tlcsdm.eclipse.gefemf.demo.perspective";

    @Override
    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();

        // Add Project Explorer on the left
        IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.25f, editorArea);
        left.addView(IPageLayout.ID_PROJECT_EXPLORER);

        // Add Palette view below the Project Explorer
        IFolderLayout leftBottom = layout.createFolder("leftBottom", IPageLayout.BOTTOM, 0.5f, "left");
        leftBottom.addView("com.tlcsdm.eclipse.gefemf.demo.paletteView");

        // Add Properties and Outline views on the right
        IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, 0.75f, editorArea);
        right.addView(IPageLayout.ID_PROP_SHEET);
        right.addView(IPageLayout.ID_OUTLINE);

        // Add view shortcuts
        layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut("com.tlcsdm.eclipse.gefemf.demo.paletteView");

        // Add new wizard shortcut
        layout.addNewWizardShortcut("com.tlcsdm.eclipse.gefemf.demo.wizard.newDiagram");
    }
}

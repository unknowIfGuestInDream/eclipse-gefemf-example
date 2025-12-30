/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen;
import com.tlcsdm.eclipse.gefemf.demo.model.LvglXmlSerializer;

/**
 * Wizard for creating a new LVGL UI file.
 */
public class NewDiagramWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;
	private NewDiagramWizardPage page;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle("New LVGL UI File");
	}

	@Override
	public void addPages() {
		page = new NewDiagramWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			IFile file = page.createNewFile();
			if (file == null) {
				return false;
			}

			// Open the editor
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IDE.openEditor(workbenchPage, file, true);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Wizard page implementation extending WizardNewFileCreationPage for creating
	 * new LVGL UI files with container browsing capability.
	 */
	private static class NewDiagramWizardPage extends WizardNewFileCreationPage {

		protected NewDiagramWizardPage(IStructuredSelection selection) {
			super("newDiagramPage", selection);
			setTitle("New LVGL UI File");
			setDescription("Create a new LVGL UI design file (.gefxml).");
			setFileExtension("gefxml");
			setFileName("ui_screen.gefxml");
		}

		@Override
		protected InputStream getInitialContents() {
			// Create an empty LVGL screen
			LvglScreen screen = new LvglScreen("main_screen");
			screen.setWidth(480);
			screen.setHeight(320);
			screen.setBgColor(0xFFFFFF);

			// Serialize the screen to XML
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			LvglXmlSerializer serializer = new LvglXmlSerializer();
			try {
				serializer.save(screen, baos);
				return new ByteArrayInputStream(baos.toByteArray());
			} catch (Exception e) {
				// Return empty content if serialization fails
				e.printStackTrace();
				return new ByteArrayInputStream(new byte[0]);
			}
		}

		@Override
		protected boolean validatePage() {
			if (!super.validatePage()) {
				return false;
			}

			String fileName = getFileName();
			if (fileName != null && !fileName.endsWith(".gefxml")) {
				setErrorMessage("File name must end with .gefxml extension.");
				return false;
			}

			return true;
		}
	}
}

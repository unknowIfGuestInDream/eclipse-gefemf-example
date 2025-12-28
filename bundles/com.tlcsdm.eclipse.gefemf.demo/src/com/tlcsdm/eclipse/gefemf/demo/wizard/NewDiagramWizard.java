/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Wizard for creating a new diagram file.
 */
public class NewDiagramWizard extends Wizard implements INewWizard {

    private IStructuredSelection selection;
    private NewDiagramWizardPage page;

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        setWindowTitle("New Diagram File");
    }

    @Override
    public void addPages() {
        page = new NewDiagramWizardPage(selection);
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        String fileName = page.getFileName();
        IResource container = page.getSelectedContainer();

        if (container == null || !(container instanceof IFolder || container instanceof IProject)) {
            return false;
        }

        try {
            IFile file;
            if (container instanceof IFolder) {
                file = ((IFolder) container).getFile(fileName);
            } else {
                file = ((IProject) container).getFile(fileName);
            }

            // Create an empty diagram
            Diagram diagram = new Diagram("NewDiagram");
            diagram.setPackageName("com.example.generated");

            // Serialize the diagram
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(diagram);
            oos.close();

            ByteArrayInputStream source = new ByteArrayInputStream(baos.toByteArray());

            if (file.exists()) {
                file.setContents(source, true, true, new NullProgressMonitor());
            } else {
                file.create(source, true, new NullProgressMonitor());
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
     * Wizard page for creating a new diagram file.
     */
    private static class NewDiagramWizardPage extends WizardPage {

        private IStructuredSelection selection;
        private Text fileNameText;
        private Text containerText;
        private IResource container;

        protected NewDiagramWizardPage(IStructuredSelection selection) {
            super("newDiagramPage");
            this.selection = selection;
            setTitle("New Diagram File");
            setDescription("Create a new GEF EMF diagram file.");
        }

        @Override
        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));

            // Container selection
            Label containerLabel = new Label(composite, SWT.NONE);
            containerLabel.setText("Container:");

            containerText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
            containerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            // Initialize container from selection
            if (selection != null && !selection.isEmpty()) {
                Object selected = selection.getFirstElement();
                if (selected instanceof IResource) {
                    container = (IResource) selected;
                    if (container instanceof IFile) {
                        container = container.getParent();
                    }
                    containerText.setText(container.getFullPath().toString());
                }
            }

            // File name
            Label fileLabel = new Label(composite, SWT.NONE);
            fileLabel.setText("File name:");

            fileNameText = new Text(composite, SWT.BORDER);
            fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            fileNameText.setText("diagram.gefemf");
            fileNameText.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    validatePage();
                }
            });

            setControl(composite);
            validatePage();
        }

        private void validatePage() {
            String fileName = fileNameText.getText();
            if (fileName == null || fileName.isEmpty()) {
                setErrorMessage("File name is required.");
                setPageComplete(false);
                return;
            }
            if (!fileName.endsWith(".gefemf")) {
                setErrorMessage("File name must end with .gefemf extension.");
                setPageComplete(false);
                return;
            }
            if (container == null) {
                setErrorMessage("Please select a container (project or folder).");
                setPageComplete(false);
                return;
            }
            setErrorMessage(null);
            setPageComplete(true);
        }

        public String getFileName() {
            return fileNameText.getText();
        }

        public IResource getSelectedContainer() {
            return container;
        }
    }
}

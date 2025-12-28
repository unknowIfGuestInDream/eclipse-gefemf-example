/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.handler;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tlcsdm.eclipse.gefemf.demo.editor.DiagramEditor;
import com.tlcsdm.eclipse.gefemf.demo.generator.JavaCodeGenerator;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Handler for the Generate Code command.
 */
public class GenerateCodeHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IEditorPart editor = HandlerUtil.getActiveEditor(event);

        if (!(editor instanceof DiagramEditor)) {
            MessageDialog.openWarning(
                HandlerUtil.getActiveShell(event),
                "Warning",
                "Please open a diagram editor first."
            );
            return null;
        }

        DiagramEditor diagramEditor = (DiagramEditor) editor;
        Diagram diagram = diagramEditor.getDiagram();

        if (diagram == null || diagram.getChildren().isEmpty()) {
            MessageDialog.openWarning(
                HandlerUtil.getActiveShell(event),
                "Warning",
                "The diagram is empty. Please add some classes first."
            );
            return null;
        }

        // Generate code
        JavaCodeGenerator generator = new JavaCodeGenerator(diagram);
        Map<String, String> generatedCode = generator.generateAll();

        // Save generated files
        try {
            IFile diagramFile = (IFile) diagramEditor.getEditorInput().getAdapter(IFile.class);
            if (diagramFile != null) {
                IProject project = diagramFile.getProject();
                IFolder srcGenFolder = project.getFolder("src-gen");

                if (!srcGenFolder.exists()) {
                    srcGenFolder.create(true, true, new NullProgressMonitor());
                }

                // Create package folder
                String packagePath = diagram.getPackageName().replace('.', '/');
                IFolder packageFolder = srcGenFolder.getFolder(packagePath);
                createFolders(packageFolder);

                // Generate files
                for (Map.Entry<String, String> entry : generatedCode.entrySet()) {
                    String className = entry.getKey();
                    String code = entry.getValue();
                    IFile javaFile = packageFolder.getFile(className + ".java");

                    ByteArrayInputStream source = new ByteArrayInputStream(code.getBytes("UTF-8"));
                    if (javaFile.exists()) {
                        javaFile.setContents(source, true, true, new NullProgressMonitor());
                    } else {
                        javaFile.create(source, true, new NullProgressMonitor());
                    }
                }

                MessageDialog.openInformation(
                    HandlerUtil.getActiveShell(event),
                    "Code Generated",
                    "Successfully generated " + generatedCode.size() + " Java class(es) in:\n" +
                    srcGenFolder.getFullPath().toString() + "/" + packagePath
                );
            }
        } catch (Exception e) {
            MessageDialog.openError(
                HandlerUtil.getActiveShell(event),
                "Error",
                "Failed to generate code: " + e.getMessage()
            );
            e.printStackTrace();
        }

        return null;
    }

    private void createFolders(IFolder folder) throws CoreException {
        if (!folder.exists()) {
            if (!folder.getParent().exists() && folder.getParent() instanceof IFolder) {
                createFolders((IFolder) folder.getParent());
            }
            folder.create(true, true, new NullProgressMonitor());
        }
    }
}

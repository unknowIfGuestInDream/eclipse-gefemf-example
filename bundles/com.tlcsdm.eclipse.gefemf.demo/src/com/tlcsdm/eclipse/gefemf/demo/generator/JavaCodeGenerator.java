/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.generator;

import java.util.List;

import com.tlcsdm.eclipse.gefemf.demo.model.ClassNode;
import com.tlcsdm.eclipse.gefemf.demo.model.Connection;
import com.tlcsdm.eclipse.gefemf.demo.model.Diagram;

/**
 * Code generator that generates Java code from the diagram model.
 */
public class JavaCodeGenerator {

    private final Diagram diagram;
    private final String packageName;

    public JavaCodeGenerator(Diagram diagram) {
        this.diagram = diagram;
        this.packageName = diagram.getPackageName();
    }

    /**
     * Generate Java code for all classes in the diagram.
     * @return a map of class name to generated code
     */
    public java.util.Map<String, String> generateAll() {
        java.util.Map<String, String> result = new java.util.LinkedHashMap<>();

        for (ClassNode node : diagram.getChildren()) {
            String code = generateClass(node);
            result.put(node.getClassName(), code);
        }

        return result;
    }

    /**
     * Generate Java code for a single class.
     */
    public String generateClass(ClassNode node) {
        StringBuilder sb = new StringBuilder();

        // Package declaration
        sb.append("package ").append(packageName).append(";\n\n");

        // Imports
        sb.append("import java.io.Serializable;\n\n");

        // Class declaration
        String superClass = getSuperClass(node);
        if (superClass != null) {
            sb.append("public class ").append(node.getClassName())
              .append(" extends ").append(superClass)
              .append(" implements Serializable {\n\n");
        } else {
            sb.append("public class ").append(node.getClassName())
              .append(" implements Serializable {\n\n");
        }

        // Serial version UID
        sb.append("    private static final long serialVersionUID = 1L;\n\n");

        // Fields from attributes
        for (String attr : node.getAttributes()) {
            generateField(sb, attr);
        }

        if (!node.getAttributes().isEmpty()) {
            sb.append("\n");
        }

        // Default constructor
        sb.append("    public ").append(node.getClassName()).append("() {\n");
        sb.append("        // Default constructor\n");
        sb.append("    }\n\n");

        // Getters and setters
        for (String attr : node.getAttributes()) {
            generateGetterSetter(sb, attr);
        }

        // Methods from model
        for (String method : node.getMethods()) {
            generateMethod(sb, method);
        }

        // References from connections
        generateReferences(sb, node);

        // Close class
        sb.append("}\n");

        return sb.toString();
    }

    private void generateField(StringBuilder sb, String attr) {
        String[] parts = attr.split(":");
        String name = parts[0].trim();
        String type = parts.length > 1 ? mapType(parts[1].trim()) : "String";

        sb.append("    private ").append(type).append(" ").append(name).append(";\n");
    }

    private void generateGetterSetter(StringBuilder sb, String attr) {
        String[] parts = attr.split(":");
        String name = parts[0].trim();
        String type = parts.length > 1 ? mapType(parts[1].trim()) : "String";
        String capName = capitalize(name);

        // Getter
        sb.append("    public ").append(type).append(" get").append(capName).append("() {\n");
        sb.append("        return ").append(name).append(";\n");
        sb.append("    }\n\n");

        // Setter
        sb.append("    public void set").append(capName).append("(").append(type).append(" ").append(name).append(") {\n");
        sb.append("        this.").append(name).append(" = ").append(name).append(";\n");
        sb.append("    }\n\n");
    }

    private void generateMethod(StringBuilder sb, String method) {
        // Parse method signature like "getName()" or "setName(name)"
        String methodName = method.replace("()", "").trim();
        if (method.contains("(") && !method.endsWith("()")) {
            // Has parameters - already has getter/setter generated
            return;
        }

        sb.append("    public void ").append(methodName).append("() {\n");
        sb.append("        // TODO: Implement ").append(methodName).append("\n");
        sb.append("    }\n\n");
    }

    private void generateReferences(StringBuilder sb, ClassNode node) {
        for (Connection conn : node.getSourceConnections()) {
            ClassNode target = conn.getTarget();
            String refName = decapitalize(target.getClassName());

            switch (conn.getType()) {
                case COMPOSITION:
                    sb.append("    private ").append(target.getClassName()).append(" ").append(refName).append(";\n\n");
                    break;
                case AGGREGATION:
                    sb.append("    private ").append(target.getClassName()).append(" ").append(refName).append(";\n\n");
                    break;
                case ASSOCIATION:
                    sb.append("    private ").append(target.getClassName()).append(" ").append(refName).append(";\n\n");
                    break;
                case INHERITANCE:
                    // Handled in class declaration
                    break;
            }
        }
    }

    private String getSuperClass(ClassNode node) {
        for (Connection conn : node.getSourceConnections()) {
            if (conn.getType() == Connection.ConnectionType.INHERITANCE) {
                return conn.getTarget().getClassName();
            }
        }
        return null;
    }

    private String mapType(String type) {
        switch (type.toLowerCase()) {
            case "int":
            case "integer":
                return "int";
            case "long":
                return "long";
            case "double":
                return "double";
            case "float":
                return "float";
            case "boolean":
                return "boolean";
            case "string":
                return "String";
            default:
                return type;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}

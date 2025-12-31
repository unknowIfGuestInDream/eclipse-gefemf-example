/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 调色板 - Palette
 * 
 * <p>本包定义工具面板中可拖拽的控件类型和工具。用户从调色板拖拽控件到画布创建新控件。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.palette.LvglPaletteFactory} - 
 *       调色板工厂，创建调色板的根节点和所有工具条目：
 *       <ul>
 *         <li><b>Tools (工具)</b> - 选择工具等基础工具</li>
 *         <li><b>Basic (基础控件)</b> - Container、Button、Label、Image、Line、Arc、Bar</li>
 *         <li><b>Input (输入控件)</b> - Slider、Switch、Checkbox、Dropdown、Textarea、Roller、Spinbox、Keyboard、Button Matrix</li>
 *         <li><b>Advanced (高级控件)</b> - LED、Scale、Spinner、Chart、Table、Calendar、List、Menu、Tab View 等</li>
 *       </ul>
 *       内部类 LvglWidgetFactory 实现 CreationFactory 接口，创建 LvglWidget 实例。
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.palette.LvglWidgetIcons} - 
 *       控件图标管理器，为每种控件类型提供调色板图标</li>
 * </ul>
 * 
 * <h2>调色板结构</h2>
 * <pre>
 * PaletteRoot
 * │
 * ├── PaletteDrawer "Tools"
 * │   └── SelectionToolEntry (选择工具)
 * │
 * ├── PaletteDrawer "Basic"
 * │   ├── CreationToolEntry "Container"
 * │   ├── CreationToolEntry "Button"
 * │   ├── CreationToolEntry "Label"
 * │   └── ...
 * │
 * ├── PaletteDrawer "Input"
 * │   ├── CreationToolEntry "Slider"
 * │   ├── CreationToolEntry "Switch"
 * │   └── ...
 * │
 * └── PaletteDrawer "Advanced"
 *     ├── CreationToolEntry "LED"
 *     ├── CreationToolEntry "Chart"
 *     └── ...
 * </pre>
 * 
 * <h2>创建控件流程</h2>
 * <pre>
 * 用户拖拽调色板条目到画布
 *         │
 *         ▼
 * CreationToolEntry 包含 CreationFactory
 *         │
 *         ▼
 * LvglWidgetFactory.getNewObject() 创建 LvglWidget
 *         │
 *         ▼
 * CreateRequest 发送到目标 EditPart
 *         │
 *         ▼
 * EditPolicy.getCreateCommand() 返回创建命令
 *         │
 *         ▼
 * Command.execute() 将控件添加到模型
 * </pre>
 * 
 * <h2>GEF 调色板组件</h2>
 * <ul>
 *   <li><b>PaletteRoot</b> - 调色板根节点</li>
 *   <li><b>PaletteDrawer</b> - 可折叠的工具组</li>
 *   <li><b>SelectionToolEntry</b> - 选择工具</li>
 *   <li><b>CreationToolEntry</b> - 创建工具，关联 CreationFactory</li>
 *   <li><b>CreationFactory</b> - 创建新模型对象的工厂接口</li>
 * </ul>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>被 editor 包的 DiagramEditor.getPaletteRoot() 使用</li>
 *   <li>创建 model 包的 LvglWidget 对象</li>
 *   <li>与 editpart 包的 EditPolicy 配合处理创建请求</li>
 * </ul>
 * 
 * @see org.eclipse.gef.palette.PaletteRoot
 * @see org.eclipse.gef.palette.CreationToolEntry
 * @see org.eclipse.gef.requests.CreationFactory
 */
package com.tlcsdm.eclipse.gefemf.demo.palette;

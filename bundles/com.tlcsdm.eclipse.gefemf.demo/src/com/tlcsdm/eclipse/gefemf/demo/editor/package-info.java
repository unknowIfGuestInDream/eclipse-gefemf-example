/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 编辑器层 - Editor Layer
 * 
 * <p>本包实现 Eclipse 编辑器扩展点，提供图形化编辑器和代码生成功能。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editor.DiagramMultiPageEditor} - 
 *       多页编辑器，包含两个页面：
 *       <ul>
 *         <li>第一页：图形编辑器 (DiagramEditor)，可视化设计界面</li>
 *         <li>第二页：XML 源码编辑器，直接编辑 XML 格式的模型文件</li>
 *       </ul>
 *       两个页面自动同步，切换时更新对方的内容。
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editor.DiagramEditor} - 
 *       GEF 图形编辑器，继承自 GraphicalEditorWithPalette：
 *       <ul>
 *         <li>管理 GraphicalViewer（画布）和 PaletteViewer（调色板）</li>
 *         <li>加载和保存模型 (.gefxml 文件)</li>
 *         <li>配置 EditPartFactory、RootEditPart 等</li>
 *         <li>管理 CommandStack 实现撤销/重做</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editor.DiagramEditorActionBarContributor} - 
 *       编辑器动作贡献者，向 Eclipse 工作台贡献：
 *       <ul>
 *         <li>工具栏按钮（撤销、重做、缩放等）</li>
 *         <li>菜单项</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editor.DiagramContextMenuProvider} - 
 *       右键上下文菜单提供者，定义画布上的右键菜单项</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editor.GenerateLvglCodeAction} - 
 *       生成 LVGL 代码动作，调用 LvglCodeGenerator 生成 C 代码</li>
 * </ul>
 * 
 * <h2>编辑器注册</h2>
 * <p>在 plugin.xml 中通过 org.eclipse.ui.editors 扩展点注册：
 * <pre>
 * &lt;extension point="org.eclipse.ui.editors"&gt;
 *   &lt;editor
 *     id="com.tlcsdm.eclipse.gefemf.demo.editor"
 *     name="LVGL UI Editor"
 *     extensions="gefxml"
 *     class="...DiagramMultiPageEditor"
 *     contributorClass="...DiagramEditorActionBarContributor"/&gt;
 * &lt;/extension&gt;
 * </pre>
 * 
 * <h2>GEF 编辑器结构</h2>
 * <pre>
 * DiagramEditor (GraphicalEditorWithPalette)
 * │
 * ├── GraphicalViewer (画布)
 * │   ├── RootEditPart (ScalableFreeformRootEditPart)
 * │   │   └── LvglScreenEditPart (屏幕)
 * │   │       └── LvglWidgetEditPart (控件) ...
 * │   └── EditPartFactory (LvglEditPartFactory)
 * │
 * ├── PaletteViewer (调色板)
 * │   └── PaletteRoot (LvglPaletteFactory.createPalette())
 * │
 * ├── CommandStack (命令栈)
 * │   └── 管理撤销/重做历史
 * │
 * └── ActionRegistry (动作注册表)
 *     └── 注册各种编辑动作
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>使用 model 包的 LvglScreen 作为编辑内容</li>
 *   <li>使用 editpart 包的 EditPartFactory 创建控制器</li>
 *   <li>使用 palette 包创建调色板</li>
 *   <li>使用 generator 包生成代码</li>
 *   <li>管理 command 包的 CommandStack</li>
 * </ul>
 * 
 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithPalette
 * @see org.eclipse.gef.GraphicalViewer
 * @see org.eclipse.ui.part.MultiPageEditorPart
 */
package com.tlcsdm.eclipse.gefemf.demo.editor;

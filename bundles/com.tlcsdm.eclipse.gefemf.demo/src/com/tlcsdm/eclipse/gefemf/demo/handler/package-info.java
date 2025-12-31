/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 命令处理器 - Command Handlers
 * 
 * <p>本包处理 Eclipse 工作台命令，响应用户在工具栏、菜单或快捷键触发的操作。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.handler.GenerateCodeHandler} - 
 *       生成代码命令处理器，响应工具栏"Generate C Code"按钮点击：
 *       <ul>
 *         <li>获取当前活动编辑器中的 LvglScreen</li>
 *         <li>创建 LvglCodeGenerator 实例</li>
 *         <li>生成 .h 和 .c 文件</li>
 *         <li>在控制台输出生成结果</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.handler.GenerateCodeFromFileHandler} - 
 *       从文件生成代码命令处理器，响应右键菜单"Generate LVGL C Code"：
 *       <ul>
 *         <li>从选中的 .gefxml 文件加载模型</li>
 *         <li>生成代码文件到同一目录</li>
 *       </ul>
 *   </li>
 * </ul>
 * 
 * <h2>命令注册</h2>
 * <p>在 plugin.xml 中通过扩展点注册：
 * <pre>
 * &lt;!-- 命令定义 --&gt;
 * &lt;extension point="org.eclipse.ui.commands"&gt;
 *   &lt;command id="...commands.generate" name="Generate C Code"/&gt;
 *   &lt;command id="...commands.generateFromFile" name="Generate LVGL C Code from File"/&gt;
 * &lt;/extension&gt;
 * 
 * &lt;!-- 命令处理器绑定 --&gt;
 * &lt;extension point="org.eclipse.ui.handlers"&gt;
 *   &lt;handler commandId="...commands.generate"
 *            class="...handler.GenerateCodeHandler"/&gt;
 *   &lt;handler commandId="...commands.generateFromFile"
 *            class="...handler.GenerateCodeFromFileHandler"/&gt;
 * &lt;/extension&gt;
 * 
 * &lt;!-- 工具栏/菜单贡献 --&gt;
 * &lt;extension point="org.eclipse.ui.menus"&gt;
 *   &lt;menuContribution locationURI="toolbar:org.eclipse.ui.main.toolbar"&gt;
 *     &lt;command commandId="...commands.generate" icon="icons/generate.png"/&gt;
 *   &lt;/menuContribution&gt;
 *   &lt;menuContribution locationURI="popup:org.eclipse.ui.popup.any"&gt;
 *     &lt;command commandId="...commands.generateFromFile"&gt;
 *       &lt;visibleWhen&gt;...只在 .gefxml 文件上显示&lt;/visibleWhen&gt;
 *     &lt;/command&gt;
 *   &lt;/menuContribution&gt;
 * &lt;/extension&gt;
 * </pre>
 * 
 * <h2>Handler 执行流程</h2>
 * <pre>
 * 用户点击工具栏按钮/菜单项
 *         │
 *         ▼
 * Eclipse Command Framework 查找对应 Handler
 *         │
 *         ▼
 * Handler.execute(ExecutionEvent event)
 *         │
 *         ├── 从 event 获取当前选择或活动编辑器
 *         ├── 获取 LvglScreen 模型
 *         ├── 调用 LvglCodeGenerator
 *         └── 输出生成结果
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>获取 editor 包的当前编辑器</li>
 *   <li>读取 model 包的 LvglScreen</li>
 *   <li>调用 generator 包生成代码</li>
 *   <li>使用 util 包输出日志</li>
 * </ul>
 * 
 * @see org.eclipse.core.commands.AbstractHandler
 * @see org.eclipse.core.commands.ExecutionEvent
 */
package com.tlcsdm.eclipse.gefemf.demo.handler;

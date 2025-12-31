/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 向导 - Wizards
 * 
 * <p>本包实现新建文件向导，用于创建新的图形设计文件。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.wizard.NewDiagramWizard} - 
 *       新建图表文件向导，在 File → New → Other... → GEF EMF Demo 分类下显示：
 *       <ul>
 *         <li>允许用户选择目标文件夹</li>
 *         <li>输入文件名（自动添加 .gefxml 扩展名）</li>
 *         <li>创建包含默认屏幕配置的新文件</li>
 *       </ul>
 *   </li>
 * </ul>
 * 
 * <h2>向导注册</h2>
 * <p>在 plugin.xml 中通过 org.eclipse.ui.newWizards 扩展点注册：
 * <pre>
 * &lt;extension point="org.eclipse.ui.newWizards"&gt;
 *   &lt;category id="com.tlcsdm.eclipse.gefemf.demo.category"
 *             name="GEF EMF Demo"/&gt;
 *   &lt;wizard id="com.tlcsdm.eclipse.gefemf.demo.wizard.newDiagram"
 *           name="New Diagram File"
 *           category="com.tlcsdm.eclipse.gefemf.demo.category"
 *           class="...wizard.NewDiagramWizard"
 *           icon="icons/sample.png"/&gt;
 * &lt;/extension&gt;
 * </pre>
 * 
 * <h2>向导执行流程</h2>
 * <pre>
 * 用户选择 File → New → Other...
 *         │
 *         ▼
 * 展开 "GEF EMF Demo" 分类
 *         │
 *         ▼
 * 选择 "New Diagram File" 向导
 *         │
 *         ▼
 * 向导页面：选择目标文件夹，输入文件名
 *         │
 *         ▼
 * NewDiagramWizard.performFinish()
 *         │
 *         ├── 创建默认 LvglScreen
 *         ├── 使用 LvglXmlSerializer 序列化
 *         └── 创建 .gefxml 文件
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>创建 model 包的 LvglScreen 对象</li>
 *   <li>使用 model 包的 LvglXmlSerializer 保存文件</li>
 * </ul>
 * 
 * @see org.eclipse.jface.wizard.Wizard
 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage
 */
package com.tlcsdm.eclipse.gefemf.demo.wizard;

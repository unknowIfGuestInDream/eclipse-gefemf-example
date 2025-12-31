/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 图形层 - Figure Layer (View in MVC)
 * 
 * <p>本包包含所有 Draw2D 图形类，负责在画布上绘制控件的可视化表示。
 * 在 GEF 的 MVC 架构中，Figure 是 View 层的实现。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.figure.LvglWidgetFigure} - 
 *       LVGL 控件图形，根据控件类型（按钮、标签、滑块等）绘制不同的外观。
 *       模拟真实 LVGL 控件在嵌入式设备上的显示效果。
 *       <ul>
 *         <li>drawButton() - 绘制带 3D 效果的按钮</li>
 *         <li>drawLabel() - 绘制文本标签</li>
 *         <li>drawSlider() - 绘制滑块轨道和滑块</li>
 *         <li>drawSwitch() - 绘制开关控件</li>
 *         <li>drawCheckbox() - 绘制复选框</li>
 *         <li>drawDropdown() - 绘制下拉框</li>
 *         <li>drawTextarea() - 绘制文本区域</li>
 *         <li>drawArc() - 绘制弧形进度条</li>
 *         <li>drawBar() - 绘制进度条</li>
 *         <li>drawImage() - 绘制图片占位符</li>
 *         <li>drawContainer() - 绘制容器边框</li>
 *         <li>drawTable() - 绘制表格网格</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.figure.LvglContainerFigure} - 
 *       容器控件图形，支持子控件的布局和显示</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.figure.ClassNodeFigure} - 
 *       类节点图形，用于类图显示（作为参考实现）</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.figure.ConnectionFigure} - 
 *       连接线图形，绘制控件之间的关联线</li>
 * </ul>
 * 
 * <h2>Draw2D 基础</h2>
 * <p>Draw2D 是 GEF 使用的 2D 图形绘制库，基于 SWT Graphics。主要概念：
 * <ul>
 *   <li><b>Figure</b> - 可绘制的图形元素，有位置、大小、子 Figure 等属性</li>
 *   <li><b>LayoutManager</b> - 负责子 Figure 的布局</li>
 *   <li><b>Border</b> - Figure 的边框样式</li>
 *   <li><b>paintFigure()</b> - 核心绘制方法，在此绘制图形内容</li>
 * </ul>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>由 editpart 包的 EditPart.createFigure() 方法创建</li>
 *   <li>由 EditPart.refreshVisuals() 方法更新显示属性</li>
 *   <li>不直接访问 model 包，所有数据通过 EditPart 传递</li>
 * </ul>
 * 
 * @see org.eclipse.draw2d.Figure
 * @see com.tlcsdm.eclipse.gefemf.demo.editpart
 */
package com.tlcsdm.eclipse.gefemf.demo.figure;

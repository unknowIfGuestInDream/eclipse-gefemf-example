/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 数据模型层 - Model Layer
 * 
 * <p>本包包含图形编辑器中所有的数据模型类，遵循 GEF 的 MVC 架构中的 Model 部分。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.ModelElement} - 
 *       所有模型元素的抽象基类，提供属性变更通知机制 (PropertyChangeSupport)，
 *       当模型属性发生变化时通知对应的 EditPart 刷新视图</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen} - 
 *       表示一个 LVGL 屏幕，是所有控件的根容器，定义屏幕的尺寸和背景色</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget} - 
 *       表示一个 LVGL 控件（按钮、标签、滑块等），包含控件类型、位置、大小、
 *       文本、颜色等属性，支持嵌套子控件形成控件树</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.Connection} - 
 *       表示控件之间的连接线，用于可视化事件绑定关系</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.LvglXmlSerializer} - 
 *       XML 序列化器，负责将模型保存为 XML 文件或从 XML 文件加载模型</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.Diagram} - 
 *       类图模型，用于展示 EMF 建模功能（作为参考实现）</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.model.ClassNode} - 
 *       类节点模型，表示类图中的一个类（作为参考实现）</li>
 * </ul>
 * 
 * <h2>设计模式</h2>
 * <ul>
 *   <li><b>观察者模式</b> - 通过 PropertyChangeSupport 实现模型变更通知</li>
 *   <li><b>组合模式</b> - LvglWidget 可以包含子 LvglWidget，形成控件树</li>
 * </ul>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>被 editpart 包的 EditPart 类观察，模型变化时触发视图刷新</li>
 *   <li>被 generator 包读取，用于生成 C 代码</li>
 *   <li>被 property 包适配，用于在属性视图中显示和编辑</li>
 * </ul>
 * 
 * @see com.tlcsdm.eclipse.gefemf.demo.editpart
 * @see com.tlcsdm.eclipse.gefemf.demo.generator
 */
package com.tlcsdm.eclipse.gefemf.demo.model;

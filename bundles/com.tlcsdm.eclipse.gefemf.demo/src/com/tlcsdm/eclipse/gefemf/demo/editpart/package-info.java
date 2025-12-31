/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 控制器层 - EditPart Layer (Controller in MVC)
 * 
 * <p>本包包含 GEF 的核心组件 EditPart，是 MVC 架构中的 Controller 层。
 * EditPart 连接 Model（数据模型）和 Figure（视图），负责：
 * <ul>
 *   <li>创建和管理对应的 Figure</li>
 *   <li>监听 Model 的属性变化，刷新 Figure 显示</li>
 *   <li>处理用户交互（选择、拖拽等）</li>
 *   <li>安装 EditPolicy 定义支持的编辑操作</li>
 * </ul>
 * 
 * <h2>核心类说明</h2>
 * 
 * <h3>EditPart 类</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglScreenEditPart} - 
 *       屏幕控制器，管理整个画布和所有顶级控件
 *       <ul>
 *         <li>createFigure() - 创建屏幕画布 Figure</li>
 *         <li>getModelChildren() - 返回屏幕上的所有控件</li>
 *         <li>createEditPolicies() - 安装布局编辑策略</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglWidgetEditPart} - 
 *       控件控制器，管理单个 LVGL 控件
 *       <ul>
 *         <li>createFigure() - 创建 LvglWidgetFigure</li>
 *         <li>refreshVisuals() - 同步模型属性到 Figure</li>
 *         <li>propertyChange() - 响应模型属性变化</li>
 *         <li>getAdapter() - 提供属性视图适配器</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglContainerEditPart} - 
 *       容器控件控制器，支持嵌套子控件</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.ConnectionEditPart} - 
 *       连接线控制器</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglEditPartFactory} - 
 *       EditPart 工厂，根据模型类型创建对应的 EditPart</li>
 * </ul>
 * 
 * <h3>EditPolicy 类（编辑策略）</h3>
 * <p>EditPolicy 定义 EditPart 支持的编辑操作，返回对应的 Command：
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglScreenLayoutEditPolicy} - 
 *       屏幕布局策略，处理控件创建和移动
 *       <ul>
 *         <li>getCreateCommand() - 返回创建控件命令</li>
 *         <li>getMoveChildCommand() - 返回移动控件命令</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglWidgetEditPolicy} - 
 *       控件组件策略，处理控件删除
 *       <ul>
 *         <li>createDeleteCommand() - 返回删除控件命令</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.LvglContainerLayoutEditPolicy} - 
 *       容器布局策略，处理在容器内创建控件</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.editpart.ClassNodeGraphicalNodeEditPolicy} - 
 *       节点连接策略，处理连接线的创建</li>
 * </ul>
 * 
 * <h2>GEF EditPart 生命周期</h2>
 * <pre>
 * 1. EditPartFactory.createEditPart() - 创建 EditPart 实例
 * 2. EditPart.setModel() - 设置关联的模型对象
 * 3. EditPart.activate() - 激活，开始监听模型变化
 * 4. EditPart.createFigure() - 创建视图 Figure
 * 5. EditPart.createEditPolicies() - 安装编辑策略
 * 6. EditPart.refreshVisuals() - 刷新视图显示
 * 7. ... (用户操作，模型变化，视图刷新)
 * 8. EditPart.deactivate() - 停用，取消监听
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>观察 model 包的模型对象，响应属性变化</li>
 *   <li>创建和更新 figure 包的 Figure 对象</li>
 *   <li>使用 command 包的 Command 对象执行编辑操作</li>
 *   <li>被 editor 包的 GraphicalViewer 管理</li>
 * </ul>
 * 
 * @see org.eclipse.gef.EditPart
 * @see org.eclipse.gef.EditPolicy
 * @see com.tlcsdm.eclipse.gefemf.demo.model
 * @see com.tlcsdm.eclipse.gefemf.demo.figure
 * @see com.tlcsdm.eclipse.gefemf.demo.command
 */
package com.tlcsdm.eclipse.gefemf.demo.editpart;

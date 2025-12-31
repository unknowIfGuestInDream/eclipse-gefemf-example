/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 控件属性提供者 - Widget Property Providers
 * 
 * <p>本包包含针对不同控件类型的属性提供者，为属性视图提供控件特定的属性。
 * 
 * <h2>设计模式</h2>
 * <p>使用策略模式（Strategy Pattern），通过注册表根据控件类型动态选择属性提供者。
 * 
 * <h2>核心接口和类</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProvider} - 
 *       属性提供者接口，定义：
 *       <ul>
 *         <li>getPropertyDescriptors() - 获取控件特定的属性描述符</li>
 *         <li>getPropertyValue() - 获取控件特定的属性值</li>
 *         <li>setPropertyValue() - 设置控件特定的属性值</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProviderRegistry} - 
 *       属性提供者注册表，维护控件类型到属性提供者的映射，
 *       根据 LvglWidget.WidgetType 返回对应的 WidgetPropertyProvider 列表</li>
 * </ul>
 * 
 * <h2>属性提供者实现</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ImagePropertyProvider} - 
 *       图片控件属性提供者
 *       <ul>
 *         <li>imageSource - 图片源路径或变量名</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.CheckboxPropertyProvider} - 
 *       复选框/开关控件属性提供者
 *       <ul>
 *         <li>checked - 选中状态</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ValuePropertyProvider} - 
 *       滑块/弧形/进度条控件属性提供者
 *       <ul>
 *         <li>value - 当前值</li>
 *         <li>minValue - 最小值</li>
 *         <li>maxValue - 最大值</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.TablePropertyProvider} - 
 *       表格控件属性提供者
 *       <ul>
 *         <li>rowCount - 行数</li>
 *         <li>columnCount - 列数</li>
 *         <li>tableData - 表格数据（分号分隔行，逗号分隔列）</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ContainerPropertyProvider} - 
 *       容器控件属性提供者
 *       <ul>
 *         <li>layoutType - 布局类型（None/Flex/Grid）</li>
 *         <li>flexFlow - Flex 流向</li>
 *         <li>flexMainAlign - 主轴对齐</li>
 *         <li>flexCrossAlign - 交叉轴对齐</li>
 *         <li>padRow/padColumn - 行列间距</li>
 *       </ul>
 *   </li>
 * </ul>
 * 
 * <h2>扩展指南</h2>
 * <p>为新控件类型添加属性提供者：
 * <ol>
 *   <li>创建实现 WidgetPropertyProvider 接口的类</li>
 *   <li>在 WidgetPropertyProviderRegistry.initializeProviders() 中注册</li>
 * </ol>
 * 
 * @see com.tlcsdm.eclipse.gefemf.demo.property.LvglWidgetPropertySource
 */
package com.tlcsdm.eclipse.gefemf.demo.property.widget;

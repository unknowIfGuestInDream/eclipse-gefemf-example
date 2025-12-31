/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 属性视图 - Properties View
 * 
 * <p>本包实现 Eclipse Properties 视图的适配器，允许用户在属性视图中查看和编辑选中控件的属性。
 * 
 * <h2>核心类说明</h2>
 * 
 * <h3>PropertySource 类（属性源）</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.LvglWidgetPropertySource} - 
 *       LVGL 控件属性源，实现 IPropertySource 接口：
 *       <ul>
 *         <li>getPropertyDescriptors() - 返回属性描述符数组</li>
 *         <li>getPropertyValue() - 获取属性值</li>
 *         <li>setPropertyValue() - 设置属性值</li>
 *         <li>使用 WidgetPropertyProviderRegistry 获取特定控件类型的属性</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.LvglScreenPropertySource} - 
 *       屏幕属性源，定义屏幕级别的属性（名称、尺寸、背景色等）</li>
 * </ul>
 * 
 * <h3>PropertyDescriptor 类（属性描述符）</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.ColorPropertyDescriptor} - 
 *       颜色属性描述符，提供颜色选择器</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.CheckboxPropertyDescriptor} - 
 *       复选框属性描述符，用于布尔值属性</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.FilePropertyDescriptor} - 
 *       文件属性描述符，提供文件选择对话框</li>
 * </ul>
 * 
 * <h3>CellEditor 类（单元格编辑器）</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.PositionedColorCellEditor} - 
 *       颜色单元格编辑器，弹出颜色选择对话框</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.CheckboxCellEditor} - 
 *       复选框单元格编辑器</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.FileDialogCellEditor} - 
 *       文件对话框单元格编辑器</li>
 * </ul>
 * 
 * <h3>widget 子包（控件特定属性提供者）</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProvider} - 
 *       属性提供者接口，定义获取属性描述符和处理属性值的方法</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.WidgetPropertyProviderRegistry} - 
 *       属性提供者注册表，根据控件类型返回对应的属性提供者</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ImagePropertyProvider} - 
 *       图片控件属性提供者，添加图片源属性</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.CheckboxPropertyProvider} - 
 *       复选框/开关控件属性提供者，添加选中状态属性</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ValuePropertyProvider} - 
 *       滑块/弧形/进度条控件属性提供者，添加值和范围属性</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.TablePropertyProvider} - 
 *       表格控件属性提供者，添加行列数和数据属性</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.property.widget.ContainerPropertyProvider} - 
 *       容器控件属性提供者，添加布局相关属性</li>
 * </ul>
 * 
 * <h2>属性视图工作原理</h2>
 * <pre>
 * 用户选中控件
 *     │
 *     ▼
 * Eclipse Workbench 查询 IPropertySource
 *     │
 *     ▼
 * EditPart.getAdapter(IPropertySource.class)
 *     │
 *     ▼
 * 返回 LvglWidgetPropertySource
 *     │
 *     ▼
 * Properties View 调用 getPropertyDescriptors()
 *     │
 *     ▼
 * 显示属性列表和编辑器
 *     │
 *     ▼
 * 用户修改属性 → setPropertyValue() → widget.setXxx()
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>被 editpart 包的 EditPart.getAdapter() 返回</li>
 *   <li>读写 model 包的模型对象属性</li>
 *   <li>使用 PropertyUtils 进行属性值转换</li>
 * </ul>
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 * @see org.eclipse.ui.views.properties.IPropertyDescriptor
 * @see org.eclipse.jface.viewers.CellEditor
 */
package com.tlcsdm.eclipse.gefemf.demo.property;

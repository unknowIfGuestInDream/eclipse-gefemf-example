/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 代码生成器 - Code Generator
 * 
 * <p>本包负责将图形模型转换为 LVGL C 代码，生成可在嵌入式设备上运行的 UI 代码。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.generator.LvglCodeGenerator} - 
 *       LVGL 代码生成器，将 LvglScreen 模型转换为 C 代码：
 *       <ul>
 *         <li>generateHeader() - 生成头文件 (.h)
 *           <ul>
 *             <li>包含保护宏 (#ifndef/#define/#endif)</li>
 *             <li>#include "lvgl.h"</li>
 *             <li>extern 声明屏幕和所有控件变量</li>
 *             <li>函数声明 (create/delete)</li>
 *           </ul>
 *         </li>
 *         <li>generateSource() - 生成源文件 (.c)
 *           <ul>
 *             <li>变量定义</li>
 *             <li>create 函数实现
 *               <ul>
 *                 <li>创建屏幕对象</li>
 *                 <li>遍历创建所有控件 (lv_xxx_create)</li>
 *                 <li>设置位置和大小 (lv_obj_set_pos/size)</li>
 *                 <li>设置文本、颜色等属性</li>
 *                 <li>递归处理嵌套子控件</li>
 *               </ul>
 *             </li>
 *             <li>delete 函数实现 (lv_obj_del)</li>
 *           </ul>
 *         </li>
 *       </ul>
 *   </li>
 * </ul>
 * 
 * <h2>代码生成流程</h2>
 * <pre>
 * LvglScreen (屏幕模型)
 *     │
 *     ▼
 * LvglCodeGenerator
 *     │
 *     ├── generateHeader()
 *     │   │
 *     │   ├── 生成头文件保护宏
 *     │   ├── 生成 #include
 *     │   ├── 遍历控件生成 extern 声明
 *     │   └── 生成函数声明
 *     │
 *     └── generateSource()
 *         │
 *         ├── 生成变量定义
 *         ├── 生成 create 函数
 *         │   │
 *         │   ├── lv_obj_create(NULL) 创建屏幕
 *         │   ├── 设置屏幕大小和背景色
 *         │   └── 遍历控件
 *         │       │
 *         │       ├── getCreateFunction() 获取创建函数名
 *         │       ├── lv_xxx_create(parent) 创建控件
 *         │       ├── lv_obj_set_pos/size 设置位置大小
 *         │       ├── 根据控件类型设置特定属性
 *         │       │   ├── Button: 创建内部 Label
 *         │       │   ├── Label: lv_label_set_text
 *         │       │   ├── Slider: lv_slider_set_range/value
 *         │       │   ├── Image: lv_img_set_src
 *         │       │   └── ...
 *         │       ├── 设置样式属性 (颜色、边框、圆角)
 *         │       └── 递归处理子控件
 *         │
 *         └── 生成 delete 函数
 * </pre>
 * 
 * <h2>支持的 LVGL 控件类型</h2>
 * <table border="1">
 *   <tr><th>WidgetType</th><th>创建函数</th><th>特殊处理</th></tr>
 *   <tr><td>BUTTON</td><td>lv_btn_create</td><td>创建内部 Label</td></tr>
 *   <tr><td>LABEL</td><td>lv_label_create</td><td>lv_label_set_text</td></tr>
 *   <tr><td>SLIDER</td><td>lv_slider_create</td><td>range/value</td></tr>
 *   <tr><td>SWITCH</td><td>lv_switch_create</td><td>checked state</td></tr>
 *   <tr><td>CHECKBOX</td><td>lv_checkbox_create</td><td>text/checked</td></tr>
 *   <tr><td>DROPDOWN</td><td>lv_dropdown_create</td><td>options</td></tr>
 *   <tr><td>TEXTAREA</td><td>lv_textarea_create</td><td>text</td></tr>
 *   <tr><td>IMAGE</td><td>lv_img_create</td><td>src</td></tr>
 *   <tr><td>ARC</td><td>lv_arc_create</td><td>range/value</td></tr>
 *   <tr><td>BAR</td><td>lv_bar_create</td><td>range/value</td></tr>
 *   <tr><td>TABLE</td><td>lv_table_create</td><td>row/col/data</td></tr>
 *   <tr><td>CONTAINER</td><td>lv_obj_create</td><td>layout</td></tr>
 *   <tr><td>...</td><td>...</td><td>...</td></tr>
 * </table>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>读取 model 包的 LvglScreen 和 LvglWidget</li>
 *   <li>被 handler 包的 GenerateCodeHandler 调用</li>
 *   <li>被 editor 包的 GenerateLvglCodeAction 调用</li>
 *   <li>读取 preferences 包的许可证头配置</li>
 * </ul>
 * 
 * @see com.tlcsdm.eclipse.gefemf.demo.model.LvglScreen
 * @see com.tlcsdm.eclipse.gefemf.demo.model.LvglWidget
 */
package com.tlcsdm.eclipse.gefemf.demo.generator;

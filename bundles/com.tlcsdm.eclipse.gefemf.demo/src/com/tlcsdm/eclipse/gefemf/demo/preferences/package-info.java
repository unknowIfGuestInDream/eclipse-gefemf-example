/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 首选项 - Preferences
 * 
 * <p>本包管理插件的用户偏好设置，通过 Eclipse Preferences 机制持久化。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.preferences.LvglPreferencePage} - 
 *       首选项页面，在 Window → Preferences → LVGL UI Editor 下显示：
 *       <ul>
 *         <li>许可证头 - 代码生成时添加到文件头部的版权声明</li>
 *         <li>其他代码生成选项</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.preferences.LvglPreferenceInitializer} - 
 *       首选项初始化器，设置默认值</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.preferences.LvglPreferenceConstants} - 
 *       首选项常量定义，包含所有首选项的键名</li>
 * </ul>
 * 
 * <h2>首选项注册</h2>
 * <p>在 plugin.xml 中通过扩展点注册：
 * <pre>
 * &lt;!-- 首选项页面 --&gt;
 * &lt;extension point="org.eclipse.ui.preferencePages"&gt;
 *   &lt;page id="com.tlcsdm.eclipse.gefemf.demo.preferences"
 *         name="LVGL UI Editor"
 *         class="...preferences.LvglPreferencePage"/&gt;
 * &lt;/extension&gt;
 * 
 * &lt;!-- 首选项初始化器 --&gt;
 * &lt;extension point="org.eclipse.core.runtime.preferences"&gt;
 *   &lt;initializer class="...preferences.LvglPreferenceInitializer"/&gt;
 * &lt;/extension&gt;
 * </pre>
 * 
 * <h2>首选项访问方式</h2>
 * <pre>
 * // 获取首选项值
 * IPreferenceStore store = Activator.getDefault().getPreferenceStore();
 * String licenseHeader = store.getString(LvglPreferenceConstants.P_LICENSE_HEADER);
 * 
 * // 设置首选项值
 * store.setValue(LvglPreferenceConstants.P_LICENSE_HEADER, newHeader);
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>被 generator 包读取，获取许可证头等配置</li>
 *   <li>使用 Activator 获取 PreferenceStore</li>
 * </ul>
 * 
 * @see org.eclipse.jface.preference.PreferencePage
 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer
 * @see org.eclipse.jface.preference.IPreferenceStore
 */
package com.tlcsdm.eclipse.gefemf.demo.preferences;

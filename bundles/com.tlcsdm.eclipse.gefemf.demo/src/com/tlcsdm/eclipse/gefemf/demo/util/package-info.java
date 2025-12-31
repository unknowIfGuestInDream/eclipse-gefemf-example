/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 工具类 - Utilities
 * 
 * <p>本包包含通用工具类，提供辅助功能支持。
 * 
 * <h2>核心类说明</h2>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.util.ConsoleLogger} - 
 *       控制台日志工具，将日志消息输出到 Eclipse Console 视图：
 *       <ul>
 *         <li>logInfo(String message) - 输出信息级别日志</li>
 *         <li>logError(String message, Throwable t) - 输出错误级别日志</li>
 *         <li>logWarning(String message) - 输出警告级别日志</li>
 *       </ul>
 *       日志消息会显示在 Eclipse 的 Console 视图中，方便用户查看操作结果。
 *   </li>
 * </ul>
 * 
 * <h2>Console 日志使用示例</h2>
 * <pre>
 * // 输出信息
 * ConsoleLogger.logInfo("代码生成完成: " + filePath);
 * 
 * // 输出错误
 * try {
 *     // ... 操作
 * } catch (Exception e) {
 *     ConsoleLogger.logError("保存文件失败", e);
 * }
 * </pre>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>被 handler 包用于输出命令执行结果</li>
 *   <li>被 editor 包用于输出保存等操作的错误信息</li>
 *   <li>被 generator 包用于输出代码生成进度</li>
 * </ul>
 * 
 * @see org.eclipse.ui.console.MessageConsole
 * @see org.eclipse.ui.console.ConsolePlugin
 */
package com.tlcsdm.eclipse.gefemf.demo.util;

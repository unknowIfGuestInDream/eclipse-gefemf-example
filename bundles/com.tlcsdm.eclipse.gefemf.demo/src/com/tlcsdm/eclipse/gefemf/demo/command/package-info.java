/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/

/**
 * 命令层 - Command Layer
 * 
 * <p>本包包含所有可撤销/重做的操作命令，遵循命令模式（Command Pattern）。
 * GEF 使用 CommandStack 管理命令的执行、撤销和重做。
 * 
 * <h2>核心类说明</h2>
 * 
 * <h3>LVGL 控件相关命令</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetCreateCommand} - 
 *       创建 LVGL 控件命令
 *       <ul>
 *         <li>execute() - 将控件添加到屏幕</li>
 *         <li>undo() - 从屏幕移除控件</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetDeleteCommand} - 
 *       删除 LVGL 控件命令
 *       <ul>
 *         <li>execute() - 从屏幕移除控件</li>
 *         <li>undo() - 将控件添加回屏幕</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetSetConstraintCommand} - 
 *       设置控件位置和大小命令
 *       <ul>
 *         <li>execute() - 设置新的位置大小</li>
 *         <li>undo() - 恢复原来的位置大小</li>
 *       </ul>
 *   </li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetAddToContainerCommand} - 
 *       将控件添加到容器命令</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.LvglWidgetRemoveFromContainerCommand} - 
 *       从容器移除控件命令</li>
 * </ul>
 * 
 * <h3>类图相关命令（参考实现）</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeCreateCommand} - 
 *       创建类节点命令</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeDeleteCommand} - 
 *       删除类节点命令</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ClassNodeSetConstraintCommand} - 
 *       设置类节点约束命令</li>
 * </ul>
 * 
 * <h3>连接线相关命令</h3>
 * <ul>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ConnectionCreateCommand} - 
 *       创建连接线命令</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ConnectionDeleteCommand} - 
 *       删除连接线命令</li>
 *   <li>{@link com.tlcsdm.eclipse.gefemf.demo.command.ConnectionReconnectCommand} - 
 *       重新连接命令</li>
 * </ul>
 * 
 * <h2>命令模式原理</h2>
 * <pre>
 * ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
 * │  EditPolicy  │────►│   Command    │────►│    Model     │
 * │  (请求处理)   │     │  (封装操作)   │     │  (执行变更)   │
 * └──────────────┘     └──────────────┘     └──────────────┘
 *                             │
 *                             ▼
 *                      ┌──────────────┐
 *                      │ CommandStack │
 *                      │ (管理历史)    │
 *                      │ - execute()  │
 *                      │ - undo()     │
 *                      │ - redo()     │
 *                      └──────────────┘
 * </pre>
 * 
 * <h2>Command 基类要求</h2>
 * <p>所有 Command 必须继承 {@link org.eclipse.gef.commands.Command} 并实现：
 * <ul>
 *   <li>{@code canExecute()} - 判断命令是否可执行</li>
 *   <li>{@code execute()} - 执行命令，修改模型</li>
 *   <li>{@code undo()} - 撤销命令，恢复模型状态</li>
 *   <li>{@code canUndo()} - 判断命令是否可撤销（默认返回 true）</li>
 * </ul>
 * 
 * <h2>与其他包的关系</h2>
 * <ul>
 *   <li>由 editpart 包的 EditPolicy 创建并返回</li>
 *   <li>操作 model 包的模型对象</li>
 *   <li>被 editor 包的 CommandStack 执行和管理</li>
 * </ul>
 * 
 * @see org.eclipse.gef.commands.Command
 * @see org.eclipse.gef.commands.CommandStack
 * @see com.tlcsdm.eclipse.gefemf.demo.editpart
 */
package com.tlcsdm.eclipse.gefemf.demo.command;

# Eclipse GEF EMF Example

基于 Eclipse + GEF Classic + EMF 实现的代码生成器示例项目。

## 功能

- 拖拽式的图形化编辑器
- 基于EMF的数据模型
- 点击生成按钮实现代码生成

## 项目结构

```
eclipse-gefemf-example/
├── bundles/                          # 插件模块
│   └── com.tlcsdm.eclipse.gefemf.demo/   # 主演示插件（包含GEF编辑器和代码生成）
├── features/                         # Feature模块
│   └── com.tlcsdm.eclipse.gefemf.demo.feature/
├── sites/                            # 产品和更新站点
│   └── com.tlcsdm.eclipse.gefemf.demo.site/
└── target-platform.target            # 目标平台配置
```

## 模块功能说明

主插件 `com.tlcsdm.eclipse.gefemf.demo` 的包结构及功能：

| 包名 | 功能说明 |
|------|----------|
| `model` | **数据模型层** - 定义图形编辑器中的所有模型对象，如 LvglScreen（屏幕）、LvglWidget（控件）等，继承自 ModelElement 基类，支持属性变更通知 |
| `figure` | **图形层** - 定义 Draw2D 图形，负责在画布上绘制控件的可视化表示，如按钮、标签、滑块等 LVGL 控件的外观 |
| `editpart` | **控制器层** - GEF 的核心组件，连接模型（Model）和视图（Figure），监听模型变化并更新视图，处理用户交互 |
| `command` | **命令层** - 实现可撤销/重做的操作命令，如创建控件、删除控件、移动控件等，遵循命令模式 |
| `editor` | **编辑器层** - 实现 Eclipse 编辑器扩展点，包含图形编辑器和多页编辑器，管理调色板和上下文菜单 |
| `palette` | **调色板** - 定义工具面板中可拖拽的控件类型和工具，用户从此处拖拽控件到画布 |
| `property` | **属性视图** - 实现 Eclipse Properties 视图的适配器，允许用户在属性视图中编辑选中控件的属性 |
| `generator` | **代码生成器** - 将图形模型转换为 LVGL C 代码，生成头文件和源文件 |
| `handler` | **命令处理器** - 处理 Eclipse 工作台命令，如工具栏按钮点击事件 |
| `preferences` | **首选项** - 管理插件的用户偏好设置，如代码生成的许可证头 |
| `wizard` | **向导** - 实现新建文件向导，用于创建新的图形设计文件 |
| `util` | **工具类** - 通用工具类，如控制台日志输出 |

## 架构文档

详细的 GEF + EMF 架构说明和实现框架图请参考 [ARCHITECTURE.md](./ARCHITECTURE.md)。

## 构建

```bash
mvn clean verify
```

## 开发环境

- Java 21
- Eclipse 2025-03 or later
- Maven 3.9.0+

## 许可证

Eclipse Public License - v2.0

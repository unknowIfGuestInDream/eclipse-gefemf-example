# GEF + EMF 架构说明 / Architecture Documentation

本文档详细说明项目中 GEF (Graphical Editing Framework)、EMF (Eclipse Modeling Framework) 以及代码生成的实现架构。

---

## 目录

1. [整体架构图](#整体架构图)
2. [GEF MVC 架构](#gef-mvc-架构)
3. [EMF 数据模型](#emf-数据模型)
4. [代码生成流程](#代码生成流程)
5. [核心类关系图](#核心类关系图)
6. [数据流说明](#数据流说明)

---

## 整体架构图

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           Eclipse Workbench                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────────────────┐ │
│  │   Palette       │  │   Editor Area   │  │   Properties View           │ │
│  │   (调色板)       │  │   (编辑区域)     │  │   (属性视图)                │ │
│  │                 │  │                 │  │                             │ │
│  │  ┌───────────┐  │  │  ┌───────────┐  │  │  Widget Name: btn_ok        │ │
│  │  │ Selection │  │  │  │           │  │  │  Type: Button               │ │
│  │  │ Button    │  │  │  │  Canvas   │  │  │  Text: OK                   │ │
│  │  │ Label     │  │  │  │  (画布)   │  │  │  X: 100  Y: 100             │ │
│  │  │ Slider    │  │  │  │           │  │  │  Width: 120  Height: 50     │ │
│  │  │ ...       │  │  │  └───────────┘  │  │  Background: #FFFFFF        │ │
│  │  └───────────┘  │  │                 │  │                             │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────────────────┘ │
├─────────────────────────────────────────────────────────────────────────────┤
│                              GEF Framework                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │                         MVC 架构                                      │  │
│  │  ┌─────────────┐     ┌─────────────┐     ┌─────────────┐            │  │
│  │  │   Model     │◄───►│  EditPart   │◄───►│   Figure    │            │  │
│  │  │   (模型)    │     │  (控制器)    │     │   (视图)    │            │  │
│  │  │             │     │             │     │             │            │  │
│  │  │ LvglScreen  │     │ LvglScreen  │     │ Draw2D      │            │  │
│  │  │ LvglWidget  │     │ EditPart    │     │ Figures     │            │  │
│  │  └─────────────┘     └─────────────┘     └─────────────┘            │  │
│  │         │                   │                   │                    │  │
│  │         │PropertyChange     │refreshVisuals()   │repaint()          │  │
│  │         └───────────────────┴───────────────────┘                    │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
├─────────────────────────────────────────────────────────────────────────────┤
│                            EMF / Persistence                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │  LvglXmlSerializer                                                    │  │
│  │  ├── save(LvglScreen, OutputStream)  // 保存模型到 XML               │  │
│  │  └── load(InputStream) : LvglScreen  // 从 XML 加载模型              │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
├─────────────────────────────────────────────────────────────────────────────┤
│                          Code Generation                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │  LvglCodeGenerator                                                    │  │
│  │  ├── generateHeader() : String   // 生成 .h 头文件                   │  │
│  │  └── generateSource() : String   // 生成 .c 源文件                   │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## GEF MVC 架构

GEF 采用经典的 MVC (Model-View-Controller) 架构，本项目的实现如下：

### Model (模型层) - `model` 包

```
┌─────────────────────────────────────────────────────────────────┐
│                        ModelElement                              │
│  (所有模型元素的抽象基类)                                         │
│  ├── PropertyChangeSupport  // 属性变更通知支持                  │
│  ├── addPropertyChangeListener()                                │
│  ├── removePropertyChangeListener()                             │
│  └── firePropertyChange()                                       │
└─────────────────────────────────────────────────────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
                ▼               ▼               ▼
┌───────────────────┐ ┌───────────────┐ ┌───────────────────┐
│    LvglScreen     │ │  LvglWidget   │ │    Connection     │
│   (屏幕/画布)      │ │  (控件)       │ │    (连接线)       │
│                   │ │               │ │                   │
│ - name: String    │ │ - name        │ │ - source          │
│ - width: int      │ │ - widgetType  │ │ - target          │
│ - height: int     │ │ - bounds      │ │ - bendpoints      │
│ - bgColor: int    │ │ - text        │ │                   │
│ - widgets: List   │ │ - bgColor     │ │                   │
│                   │ │ - children    │ │                   │
└───────────────────┘ └───────────────┘ └───────────────────┘
```

**关键类说明：**

| 类名 | 说明 |
|------|------|
| `ModelElement` | 抽象基类，提供 PropertyChangeSupport，用于通知 EditPart 模型变化 |
| `LvglScreen` | 表示一个 LVGL 屏幕，是控件的容器，定义屏幕尺寸和背景色 |
| `LvglWidget` | 表示一个 LVGL 控件，支持多种类型（按钮、标签、滑块等），可嵌套子控件 |
| `Connection` | 表示控件之间的连接线，用于事件绑定等场景 |

### View (视图层) - `figure` 包

```
┌─────────────────────────────────────────────────────────────────┐
│                    Draw2D Figure 层次结构                        │
│                                                                 │
│  org.eclipse.draw2d.Figure                                      │
│           │                                                     │
│           ├── LvglWidgetFigure                                  │
│           │   (控件图形，绘制各种 LVGL 控件外观)                  │
│           │   ├── drawButton()    // 绘制按钮                   │
│           │   ├── drawLabel()     // 绘制标签                   │
│           │   ├── drawSlider()    // 绘制滑块                   │
│           │   ├── drawSwitch()    // 绘制开关                   │
│           │   ├── drawCheckbox()  // 绘制复选框                 │
│           │   └── ...                                           │
│           │                                                     │
│           └── LvglContainerFigure                               │
│               (容器图形，支持子控件布局)                          │
│                                                                 │
│  org.eclipse.draw2d.PolylineConnection                          │
│           │                                                     │
│           └── ConnectionFigure                                  │
│               (连接线图形)                                       │
└─────────────────────────────────────────────────────────────────┘
```

**关键类说明：**

| 类名 | 说明 |
|------|------|
| `LvglWidgetFigure` | 绘制各种 LVGL 控件的可视化外观，根据 widgetType 绘制不同样式 |
| `LvglContainerFigure` | 支持子控件的容器图形，用于嵌套布局 |
| `ConnectionFigure` | 绘制控件之间的连接线 |

### Controller (控制器层) - `editpart` 包

```
┌─────────────────────────────────────────────────────────────────┐
│                   EditPart 继承层次                              │
│                                                                 │
│  AbstractGraphicalEditPart                                      │
│           │                                                     │
│           ├── LvglScreenEditPart                                │
│           │   ├── createFigure()         // 创建屏幕图形        │
│           │   ├── createEditPolicies()   // 安装编辑策略        │
│           │   ├── getModelChildren()     // 获取子控件          │
│           │   └── propertyChange()       // 响应模型变化        │
│           │                                                     │
│           ├── LvglWidgetEditPart                                │
│           │   ├── createFigure()         // 创建控件图形        │
│           │   ├── refreshVisuals()       // 刷新视图            │
│           │   └── getAdapter()           // 适配属性视图        │
│           │                                                     │
│           └── LvglContainerEditPart                             │
│               └── getModelChildren()     // 获取嵌套子控件       │
│                                                                 │
│  AbstractConnectionEditPart                                     │
│           │                                                     │
│           └── ConnectionEditPart                                │
│               └── createFigure()         // 创建连接线图形      │
└─────────────────────────────────────────────────────────────────┘
```

**关键类说明：**

| 类名 | 说明 |
|------|------|
| `LvglScreenEditPart` | 屏幕的控制器，管理屏幕上所有控件的创建和布局 |
| `LvglWidgetEditPart` | 控件的控制器，监听模型变化并刷新视图，提供属性视图适配器 |
| `LvglContainerEditPart` | 容器控件的控制器，支持嵌套子控件 |
| `LvglEditPartFactory` | 工厂类，根据模型类型创建对应的 EditPart |

### EditPolicy (编辑策略) - `editpart` 包

```
┌─────────────────────────────────────────────────────────────────┐
│                     EditPolicy 策略                              │
│  (定义 EditPart 支持的编辑操作)                                   │
│                                                                 │
│  LvglScreenLayoutEditPolicy                                     │
│  ├── getCreateCommand()    // 创建控件命令                       │
│  └── getMoveChildCommand() // 移动控件命令                       │
│                                                                 │
│  LvglWidgetEditPolicy                                           │
│  └── getDeleteCommand()    // 删除控件命令                       │
│                                                                 │
│  LvglContainerLayoutEditPolicy                                  │
│  └── getCreateCommand()    // 在容器内创建控件                   │
│                                                                 │
│  ClassNodeGraphicalNodeEditPolicy                               │
│  └── getConnectionCreateCommand() // 创建连接线                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## EMF 数据模型

本项目使用简化的 EMF 概念进行数据建模和序列化：

```
┌─────────────────────────────────────────────────────────────────┐
│                    数据持久化流程                                │
│                                                                 │
│  ┌─────────────┐                       ┌─────────────────────┐ │
│  │ LvglScreen  │                       │    XML 文件         │ │
│  │ (内存模型)   │                       │    (.gefxml)       │ │
│  │             │                       │                     │ │
│  │  widgets[]  │   LvglXmlSerializer   │  <screen>           │ │
│  │  ├─ Button  │ ◄──────────────────►  │    <widget>         │ │
│  │  ├─ Label   │     save() / load()   │      <name>         │ │
│  │  └─ Slider  │                       │      <type>         │ │
│  │             │                       │      <bounds>       │ │
│  └─────────────┘                       │    </widget>        │ │
│                                        │  </screen>          │ │
│                                        └─────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### XML 序列化格式示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<screen name="main_screen" width="480" height="320" bgColor="16777215">
    <widget name="btn_ok" type="BUTTON" x="100" y="100" 
            width="120" height="50" text="OK" bgColor="16777215"/>
    <widget name="lbl_title" type="LABEL" x="100" y="30" 
            width="200" height="40" text="LVGL UI Designer"/>
</screen>
```

---

## 代码生成流程

```
┌─────────────────────────────────────────────────────────────────┐
│                      代码生成流程                                │
│                                                                 │
│   用户操作                                                       │
│      │                                                          │
│      ▼                                                          │
│  ┌─────────────────┐                                            │
│  │ 点击"生成代码"   │  (工具栏按钮或右键菜单)                     │
│  │ 按钮            │                                            │
│  └────────┬────────┘                                            │
│           │                                                     │
│           ▼                                                     │
│  ┌─────────────────┐                                            │
│  │ GenerateCode    │  handler 包                                │
│  │ Handler         │  处理命令，获取当前编辑器的 LvglScreen      │
│  └────────┬────────┘                                            │
│           │                                                     │
│           ▼                                                     │
│  ┌─────────────────┐                                            │
│  │ LvglCode        │  generator 包                              │
│  │ Generator       │  遍历屏幕中的所有控件，生成 C 代码          │
│  │                 │                                            │
│  │ ┌─────────────┐ │                                            │
│  │ │ generate    │ │  生成头文件声明                             │
│  │ │ Header()    │ │  - 控件变量声明 (extern lv_obj_t*)         │
│  │ │             │ │  - 函数声明 (create/delete)                │
│  │ └─────────────┘ │                                            │
│  │                 │                                            │
│  │ ┌─────────────┐ │                                            │
│  │ │ generate    │ │  生成源文件实现                             │
│  │ │ Source()    │ │  - 控件变量定义                            │
│  │ │             │ │  - create 函数实现                         │
│  │ │             │ │    - 创建控件 (lv_xxx_create)              │
│  │ │             │ │    - 设置位置大小                          │
│  │ │             │ │    - 设置属性 (文本、颜色等)                │
│  │ │             │ │  - delete 函数实现                         │
│  │ └─────────────┘ │                                            │
│  └────────┬────────┘                                            │
│           │                                                     │
│           ▼                                                     │
│  ┌─────────────────┐                                            │
│  │ 输出文件        │                                            │
│  │ - xxx.h        │  头文件                                     │
│  │ - xxx.c        │  源文件                                     │
│  └─────────────────┘                                            │
└─────────────────────────────────────────────────────────────────┘
```

### 生成的代码示例

**头文件 (screen.h):**
```c
#ifndef SCREEN_H
#define SCREEN_H

#include "lvgl.h"

#ifdef __cplusplus
extern "C" {
#endif

extern lv_obj_t *screen;
extern lv_obj_t *btn_ok;
extern lv_obj_t *lbl_title;

void screen_create(void);
void screen_delete(void);

#ifdef __cplusplus
}
#endif

#endif /* SCREEN_H */
```

**源文件 (screen.c):**
```c
#include "screen.h"

lv_obj_t *screen = NULL;
lv_obj_t *btn_ok = NULL;
lv_obj_t *lbl_title = NULL;

void screen_create(void) {
    screen = lv_obj_create(NULL);
    lv_obj_set_size(screen, 480, 320);
    lv_obj_set_style_bg_color(screen, lv_color_hex(0xFFFFFF), LV_PART_MAIN);

    btn_ok = lv_btn_create(screen);
    lv_obj_set_pos(btn_ok, 100, 100);
    lv_obj_set_size(btn_ok, 120, 50);
    {
        lv_obj_t *label = lv_label_create(btn_ok);
        lv_label_set_text(label, "OK");
        lv_obj_center(label);
    }

    lbl_title = lv_label_create(screen);
    lv_obj_set_pos(lbl_title, 100, 30);
    lv_obj_set_size(lbl_title, 200, 40);
    lv_label_set_text(lbl_title, "LVGL UI Designer");
}

void screen_delete(void) {
    if (screen != NULL) {
        lv_obj_del(screen);
        screen = NULL;
    }
}
```

---

## 核心类关系图

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                             核心类关系                                       │
│                                                                             │
│  editor 包                                                                  │
│  ┌─────────────────────────────────────┐                                   │
│  │        DiagramMultiPageEditor       │                                   │
│  │  (多页编辑器：图形视图 + XML 视图)    │                                   │
│  │  ┌────────────────────────────────┐ │                                   │
│  │  │        DiagramEditor           │ │                                   │
│  │  │  (GEF 图形编辑器)               │ │                                   │
│  │  │  - GraphicalViewer             │ │                                   │
│  │  │  - PaletteRoot                 │ │                                   │
│  │  │  - CommandStack                │ │                                   │
│  │  └────────────────────────────────┘ │                                   │
│  └─────────────────────────────────────┘                                   │
│                    │                                                        │
│                    │ uses                                                   │
│                    ▼                                                        │
│  ┌─────────────────────────────────────┐                                   │
│  │     LvglEditPartFactory             │  editpart 包                      │
│  │  (EditPart 工厂)                    │                                   │
│  │  createEditPart(model)              │                                   │
│  │    │                                │                                   │
│  │    ├─► LvglScreenEditPart          │                                   │
│  │    ├─► LvglWidgetEditPart          │                                   │
│  │    ├─► LvglContainerEditPart       │                                   │
│  │    └─► ConnectionEditPart          │                                   │
│  └─────────────────────────────────────┘                                   │
│                    │                                                        │
│                    │ creates                                                │
│                    ▼                                                        │
│  ┌─────────────────────────────────────┐                                   │
│  │        LvglWidgetFigure             │  figure 包                        │
│  │  (控件图形)                         │                                   │
│  │  - setWidgetType()                 │                                   │
│  │  - setWidgetText()                 │                                   │
│  │  - paintFigure()                   │                                   │
│  └─────────────────────────────────────┘                                   │
│                    ▲                                                        │
│                    │ observes                                               │
│                    │                                                        │
│  ┌─────────────────────────────────────┐                                   │
│  │          LvglWidget                 │  model 包                         │
│  │  (控件模型)                         │                                   │
│  │  - name, widgetType, bounds, text  │                                   │
│  │  - PropertyChangeSupport           │                                   │
│  └─────────────────────────────────────┘                                   │
│                    │                                                        │
│                    │ serialized by                                          │
│                    ▼                                                        │
│  ┌─────────────────────────────────────┐                                   │
│  │       LvglXmlSerializer             │  model 包                         │
│  │  (XML 序列化器)                     │                                   │
│  │  - save(LvglScreen, OutputStream)  │                                   │
│  │  - load(InputStream)               │                                   │
│  └─────────────────────────────────────┘                                   │
│                                                                             │
│  ┌─────────────────────────────────────┐                                   │
│  │       LvglCodeGenerator             │  generator 包                     │
│  │  (代码生成器)                       │                                   │
│  │  - generateHeader()                │                                   │
│  │  - generateSource()                │                                   │
│  └─────────────────────────────────────┘                                   │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 数据流说明

### 1. 创建控件流程

```
用户从调色板拖拽控件 → LvglPaletteFactory.LvglWidgetFactory.getNewObject()
                                    ↓
                        创建 LvglWidget 实例
                                    ↓
                        LvglScreenLayoutEditPolicy.getCreateCommand()
                                    ↓
                        LvglWidgetCreateCommand.execute()
                                    ↓
                        screen.addWidget(widget)
                                    ↓
                        firePropertyChange(PROPERTY_ADD)
                                    ↓
                        LvglScreenEditPart.propertyChange()
                                    ↓
                        refreshChildren() → 创建新的 LvglWidgetEditPart
                                    ↓
                        createFigure() → 创建 LvglWidgetFigure
                                    ↓
                        界面显示新控件
```

### 2. 属性编辑流程

```
用户在属性视图修改属性 → LvglWidgetPropertySource.setPropertyValue()
                                    ↓
                        widget.setXxx(value)
                                    ↓
                        firePropertyChange("xxx", oldValue, newValue)
                                    ↓
                        LvglWidgetEditPart.propertyChange()
                                    ↓
                        refreshVisuals()
                                    ↓
                        figure.setWidgetXxx(value)
                                    ↓
                        figure.repaint()
                                    ↓
                        界面更新控件外观
```

### 3. 保存流程

```
用户按 Ctrl+S → DiagramEditor.doSave()
                        ↓
                LvglXmlSerializer.save(screen, outputStream)
                        ↓
                遍历 screen.getWidgets()
                        ↓
                生成 XML 结构
                        ↓
                写入 .gefxml 文件
```

### 4. 代码生成流程

```
用户点击生成按钮 → GenerateCodeHandler.execute()
                        ↓
                获取当前编辑器的 LvglScreen
                        ↓
                new LvglCodeGenerator(screen)
                        ↓
                generateHeader() / generateSource()
                        ↓
                遍历控件，生成 C 代码
                        ↓
                写入 .h 和 .c 文件
```

---

## 扩展指南

### 添加新控件类型

1. **model 包**: 在 `LvglWidget.WidgetType` 枚举中添加新类型
2. **figure 包**: 在 `LvglWidgetFigure` 中添加绘制方法
3. **palette 包**: 在 `LvglPaletteFactory` 中添加调色板条目
4. **generator 包**: 在 `LvglCodeGenerator` 中添加代码生成逻辑
5. **property 包**: 如需特殊属性，在 `widget` 子包中添加属性提供者

### 添加新的编辑操作

1. **command 包**: 创建新的 Command 类
2. **editpart 包**: 在相关 EditPolicy 中返回该 Command
3. 确保 Command 实现 `execute()` 和 `undo()` 方法

---

## 参考资料

- [GEF (Graphical Editing Framework)](https://eclipse.dev/gef/)
- [EMF (Eclipse Modeling Framework)](https://eclipse.dev/modeling/emf/)
- [Draw2D](https://eclipse.dev/gef/draw2d/)
- [LVGL (Light and Versatile Graphics Library)](https://lvgl.io/)

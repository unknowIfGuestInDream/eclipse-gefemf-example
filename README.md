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
│   └── com.tlcsdm.eclipse.gefemf.demo.product/
└── target-platform/                  # 目标平台配置
```

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

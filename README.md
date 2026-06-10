# CoreMall- 分布式电商项目

> 基于 Spring Cloud Alibaba 的分布式微服务电商平台项目

## 项目简介

这是一套完整的 B2C 电商平台，采用前后端分离 + 微服务架构。
本仓库为**基础篇**实现，聚焦微服务环境搭建、分布式组件整合，以及商品服务核心业务（三级分类、品牌管理、SPU/SKU、平台属性）的开发。

## 技术栈

### 后端核心
| 技术 | 用途 |
|------|------|
| Spring Boot 2.x | 基础框架 |
| Spring Cloud Alibaba | 微服务全家桶 |
| Nacos | 服务注册与发现 + 配置中心 |
| OpenFeign | 服务间远程调用 |
| Spring Cloud Gateway | API 网关（统一入口、路由、鉴权） |
| MyBatis-Plus | ORM 框架，简化 CRUD |
| Spring Cloud LoadBalancer | 客户端负载均衡 |

### 存储与中间件
| 技术 | 用途 |
|------|------|
| MySQL 8.0 | 主数据库 |
| Redis | 缓存 |
| 阿里云 OSS | 商品图片对象存储 |

### 前端与工具
| 技术 | 用途 |
|------|------|
| Vue + Element UI | 后台管理界面 |
| renren-fast | 后台管理脚手架（快速开发） |
| renren-generator | 代码生成器（自动生成 CRUD） |
| Nginx | 反向代理、域名映射 |

## 微服务模块

```
gulimall
├── coremall-common          # 公共模块（工具类、统一响应、异常）
├── coremall-gateway         # API 网关
├── coremall-product         # 商品服务（核心）
├── coremall-coupon          # 优惠券服务
├── coremall-member          # 会员服务
├── coremall-order           # 订单服务
├── coremall-ware            # 库存服务
└── renren-fast              # 后台管理系统
```

## 核心功能（基础篇）

- **商品三级分类**：树形结构的分类管理（CRUD + 拖拽排序 + 批量操作）
- **品牌管理**：品牌的增删改查 + 阿里云 OSS 图片上传
- **品牌分类关联**：品牌与分类的多对多关联维护
- **平台属性**：属性分组、规格参数、销售属性管理
- **SPU/SKU 管理**：标准产品单元与库存量单元的数据建模

## 架构特点

```
前端(Vue) → Nginx → Gateway(网关) → 各微服务
                                      ↓
                              Nacos(注册+配置中心)
                                      ↓
                          MySQL / Redis / OSS
```

- **服务治理**：所有微服务注册到 Nacos，通过 OpenFeign 互相调用
- **统一配置**：配置集中存放在 Nacos 配置中心，支持动态刷新
- **统一入口**：Gateway 作为唯一入口，负责路由转发和跨域处理
- **代码生成**：用 renren-generator 自动生成基础 CRUD，提升开发效率

## 环境要求

- JDK 1.8
- Maven 3.6+
- MySQL 8.0
- Redis
- Nacos Server
- Node.js（前端）

## 快速启动

```bash
# 1. 启动 Nacos
sh startup.sh -m standalone

# 2. 启动各微服务（IDEA 中分别启动）
# coremall-gateway / coremall-product ...

# 3. 启动后台管理前端
cd renren-fast-vue
npm install
npm run dev
```

## 学习收获

通过本项目掌握：
- Spring Cloud Alibaba 微服务体系的搭建与整合
- Nacos 注册中心与配置中心的使用
- 网关 Gateway 的路由配置与跨域处理
- OpenFeign 服务间调用
- MyBatis-Plus 的高效 CRUD 与分页
- 前后端分离开发模式

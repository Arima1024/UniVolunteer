# UniVolunteer 高校志愿联盟平台

一个基于 Spring Boot + Spring Cloud Alibaba 构建的多模块志愿服务平台，旨在为高校提供统一、高效、智能的志愿活动发布、管理与统计解决方案。

---

## 🧩 技术栈

- Spring Boot 3.4.4
- Spring Cloud Alibaba
- Spring Security + JWT
- MyBatis-Plus
- MySQL + Redis
- Maven 多模块管理
- Feign、OpenAPI、Nacos、Gateway

---

## 📦 模块说明

| 模块名 | 说明 |
|--------|------|
| `common-lib` | 公共库模块（工具类、DTO、Feign 接口等） |
| `service-user` | 用户与权限服务模块 |
| `service-activity` | 活动管理服务模块 |
| `service-registration` | 报名与审批服务模块 |
| `service-record` | 志愿记录与认证服务模块 |
| `service-comment` | 评论与反馈服务模块 |
| `service-recommendation` | 活动推荐与搜索服务模块 |
| `service-statistics` | 数据统计服务模块 |
| `service-checkin` | 移动签到服务模块 |
| `service-log` | 日志审计与追踪服务模块 |
| `service-admin` | 平台配置与运维服务模块 |
| `uv-gateway` | API 网关模块，统一入口，鉴权路由 |

---

## 🚀 快速启动

1. 克隆项目
```bash
git clone https://github.com/MYX24/UniVolunteer.git
cd UniVolunteer
```

2. 配置数据库与 Redis（application.yml）

3. 启动 Nacos、MySQL、Redis 环境

4. 按顺序启动核心模块（推荐先启动 user、activity、gateway）

---

## 📌 功能特性

- ✅ 志愿活动在线发布与审核
- ✅ 志愿者报名、签到、记录归档
- ✅ 后台权限管理与操作日志审计
- ✅ 支持评论、推荐、统计等辅助功能
- ✅ 基于网关统一鉴权与限流

---

## 📚 项目背景

本系统面向高校志愿服务场景，整合志愿者管理、活动调度、数据分析等功能，提升志愿活动的组织效率与用户体验。

---

## 🔧 TODO

- [ ] 接入前端界面（Vue3）
- [ ] 支持短信/邮箱通知
- [ ] 容器化部署（Docker + Nginx）

---

## 🧑‍💻 开发者

如有任何问题欢迎联系开发者：`your_email@example.com`

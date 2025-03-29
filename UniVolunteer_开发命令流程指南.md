
# 🛠️ UniVolunteer 开发者 Git 操作流程指南

适用于每位参与 UniVolunteer 项目的开发者，完整记录从拉取项目、创建分支、开发、提交、合并到继续开发的命令流程。

---

## 📥 1. 第一次参与项目（首次拉取代码）

```bash
git clone https://github.com/MYX24/UniVolunteer.git
cd UniVolunteer
git fetch origin
```

---

## 🌱 2. 创建自己的功能开发分支（基于 dev）

```bash
# 确保本地有 dev 分支
git checkout dev
git pull origin dev

# 创建功能分支（替换自己的模块名和缩写）
git checkout -b feature/your-module-yourname
git push origin feature/your-module-yourname
```

---

## 💻 3. 每日开发流程

```bash
# 1. 确保 dev 是最新的
git checkout dev
git pull origin dev

# 2. 回到自己的功能分支
git checkout feature/your-module-yourname

# 3. 合并最新 dev（保持同步，避免冲突）
git merge dev

# 4. 编码并提交
git add .
git commit -m "feat: 添加xxx功能"
git push
```

---

## 🔁 4. 功能开发完成后合并到 dev

```bash
# 切换到 dev 分支并拉取最新
git checkout dev
git pull origin dev

# 合并你的功能分支
git merge feature/your-module-yourname

# 推送合并结果到远程 dev
git push origin dev
```

---

## 🔄 5. 合并后继续开发？切回你的分支继续写

```bash
git checkout feature/your-module-yourname

# 推荐合并一次 dev（保持一致）
git pull origin dev
```

---

## ✅ 分支命名建议

| 类型 | 示例 |
|------|------|
| 用户模块 | `feature/service-user-myx` |
| 活动模块 | `feature/activity-xh` |
| 网关模块 | `feature/gateway-my` |
| 公共库重构 | `refactor/common-lib-ls` |

---

欢迎使用本指南规范团队协作流程，配合 GitHub PR 操作更佳。如有补充需求请联系组长更新。

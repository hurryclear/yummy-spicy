# Most important tech stack

## A. 分层架构与可维护性 Layered architecture and Maintainability

- Controller / Service / Repository(Mapper) 分层
	- Controller: 接收 HTTP 请求, 参数校验, 调用 Service, 返回 HTTP 响应
	- Service: 业务规则, 事务, 状态流转, 跨表操作
	- Repository/Mapper: 和数据库打交道, CRUD, 查询优化
- DTO/VO 的使用（不要直接暴露 Entity）
- 统一返回格式 (Response wrapper)
- 全局/统一异常处理（@ControllerAdvice / 自定义异常 / 错误码）

> 面试要能讲：为什么要分层？为什么要 DTO？异常为什么要统一？

![image-20251231455250.png](yummy-backend/Notes/assets/Note2.assets/image-20251231455250.png)
### Why DTO not Entity?
- Security（avoid exposing of fields 避免暴露字段）
- Decoupling API from DB
- Flexibility for future changes
### Controller
- Controller should only handle HTTP concerns
- Business logic belongs to service layer
- Improves testability and reuse
## B. 认证与授权 Authentication and Authorization

- JWT 登录流程（access token 的生成、校验、过期处理）
- 权限控制（角色：Admin/Employee/Customer；接口级鉴权）
- 密码加密（BCrypt / 盐值）

> 面试要能讲：JWT vs Session 各自适用场景；token 放哪里；如何避免泄露。

## C. 数据库与事务 Database and Transaction

- MySQL 表设计（用户、菜品、订单、订单明细…）
- 事务（下单写多表、库存/销量更新的原子性）
- 索引与查询优化基本意识（订单列表分页、时间范围查询）
- PageHelper

> 面试要能讲：一次下单为什么需要事务？怎么避免“半成功”？

## D. 缓存与性能 Caching and Performance（如果你用到 Redis）

- 缓存菜单/分类等读多写少数据
- 缓存一致性策略（更新时删缓存 or 延迟双删）
- token 黑名单/验证码/限流（可选加分）

> 面试要能讲：哪些数据适合缓存？缓存失效策略怎么做？

## E. 并发与一致性 Concurrency and Consistency（很加分）

- “重复下单 / 重复支付回调” 的幂等性
- 乐观锁/版本号（如果你做了）
- 订单状态机（状态流转的限制）

> 这一块是 junior 里非常亮眼的点：你不用做得很复杂，但要能说清楚“风险在哪里、怎么处理”。

## F. 工程化 Project engineering（德国特别看重）

- 日志（结构化日志、关键链路 log）
- 配置管理（dev/test/prod profiles，环境变量）
- API 文档（OpenAPI/Swagger）
- 测试（最少：service 层单测 + 1~2 个集成测试）
- Docker（能 docker-compose 起 MySQL/Redis 就很强）

# Interview relevant

## ① 你做了什么“自己的决策”

他们会问：
•	你自己改了哪些？为什么这么改？
•	遇到过什么 bug？怎么定位的？
•	如果重新做一次，你会怎么重构？

✅ 你一定要准备 2–3 个“我做的改动”，哪怕很小，但要能讲清楚取舍。

## ② 认证鉴权与安全

常见追问：
•	JWT 的结构是什么？怎么校验？过期怎么办？
•	token 存浏览器哪里？XSS 风险怎么考虑？
•	密码怎么存？为什么不能明文/可逆加密？

## ③ 数据一致性与事务

追问方向：
•	下单涉及哪些表？为什么要事务？
•	订单取消/支付失败怎么处理？
•	怎么保证“状态不会乱跳”（状态机/校验）

## ④ 数据库设计与查询

追问方向：
•	订单表和订单明细表怎么关联？
•	为什么要分页？怎么实现？
•	你给哪些字段建了索引？为什么？

## ⑤ 代码质量与可测试性

追问方向：
•	你的 service 怎么测试？
•	你如何做异常处理？
•	你用过什么 lint/format/CI 吗？（不会也没事，但有就是加分）

# 最推荐你做的 3 个“德国求职加分改造”（投入小，回报大）

你不需要全部做完，但做其中 1–2 个就能明显提升。
1.	Docker Compose 一键启动 MySQL/Redis
2.	OpenAPI/Swagger 文档 + Postman 集合
3.	1~2 个集成测试（Testcontainers 更加分，但普通也行）
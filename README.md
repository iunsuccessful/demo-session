## 可序列化的 Session 实现

### shiro 方式实现

步骤：

1. 在 web.xml 里面使用 Spring 代理过滤器，激活 Shiro 功能。
2. 在 spring-shiro.xml 中实现 shiroFilter，设置 自己的 securityManager。
3. 把 SessionManager 注入 securityManager 中。

### Spring Session 方式实现

1. 在 web.xml 设置代理过滤器
2. 实例化 SpringHttpSessionConfiguration
3. 实现 SessionRepository
4. 实现 ExpiringSession

注：如果使用 Redis, Spring 已帮我们实现好 Repository 与 Session，直接注入 RedisHttpSessionConfiguration 即可。

#### 原理

通过 Filter 替换掉了原来的 Request，改写了 Request.getSession() 方法，直接调用了我们定义的 Session.

### 分布式 Session 难点

1. SessionID 生成
2. Session 管理（储存与过期）
3. 与容器 Session 处理问题（兼容 or 不管）
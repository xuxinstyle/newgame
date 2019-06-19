### newgame小游戏服务器项目使用说明
#### 数据库相关
* mysql
* 用户名：root
* 密码：root
* 持久层框架：mybatis
* 数据库名：newgame

#### 服务器和客户端启动
* 服务器端口：配置在server.properties中，默认8888
* 服务端执行start的main方法
* 客户端执行start中的main方法

#### 系统架构
* 网络通信 netty（已完成）
* 协议包 messagePack（已完成）
* 持久化 mybatis+mysql（待实现缓存）
* 日志 slf4j（已完成，需要完善）
* 容器管理 Spring（已完成，需要完善）
* api java 8（已使用）
* 线程池 （待实现）
* 实现静态资源映射（已实现，可使用，需要优化）

#### 目录结构说明
* socket是客户端和服务端通信相关的
* resource是静态资源映射相关的
    @Analyze注解所对应的方法必须是public权限
* game里面是业务相关的
* db准备放缓存相关
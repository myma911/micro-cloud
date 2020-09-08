章节 | 标题 | 完成功能 技术点
1 系统设计与工程搭建 | 需求分析 表结构设计 前后端接口文档 环境搭建  基础微服务  SpringBoot 、SpringMVC 、SpringDataJPA、 Postman
2 查询与缓存 |基础微服务、招聘微服务、活动微服务、问答微服务、文章微服务 SpringDataRedis 、SpringDataJPA
3 文档型数据库MongoDB 吐槽微服务、文章评论   MongoDB 、SpringDataMongoDB
4 分布式搜索引擎 |ElasticSearch 搜索微服务 | Elasticsearch 、SpringDataElasticsearch
5 消息中间件RabbitMQ| 用户微服务（注册） 短信微服务| RabbitMQ 、 阿里云通信
6 密码加密与微服务鉴权JWT| 密码加密 微服务鉴权| BCrypt 、 JWT
7 SpringCloud之初入江湖 |交友微服务 微服务间调用 | Eureka、 Feign
8 SpringCloud之一统天下 | 微服务熔断 微服务网关 码云 配置管理中心 | Hystrix、 Zuul 、 码云、 Spring Cloud Config、Spring Cloud Bus
9 微服务容器部署与持续集成 | 微服务容器部署 版本控制Git+Gogs 微服务持续集成 |Gogs Docker私有仓库 Jenkins
10 容器管理与弹性扩容 |容器管理，弹性扩容 |Rancher容器部署管理 Grafana 实时监控


# micro-cloud
采用目前主流的微服务系统架构
SpringBoot+SpringCloud+SpringData进行开发，前端技术采用Vue.js 。系统整体分为 三大部分：微服务、网站前台、网站管理后台。功能模块包括文章、问答、招聘、活动、吐槽、交友、用户中心、搜索中心及第三方登陆等。
项目融合了Docker容器化部署、第三方登陆、SpringBoot、 SpringCloud、SpringData 、人工智能、爬虫、RabbitMQ等技术。  


# 技术架构
后端架构：SpringBoot+SpringCloud+SpringData+StringMVC（Spring全家桶） 
前端架构：Node.js+Vue.js+ElementUI+NUXT 



## 表结构分析
我们这里采用的分库分表设计，每个业务模块为1个独立的数据库。
micro_article  文章
micro_base    基础
micro_friend   交友
micro_gathering 活动
micro_qa 问答
micro_recruit 招聘
micro_user   用户
micro_spit   吐槽





## 项目结构
| 模块 | 名称 | 端口 | |
| --- | --- | --- | --- |
| `micro-ai` | 人工智能微服务 |  | |
| `micro-article` | 文章 HTTP 服务 | HTTP 9004 |
| `micro-base` | 基础 HTTP 服务 | HTTP 9001 |
| `micro-common` | 公共工具类 |  |
| `micro-config` | 配置中心 |  |
| `micro-doc` | 文档说明 |  |
| `micro-eureka` | 服务注册中心服务 | http://127.0.0.1:6868/eureka/ |
| `micro-friend` | 朋友 HTTP 服务 | HTTP 9010 |
| `micro-gathering` | 活动 HTTP 服务 | HTTP 9005 |
| `micro-manager` | 后台微服务网关 | HTTP 18086 |
| `micro-spider` | 文章爬虫微服务 |  |
| `micro-qa` | 问答 HTTP 服务 | HTTP 9003 |
| `micro-recruit` | 招聘 HTTP 服务 | HTTP 9002 |
| `micro-search` | 搜索 HTTP 服务 | HTTP 9007 |
| `micro-sms` | 短信服务 | 9009 |
| `micro-spit` | 吐槽 HTTP 服务 | HTTP 9006 |
| `micro-user` | 用户 HTTP 服务 | HTTP 9008 |




-------
## 技术栈

### 后端

| 框架 | 说明 |  版本 |
| --- | --- | --- |
| [Spring boot] | 应用开发框架 |   2.2.9.RELEASE |
| [Spring cloud] | 应用开发框架 |   Hoxton.RELEASE |
| [MySQL](https://www.mysql.com/cn/) | 数据库服务器 | 5.6 |
| [JPA]| 数据持久层框架 | 2.2.9.RELEASE |
| [Redis](https://redis.io/) | key-value 数据库 |  |
| [RedisTemplate] | Redis 客户端 | 2.2.9.RELEASE |
| [Elasticsearch](https://www.elastic.co/cn/) | 分布式搜索引擎 | 7.4.2 |
###| [Elasticsearch](https://www.elastic.co/cn/) | 分布式搜索引擎 | 5.6.8 |
| [mongodb] | mongodb数据库 | 4.2|
| [logstash] | 日志搜索处理框架 | 5.6.8
| [rabbitmq] | 消息中间件 | |







## 启动说明
1. 先启动micro_eureka
2. 启动micro_user


## Swagger 
一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。总体目标是使客户端和文件系统作为服务器以同样的速度来更新。作用：1.接口的文档在线自动生成；2.接口功能测试。



## API文档
前后端约定的返回码列表：

状态描述	返回码
成功	20000
失败	20001
用户名密码错误	20002
权限不足	20003
远程调用失败	20004
重复操作	20005
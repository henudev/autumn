# Autumn[秋天]

[TOC]

## 待办

- [ ] 搭建gateway
- [ ] 权限设计
- [ ] 每个模块README文件更新截至3.8号

## 系统设计

### 模块介绍

1. base-security----认证授权
2. base-admin----系统基础功能
3. base-gateway----网关服务
4. base-commons----通用服务
5. autumn-data----数据处理子工程
6. mid-es----ES集成
7. mid-redis----redis集成

### base-security----认证授权

**授权方案**

OAuth2是一种授权框架，而JWT是一种认证协议。
SpringCloud OAuth2 是 SpringCloud 体系对OAuth2协议的实现，可以用来做多个微服务的统一认证(验证身份合法性)授权(验证权限)。通过向OAuth2服务 (统一认证授权服务)发送某个类型的grant_type进行集中认证和授权，从而获得 access_token(访问令牌)，而这个token是受其他微服务信任的。

OAuth2颁发Token授权方式：

- 授权码
- 密码
- 隐藏式
- 客户端凭证

 流程如下：

![image-20220407175425812](E:\11111qinzheng\git\henu\autumn\README.assets\image-20220407175425812.png)


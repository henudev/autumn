# Autumn[秋天]

[TOC]



## 待办

- [ ] 搭建gateway
- [ ] 权限设计

## 系统设计

### 权限方案

OAuth2是一种授权框架，而JWT是一种认证协议。

**用户认证:**

采用OAuth2认证框架：

- 资源拥有者(Resource Owner)
- 资源服务器(Resource Server)
- 授权服务器(Authorization Server)
- 客户端(Client)

OAuth2包含4种授权模式：

- 授权码（认证码）模式 （Authorization code)
- 简化（隐形）模式 (Impilict
- 用户名密码模式 (Resource Owner Password Credential)
- 客户端模式 (Client Credential)

OAuth2的运行流程如下图：

```text
+--------+                               +---------------+
|        |--(A)- Authorization Request ->|   Resource    |
|        |                               |     Owner     |
|        |<-(B)-- Authorization Grant ---|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(C)-- Authorization Grant -->| Authorization |
| Client |                               |     Server    |
|        |<-(D)----- Access Token -------|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(E)----- Access Token ------>|    Resource   |
|        |                               |     Server    |
|        |<-(F)--- Protected Resource ---|               |
+--------+                               +---------------+
```

**JWT认证协议**

```text
+-----------+                                     +-------------+
|           |       1-Request Authorization       |             |
|           |------------------------------------>|             |
|           |     grant_type&username&password    |             |--+
|           |                                     |Authorization|  | 2-Gen
|           |                                     |Service      |  |   JWT
|           |       3-Response Authorization      |             |<-+
|           |<------------------------------------| Private Key |
|           |    access_token / refresh_token     |             |
|           |    token_type / expire_in           |             |
|  Client   |                                     +-------------+
|           |                                 
|           |                                     +-------------+
|           |       4-Request Resource            |             |
|           |-----------------------------------> |             |
|           | Authorization: bearer Access Token  |             |--+
|           |                                     | Resource    |  | 5-Verify
|           |                                     | Service     |  |  Token
|           |       6-Response Resource           |             |<-+
|           |<----------------------------------- | Public Key  |
+-----------+                                     +-------------+
```
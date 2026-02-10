---
name: api-tester
description: 测试Spring Boot项目的RESTful API接口。支持GET、POST、PUT、DELETE等HTTP方法，可设置请求头、请求体，并展示响应结果。使用此技能时，需要提供接口URL、HTTP方法和请求参数。
---

基于当前项目的API规范，快速测试RESTful接口。

## 技术栈
- Spring Boot 3.2.2
- RESTful API
- 统一返回格式 `Result<T>`

## 项目API规范

### 请求基础路径
- 接口前缀: `/api/{模块名}`
- 示例: `/api/product/page`, `/api/category/add`

### 统一返回格式
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {...}
}
```

### 常用HTTP方法
| 方法 | 用途 | 示例 |
|------|------|------|
| GET | 查询 | GET /api/product/{id} |
| POST | 新增 | POST /api/product |
| PUT | 更新 | PUT /api/product |
| DELETE | 删除 | DELETE /api/product/{id} |

## 使用curl测试接口

### GET请求 - 查询列表
```bash
curl -X GET "http://localhost:8080/api/product/list" \
  -H "Cookie: JSESSIONID=xxx"
```

### GET请求 - 分页查询
```bash
curl -X GET "http://localhost:8080/api/product/page?pageNum=1&pageSize=10" \
  -H "Cookie: JSESSIONID=xxx"
```

### POST请求 - 新增数据
```bash
curl -X POST "http://localhost:8080/api/product" \
  -H "Content-Type: application/json" \
  -H "Cookie: JSESSIONID=xxx" \
  -d '{
    "productName": "测试商品",
    "price": 99.99,
    "stock": 100
  }'
```

### PUT请求 - 更新数据
```bash
curl -X PUT "http://localhost:8080/api/product" \
  -H "Content-Type: application/json" \
  -H "Cookie: JSESSIONID=xxx" \
  -d '{
    "id": 1,
    "productName": "更新商品名",
    "price": 199.99
  }'
```

### DELETE请求 - 删除数据
```bash
curl -X DELETE "http://localhost:8080/api/product/1" \
  -H "Cookie: JSESSIONID=xxx"
```

## 使用说明
调用此技能时，请提供以下信息：
1. **接口URL**: 如 /api/product/page
2. **HTTP方法**: GET、POST、PUT、DELETE
3. **请求参数**: Query参数或JSON请求体
4. **认证信息**: 如需要登录，提供Session或Token

## 项目主要API列表

### 用户认证
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/user/login | POST | 用户登录 |
| /api/user/logout | POST | 用户登出 |

### 商品管理
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/product/page | GET | 分页查询 |
| /api/product/{id} | GET | 根据ID查询 |
| /api/product | POST | 新增商品 |
| /api/product | PUT | 更新商品 |
| /api/product/{id} | DELETE | 删除商品 |
| /api/product/list | GET | 获取全部列表 |

### 分类管理
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/category/page | GET | 分页查询 |
| /api/category/{id} | GET | 根据ID查询 |
| /api/category | POST | 新增分类 |
| /api/category | PUT | 更新分类 |
| /api/category/{id} | DELETE | 删除分类 |
| /api/category/tree | GET | 获取分类树 |

### 品牌管理
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/brand/page | GET | 分页查询 |
| /api/brand | POST | 新增品牌 |
| /api/brand | PUT | 更新品牌 |
| /api/brand/{id} | DELETE | 删除品牌 |

### 库存管理
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/stock/page | GET | 分页查询 |
| /api/stock/in | POST | 入库 |
| /api/stock/out | POST | 出库 |
| /api/stock/records | GET | 获取库存记录 |

## 常见错误码
| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未登录或登录过期 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 注意事项
- 大部分接口需要先登录获取Session
- POST/PUT请求需要设置 `Content-Type: application/json`
- 分页参数: pageNum (页码), pageSize (每页数量)
- 删除操作支持批量删除，传递ID数组

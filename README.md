# cloud-mall
###描述:
springcloud alibaba，nacos，sentinel，redis分布式锁，rocketmq

##### 项目处于架构测试阶段，模块之间没有相关业务逻辑，待开发......
##### 对大家有帮助的话，麻烦start
### 技术栈

- nacos是注册中心和配置中心，持久化sentinel配置
- sentinel是接口监控，限流降级
- redis实现分布式锁
- dubbo实现服务之间的调用
- rocketmq事务消息
- shiro+redis实现用户单设备登录，接口鉴权



### 模块说明

- **cloud-mall**  ：整个项目的父控模块，管理整个项目的依赖版本
  - **common**：整个项目的公共模块，相关公共类放在此处
  - **order-service-api** ：order模块api接口申明模块，接口提供给order-service-core进行实现，提供给其他consumer模块进行消费
  - **order-service-core** ：具体实现order相关逻辑，并提供order相关服务
  - **stock-service-core** ：具体实现stock相关逻辑。现阶段用于整个架构功能测试
  - **ouath-service-core**：用户登录，接口鉴权模块，现在是一整个web项目，后续需要考虑抽出成公共模块



### 项目启动说明

1. 启动nacos服务，新建命名空间
2. 修改项目配置文件中的nacos相关地址及namespace地址
3. 启动sentinel-dashboard服务
4. 修改项目中配置文件中sentinel相关地址
5. 安装redis服务
6. 修改redis地址
7. 安装rocketmq服务
8. 修改redis地址
9. 启动**order-service-core**，**stock-service-core**，**ouath-service-core**



注：**ouath-service-core**现在是一个单独的web项目，后续再加入进整体项目中




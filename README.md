# 本地访问容器服务

如果server端是容器部署，本地client端从公有云上获取的server端地址就是容器地址（例如127.x.x.x），而client端是不能直接访问容器地址的，需要将容器地址替换成可以访问的地址。

所以该需求分为两步：

1、将服务端的容器地址映射成外部可访问的弹性ip

2、改造本地客户端，使其能读取配置文件中配好的ip

### 容器服务端映射步骤：

1、容器端口映射成节点端口

在应用管理页面——&gt;访问方式，添加服务，选择VPC内网访问，服务端对外暴露的是容器ip和端口，例如172.31.0.5:8080，所以首先将8080端口映射为服务所在节点的某个端口，例如30101端口。

2、节点ip和端口映射成弹性公网ip和端口

新建NET网关，添加DNAT规则，将私网节点ip和端口映射为弹性公网ip和端口。

### 本地客户端改造步骤：

本地客户端从环境SC上获取的服务端ip和端口是容器的ip和端口，例如172.31.0.5:8080，需要替换为环境上映射的弹性公网ip和端口。

具体实现可以参考[https://github.com/weichao666/netserverfilter](https://github.com/weichao666/netserverfilter)里的helloClient工程。

1、添加NetServerListFilter.java类

2、在microservice.yaml文件里添加

```yaml
cse:
  loadbalance:
    serverListFilters: netserver
    serverListFilter:
      netserver:
        className: com.huawei.paas.cse.net.NetServerListFilter
  proxy:
    address: 139.159.161.123:30101
```




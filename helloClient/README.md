1、	添加NetServerListFilter.java类
2、	在microservice.yaml文件里添加
cse:
  loadbalance:
    serverListFilters: netserver
    serverListFilter:
      netserver:
        className: 包名.NetServerListFilter
  proxy:
    address: 弹性ip

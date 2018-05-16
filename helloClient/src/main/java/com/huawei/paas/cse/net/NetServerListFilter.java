package com.huawei.paas.cse.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.servicecomb.core.Endpoint;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.core.Transport;
import org.apache.servicecomb.loadbalance.CseServer;
import org.apache.servicecomb.loadbalance.ServerListFilterExt;
import org.apache.servicecomb.serviceregistry.cache.CacheEndpoint;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.loadbalancer.Server;

public class NetServerListFilter implements ServerListFilterExt {
  private static final String POLICY = "cse.proxy.address";
  
  private ThreadLocal<Invocation> invocationContext = new ThreadLocal<>();

  public NetServerListFilter() {
  }

  @Override
  public void setInvocation(Invocation invocation) {
    invocationContext.set(invocation);
  }
  
  @Override
  public List<Server> getFilteredListOfServers(List<Server> serverList) {
    if (invocationContext.get() == null) {
      return serverList;
    }
    DynamicStringProperty ruleStr = DynamicPropertyFactory.getInstance().getStringProperty(
        String.format(POLICY), null);
    if (ruleStr.get() == null) {
      return serverList;
    }
    List<Server> newServerList = new ArrayList<>();
    for (Server server : serverList) {
      CseServer s = (CseServer) server;
      Endpoint endpoint = s.getEndpoint();
      Transport transport = endpoint.getTransport();
      String oldEndpoint = endpoint.getEndpoint();
      URI uri = null;
      try {
        uri = new URI(oldEndpoint);
      } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String schema = uri.getScheme();
      int port = uri.getPort();
      String newEndpoint = new StringBuffer(schema).append("://").append(ruleStr.get()).append(":").append(port).toString();
      CacheEndpoint cacheEndpoint = new CacheEndpoint(newEndpoint, endpoint.getMicroserviceInstance());
      CseServer newServer = new CseServer(transport, cacheEndpoint);
      newServerList.add(newServer);
    }
    return newServerList;
  }

}

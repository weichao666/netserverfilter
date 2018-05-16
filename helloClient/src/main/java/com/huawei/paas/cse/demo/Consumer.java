package com.huawei.paas.cse.demo;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer{
  private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
  
  public static void main(String[] args) throws Exception {
    Log4jUtils.init(); //日志初始化
    BeanUtils.init(); //Spring bean初始化
    HelloWorld hello = BeanUtils.getBean("helloworld");//服务调用
    String result = hello.sayHello("world");
    System.out.println(result);
    logger.info(result);
 }
}
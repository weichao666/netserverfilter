package com.huawei.paas.cse.demo;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;

public class Provider {

  public static void main(String[] args) throws Exception {
    // TODO Auto-generated method stub
    Log4jUtils.init(); //日志初始化
    BeanUtils.init(); //Spring初始化
  }

}

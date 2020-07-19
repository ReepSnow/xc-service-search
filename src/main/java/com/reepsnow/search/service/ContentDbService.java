package com.reepsnow.search.service;

import org.springframework.stereotype.Service;

import com.reepsnow.search.aop.NotifyContentEs;
import com.reepsnow.search.service.info.CreateOrUpdateContentInfo;

/**
 * @Auther: 王鹏涛 pengtao.wang@weimob.com
 * @Date: 2020/7/19 13:38
 * @Description:
 */
@Service("contentDbService")
public class ContentDbService {

  @NotifyContentEs
  public void dbAdd(CreateOrUpdateContentInfo createOrUpdateContentInfo){
    System.out.println("111");
  }
}

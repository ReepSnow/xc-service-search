package com.reepsnow.search;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reepsnow.search.aop.NotifyContentEs;

/**
 * @author Administrator
 * @Date: 2020/7/18 21:51
 * @Description:
 */
@Service("contentService")
public class ContentService {
  @Autowired
  RestHighLevelClient client;


  public void esAdd(String d) throws IOException {
    //文档内容
    //准备json数据
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("activityId", 111);
    jsonMap.put("endDate", System.currentTimeMillis());
    jsonMap.put("pid", 111);
    List<Long> arr = new ArrayList<>();
    arr.add(111L);
    arr.add(222L);
    jsonMap.put("goodsIds", arr);


    //创建索引创建对象
    IndexRequest indexRequest = new IndexRequest("content","buyerShow");
    //文档内容
    indexRequest.source(jsonMap);
    //通过client进行http的请求
    IndexResponse indexResponse = client.index(indexRequest);
    DocWriteResponse.Result result = indexResponse.getResult();
    System.out.println(result);

  }
  @NotifyContentEs
  public void dbAdd(String d){
    System.out.println("111");
  }
}

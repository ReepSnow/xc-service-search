package com.reepsnow.search.service;

import java.io.IOException;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.reepsnow.search.service.info.CreateOrUpdateContentInfo;

/**
 * @author Administrator
 * @Date: 2020/7/18 21:51
 * @Description:
 */
@Service("contentService")
public class ContentEsService {
  @Autowired
  RestHighLevelClient client;


  public void esAddOrUpdate(CreateOrUpdateContentInfo createOrUpdateContentInfo) throws IOException {
    //文档内容
    //准备json数据
    /*Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("activityId", 111);
    jsonMap.put("endDate", System.currentTimeMillis());
    jsonMap.put("pid", 111);
    List<Long> arr = new ArrayList<>();
    arr.add(111L);
    arr.add(222L);
    jsonMap.put("goodsIds", arr);*/


    //创建索引创建对象
    String id = createOrUpdateContentInfo.getActivityId().toString();
    IndexRequest indexRequest = new IndexRequest("content","buyerShow",id);
    //文档内容
    indexRequest.source(JSON.toJSONString(createOrUpdateContentInfo), XContentType.JSON);
    //通过client进行http的请求
    IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
    DocWriteResponse.Result result = indexResponse.getResult();
    System.out.println(result);

  }

}

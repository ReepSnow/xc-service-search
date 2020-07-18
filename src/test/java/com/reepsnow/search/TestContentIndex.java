package com.reepsnow.search;

import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestContentIndex {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient restClient;


    //添加文档
    @Test
    public void testAddDoc() throws IOException {
        //文档内容
        //准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("activityId", 111);
        jsonMap.put("endDate", new Date().getTime());
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

    //查询文档
    @Test
    public void testGetDoc() throws IOException {
        //查询请求对象
        GetRequest getRequest = new GetRequest("content","buyerShow","tzk2-");
        GetResponse getResponse = client.get(getRequest);
        //得到文档的内容
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        System.out.println(sourceAsMap);
    }

    //查询文档
    @Test
    public void testSearchRequest() throws IOException {
        //查询请求对
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("content").types("buyerShow");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(matchQuery("activityId","111"));
        List<Long> goodsId = new ArrayList<>();
        goodsId.add(111L);
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(termsQuery("goodsIds",goodsId));
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse getResponse = client.search(searchRequest);
        SearchHit[] searchHits = getResponse.getHits().getHits();
        List<Content> contents = new ArrayList<>();
        for(SearchHit hit : searchHits){
            contents.add(JSON.parseObject(hit.getSourceAsString(),Content.class)) ;
        }
        //得到文档的内容
        System.out.println(getResponse);
    }
}

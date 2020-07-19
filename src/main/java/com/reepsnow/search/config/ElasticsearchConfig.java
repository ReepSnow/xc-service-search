package com.reepsnow.search.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration()
public class ElasticsearchConfig {

    //@Value("${reepsnow.elasticsearch.hostlist}")
    private String hostlist="10.12.112.39:9200";
    //socket连接超时时间3秒
    private int socketIimeout = 3*1000;
    private int connectTimeOut = 2*1000;
    private String userName = "";
    private String password ="";

    @Bean()
    public RestHighLevelClient restHighLevelClient(){
        //解析hostlist配置信息
        String[] split = hostlist.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for(int i=0;i<split.length;i++){
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        //创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHostArray).setRequestConfigCallback(
            new RequestConfigCallback() {
              @Override
              public Builder customizeRequestConfig(Builder builder) {
                return builder.setSocketTimeout(socketIimeout).setConnectTimeout(connectTimeOut);
              }
            }).setHttpClientConfigCallback(new HttpClientConfigCallback() {
          @Override
          public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
            httpAsyncClientBuilder.disableAuthCaching();
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
            return httpAsyncClientBuilder.setDefaultCredentialsProvider(provider);
          }
        }));
    }


}

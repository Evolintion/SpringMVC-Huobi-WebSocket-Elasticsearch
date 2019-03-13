package com.personal.quotation.base.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;

import com.alibaba.fastjson.JSONObject;

public class ElasticUtils {
	// 相当于数据库名称（数据量小）
	public static String indexName = "market";
	public static RequestOptions.Builder options = RequestOptions.DEFAULT.toBuilder();
	static CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	static RestClientBuilder builder = RestClient
			.builder(new HttpHost("{eshost}", 9200) )
			.setHttpClientConfigCallback(new HttpClientConfigCallback() {
				@Override
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
					credentialsProvider.setCredentials(AuthScope.ANY,
							new UsernamePasswordCredentials("user", "password"));
					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				}
			});
	public static RestHighLevelClient client = new RestHighLevelClient(builder);

	// 插入指定type，数据
	public static void addDocument(JSONObject json) throws IOException {
		JSONObject quotationObj = json.getJSONObject("tick");
		String id = json.getString("ch");
		String ts = json.getString("ts");
		String time = quotationObj.getString("id");// 时间戳
		String high = quotationObj.getString("high"); // 最高价
		String low = quotationObj.getString("low"); // 最低价
		String open = quotationObj.getString("open"); // 开盘价
		String close = quotationObj.getString("close"); // 收盘价
		String amount = quotationObj.getString("amount");// 成交量
		String vol = quotationObj.getString("vol"); // 成交额

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put("id", id);
		jsonMap.put("time", time);
		jsonMap.put("close", close);
		jsonMap.put("open", open);
		jsonMap.put("low", low);
		jsonMap.put("high", high);
		jsonMap.put("currency_name", "btc");
		jsonMap.put("legal_name", "usdt");
		jsonMap.put("amount", amount);
		jsonMap.put("vol", vol);
		jsonMap.put("ts", ts);
		IndexRequest indexRequest = new IndexRequest(indexName, "market", id + time).source(jsonMap);
		client.index(indexRequest, options.build());
	}
}

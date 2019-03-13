package test;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@WebAppConfiguration
public class TestSpringMybatis {
	
	//初始化client
	@Test
	public void test1() throws Exception {
//	  DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(indexName);
//		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("_index","market");
//		deleteByQueryRequest.setQuery(matchQueryBuilder);
//		deleteByQueryRequest.setDocTypes("market");
//		long deleted = client.deleteByQuery(deleteByQueryRequest, options.build()).getDeleted();
//		System.out.println("已经从ElasticSearch服务器上删除"+deleted+"条数据");
		
		
		
		SearchRequest searchRequest = new SearchRequest(indexName);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchPhraseQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery("_index","market");
		QueryBuilder qb2 = QueryBuilders.boolQuery().must(mpq1);//.must(mpq2);
		// 关键字匹配对应字段
		// 模糊匹配
		sourceBuilder.query(qb2);
		searchRequest.source(sourceBuilder);
		searchRequest.types("market");
		SearchResponse response = client.search(searchRequest, options.build());
		SearchHits hits = response.getHits();
		JSONArray json = new JSONArray();
		 BulkRequest bulkRequest = new BulkRequest();
		for (SearchHit hit : hits.getHits()) {
			json.add(hit.getSourceAsMap());
			  DeleteRequest deleteRequest = new DeleteRequest("market","market", hit.getId());
			  System.out.println(hit.getId());
	          bulkRequest.add(deleteRequest);
		}
		System.out.println(json);
		client.bulk(bulkRequest);
		
		
		
		
		
		
//		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
//                .newRequestBuilder(client)
//                .filter(QueryBuilders.matchQuery("title", "工厂"))
//                .source("index1")
//                .get();
// 
//        long counts = response.getDeleted();
//        System.out.println(counts);
		
		
		
		
//		 DeleteRequest request = new DeleteRequest();
//		 request.index(indexName);
//		 request.type("_index");
//		 request.id("market");
//	        DeleteResponse response = null;
//	        try {
//	            response = client.delete(request);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//	        System.out.println(response);
	}
	// 相当于数据库名称（数据量小）
		public static String indexName = "market";
		public static RequestOptions.Builder options = RequestOptions.DEFAULT.toBuilder();
		static CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		static RestClientBuilder builder = RestClient
				.builder(new HttpHost("es-cn-459115w5i000gm5fq.public.elasticsearch.aliyuncs.com", 9200))
				.setHttpClientConfigCallback(new HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						System.out.println("创建了========");
						credentialsProvider.setCredentials(AuthScope.ANY,
								new UsernamePasswordCredentials("elastic", "Lianboxing2019@"));
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});
		public static RestHighLevelClient client = new RestHighLevelClient(builder);
}

//final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//credentialsProvider.setCredentials(AuthScope.ANY,
//        new UsernamePasswordCredentials("elastic", "Lianboxing2019@"));
//RestClient  client = RestClient.builder(new HttpHost("es-cn-459115w5i000gm5fq.public.elasticsearch.aliyuncs.com",9200))
//        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        })
//        .build();

package com.taodaye.search;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taodaye.search.entity.SearchApiOrderParam;
import com.taodaye.search.entity.SearchResultObject;
import com.taodaye.search.processor.DataProcessor;

public class BaiDuYunSearcher implements Searcher{
	
	private static final Logger logger = LogManager.getLogger(BaiDuYunSearcher.class);

	private String searchEngineApi = "http://api.ygyhg.com/pc/free";
	
	@Override
	public String getApi() {
		return searchEngineApi;
	}

	@Override
	public SearchResultObject search(String searchStr, DataProcessor processor) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		searchEngineApi = String.format("%s?keyword=%s&order=%s&num=%d", getApi(), searchStr, SearchApiOrderParam.ORDER_FREETIME,10);
		logger.info(String.format("Starting to request: %s.", searchEngineApi));
		HttpGet httpGet = new HttpGet(searchEngineApi);
		CloseableHttpResponse response = null;
		SearchResultObject res = null;
		try {
			response = httpclient.execute(httpGet);
			// The underlying HTTP connection is still held by the response object
			// to allow the response content to be streamed directly from the network socket.
			// In order to ensure correct deallocation of system resources
			// the user MUST call CloseableHttpResponse#close() from a finally clause.
			// Please note that if response content is not fully consumed the underlying
			// connection cannot be safely re-used and will be shut down and discarded
			// by the connection manager. 
			logger.info(response.getStatusLine());
		    HttpEntity entity1 = response.getEntity();
		    InputStream in = entity1.getContent();
		    res = processor.process(in, SearchResultObject.class);
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity1);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("Failed to close the http response.", e);
				}
			}
		}
		return res;
	}

}

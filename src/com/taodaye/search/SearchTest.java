package com.taodaye.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taodaye.search.entity.SearchResultObject;
import com.taodaye.search.entity.SearchResultRecordDetail;
import com.taodaye.search.processor.BaiDuYunDataProcessor;

public class SearchTest {

	private static Logger logger = LogManager.getLogger(SearchTest.class);
	public static void main(String[] args) {
		Searcher searcher = new BaiDuYunSearcher();
		SearchResultObject obj = searcher.search("三生三世", new BaiDuYunDataProcessor());
		logger.info(obj);
		for (SearchResultRecordDetail res : obj.getContent()) {
			if (res.getType().equals("0")) {
				logger.info(res.getUrl());
			}
		}
	}
}

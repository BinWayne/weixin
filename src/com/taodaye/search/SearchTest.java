package com.taodaye.search;

import java.util.Iterator;

import com.taodaye.search.entity.SearchResultObject;
import com.taodaye.search.entity.SearchResultRecordDetail;
import com.taodaye.search.processor.BaiDuYunDataProcessor;

public class SearchTest {

	public static void main(String[] args) {
		Searcher searcher = new BaiDuYunSearcher();
		SearchResultObject obj = searcher.search("三生三世", new BaiDuYunDataProcessor());
		System.out.println(obj);
		for (SearchResultRecordDetail res : obj.getContent()) {
			if (res.getType().equals("0")) {
				System.out.println(res.getUrl());
			}
		}
	}
}

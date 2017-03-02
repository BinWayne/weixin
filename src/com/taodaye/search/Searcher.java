package com.taodaye.search;

import com.taodaye.search.processor.DataProcessor;

public interface Searcher {

	public abstract String getApi();
	
	public abstract <T> T search(String searchStr, DataProcessor processor);
}

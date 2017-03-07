package com.taodaye.search.processor;

import org.jsoup.nodes.Element;

public interface JsoupDataProcessor {

	<T> T process(Element in) throws Exception;
}

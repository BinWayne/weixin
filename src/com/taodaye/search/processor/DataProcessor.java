package com.taodaye.search.processor;

import java.io.InputStream;

public interface DataProcessor {

	<T> T process(InputStream in, Class<T> clazz) throws Exception;
}

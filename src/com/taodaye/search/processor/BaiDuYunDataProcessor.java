package com.taodaye.search.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taodaye.util.JsonUtil;

public class BaiDuYunDataProcessor implements DataProcessor{
	
	private static final Logger logger = LogManager.getLogger(BaiDuYunDataProcessor.class);

	@Override
	public <T> T process(InputStream in, Class<T> clazz) throws Exception {
		if (in == null) {
			return null;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line = "";
	    T res = null;
	    do {
	    	try {
				line = br.readLine();
				if (StringUtils.isNotBlank(line)) {
					logger.info(line);
					res = JsonUtil.jacksonJson2Ojbect(line, clazz);
				}
			} catch (Exception e) {
				logger.error("Failed to parse response data.", e);
				throw e;
			}
		} while (line != null);
		return res;
	}

}

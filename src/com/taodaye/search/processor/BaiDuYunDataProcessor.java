package com.taodaye.search.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

import com.taodaye.util.JsonUtil;

public class BaiDuYunDataProcessor implements DataProcessor{

	@Override
	public <T> T process(InputStream in, Class<T> clazz) {
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
					System.out.println(line);
					res = JsonUtil.jacksonJson2Ojbect(line, clazz);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (line != null);
		return res;
	}

}

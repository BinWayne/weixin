package com.taodaye.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taodaye.entity.SearchResult;

import net.sf.json.JSONObject;

public class JsonUtil {

	public static String objectToJson(SearchResult result) {
		if (result == null) {
			return "";
		}
		JSONObject json = JSONObject.fromObject(result);

		return json.toString();
	}

	public static SearchResult jsonToObject(String jsonString) {

		SearchResult result = new SearchResult();
		if (jsonString == "" || jsonString == null) {
			return result;
		}
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		result = (SearchResult) JSONObject.toBean(jsonObj, SearchResult.class);
		return result;
	}
	
	public static <T> T json2Ojbect(String jsonInString, Class<T> clazz) {
		JSONObject jsonObj = JSONObject.fromObject(jsonInString);
		@SuppressWarnings("unchecked")
		T obj = (T) JSONObject.toBean(jsonObj, clazz);
		return obj;
	}
	
	public static <T> T jacksonJson2Ojbect(String jsonInString, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();

		T obj = mapper.readValue(jsonInString, clazz);
		return obj;
	}
	
	public static void main(String[] args){
		String jsonStr = "{\"error\": 0,\"counts\": 5824,\"content\": [{\"id\": \"13323272\",\"title\": \"VA - Magic Instrumental Music Vol.4 (2017)\",\"size\": \"1024\",\"type\": \"0\",\"feedtime\": \"1487207215407\",\"url\": \"http://pan.baidu.com/share/link?uk=403089040&shareid=2322484979\"}]}";
		SearchResult result = JsonUtil.jsonToObject(jsonStr);
		System.out.println(result);
	}
}

package com.taodaye.util;

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
	
	public static void main(String[] args){
		String jsonStr = "{\"error\": 0,\"counts\": 5824,\"content\": [{\"id\": \"13323272\",\"title\": \"VA - Magic Instrumental Music Vol.4 (2017)\",\"size\": \"1024\",\"type\": \"0\",\"feedtime\": \"1487207215407\",\"url\": \"http://pan.baidu.com/share/link?uk=403089040&shareid=2322484979\"}]}";
		SearchResult result = JsonUtil.jsonToObject(jsonStr);
		System.out.println(result);
	}
}

package com.taodaye.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.taodaye.search.processor.JsoupDataProcessor;
import com.taodaye.search.processor.YgyhgJsoupDataProcessor;

public class YgyhgSearcher {
	
	private String requestUrl = "https://so.ygyhg.com/s?keyword=%s&o=%d&t=%d";
	private Logger logger = LogManager.getLogger(getClass());

	public String getApi() {
		return requestUrl;
	}
	
	public void setApi(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public <T> T search(String searchStr) {
		//搜素关键字，按类型：视频， 排序：综合
		setApi(prepareReqUrl(searchStr, 4, 1));
		try {
			logger.info("Connecting to " + getApi());
			Document doc = Jsoup.connect(getApi()).get();
			Elements eles = doc.select(".bd-title a");
			logger.info("parsing resut urls: ");
			
			YgyhgJsoupDataProcessor processor = YgyhgJsoupDataProcessor.getInstance();
			
			for (org.jsoup.nodes.Element element : eles) {
				logger.info(element.attr("href"));
				logger.info(processor.process(element).toString());
			}
		} catch (IOException e) {
			logger.error("Failed to get response for " + getApi(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	private String prepareReqUrl(String keywords, int order, int type){
		try {
			keywords = URLEncoder.encode(keywords, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Failed to encode the keywords: " + keywords);
		}
		return String.format(requestUrl, keywords, order, type);
	}
	
	public static void main(String[] args) {
		YgyhgSearcher searcher = new YgyhgSearcher();
		searcher.search("金刚狼3");
	}
}

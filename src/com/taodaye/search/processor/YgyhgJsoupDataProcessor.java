package com.taodaye.search.processor;

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

public class YgyhgJsoupDataProcessor implements JsoupDataProcessor{

	private Logger logger = LogManager.getLogger(getClass());
	
	private static volatile WebDriver driver = null;
	
	private static volatile YgyhgJsoupDataProcessor instance = null;
	
	private YgyhgJsoupDataProcessor(){
		
	}
	
	public static YgyhgJsoupDataProcessor getInstance(){
		if (instance == null) {
			synchronized(YgyhgJsoupDataProcessor.class){
				if (instance == null) {
					instance = new YgyhgJsoupDataProcessor();
				}
			}
		}
		
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String process(Element in) throws Exception {
		String url = in.attr("href");
		logger.info(url);
		driver = new HtmlUnitDriver(true);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		String htmlContent = driver.getPageSource();
		driver.quit();
        
        /**jsoup解析文档*/  
        Document doc2 = Jsoup.parse(htmlContent);   
  
		Elements eles = doc2.select(".bdylink");
		System.out.println("Elements Size: " + eles.size());
		for(Attribute attr: eles.first().attributes()){
			logger.info(attr.getKey()+ " : " + attr.getValue());
		}
		return eles.first().attr("href");
	}

	public void initDriver(){
		if (driver == null) {
			synchronized(YgyhgJsoupDataProcessor.class){
				if (driver == null) {
					driver = new HtmlUnitDriver(true);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				}
			}
		}
	}
	
	public void closeDriver(){
		if (driver != null) {
			driver.close();
		}
	}
}

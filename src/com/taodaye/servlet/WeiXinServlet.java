package com.taodaye.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.taodaye.entity.SearchResult;
import com.taodaye.entity.TextMessage;
import com.taodaye.util.CheckUtil;
import com.taodaye.util.JsonUtil;
import com.taodaye.util.MessageUtil;
import com.yunpan.service.SearchResourceService;
import com.yunpan.service.impl.SearchResourceServiceImpl;

public class WeiXinServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			
			out.print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out  = resp.getWriter();
		Map<String, String> map;
		try {
			map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			if(content!=null && content!=""){
				SearchResourceService searchService = new SearchResourceServiceImpl();
				String resultJson = searchService.searchOrderByTime(content);
				SearchResult result = JsonUtil.jsonToObject(resultJson);
				//next step: write a jsp/html, then generate link to user
			}
			String msgId = map.get("MsgId");
			String message = null;
			if("text".equals(msgType)){
				TextMessage textMessage = new TextMessage();
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(fromUserName);
				textMessage.setMsgType("text");
				long currentTime = new Date().getTime();
				textMessage.setCreateTime(currentTime+"");
				textMessage.setMsgId(msgId);
				textMessage.setContent("The result is: "+"<a href='http://www.baidu.com'>"+content+"</a>");
				message = MessageUtil.messageToXML(textMessage);
				}
			
			
			out.print(message);
			out.flush();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			out.close();
		}
		
		
	}

	
}

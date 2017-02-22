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

import com.taodaye.entity.TextMessage;
import com.taodaye.util.CheckUtil;
import com.taodaye.util.MessageUtil;
import com.thoughtworks.xstream.converters.basic.DateConverter;

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
		System.out.println(new Date().getTime());
		PrintWriter out  = resp.getWriter();
		Map<String, String> map;
		try {
			map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String msgId = map.get("MsgId");
			String message = null;
			if("text".equals(msgType)){
				TextMessage textMessage = new TextMessage();
				textMessage.setFromUserName(fromUserName);
				textMessage.setToUserName(toUserName);
				textMessage.setMsgType("text");
				long currentTime = new Date().getTime();
				textMessage.setCreateTime(currentTime+"");
				textMessage.setMsgId(msgId);
				textMessage.setContent("The message you sent is: "+content);
				message = MessageUtil.textMessageToXML(textMessage);
				}
			System.out.print(message);
			out.print(message);
			out.flush();
			System.out.println(new Date().getTime());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			out.close();
		}
		
		
	}

	
}

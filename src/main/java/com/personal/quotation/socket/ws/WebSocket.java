package com.personal.quotation.socket.ws;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSONObject;
import com.personal.quotation.base.utils.ElasticUtils;
import com.personal.quotation.base.utils.JSONUtils;
import com.personal.quotation.base.utils.ZipUtils;

public class WebSocket extends WebSocketClient {
	public static String KLINE = "market.%s.kline.%s";

	public WebSocket(URI uri) {
		super(uri, new Draft_17());
		this.uri = uri;
	}

	@Override
	public void onOpen(ServerHandshake shake) {
		String topic = String.format(KLINE, "btcusdt", "1min");
		sendWsMarket("sub", topic);
	}

	@Override
	public void onMessage(String arg0) {
		if (arg0 != null) {
			System.out.println("receive message " + arg0);
		}
	}

	@Override
	public void onError(Exception arg0) {
		String message = "";
		try {
			message = new String(arg0.getMessage().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("has error ,the message is :" + message);
		}
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		System.out.println("connection close ");
		System.out.println(arg0 + "   " + arg1 + "  " + arg2);
	}

	@Override
	public void onMessage(ByteBuffer bytes) {
		try {
			String message = new String(ZipUtils.decompress(bytes.array()), "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(message);
			if (!"".equals(message)) {
				if (message.indexOf("ping") > 0) {
					String pong = jsonObject.toString();
					send(pong.replace("ping", "pong"));
				} else {
					String tick = jsonObject.getString("tick");
					if (null != tick && !"".equals(tick) && JSONUtils.isJsonObject(tick)) {
						System.out.println(message);
//						JSONObject quotationObj = JSONObject.parseObject(tick);
//						String high = quotationObj.getString("high"); // 最高价
//						String open = quotationObj.getString("open"); // 开盘价
//						String close = quotationObj.getString("close"); // 收盘价
//						String low = quotationObj.getString("low"); // 最低价
//						String vol = quotationObj.getString("vol"); // 成交额
//						String id = quotationObj.getString("id");//时间戳
//						String amount = quotationObj.getString("amount");//成交量
						ElasticUtils.addDocument(jsonObject);
					}
				}
			}
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendWsMarket(String op, String topic) {
		JSONObject req = new JSONObject();
		req.put(op, topic);
		send(req.toString());
	}

}

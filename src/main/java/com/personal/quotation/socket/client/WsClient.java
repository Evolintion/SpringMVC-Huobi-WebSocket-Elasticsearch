package com.personal.quotation.socket.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.personal.quotation.base.client.Client;
import com.personal.quotation.socket.ws.WebSocket;

@Component
public class WsClient {
	@Autowired
	private Client client;

	@PostConstruct
	public void run() {
		try {
			// URI uri = new URI("ws://api.huobi.pro:443/ws");
			URI uri = new URI("ws://api.huobi.br.com:443/ws");// 国内不被墙的地址
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(60000);
							WebSocket ws = new WebSocket(uri);
							client.connect(ws);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			WebSocket ws = new WebSocket(uri);
			client.connect(ws);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}

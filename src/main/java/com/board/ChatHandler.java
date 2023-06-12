package com.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.websocket.Session;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler{
	
	private static List<WebSocketSession> list = new ArrayList<>();

	//클라이언트가 접속할 때 생성된 세션(ID + 메세지)을 저장
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("접속 Session ID = {}",session.getId());
		list.add(session);
	}
	
	//websocket으로보터 받아온 메세지가 List<WebSocketSession>에 저장이 되고 이 message를 클라이언트에게 전달
	@Override 
	public void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {
		for(WebSocketSession s: list)
			s.sendMessage(message);
	}
	
	//접속해제 처리 --> 세션 삭제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		list.remove(session);
	}

}

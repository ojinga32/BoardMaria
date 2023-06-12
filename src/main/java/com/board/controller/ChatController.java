package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ChatController {

	@GetMapping("/board/chat")
	public void getChat() throws Exception {
		log.info("====================== 채팅 진행 중 ======================");
	}
	
}

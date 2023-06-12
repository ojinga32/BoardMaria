package com.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;
import com.board.entity.repository.BoardRepository;
import com.board.entity.repository.MemberRepository;
import com.board.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RESTController {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	MemberService service;
	
	// 전체 게시물 목록 가져오기
	@GetMapping("/restapi/list")
	public List<BoardDTO> getList() throws Exception {
		List<BoardDTO> boardDTOs = new ArrayList<>();
		boardRepository.findAll().stream().forEach(list-> boardDTOs.add(new BoardDTO(list)));
		
		return boardDTOs;
	}
	
	
	// 로그인 처리
	@PostMapping("/restapi/loginCheck")
	public String postLogIn(MemberDTO loginData,HttpSession session) {

		//아이디 존재 여부 확인
		if(service.idCheck(loginData.getEmail()) == 0)
			return "{\"message\":\"ID_NOT_FOUND\"}";
		
		//아이디가 존재하면 읽어온 email로 로그인 정보 가져 오기
		MemberDTO member = service.memberInfo(loginData.getEmail());
		
		//패스워드 확인
		if(!pwdEncoder.matches(loginData.getPassword(),member.getPassword())) 
			return "{\"message\":\"PASSWORD_NOT_FOUND\"}";
			
		return "{\"message\":\"GOOD\"}";

	}
	
	
	
	
	
	
	
}

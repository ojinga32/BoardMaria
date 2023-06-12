package com.board;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.board.entity.MemberEntity;
import com.board.entity.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private final MemberRepository memberRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		MemberEntity memberInfo = memberRepository.findById(authentication.getName()).get();
		
		//세션 생성
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600*24*7);
		session.setAttribute("email", memberInfo.getEmail());
		session.setAttribute("username", memberInfo.getUsername());
		session.setAttribute("role", memberInfo.getRole());
		System.out.println("*************** FormLogin 성공 !!! ***************");
		
		setDefaultTargetUrl("/board/list?page=1");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

package com.board;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       
    	System.out.println("************* 구글 인증 통과 *************");
    	
		setDefaultTargetUrl("/board/list?page=1");
        super.onAuthenticationSuccess(request, response, authentication);
       
    }
}
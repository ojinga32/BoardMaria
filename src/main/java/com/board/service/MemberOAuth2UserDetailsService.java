package com.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.board.dto.MemberOAuth2DTO;
import com.board.entity.MemberEntity;
import com.board.entity.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberOAuth2UserDetailsService extends DefaultOAuth2UserService{
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder pwdEncoder;
	private final HttpSession session;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest)throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		String provider = userRequest.getClientRegistration().getRegistrationId();
		String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        		
        System.out.println("privoider = " + provider);
        System.out.println("privoiderId = " + providerId);
        System.out.println("email = " + email);
        
		//oAuth2User.getAttributes().forEach((k,v)->{log.info(k + ":" + v);});
		
		MemberEntity member = saveSocialMember(email);

		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole());
		grantedAuthorities.add(grantedAuthority);

		MemberOAuth2DTO memberOAuth2DTO = new MemberOAuth2DTO();
		memberOAuth2DTO.setAttribute(oAuth2User.getAttributes());
		memberOAuth2DTO.setAuthoroties(grantedAuthorities);
	    memberOAuth2DTO.setName(member.getUsername());
	    
	    session.setAttribute("email", email);
	    session.setAttribute("username", member.getUsername());
	    session.setAttribute("role", member.getRole());
	    
		return memberOAuth2DTO;
	}

	
	private MemberEntity saveSocialMember(String email) {
		
		Optional<MemberEntity> result = memberRepository.findById(email);
		
		if(result.isPresent()) {
			
			return result.get();
			
		}	
			
		MemberEntity member = MemberEntity.builder()
							.email(email)
							.username(email)
							.password(pwdEncoder.encode("12345"))
							.role("USER")
							.regdate(LocalDateTime.now())
							.FromSocial("Y")
							.build();
		
		memberRepository.save(member);
		
		return member;
		
	}
}

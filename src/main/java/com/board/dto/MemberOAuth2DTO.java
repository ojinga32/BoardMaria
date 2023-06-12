package com.board.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Setter;

@Setter
public class MemberOAuth2DTO implements OAuth2User{

	private Map<String,Object> attribute;
	private Collection<? extends GrantedAuthority> authoroties;
	private String name;

	@Override
	public Map<String, Object> getAttributes() {
		return attribute;
	}

	@Override
	public String getName() {
        return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authoroties;
	}

}

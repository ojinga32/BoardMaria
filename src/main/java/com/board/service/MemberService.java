package com.board.service;

import org.springframework.data.domain.Page;

import com.board.dto.MemberDTO;
import com.board.entity.AddressEntity;
import com.board.entity.MemberEntity;

public interface MemberService {

	//아이디 중복 체크. 카운터가 0이면 아이디 사용 가능, 1이면 기존 사용 중인 아이디
	public int idCheck(String email);
	
	//사용자 정보 보기
	public MemberDTO memberInfo(String email);
	
	//사용자등록
	public void signup(MemberDTO member);
	
	//사용자 패스워드 수정
	public void memberPasswordModify(String email,String Password);
	
	//사용자 자동 로그인을 위한 authkey 등록
	public void authkeyUpdate(MemberDTO member);
	
	//사용자 자동 로그인을 위한 authkey로 사용자 정보 가져 오기 
	public MemberEntity memberInfoByAuthkey(String authkey);
	
	//아이디 찾기
	public String memberSearchID(MemberDTO member);
	
	//임시패스워드 생성
	public String tempPassowrdMaker();
	
	//주소 검색
	public Page<AddressEntity> addrSearch(int pageNum, int postNum, String addrSearch);
	
	
}

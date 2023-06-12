package com.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.MemberDTO;
import com.board.entity.MemberEntity;
import com.board.entity.repository.MemberRepository;
import com.board.entity.AddressEntity;
import com.board.service.MemberService;
import com.board.util.PageUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;
	private final BCryptPasswordEncoder pwdEncoder; 

	//로그인
	@GetMapping("/member/login")
	public void getLogin(Model model) {}
	
	//로그인
	@PostMapping("/member/login")
	public void postLogin(Model model) {}
	
	@ResponseBody
	@PostMapping("/member/loginCheck")
	public String postLogIn(MemberDTO loginData,HttpSession session) {

		//아이디 존재 여부 확인
		if(service.idCheck(loginData.getEmail()) == 0)
			return "{\"message\":\"ID_NOT_FOUND\"}";
		
		//아이디가 존재하면 읽어온 email로 로그인 정보 가져 오기
		MemberDTO member = service.memberInfo(loginData.getEmail());
		
		//패스워드 확인
		if(!pwdEncoder.matches(loginData.getPassword(),member.getPassword())) 
			return "{\"message\":\"PASSWORD_NOT_FOUND\"}";
			
		return "{\"message\":\"good\"}";

	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public String getLogout(HttpSession session) throws Exception {
		
		session.invalidate();
		return "redirect:/";
	}
	
	//회원 가입
	@GetMapping("/member/signup")
	public void getSignup() throws Exception { }
	
	//회원 가입
	@ResponseBody
	@PostMapping("/member/signup")
	public String postSignup(MemberDTO member, @RequestParam("fileUpload") MultipartFile mpr) throws Exception {
		
		String path = "c:\\Repository\\profile\\"; 
		String org_filename = "";
		long filesize = 0L;
		
		if(!mpr.isEmpty()) {
			File targetFile = null; 
				
			org_filename = mpr.getOriginalFilename();	
			String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
			String stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
			filesize = mpr.getSize();
			targetFile = new File(path + stored_filename);
			mpr.transferTo(targetFile);	//raw data를 targetFile에서 가진 정보대로 변환
			member.setOrg_filename(org_filename);
			member.setStored_filename(stored_filename);
			member.setFilesize(filesize);
		}
		
		member.setPassword(pwdEncoder.encode(member.getPassword()));
		
		service.signup(member);		
		return "{\"username\":\"" + URLEncoder.encode(member.getUsername(),"UTF-8") + "\",\"status\":\"good\"}";
		
	}
	
	//회원정보 보기
	@GetMapping("/member/memberInfo")
	public void getUserInfo(Model model, HttpSession session) { 
		
		String session_email = (String)session.getAttribute("email");
		model.addAttribute("member", service.memberInfo(session_email));
		
	}
	
	//회원 패스워드 변경
	@GetMapping("/member/memberPasswordModify")
	public void getMemberPasswordModify() {	}
	
	//회원 패스워드 변경
	@PostMapping("/member/memberPasswordModify")
	public String postMemberPasswordModify(@RequestParam("old_userpassword") String old_password,
			@RequestParam("new_userpassword") String new_password, HttpSession session) {
		
		String email = (String)session.getAttribute("email");
		
		MemberDTO memberDTO = service.memberInfo(email);
		if(pwdEncoder.matches(old_password, memberDTO.getPassword()))
			service.memberPasswordModify(email, new_password);
		
		return "redirect:/member/logout";
	}
	
	//아이디 중복 체크
	@ResponseBody
	@PostMapping("/member/idCheck")
	public int getIdCheck(@RequestBody String email) {		
		return service.idCheck(email);		
	}
	
	//아이디 찾기
	@GetMapping("/member/searchID")
	public void getSearchID() {}
	
	//아이디찾기
	@ResponseBody
	@PostMapping("/member/searchID")
	public String postSearchID(MemberDTO member) {
		
		String email = service.memberSearchID(member);
		
		if(email.equals("ID_NOT_FOUND"))
			return "{\"status\":\"ID_NOT_FOUND\"}";
		
		return "{\"status\":\"good\",\"email\":\"" + email + "\"}";
	}
	
	//임시패스워드 생성
	@GetMapping("/member/searchPassword")
	public void getSearchPassword() {}
	
	//임시패스워드 생성
	@ResponseBody
	@PostMapping("/member/searchPassword")
	public String postSearchPassword(MemberDTO memberDTO) throws Exception{
		
		if(service.idCheck(memberDTO.getEmail()) == 0)
			return "{\"status\":\"ID_NOT_FOUND\"}";
		
		if(!service.memberInfo(memberDTO.getEmail()).getUsername()
					.equals(memberDTO.getUsername())) 
			return "{\"status\":\"USERNAME_NOT_FOUND\"}";
		
		//임시 패스워드 생성
		String rawTempPW = service.tempPassowrdMaker();
		//임시패스워드 인코딩 후 수정
		service.memberPasswordModify(memberDTO.getEmail(), rawTempPW);
		
		return "{\"status\":\"good\",\"password\":\"" + rawTempPW + "\"}";
	}
	
	//주소검색
	@GetMapping("/member/addrSearch")
	public void getSearchAddr(@RequestParam("addrSearch") String addrSearch,
			@RequestParam("page") int pageNum,Model model) throws Exception {
		
		int postNum = 5;
		int listCount = 10;
		
		PageUtil page = new PageUtil();
		
		Page<AddressEntity> list = service.addrSearch(pageNum, postNum, addrSearch);
		int totalCount = (int)list.getTotalElements();

		model.addAttribute("list", list);
		model.addAttribute("pageListView", page.getPageAddress(pageNum, postNum, listCount, totalCount, addrSearch));
		
	}
	
}

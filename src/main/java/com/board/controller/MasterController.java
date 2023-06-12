package com.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.entity.FileEntity;
import com.board.service.MasterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MasterController {
	
	private final MasterService service;
	
	@GetMapping("/master/sysmanage")
	public void getSysmanage() {}
	
	@GetMapping("/master/sysinfo")
	public void getSysinfo() {}
	
	//파일 관리 화면 보기
	@GetMapping("/master/filemanage")
	public void getFilemanage(Model model) {
		model.addAttribute("count", service.filedeleteCount());//삭제할 파일 갯수 가져 오기
	}
	
	//파일 삭제
	@ResponseBody
	@GetMapping("/master/fileDelete")
	public List<Map<String,String>> getFileDelete() throws Exception {
		
		String path = "c://Repository//file//";
		int count = 0;
		
		List<FileEntity> filedeleteList = service.filedeleteList();
		
		List<Map<String,String>> data = new ArrayList<>();
		
		for(FileEntity f : filedeleteList) {
			
			//웹브라우저에 삭제할 파일 정보 전송을 위해 파일 정보를 저장
			Map<String,String> result = new HashMap<>();
			result.put("count", Integer.toString(count));
			result.put("org_filename", f.getOrg_filename());
			
			data.add(result);
			count ++;
			
			//파일 삭제
			File file = new File(path + f.getStored_filename());
			file.delete();
			
			//tbl_file 내 파일 정보 삭제
			service.deleteFile(f.getFileseqno());
		}
		
		return data;
	}

}

package org.raoul.controller;

import org.raoul.domain.MemberVO;
import org.raoul.persistence.MemberMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/member/")
@AllArgsConstructor
@CrossOrigin
@Log
public class MemberController {

	private MemberMapper mMapper;
	
	@RequestMapping("/getMember")
	public MemberVO getMember(@RequestBody MemberVO vo) {
		
		log.info("param: " + vo);
		
		MemberVO result = mMapper.findByUidAndPw(vo.getUid(), vo.getPw());
		
		return result;
		 
	}
}
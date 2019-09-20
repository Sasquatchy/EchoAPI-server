package org.raoul.domain;

import java.util.Date;
import java.util.List;

import org.raoul.domain.AuthVO;

import lombok.Data;

@Data
public class MemberVO {

	private Integer mno;
	private String uid;
	private String pw;
	private String username;
	private String mobile;
	private Date regDate;
	private Date updateDate;
	
	private List<AuthVO> authList;
	
	
}

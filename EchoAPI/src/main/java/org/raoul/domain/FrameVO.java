package org.raoul.domain;

import java.util.Date;

import lombok.Data;

@Data
public class FrameVO {

	private Integer fno;
	private String mac;
	private String address;
	private Date regDate;
	private Date updateDate;
	
}

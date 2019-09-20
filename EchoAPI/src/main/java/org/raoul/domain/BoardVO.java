package org.raoul.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVO {

	private Integer bno, mno;
	private String uid;
	private Date regDate;
	private Date updateDate;
	private boolean sent;
	
	private List<PhotoDTO> attachList;
}

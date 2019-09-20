package org.raoul.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PhotoVO {

	private Integer pno;
	private String photoName;
	private String path;
	private Date regDate;
}

package org.raoul.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoDTO {

	private Integer pno;
	private String uuid;
	private String originalPhotoName;
	private String folderPath;
	private Date regDate;
	
	private Integer bno;
	private String uid;
	
	@Override
	public String toString() {
		return "\n========================\n"+
				"pno=\t\t"+pno+"\n"+
				"uuid=\t\t"+uuid+"\n"+
				"originalPhotoName=\t"+originalPhotoName+"\n"+
				"path=\t\t"+folderPath+"\n"+
				"regDate=\t"+regDate+"\n"+
				"bno=\t\t"+bno+"\n"+
				"uid=\t\t"+uid+"\n"+
				"=======================";
	}
}

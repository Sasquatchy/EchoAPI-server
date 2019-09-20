package org.raoul.service;

import java.util.List;

import org.raoul.domain.PhotoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService  {
	
	public List<PhotoDTO> upload(MultipartFile[] uploadFile,String rootPath, String memberUid) ;
	
	public void insertList(List<PhotoDTO> list);
	
	public PhotoDTO read(Integer pno);

	public int remove(Integer pno) ;

//	public int removeList(List<PhotoDTO> list) ;
	
//	public int removeWithBoard(Integer bno);

	public List<PhotoDTO> getListByBoard(Integer bno) ;
	
	public List<PhotoDTO> getListByMember(String uid);
}

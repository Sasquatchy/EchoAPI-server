package org.raoul.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.raoul.domain.BoardVO;
import org.raoul.domain.PhotoDTO;
import org.raoul.service.BoardService;
import org.raoul.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@RestController
@Log
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	BoardService bService;
	
	@Autowired
	PhotoService pService;
	
	protected final static String  rootPath = "C:"+File.separator+"upload";
	
	
	static {
		File uploadFile = new File(rootPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdir();
		}
	}
	
	@ResponseBody
	@PostMapping(value="/new")
	public ResponseEntity<String> register(@RequestParam("photo") MultipartFile[] uploadFiles,  BoardVO bvo)throws IOException {
		
		log.info("received");
//		log.info(uploadFiles.toString());
//		uploadFiles
//		Arrays.stream(uploadFiles).forEach((MultipartFile f)->{log.info("a")});
		
		
		
		log.info(""+uploadFiles.length);
		for(MultipartFile f : uploadFiles) {
			log.info(f.getName() +" "+ f.getSize()+" "+f.getOriginalFilename()+" "+f.getContentType());
		}
		log.info(bvo.toString());

		List<PhotoDTO> attachList = pService.upload(uploadFiles, rootPath, bvo.getUid());
		
		bvo.setAttachList(attachList);
		
		bService.add(bvo);
		
		return new ResponseEntity<>("success", HttpStatus.OK);

	}
	
//	@ResponseBody
//	@PostMapping(value="/board/register")
//	public ResponseEntity<String> register( BoardVO vo, MultipartFile[] uploadFiles) {
//
//		log.info("=-=-=-POST register...");
//		log.info("=-=-=-registering vo: " + vo);
//
//		if (vo.getAttachList() != null) {
//			vo.getAttachList().forEach(attach -> log.info(attach.toString()));
//		}
//		log.info("=========================================================");
//
//		bService.add(vo);
//		
//		pService.upload(uploadFiles, rootPath, vo.getUid());
//
//		HttpHeaders headers = new HttpHeaders();
//
////		headers.add(headerName, headerValue);
//		
//		return new ResponseEntity<String>("success", headers, HttpStatus.OK);
//
//	}
	
	// https://private.tistory.com/59
	// https://pangsblog.tistory.com/69
	// https://preamtree.tistory.com/58
}

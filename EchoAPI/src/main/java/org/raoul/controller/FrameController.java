package org.raoul.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.raoul.service.FrameService;
import org.raoul.service.FrameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/frame")
@Log
@CrossOrigin
public class FrameController {
	
	@Autowired
	FrameService fService;
	

	@ResponseBody
	@GetMapping(value="/download/{ufid}", produces="application/zip")
	public ResponseEntity<Resource> downloadZip (@PathVariable("ufid") String ufid){
		
		log.info("download from FrameController");
		
		
		// org.springframework.core.io.Resource 을 통해서 zip파일 보내기
		
		File zipFile = fService.makeZipByUfid(ufid);
		
		Resource resource = new FileSystemResource(zipFile);
		
		String resourceName ="";
		try {
			resourceName = new String( resource.getFilename().getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			log.info(e.toString());
		}
		
		log.info("Resource: "+resourceName);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition","attachment; filename="+resourceName);
		

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value="/downloadDummy", produces="application/zip")
	public ResponseEntity<Resource> downloadDummy (){
		
		//https://stackoverflow.com/questions/27952949/spring-rest-create-zip-file-and-send-it-to-the-client
		

		log.info("download");
		
		File zipFile = Paths.get("C:\\upload", "example.zip").toFile();
		
		
		// org.springframework.core.io.Resource 을 통해서 zip파일 보내기
		Resource resource = new FileSystemResource(zipFile);
		
		String resourceName ="";
		try {
			resourceName = new String( resource.getFilename().getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.info(e.toString());
		}
		
		log.info("Resource: "+resourceName);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition","attachment; filename="+resourceName);
		
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
}

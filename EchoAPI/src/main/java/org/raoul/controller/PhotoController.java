package org.raoul.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.raoul.domain.PhotoDTO;
import org.raoul.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin
@RequestMapping("/photo")
public class PhotoController {
	
	@Autowired
	PhotoService pService;
	
	@GetMapping(value = "/viewPhoto", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<byte[]> viewPhoto(@RequestParam("file") String file) {

		ResponseEntity<byte[]> result = null;

		log.info("file=== " + file);

		try {
			File targetFile = Paths.get(UploadController.rootPath, file).toFile();
			log.info("targetfile=== " + targetFile);
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(targetFile.toPath()));
			byte[] arr = FileCopyUtils.copyToByteArray(targetFile);

			result = new ResponseEntity<>(arr, header, HttpStatus.OK);
			log.info("result=== " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

	@GetMapping(value = "/getPhotoByBno/{bno}")
	public @ResponseBody ResponseEntity<List<PhotoDTO>> getPhotoByBno(@PathVariable("bno") Integer bno) {

		List<PhotoDTO> list = new ArrayList<>();

		ResponseEntity<List<PhotoDTO>> result = null;

		log.info("selected bno is... " + bno);
		list = pService.getListByBoard(bno);
		log.info("list of photo=== " + list);

		if (list.isEmpty()) {
			result = new ResponseEntity<List<PhotoDTO>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result = new ResponseEntity<List<PhotoDTO>>(list, HttpStatus.OK);

		return result;
	}

	@GetMapping("/getPhotoByUid/{uid}")
	public @ResponseBody ResponseEntity<List<PhotoDTO>> getPhotoByUid(@PathVariable("uid") String uid) {
		List<PhotoDTO> list = new ArrayList<>();

		ResponseEntity<List<PhotoDTO>> result = null;

		log.info("selected uid is... " + uid);
		list = pService.getListByMember(uid);
		log.info("list of photo=== " + list);

		if (list.isEmpty()) {
			result = new ResponseEntity<List<PhotoDTO>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result = new ResponseEntity<List<PhotoDTO>>(list, HttpStatus.OK);

		return result;
	}
	
	// deletion of photo by pno
	@DeleteMapping("/deletePhoto/{pno}")
	public  @ResponseBody ResponseEntity<Integer> deletePhotoList(@PathVariable("pno") Integer pno){
		
		int removeCount;
		removeCount = pService.remove(pno);
		return new ResponseEntity<Integer>(removeCount, HttpStatus.OK);
	}
}

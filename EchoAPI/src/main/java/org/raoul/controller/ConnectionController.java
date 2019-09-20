package org.raoul.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
@CrossOrigin
@RequestMapping("/connect")
public class ConnectionController {

	@GetMapping("/test")
	public ResponseEntity<String> test(){
		
		log.info("connection test");
		return  new ResponseEntity<>("Connected", HttpStatus.OK);
		
	}
}

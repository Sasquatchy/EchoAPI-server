package org.raoul.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.raoul.domain.PhotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.java.Log;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Log
public class PhotoMapperTests {

	@Autowired
	private PhotoMapper pMapper;
	
	@Test
	public void testGetPhoto() {
		PhotoDTO dto = pMapper.select(200);
		log.info(dto.toString());
	}
	
	@Test
	public void testGetPhotoByBno() {
		List<PhotoDTO> list = pMapper.findListByBoard(286);
		log.info(list.toString());
	}
	
	@Test
	public void testInsert() {
		PhotoDTO dto = new PhotoDTO(null, "3333test44444", "테스트.jpg","/",null,286,"baba");
		pMapper.insert(dto);
	}
}

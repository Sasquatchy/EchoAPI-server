package org.raoul.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.java.Log;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Log
public class FrameMapperTests {

	@Autowired
	FrameMapper fMapper;
	
	@Test
	public void testGetLast20Photos() {
		log.info(fMapper.getLast20Photos("00000001").toString());
	}
}

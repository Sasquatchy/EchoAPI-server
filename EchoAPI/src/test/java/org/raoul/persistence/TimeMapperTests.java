package org.raoul.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.raoul.persistence.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.java.Log;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Log
public class TimeMapperTests {

	@Autowired
	TimeMapper tmapper;
	// Test dummy to verify if DB is connected
	@Test
	public void testGetTime() {
		log.info(tmapper.getTime());
	}
}

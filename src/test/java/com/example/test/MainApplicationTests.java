package com.example.test;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = MainApplication.class)
public class MainApplicationTests {

	public MainApplicationTests(){

	}

	private static Logger log = LoggerFactory
			.getLogger(MainApplicationTests.class);

	@Test
	public void testAddforP1(){
		System.out.println("testAddforP1--------------------");

	}

}

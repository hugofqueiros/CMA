package org.pismofinal.cma.tests;

import java.io.IOException;

import org.junit.Test;

public class AllTests {

	@Test
	void alltest() throws IOException {
		Tests tests = new Tests();
		tests.testXMLValidator();
		
		XMLParserTest xmlParserTest = new XMLParserTest();
		xmlParserTest.testXMLParserDOM();
		xmlParserTest.testXMLParserSAX();
	}
}

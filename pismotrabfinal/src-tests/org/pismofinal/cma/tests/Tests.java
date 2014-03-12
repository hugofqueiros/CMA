package org.pismofinal.cma.tests;

import java.io.File;

import org.junit.Test;
import org.pismofinal.cma.files.XMLValidator;

public class Tests {
	
	@Test
	public void testXMLValidator() {
		String schemaURI = null;
		XMLValidator val = new XMLValidator(schemaURI);
		
		File schemaFile = null;
		XMLValidator val2 = new XMLValidator(schemaFile);
	}
}

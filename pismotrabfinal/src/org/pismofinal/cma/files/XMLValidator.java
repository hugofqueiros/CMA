package org.pismofinal.cma.files;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


public class XMLValidator {
		
	private final File schemaFile;
	private boolean canBeApplied = false;

	public XMLValidator(String schemaURI) {
		this(new File(schemaURI));
	}
	
	public XMLValidator(File schemaFile) {
		this.schemaFile = schemaFile;
		if (schemaFile.exists()) {
			canBeApplied = true;
		} else {
			canBeApplied = false;
			System.err.println("Schema file can't be found!");
		}
	}
	
	public boolean validateXMLFile(String xmlURI) throws Exception {
		return validateXMLFile(new File(xmlURI));
	}

	private boolean validateXMLFile(File xmlFile) throws Exception {
		if(canBeApplied) {
			Source schemaSource = new StreamSource(schemaFile);
			Source xmlSource = new StreamSource(xmlFile);
			
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(schemaSource);
			Validator validator = schema.newValidator();
			
			try{
				validator.validate(xmlSource);
				//Result result = new 
				System.out.println(xmlSource.getSystemId() + " is valid");
				return true;
			} catch (SAXException e) {
				System.out.println(xmlSource.getSystemId() + " NOT valid");
				System.out.println("Reason; " + e.getLocalizedMessage());
				return false;
			}
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public boolean isCanBeApplied() {
		return canBeApplied;
	}
}

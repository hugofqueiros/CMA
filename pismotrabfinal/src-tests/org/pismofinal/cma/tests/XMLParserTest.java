package org.pismofinal.cma.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.pismofinal.cma.Agente;
import org.pismofinal.cma.Apartamento;
import org.pismofinal.cma.EspacoComercial;
import org.pismofinal.cma.Moradia;
import org.pismofinal.cma.files.XMLParser;

public class XMLParserTest {
	
	@Test
	public void testXMLParserSAX() {
		
		System.out.println("########### SAX PARSER ##############");
		
		XMLParser xmlParser = new XMLParser();
		xmlParser.parseXMLFileSAX();
		
		System.out.println("Size list Agente: " + xmlParser.getListAgente().size());
		System.out.println("Size list Apartamento: " + xmlParser.getListImoveis().size());
//		System.out.println("Size list Moradia: " + xmlParser.getListMoradia().size());
//		System.out.println("Size list Espaço Comercial: " + xmlParser.getListEspacoComercial().size());
		
		assertEquals(2, xmlParser.getListAgente().size());
//		assertEquals(1, xmlParser.getListApartamento().size());
//		assertEquals(1, xmlParser.getListMoradia().size());
//		assertEquals(1, xmlParser.getListEspacoComercial().size());
		
		for (Agente agente : xmlParser.getListAgente()) {
			System.out.println(agente.toString());
		}
//		for (Apartamento apart : xmlParser.getListApartamento()) {
//			System.out.println(apart.toString());
//		}
//		for (Moradia moradia : xmlParser.getListMoradia()) {
//			System.out.println(moradia.toString());
//		}
//		for (EspacoComercial espcom : xmlParser.getListEspacoComercial()) {
//			System.out.println(espcom.toString());
//		}
	}
	
	@Test
	public void testXMLParserDOM() {
		
		System.out.println("########### DOM PARSER ##############");
		
		XMLParser xmlParser = new XMLParser();
		
		xmlParser.parseXMLFileDOM();
		
		ArrayList<Agente> listAgente = xmlParser.getListAgente();
		for (Object agente : listAgente) {
			System.out.println(agente.toString());
		}
		
//		ArrayList<Moradia> listMoradia = xmlParser.getListMoradia();
//		for (Moradia moradia : listMoradia) {
//			System.out.println(moradia.toString());
//		}
//		
//		ArrayList<Apartamento> listApartamento = xmlParser.getListApartamento();
//		for (Apartamento apartamento : listApartamento) {
//			System.out.println(apartamento.toString());
//		}
//		
//		ArrayList<EspacoComercial> listEspCom = xmlParser.getListEspacoComercial();
//		for (EspacoComercial espacoComercial : listEspCom) {
//			System.out.println(espacoComercial.toString());
//		}
	}
}

package org.pismofinal.cma.files;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.pismofinal.cma.Agente;
import org.pismofinal.cma.Apartamento;
import org.pismofinal.cma.EspacoComercial;
import org.pismofinal.cma.Imovel;
import org.pismofinal.cma.Moradia;
import org.pismofinal.cma.Visitas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses xml file
 * 
 * It has 2 implemeentations of xml parsing using DOM and SAX
 * 
 * more on DOM vs SAX on:
 * http://pic.dhe.ibm.com/infocenter/tpfhelp/current/index
 * .jsp?topic=%2Fcom.ibm.ztpf-ztpfdf.doc_put.cur%2Fgtpx1%2Fdomsax.html
 * 
 * @author hfq
 */
public class XMLParser implements Serializable {

	private static final long serialVersionUID = 3674844435818344683L;

	private File xmlFile;

	private ArrayList<Agente> listAgente;
	private ArrayList<Imovel> listImovel;

	private static final String XML_AGENTE_NAME = "agente";
	private static final String XML_APARTAMENTO_NAME = "apartamento";
	private static final String XML_MORADIA_NAME = "moradia";
	private static final String XML_ESPACO_COMERCIAL_NAME = "espaco-comercial";

	/**
	 * Default constructor
	 */
	public XMLParser() {
		this(new File(".\\src\\assets\\SGAI.XML"));
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public XMLParser(String file) throws IOException {
		this(new File(file));
	}

	/**
	 * @param xmlFile
	 */
	public XMLParser(File xmlFile) {
		if (xmlFile.exists()) {
			setXmlFile(xmlFile);
			// FIXME - check if object is already serialized
			// ObjectOutputStream
			// parseXMLFileDOM();
		} else {
			System.err.println("File does not exist");
			new JOptionPane(xmlFile.toString() + " does not exist");
			return;
		}
	}

	/**
	 * Parse xml file with sax parser - Event-based parser (sequence of events)
	 * - SAX parses the file at it reads i.e. Parses node by node - No memory
	 * constraints as it does not store the XML content in the memory - SAX is
	 * read only i.e. can't insert or delete the node - Use SAX parser when
	 * memory content is large. - SAX reads the XML file from top to bottom and
	 * backward navigation is not possible - Faster at runtime.
	 */
	public void parseXMLFileSAX() {

		listAgente = new ArrayList<Agente>();
		listImovel = new ArrayList<Imovel>();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = spf.newSAXParser();
			MySAXHandler handler = new MySAXHandler();

			saxParser.parse(new File(".\\src\\assets\\SGAI.XML"), handler);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse xml file with w3c.dom package - Document Object Model (DOM) - Tree
	 * model parser(Object based) (Tree of nodes) - Stores the entire xml
	 * document into memory before processing - Has memory constraints since it
	 * loads the whole XML file before parsing - DOM is read and wirte (can
	 * insert or delete the node) - Normally if the XML content is small, prefer
	 * DOM parser - Backward and forward search is possible for searching the
	 * tags and evaluation of the information inside the tags. This gives the
	 * ease of navigation. - Preserves comments - Generally slower than SAX in
	 * runtime
	 */
	public void parseXMLFileDOM() {

		listAgente = new ArrayList<>();
		listImovel = new ArrayList<Imovel>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			Element docElement = doc.getDocumentElement();

			System.out.println("Root of xml: "
					+ doc.getDocumentElement().getNodeName());

			NodeList nl = docElement.getChildNodes();
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0, len = nl.getLength(); i < len; ++i) {
					if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) nl.item(i);

						NodeList nlMorada, nlRua, nlLocalidade, nlQuartos, nlPreco, nlAgRespId, nlDistMin;
						Element elMor;
						String rua, localidade, agente;
						int quartos, distMin;
						float preco;

						switch (el.getNodeName()) {
						case XML_AGENTE_NAME:
							String idAgente = el.getAttribute("id");

							NodeList nlName = el.getElementsByTagName("nome");
							String name = nlName.item(0).getFirstChild()
									.getTextContent();
							listAgente.add(new Agente(idAgente, name, new ArrayList<Visitas>()));
							break;
						case XML_APARTAMENTO_NAME:
							String idApart = el.getAttribute("id");

							nlMorada = el.getElementsByTagName("morada");
							elMor = (Element) nlMorada.item(0);
							nlRua = elMor.getElementsByTagName("rua");
							rua = nlRua.item(0).getFirstChild()
									.getTextContent();
							nlLocalidade = elMor
									.getElementsByTagName("localidade");
							localidade = nlLocalidade.item(0).getFirstChild()
									.getTextContent();

							nlQuartos = el.getElementsByTagName("quartos");
							quartos = Integer.parseInt(nlQuartos.item(0)
									.getFirstChild().getTextContent());

							nlPreco = el.getElementsByTagName("preco");
							preco = Float.parseFloat(nlPreco.item(0)
									.getFirstChild().getTextContent());

							nlAgRespId = el
									.getElementsByTagName("agente-responsavel");
							agente = nlAgRespId.item(0).getFirstChild()
									.getTextContent();

							nlDistMin = el
									.getElementsByTagName("distancia-em-minutos");
							distMin = Integer.parseInt(nlDistMin.item(0)
									.getFirstChild().getTextContent());

							for (Agente a : listAgente) {
								if (a.equals(agente))
									listImovel
									.add(new Apartamento(idApart, rua,
											localidade, quartos, preco, a,
											distMin, false, new ArrayList<Visitas>()));
							}
							break;
						case XML_MORADIA_NAME:
							String idMor = el.getAttribute("id");

							nlMorada = el.getElementsByTagName("morada");
							elMor = (Element) nlMorada.item(0);
							nlRua = elMor.getElementsByTagName("rua");
							rua = nlRua.item(0).getFirstChild()
									.getTextContent();
							nlLocalidade = elMor
									.getElementsByTagName("localidade");
							localidade = nlLocalidade.item(0).getFirstChild()
									.getTextContent();

							nlQuartos = el.getElementsByTagName("quartos");
							quartos = Integer.parseInt(nlQuartos.item(0)
									.getFirstChild().getTextContent());

							NodeList nlFrentes = el
									.getElementsByTagName("frentes");
							int frentes = Integer.parseInt(nlFrentes.item(0)
									.getFirstChild().getTextContent());

							nlPreco = el.getElementsByTagName("preco");
							preco = Float.parseFloat(nlPreco.item(0)
									.getFirstChild().getTextContent());

							nlAgRespId = el
									.getElementsByTagName("agente-responsavel");
							agente = nlAgRespId.item(0).getFirstChild()
									.getTextContent();

							nlDistMin = el
									.getElementsByTagName("distancia-em-minutos");
							distMin = Integer.parseInt(nlDistMin.item(0)
									.getFirstChild().getTextContent());

							for (Agente a : listAgente) {
								if (a.equals(agente))
									listImovel.add(new Moradia(idMor, rua, localidade,
											quartos, frentes, preco, a, distMin, false, new ArrayList<Visitas>()));
							}
							break;
						case XML_ESPACO_COMERCIAL_NAME:
							String idEspCom = el.getAttribute("id");

							nlMorada = el.getElementsByTagName("morada");
							elMor = (Element) nlMorada.item(0);
							nlRua = elMor.getElementsByTagName("rua");
							rua = nlRua.item(0).getFirstChild()
									.getTextContent();
							nlLocalidade = elMor
									.getElementsByTagName("localidade");
							localidade = nlLocalidade.item(0).getFirstChild()
									.getTextContent();

							NodeList nlAreaBruta = el
									.getElementsByTagName("area-bruta");
							int areaBruta = Integer.parseInt(nlAreaBruta
									.item(0).getFirstChild().getTextContent());

							nlPreco = el.getElementsByTagName("preco");
							preco = Float.parseFloat(nlPreco.item(0)
									.getFirstChild().getTextContent());

							nlAgRespId = el
									.getElementsByTagName("agente-responsavel");
							agente = nlAgRespId.item(0).getFirstChild()
									.getTextContent();

							nlDistMin = el
									.getElementsByTagName("distancia-em-minutos");
							distMin = Integer.parseInt(nlDistMin.item(0)
									.getFirstChild().getTextContent());

							for (Agente a : listAgente) {
								if (a.equals(agente))
									listImovel.add(new EspacoComercial(
											idEspCom, rua, localidade, areaBruta,
											preco, a, distMin, false, new ArrayList<Visitas>()));
							}
							break;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author hfq
	 */
	public class MySAXHandler extends DefaultHandler {
		private static final String TAG_AGENTE = "agente";
		private static final String TAG_APARTAMENTO = "apartamento";
		private static final String TAG_MORADIA = "moradia";
		private static final String TAG_ESPACOCOMERCIAL = "espaco-comercial";

		private boolean bAgente = false;
		private boolean bApart = false;
		private boolean bMorad = false;
		private boolean bEspCom = false;

		private String tempVal;

		private Agente agente;
		private Apartamento apart;
		private Moradia moradia;
		private EspacoComercial espCom;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) {
			if (qName.equalsIgnoreCase(TAG_AGENTE)) {
				bAgente = true;
				agente = new Agente();
				agente.setId(attributes.getValue("id"));
			} else if (qName.equalsIgnoreCase(TAG_APARTAMENTO)) {
				bApart = true;
				apart = new Apartamento();
				apart.setId(attributes.getValue("id"));
			} else if (qName.equalsIgnoreCase(TAG_MORADIA)) {
				bMorad = true;
				moradia = new Moradia();
				moradia.setId(attributes.getValue("id"));
			} else if (qName.equalsIgnoreCase(TAG_ESPACOCOMERCIAL)) {
				bEspCom = true;
				espCom = new EspacoComercial();
				espCom.setId(attributes.getValue("id"));
			}
		}

		@Override
		public void characters(char ch[], int start, int length) {
			// tempVal = new String(ch, start, length).trim();
			tempVal = new String(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (qName.equalsIgnoreCase(TAG_AGENTE) && bAgente) {
				listAgente.add(agente);
				bAgente = false;
			}
			else if (qName.equalsIgnoreCase(TAG_APARTAMENTO)) {
				listImovel.add(apart);
				bApart = false;
			}
			else if (qName.equalsIgnoreCase(TAG_MORADIA)) {
				listImovel.add(moradia);
				bMorad = false;
			}
			else if (qName.equalsIgnoreCase(TAG_ESPACOCOMERCIAL)) {
				listImovel.add(espCom);
				bEspCom = false;
			}
			else if (qName.equalsIgnoreCase("nome")) {
				agente.setName(tempVal);
			}
			else if (qName.equalsIgnoreCase("rua")) {
				if (bApart) {
					apart.getMorada().setRua(tempVal);
				}
				else if (bMorad)
					moradia.getMorada().setRua(tempVal);
				else if (bEspCom)
					espCom.getMorada().setRua(tempVal);
			}
			else if (qName.equalsIgnoreCase("localidade")) {
				if (bApart)
					apart.getMorada().setLocalidade(tempVal);
				else if (bMorad)
					moradia.getMorada().setLocalidade(tempVal);
				else if (bEspCom)
					espCom.getMorada().setLocalidade(tempVal);
			}
			else if (qName.equalsIgnoreCase("quartos")) {
				if(bApart)
					apart.setQuartos(Integer.parseInt(tempVal));
				else if (bMorad)
					moradia.setQuartos(Integer.parseInt(tempVal));
			}
			else if (qName.equalsIgnoreCase("preco")) {
				if (bApart)
					apart.setPreco(Float.parseFloat(tempVal));
				else if (bMorad)
					moradia.setPreco(Float.parseFloat(tempVal));
				else if (bEspCom)
					espCom.setPreco(Float.parseFloat(tempVal));
			}
			else if (qName.equalsIgnoreCase("agente-responsavel")) {
				// TODO agente
				for (Agente a : listAgente) {
					if (a.getId().equals(tempVal)) {
						if (bApart)
							apart.setAgenteResponsavel(a);
						else if (bMorad)
							moradia.setAgenteResponsavel(a);
						else if (bEspCom)
							espCom.setAgenteResponsavel(a);	
					}
				}
			}
			else if (qName.equalsIgnoreCase("distancia-em-minutos")) {
				if(bApart)
					apart.setDistMin(Integer.parseInt(tempVal));
				else if (bMorad)
					moradia.setDistMin(Integer.parseInt(tempVal));
				else if (bEspCom)
					espCom.setDistMin(Integer.parseInt(tempVal));
			}
			else if (qName.equalsIgnoreCase("frentes"))
				moradia.setFrentes(Integer.parseInt(tempVal));
			else if (qName.equalsIgnoreCase("area-bruta"))
				espCom.setAreaBruta(Integer.parseInt(tempVal));
		}
	}

	/**
	 * @return the xmlFile
	 */
	public File getXmlFile() {
		return xmlFile;
	}

	/**
	 * @param xmlFile
	 *            the xmlFile to set
	 */
	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * @return
	 */
	public ArrayList<Agente> getListAgente() {
		return this.listAgente;
	}

	/**
	 * @return
	 */
	public ArrayList<Imovel> getListImoveis() {
		return this.listImovel;
	}

	public void setListImoveis(ArrayList<Imovel> imoveis) {
		this.listImovel = imoveis;		
	}

	public void setListAgentes(ArrayList<Agente> agentes) {
		this.listAgente = agentes;
	}
}

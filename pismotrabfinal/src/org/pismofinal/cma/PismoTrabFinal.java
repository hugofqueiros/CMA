package org.pismofinal.cma;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

import org.pismofinal.cma.files.XMLParser;

public class PismoTrabFinal {

	static XMLParser parser;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		parser = new XMLParser();

		File fileImovel = null;
		File fileAgente = null;
		boolean bool = false;

		try{
			// create new files
			fileImovel = new File(".\\src\\assets\\imoveis");
			fileAgente = new File(".\\src\\assets\\agentes");

			// tests if file exists
			if (fileImovel.exists() && fileAgente.exists()) {
				bool = true;
			}

		}catch(Exception e){
			// if any error occurs
			e.printStackTrace();
		}

		if (bool) {
			ArrayList<Imovel> imoveis = null;
			ArrayList<Agente> agentes = null;

			try {
				ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(fileImovel));
				ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(fileAgente));

				imoveis = (ArrayList<Imovel>) in1.readObject();
				agentes = (ArrayList<Agente>) in2.readObject();

				in1.close();
				in2.close();
			} catch (IOException i) {
			} catch (ClassNotFoundException c) {
				System.out.println("Employee class not found");
			}
			parser.setListImoveis(imoveis);
			parser.setListAgentes(agentes);
		} else {
			parser.parseXMLFileSAX();			
		}

		menu();

	}

	private static void menu() {

		int opt = 0;
		try {
			opt = Integer.parseInt(JOptionPane.showInputDialog(null, "Bem-Vindo\n" +
					"Menu:\n" +
					"1) Registar imovel\n" +
					"2) Registar agente\n" +
					"3) Lista de imoveis\n" +
					"4) Lista de agentes\n" +
					"5) Lista de imoveis de um agente\n" +
					"6) Agendar visita\n" +
					"7) Lista de visitas de um agente\n" +
					"8) Venda de um imovel\n" +
					"9) Lista de imoveis vendidos de um agente\n" +
					"10) Calculo das comissões\n" +
					"11) Ler ficheiro\n" +
					"12) Salvar ficheiro\n" +
					"Introduza a sua opção:"));

		} catch (NumberFormatException e) {
			sair();
		}

		switch (opt) {
		case 1:
			opt1();
			break;
		case 2:
			opt2();
			break;
		case 3:
			opt3();
			break;
		case 4:
			opt4();
			break;
		case 5:
			opt5();
			break;
		case 6:
			opt6();
			break;
		case 7:
			opt7();
			break;
		case 8:
			opt8();
			break;
		case 9:
			opt9();
			break;
		case 10:
			opt10();
			break;
		case 11:
			opt11();
			break;
		case 12:
			opt12();
			break;
		default:
			menu();
			break;
		}		
	}

	/**
	 * Registar imovel
	 */
	private static void opt1() {

		int tipo = 0; 
		try {
			tipo = Integer.parseInt(JOptionPane.showInputDialog(
					"Tipo de imovel:\n" +
							"1 - Apartamento\n" + 
							"2 - Moradia\n" + 
							"3 - Espaço comercial\n" + 
					"Introduza a sua opção:"));
		} catch (NumberFormatException e) {
			menu();
		}
		String rua,localidade;
		int nQuartos = 0;
		int nFrentes = 0;
		float preco;
		String agente;

		switch (tipo) {
		case 1:
			Apartamento a = new Apartamento();
			rua = JOptionPane.showInputDialog("Rua");
			localidade = JOptionPane.showInputDialog("Localidade:");
			try {
				nQuartos = Integer.parseInt(JOptionPane.showInputDialog("Numero de quartos:"));
			} catch (InputMismatchException e) {
				nQuartos = Integer.parseInt(JOptionPane.showInputDialog("Numero de quartos (inteiro):"));
			} catch (NoSuchElementException e) {
				nQuartos = Integer.parseInt(JOptionPane.showInputDialog("Numero de quartos:\n"+"Campo não pode ser vazio."));
			} catch (IllegalStateException e) {
			}
			preco = Float.parseFloat(JOptionPane.showInputDialog("Preço:"));
			agente = JOptionPane.showInputDialog("Agente responsavel:");	
			int tempo = Integer.parseInt(JOptionPane.showInputDialog("Deslocação em minutos (ida e volta):"));

			a.getMorada().setRua(rua);
			a.getMorada().setLocalidade(localidade);
			a.setQuartos(nQuartos);
			a.setPreco(preco);
			for (Agente ag : parser.getListAgente())
				if (ag.getId().equals(agente))
					a.setAgenteResponsavel(ag);
				else
					System.out.println("Esse Agente não existe.");
			a.setDistMin(tempo);
			a.setId("AP00"+(Apartamento.getCount()+1));

			parser.getListImoveis().add(a);
			JOptionPane.showMessageDialog(null, "Imovel insirido");
			menu();
			break;
		case 2:
			Moradia m = new Moradia();

			rua = JOptionPane.showInputDialog("Rua:");
			localidade = JOptionPane.showInputDialog("Localidade:");
			nQuartos = Integer.parseInt(JOptionPane.showInputDialog("Numero de quartos:"));
			nFrentes = Integer.parseInt(JOptionPane.showInputDialog("Numero de frentes:"));
			preco = Float.parseFloat(JOptionPane.showInputDialog("Preço"));
			agente = JOptionPane.showInputDialog("Agente responsavel:");
			tempo = Integer.parseInt(JOptionPane.showInputDialog("Deslocação em minutos (ida e volta):"));

			m.getMorada().setRua(rua);
			m.getMorada().setLocalidade(localidade);
			m.setQuartos(nQuartos);
			m.setFrentes(nFrentes);
			m.setPreco(preco);
			for (Agente ag : parser.getListAgente())
				if (ag.getId().equals(agente))
					m.setAgenteResponsavel(ag);
			m.setDistMin(tempo);
			m.setId("MD00"+(Moradia.getCount()+1));

			parser.getListImoveis().add(m);
			menu();
			break;
		case 3:
			EspacoComercial c = new EspacoComercial();

			rua = JOptionPane.showInputDialog("Rua:");
			localidade = JOptionPane.showInputDialog("Localidade:");
			int area = Integer.parseInt(JOptionPane.showInputDialog("Area bruta:"));
			preco = Float.parseFloat(JOptionPane.showInputDialog("Preço:"));
			agente = JOptionPane.showInputDialog("Agente responsavel:");
			tempo = Integer.parseInt(JOptionPane.showInputDialog("Deslocação em minutos (ida e volta):"));

			c.getMorada().setRua(rua);
			c.getMorada().setLocalidade(localidade);
			c.setAreaBruta(area);
			c.setPreco(preco);
			for (Agente ag : parser.getListAgente())
				if (ag.getId().equals(agente))
					c.setAgenteResponsavel(ag);
			c.setDistMin(tempo);
			c.setId("EC00"+(EspacoComercial.getCount()+1));

			parser.getListImoveis().add(c);
			JOptionPane.showMessageDialog(null, "Imovel insirido");
			menu();
			break;
		}
		menu();
	}
	/**
	 * Registar agente
	 */
	private static void opt2() {

		Agente a = new Agente();
		String nome = JOptionPane.showInputDialog("Nome:");

		a.setName(nome);
		a.setId("AG0"+(parser.getListAgente().size()+1));

		parser.getListAgente().add(a);
		JOptionPane.showMessageDialog(null, "Agente insirido");
		menu();
	}
	/**
	 * Lista de imoveis por ordem crescente de preço
	 */
	private static void opt3() {

		Collections.sort(parser.getListImoveis(), new Comparator<Imovel>() {

			@Override
			public int compare(Imovel arg0, Imovel arg1) {
				return (int)(arg0.getPreco()-arg1.getPreco());
			}
		});

		String finalStr = "";
		for (Imovel i : parser.getListImoveis())
			finalStr = finalStr+i.getId()+" - "+i.getPreco()+"\n";
		JOptionPane.showMessageDialog(null, finalStr);
		menu();
	}
	/**
	 * Lista de agentes
	 */
	private static void opt4() {

		String finalStr = "";
		for (Agente a : parser.getListAgente())
			finalStr = finalStr+a.getId()+" - "+a.getName()+"\n";
		JOptionPane.showMessageDialog(null, finalStr);
		menu();
	}
	/**
	 * Lista de imoveis de um agente
	 */
	private static void opt5() {

		String listaAgente = "";
		for (Agente a : parser.getListAgente()) {
			listaAgente = listaAgente+a.getId()+" - "+a.getName()+"\n";
		}

		String id = JOptionPane.showInputDialog("Agentes:\n" +listaAgente+"\nIntroduza o ID do agente:");		

		if (id == null) {
			menu();
		} else {
			String finalStr = "";
			for (Imovel i : parser.getListImoveis()) {
				if (i.getAgenteResponsavel().getId().equalsIgnoreCase(id)) {
					finalStr = finalStr+"- "+i.toString()+"\n";
				} 
			}

			if (finalStr.equals("")) {
				JOptionPane.showMessageDialog(null, "Id errado");
				opt5();
			} else
				JOptionPane.showMessageDialog(null, finalStr);
		}
		menu();
	}
	/**
	 * Agendar visita
	 */
	private static void opt6() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

		Date currentDate = new Date();
		Date currentHour = new Date();
		Date insertDate = null;
		Date insertHour = null;

		String finalStr = "";
		for (Imovel i : parser.getListImoveis())
			finalStr = finalStr+i.getId()+" - "+i.getMorada()+" - €"+i.getPreco()+"\n";

		String id = "";
		String insertId = JOptionPane.showInputDialog(finalStr+"\nIntroduza o ID do imovel que pretende visitar:");

		if (insertId == null) {
			menu();
			return;
		} else {
			for (Imovel i : parser.getListImoveis()) {
				if (i.getId().equalsIgnoreCase(insertId)) {
					id = i.getId();
					break;
				}
			}
			if (id.equals("")){
				JOptionPane.showMessageDialog(null, "id incorrecto");
				menu();
				return;
			}
		}

		String data = JOptionPane.showInputDialog("Data (dd/mm/aaaa)");
		if (data == null)
			menu();
		try {
			if (currentDate.before(insertDate))
				insertDate = dateFormat.parse(data);
			else {
				JOptionPane.showMessageDialog(null, "Data invalida.");
				menu();
				return;
			}
		} catch (ParseException e1) {
			JOptionPane.showMessageDialog(null, "formato da data errado");
			menu();
		}

		String hora = JOptionPane.showInputDialog("Hora (hh:mm)");
		if (hora == null)
			menu();
		try {
			if (hourFormat.parse("9:00").before(insertHour) && hourFormat.parse("18:00").after(insertHour))
				insertHour = hourFormat.parse(hora);
			else {
				JOptionPane.showMessageDialog(null, "Hora invalida.");
				menu();
				return;
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "formato da hora errado");
			menu();
		}

		boolean dateFlag = false;
		boolean hourFlag = false;

		try {
			boolean isPossible = false;
			for (Imovel i : parser.getListImoveis()) {
				if (i.getId().equalsIgnoreCase(id)) {
					if (i.getVisitas().isEmpty()) {
						i.getVisitas().add(new Visitas(id.toUpperCase(), insertDate, insertHour));
					} else {
						//						Collections.sort(i.getVisitas(), new Comparator<Visitas>() {
						//
						//							@Override
						//							public int compare(Visitas o1, Visitas o2) {
						//								return (int) (o1.getData().getTime()-o2.getData().getTime());
						//							}
						//						});
						for (Visitas v : i.getVisitas()) {
							if (insertDate == v.getData()) {
								for (Visitas vs : i.getVisitas()) {
									if (new Date(v.getHora().getTime()+(i.getTempoVisita()*60*60000)).before(insertHour) && insertHour.before(new Date(vs.getHora().getTime()))){
										isPossible = true;
										i.getVisitas().add(new Visitas(i.getId(), insertDate, insertHour));
										break;
									}
								}	
								//	end < my < start
							} 
						}
					}
				}
			}				
		} catch (HeadlessException e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "Visita marcada");

		menu();
	}

	/**
	 * Lista de visitas de um agente
	 */
	private static void opt7() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

		boolean exist = false;

		String listaAgente = "";
		for (Agente a : parser.getListAgente()) {
			listaAgente = listaAgente+a.getId()+" - "+a.getName()+"\n";
		}
		String id = JOptionPane.showInputDialog("Agentes:\n" +listaAgente+"\nIntroduza o ID do agente que pretende consultar:");		
		if (id == null) {
			menu();
			return;
		} else {
			String finalStr = "";
			for (Imovel im : parser.getListImoveis()) {			
				if (im.getAgenteResponsavel().getId().equals(id)) {
					exist = true;
					for (Visitas v : im.getVisitas())
						finalStr = finalStr+v.getImovelId()+" - "+dateFormat.format(v.getData())+" - "+hourFormat.format(v.getHora());
				}
			}

			if (exist) {
				if (!finalStr.equals(""))
					JOptionPane.showMessageDialog(null, finalStr);
				else 
					JOptionPane.showMessageDialog(null, "O agente "+id+" não tem visitas agendadas.");
			} else {
				JOptionPane.showMessageDialog(null, "agente invalido");
			}
		}
		menu();
	}
	/**
	 * Venda de um imovel
	 */
	private static void opt8() {

		boolean exist = false;

		String finalStr = "";
		for (Imovel i : parser.getListImoveis()) {
			if (!i.isVendido())
				finalStr = finalStr+i.getId()+" - "+i.getMorada().getRua()+"\n";
		}

		String id  = JOptionPane.showInputDialog(finalStr+"\nQual destes imoveis foi vendido?");
		if (id == null) {
			menu();
			return;
		} else {
			for (Imovel i : parser.getListImoveis()){
				if (i.getId().equals(id) && !i.isVendido()) {
					exist = true;
					i.setVendido(true);
					break;
				}
			}

			if (exist)
				JOptionPane.showMessageDialog(null, "Venda de imovel registada.");
			else
				JOptionPane.showMessageDialog(null, "imovel invalido");
		}
		menu();


	}
	/**
	 * Lista de imoveis vendidos de um agente
	 */
	private static void opt9() {


		String listaAgente = "";
		for (Agente a : parser.getListAgente()) {
			listaAgente = listaAgente+a.getId()+" - "+a.getName()+"\n";
		}
		String id = JOptionPane.showInputDialog("Agentes:\n" +listaAgente+"\nIntroduza o ID do agente que pretende consultar:");		
		if (id == null) {
			menu();
			return;
		} else {
			boolean exist = false;
			String finalStr = "";
			for (Imovel i : parser.getListImoveis()) {
				if (i.getAgenteResponsavel().getId().equals(id) && i.isVendido()) {
					exist = true;
					finalStr = finalStr+i.toString();
				}
			}
			if (exist) {
				JOptionPane.showMessageDialog(null, "Imoveis vendidos:\n"+finalStr);
			} else {
				JOptionPane.showMessageDialog(null, "Não existem imoveis vendidos do agente "+id);
			}
		}
		menu();
	}
	/**
	 * Calculo das comissões
	 */
	private static void opt10() {

		String listaAgente = "";
		for (Agente a : parser.getListAgente()) {
			listaAgente = listaAgente+a.getId()+" - "+a.getName()+"\n";
		}
		String id = JOptionPane.showInputDialog("Agentes:\n" +listaAgente+"\nIntroduza o ID do agente que pretende consultar:");		
		if (id == null) {
			menu();
			return;
		} else {
			String finalStr = "";
			for (Imovel i : parser.getListImoveis()) {
				if (i.getAgenteResponsavel().getId().equals(id) && i.isVendido())
					finalStr = finalStr+i.getAgenteResponsavel()+" - "+i.calculate()+"\n";
			}
			JOptionPane.showMessageDialog(null, finalStr);
		}
	}
	/**
	 * Ler ficheiro
	 */
	private static void opt11() {

		String fileStr = JOptionPane.showInputDialog("Introduza o nome do ficheiro:");
		if (fileStr == null) {
			menu();
			return;
		} else {
			File file = new File(".\\src\\assets\\"+fileStr);
			if (file.exists()) {
				parser = new XMLParser(file);
				parser.parseXMLFileSAX();			
			} else {
				JOptionPane.showMessageDialog(null, "Ficheiro inexistente.");
			}
		}

		menu();
	}
	/**
	 * Salvar ficheiro
	 */
	private static void opt12() {

		try {
			FileOutputStream fos = new FileOutputStream(".\\src\\assets\\imoveis");;
			ObjectOutputStream out = new ObjectOutputStream(fos);;

			out.writeObject(parser.getListImoveis());

			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileOutputStream fos = new FileOutputStream(".\\src\\assets\\agentes");
			ObjectOutputStream out = new ObjectOutputStream(fos);

			out.writeObject(parser.getListAgente());

			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu();
	}

	private static void sair () {

		try {
			FileOutputStream fos = new FileOutputStream(".\\src\\assets\\imoveis");;
			ObjectOutputStream out = new ObjectOutputStream(fos);;

			out.writeObject(parser.getListImoveis());

			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileOutputStream fos = new FileOutputStream(".\\src\\assets\\agentes");
			ObjectOutputStream out = new ObjectOutputStream(fos);

			out.writeObject(parser.getListAgente());

			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
		return;
	}
}

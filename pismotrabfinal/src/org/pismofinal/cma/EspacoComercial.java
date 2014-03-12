package org.pismofinal.cma;

import java.io.Serializable;
import java.util.ArrayList;

public class EspacoComercial extends Imovel implements Serializable {

	public static long tempoVisita = 30;
	private static float comissao = 5;
	private static int count;
	private int areaBruta;
	
	public EspacoComercial() {
		super(new Morada(), new Agente(), new ArrayList<Visitas>());
		count++;
	}

	public EspacoComercial(String id, String rua, String localidade,
			int areaBruta, float preco, Agente agenteResponsavelId, int distMin, boolean vendido, ArrayList<Visitas> visitas) {
		super(id, rua, localidade, preco, agenteResponsavelId, distMin, vendido, visitas);
		setAreaBruta(areaBruta);
		count++;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId());
		sb.append("\n" + getMorada().toString());
		sb.append("\nArea Bruta: " + getAreaBruta());
		sb.append("\nPreco: " + getPreco());
		sb.append("\nAgente Responsavel:\n" + getAgenteResponsavel());
		sb.append("\nDistancia em Minutos: " + getDistMin());
		return sb.toString();
	}

	/**
	 * @return the areaBruta
	 */
	public int getAreaBruta() {
		return areaBruta;
	}

	/**
	 * @param areaBruta the areaBruta to set
	 */
	public void setAreaBruta(int areaBruta) {
		this.areaBruta = areaBruta;
	}

	protected float comissao() {
		return comissao;
	}
	
	/**
	 * @return the tempoVisita
	 */
	public long getTempoVisita() {
		return tempoVisita;
	}
	
	@Override
	public int compare(Imovel o1, Imovel o2) {
		return (int)(o1.getPreco()-o2.getPreco());
	}

	public static int getCount() {
		return count;
	}
}

package org.pismofinal.cma;

import java.io.Serializable;
import java.util.ArrayList;

public class Apartamento extends Imovel implements Serializable {

	public static long tempoVisita = 60;
	private static int comissao = 3;
	private static int count;
	private int quartos;

	public Apartamento () {
		super(new Morada(), new Agente(), new ArrayList<Visitas>());
		count++;
	}

	public Apartamento(String id, String rua, String localidade, int quartos,
			float preco, Agente agenteResponsavel, int distMin, boolean vendido, ArrayList<Visitas> visitas) {
		super(id, rua, localidade, preco, agenteResponsavel, distMin, vendido, visitas);
		setQuartos(quartos);
		count++;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + getId());
		sb.append("\n" + getMorada().toString());
		sb.append("\nQuartos: " + getQuartos());
		sb.append("\nPreco: " + getPreco());
		sb.append("\nAgente Responsavel:\n" + getAgenteResponsavel());
		sb.append("\nDistancia em Minutos: " + getDistMin());
		return sb.toString();
	}

	/**
	 * @return the quartos
	 */
	public int getQuartos() {
		return quartos;
	}

	/**
	 * @param quartos the quartos to set
	 */
	public void setQuartos(int quartos) {
		this.quartos = quartos;
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

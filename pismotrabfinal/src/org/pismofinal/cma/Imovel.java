package org.pismofinal.cma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Imovel implements Comparator<Imovel>,Serializable {
	
	private String id;
	private Morada morada;
	private float preco;
	private Agente agenteResponsavel;
	private int distMin;
	private boolean vendido;
	ArrayList<Visitas> visitas;

	public Imovel() {
		super();
	}
	
	public Imovel(String id, String rua, String localidade,
			float preco, Agente agenteResponsavel, int distMin, boolean vendido, ArrayList<Visitas> visitas) {
		setId(id);
		setMorada(new Morada(rua, localidade));
		setPreco(preco);
		setAgenteResponsavel(agenteResponsavel);
		setDistMin(distMin);
		setVendido(vendido);
		setVisitas(visitas);
	}

	public Imovel(Morada morada, Agente agente, ArrayList<Visitas> visitas) {
		setMorada(morada);
		setAgenteResponsavel(agente);
		setVisitas(visitas);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the morada
	 */
	public Morada getMorada() {
		return morada;
	}

	/**
	 * @param morada
	 *            the morada to set
	 */
	public void setMorada(Morada morada) {
		this.morada = morada;
	}

	/**
	 * @return the preco
	 */
	public float getPreco() {
		return preco;
	}

	/**
	 * @param preco
	 *            the preco to set
	 */
	public void setPreco(float preco) {
		this.preco = preco;
	}

	/**
	 * @return the agenteResposavel
	 */
	public Agente getAgenteResponsavel() {
		return agenteResponsavel;
	}

	/**
	 * @param agenteResposavel
	 *            the agenteResposavel to set
	 */
	public void setAgenteResponsavel(Agente agenteResponsavel) {
		this.agenteResponsavel = agenteResponsavel;
	}

	/**
	 * @return the distMin
	 */
	public int getDistMin() {
		return distMin;
	}

	/**
	 * @param distMin the distMin to set
	 */
	public void setDistMin(int distMin) {
		this.distMin = distMin;
	}

	/**
	 * @return the vendido
	 */
	public boolean isVendido() {
		return vendido;
	}

	/**
	 * @param vendido the vendido to set
	 */
	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
	/**
	 * @return the visitas
	 */
	public ArrayList<Visitas> getVisitas() {
		return visitas;
	}

	/**
	 * @param visitas the visitas to set
	 */
	public void setVisitas(ArrayList<Visitas> visitas) {
		this.visitas = visitas;
	}

    protected abstract float comissao();
    
    public float calculate() {	
		return (getPreco()*comissao())/100;
	}
    
    abstract long getTempoVisita();
}

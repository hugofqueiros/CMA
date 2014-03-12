package org.pismofinal.cma;

import java.io.Serializable;
import java.util.Date;

public class Visitas implements Serializable {

	String imovelId;
	Date data;
	Date hora;
	/**
	 * @param imovelId
	 * @param data
	 * @param hora
	 */
	public Visitas(String imovelId, Date data, Date hora) {
		super();
		this.imovelId = imovelId;
		this.data = data;
		this.hora = hora;
	}
	/**
	 * 
	 */
	public Visitas() {
		super();
	}
	/**
	 * @return the imovelId
	 */
	public String getImovelId() {
		return imovelId;
	}
	/**
	 * @param imovelId the imovelId to set
	 */
	public void setImovelId(String imovelId) {
		this.imovelId = imovelId;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	/**
	 * @return the hora
	 */
	public Date getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(Date hora) {
		this.hora = hora;
	}
	
	
}

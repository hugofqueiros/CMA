package org.pismofinal.cma;

import java.io.Serializable;
import java.util.ArrayList;

public class Agente implements Serializable {
	
	private static int count;
	private String id;
	private String name;
	
	public Agente() {
		super();
		count++;
	}
	
	public Agente(String id, String name, ArrayList<Visitas> visitas) {
		setId(id);
		setName(name);
		count++;
	}
	
	@Override
	public String toString() {
		return ("Agente: \n" + "id - " + id + "\nNome: " + name);
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public static int getCount() {
		return count++;
	}
}

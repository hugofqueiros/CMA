package org.pismofinal.cma;

import java.io.Serializable;

public class Morada implements Serializable {
	private String rua;
	private String localidade;

	public Morada() {
		super();
	}

	public Morada(String rua, String localidade) {
		this.setRua(rua);
		this.setLocalidade(localidade);
	}

	@Override
	public String toString() {
		return getRua()+", "+getLocalidade();

	}

	/**
	 * @return the rua
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * @param rua
	 *            the rua to set
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * @return the localidade
	 */
	public String getLocalidade() {
		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
}
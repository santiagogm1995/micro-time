package com.santiago.micro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "CODAUTON" })
public class Province {

	@JsonProperty("CODPROV")
	private int codprov;
	@JsonProperty("NOMBRE_PROVINCIA")
	private String name;
	@JsonProperty("COMUNIDAD_CIUDAD_AUTONOMA")
	private String community;
	@JsonProperty("CAPITAL_PROVINCIA")
	private String capital;

	public Province() {
	}

	public Province(int codprov, String name, String community, String capital) {
		this.codprov = codprov;
		this.name = name;
		this.community = community;
		this.capital = capital;
	}

	public int getCodprov() {
		return codprov;
	}

	public void setCodprov(int codprov) {
		this.codprov = codprov;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getCapital() {
		return this.capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

}

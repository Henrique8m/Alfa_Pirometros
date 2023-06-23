package com.hrodriguesdev.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hrodriguesdev.utilitary.Log;

public class UnidadeMedida {
	private Long id;
	private String unidade;
	
	public Long getId() {
		return id;
	}
	public String getUnidade() {
		return unidade;
	}
	
	public UnidadeMedida(String unidade) {
		this.unidade = unidade;
	}
	
	public UnidadeMedida(ResultSet rs) {
		try {
			this.id = rs.getLong("id");
			this.unidade = rs.getString("unidade");
		}catch(SQLException e) {
			e.printStackTrace();
			Log.logString("UnidadeMedida", e.getMessage());
		}
	}
	
}

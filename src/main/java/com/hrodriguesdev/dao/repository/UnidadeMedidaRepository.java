package com.hrodriguesdev.dao.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.entities.UnidadeMedida;
import com.hrodriguesdev.utilitary.Log;

public class UnidadeMedidaRepository {
//	private Connection conn = null;
//	private Statement st = null;
	private ResultSet rs = null;
//	private PreparedStatement pst = null;
	
	public List<UnidadeMedida> findAll() {
		List<UnidadeMedida> list = new ArrayList<>();	
		rs = DB.getResultSet("SELECT * FROM alfaestoque.product;");		
			try {
				while(rs.next())
					list.add(new UnidadeMedida(rs));
					
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				Log.logString("RepositoryProducts", e.getMessage());
			}		
			finally {
				DB.closeResultSet(rs);
			}					
			return list;
		}

}

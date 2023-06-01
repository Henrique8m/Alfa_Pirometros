package com.hrodriguesdev.dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.dao.db.DB;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.entities.Empresa;
import com.hrodriguesdev.entities.Equipamento;

public class EquipamentoRepository {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Equipamento findById(Long id) throws ExceptionAlfa , DbException{
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");

			while (rs.next())
				if (rs.getLong("id") == id)
					return Equipamento.parseEquipamentoDois(rs);
			throw new ExceptionAlfa("Equipamento nao encontrado");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
	}

	public Equipamento table() {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(
					" ALTER TABLE alfaestoque.tb_equipamento, ADD COLUMN Instrumento VARCHAR(45) NULL DEFAULT   AFTER certificado, ADD COLUMN Fabricante VARCHAR(45) NULL DEFAULT   AFTER Instrumento;");

		} catch (DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return null;
	}

	public List<Equipamento> findAllByLaboratorio(boolean laboratorio) throws DbException, SQLException {
		List<Equipamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");

			while (rs.next())
				if (rs.getBoolean("laboratorio") == true)
					list.add(Equipamento.parseEquipamentoDois(rs));

		} catch (DbException | SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return list;

	}

	public List<Equipamento> findAll(String empressaName) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString(2) != null)
					if (rs.getString(2).equalsIgnoreCase(empressaName))
						list.add(Equipamento.parseEquipamentoDois(rs));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return list;

	}

	public List<Equipamento> findByName(String name) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString(2) != null) {
					String nameEmpresa = rs.getString(2).toUpperCase();
					if (nameEmpresa.contains(name.toUpperCase()) && rs.getBoolean("laboratorio") == true)
						list.add(Equipamento.parseEquipamentoDois(rs));
				}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}

	public List<Equipamento> findByIdEmpressa(Long id, Boolean laboratorio) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString("empresa_id") != null) {
					if (rs.getLong("empresa_id") == id)
						if (laboratorio) {
							if (!rs.getBoolean("laboratorio"))
								list.add(Equipamento.parseEquipamentoDois(rs));
						} else
							list.add(Equipamento.parseEquipamentoDois(rs));

				}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}

	public List<Equipamento> findAllNs(String ns) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString("ns") != null)
					if (rs.getString("ns").equalsIgnoreCase(ns))
						list.add(Equipamento.parseEquipamentoDois(rs));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;

	}

	public List<Equipamento> findAllPat(String pat) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString(8) != null)
					if (rs.getString(8).equalsIgnoreCase(pat))
						list.add(Equipamento.parseEquipamentoDois(rs));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return list;

	}

	public List<Equipamento> findByIdOrcamento(List<Long> equipamento_id) {
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				for (Long id : equipamento_id)
					if (rs.getLong("id") == id)
						list.add(Equipamento.parseEquipamentoDois(rs));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return list;
	}

	public List<Equipamento> findAll() {
		List<Equipamento> list = new ArrayList<>();
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");

			int i = 0;
			while (rs.next()) {
			}
			while (rs.previous() && i < 100) {
//					if(rs.getBoolean("fila") == false){
				i++;
				list.add(Equipamento.parseEquipamentoDois(rs));
//					}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return list;
	}

	public Equipamento findByNs(String ns) {
		Equipamento equipamento = null;
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next())
				if (rs.getString(7) != null)
					if (rs.getString("ns").equalsIgnoreCase(ns))
						equipamento = Equipamento.parseEquipamentoDois(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();

		}
		return equipamento;
	}

	public Long add(Equipamento equipamento) {
		Long id = 0l;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("INSERT INTO tb_equipamento "
					+ "(empressaName, modelo, ns, pat, empresa_id, laboratorio, fabricante, instrumento) " + "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, equipamento.getEmpressaName());
			pst.setString(2, equipamento.getModelo());
			pst.setString(3, equipamento.getNs());
			pst.setString(4, equipamento.getPat());
			pst.setLong(5, equipamento.getEmpressa());
			pst.setBoolean(6, false);
			pst.setString(7, equipamento.getFabricante());
			pst.setString(8, equipamento.getInstrumento());

			int rowsAffected = pst.executeUpdate();
			conn.commit();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while (rs.next()) {
					id = rs.getLong(1);

				}

			} else
				System.out.println("No rown affected");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();

		}
		return id;
	}

	public boolean updatedeNsPatModelo(Equipamento equipamento) {
		boolean ok = false;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(
					"UPDATE tb_equipamento " + "SET modelo = ? ," + " ns = ? ," + " pat = ? " + " WHERE " + "(id = ?)");

			pst.setString(1, equipamento.getModelo());
			pst.setString(2, equipamento.getNs());
			pst.setString(3, equipamento.getPat());
			pst.setLong(4, equipamento.getId());

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				ok = true;

		} catch (DbException | SQLException e) {
			ok = false;
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();

		}
		return ok;

	}

	public List<Equipamento> findEmpresaUpdate(Empresa empresa) {

//	Buscar equipamentos com o nome da empressa quando tiver alteração de nomes
		List<Equipamento> list = new ArrayList<>();
		conn = DB.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_equipamento;");
			while (rs.next()) {
				Long id = rs.getLong("empresa_id");
				if (id != null) {
					if (id.equals(empresa.getId()))
						list.add(Equipamento.parseEquipamentoDois(rs));
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}

	}


	public boolean updatede(Long id, Boolean laboratorioo, Long orcamento_id) {
		boolean ok = false;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento " + "SET laboratorio = " + laboratorioo + ", "
					+ "orcamento_id = ?" + " WHERE " + "(id = ?)");

			pst.setLong(1, orcamento_id);
			pst.setLong(2, id);

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				ok = true;

		} catch (DbException | SQLException e) {
			ok = false;
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();

		}
		return ok;

	}

	public Boolean updateSaida(Equipamento equipamento) {
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento " + "SET laboratorio = " + equipamento.getLaboratorio()
					+ " WHERE " + "(id = ?)");

			pst.setLong(1, equipamento.getId());

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Boolean delete(Long id) {
		boolean ok = false;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("DELETE FROM tb_equipamento WHERE (id = " + id + " )");

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				ok = true;

		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}

		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();

		}
		return ok;
	}

	public boolean updatedeEmpresaName(String empresaName, Long id) {
		boolean ok = false;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("UPDATE tb_equipamento " + "SET empressaName = ?" + " WHERE " + "(id = ?)");

			pst.setString(1, empresaName);
			pst.setLong(2, id);

			int rowsAccepted = pst.executeUpdate();
			conn.commit();

			if (rowsAccepted > 0)
				ok = true;

		} catch (DbException | SQLException e) {
			ok = false;
			e.printStackTrace();
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
			}
		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();

		}
		return ok;
	}
	
	
	
	
	public void updatedeAllDate() {
				
//	ItensRepositoryFind findItens = new ItensRepositoryFind();
//	
//		List<Orcamento> listOrcamento = new ArrayList<>();		
//		try {
//			conn = DB.getConnection();			
//			st = conn.createStatement();			
//			rs = st.executeQuery("SELECT * FROM alfaestoque.tb_orcamento;");			
//			
//			while ( rs.next() ) {
//				listOrcamento.add(new Orcamento(rs));
//			}
//			
//		}catch (SQLException e) {	
//			e.printStackTrace();
//		}
//		DB.closeStatement(pst);
//		DB.closeConnection();
//			
//		List<EstoqueConsumo> listConsumo = findItens.findAllConsumo();
//		
//		
//		
//		listOrcamento.forEach((orcamento)-> {			
//			if(orcamento.getCabos() == null || orcamento.getCabos() == 0)
//				listConsumo.forEach((consumo)->{
//					if(consumo.getOrcamento_id() == orcamento.getId()) {
//						long idcabos = 0;
//					
//						try {	
//							conn = DB.getConnection();
//							conn.setAutoCommit(false);
//							pst = conn.prepareStatement(
//									"INSERT INTO tb_itens_cabos "
//									+ "(orcamento_id, saida, nfe) "
//									+ "VALUES "
//									+ "(?,?,?)",				
//									Statement.RETURN_GENERATED_KEYS);
//							
//						pst.setLong(1, consumo.getOrcamento_id());			
//						pst.setBoolean(2, consumo.getSaida());
//						pst.setInt(3, consumo.getNfe());
//
//			
//						int rowsAffected = pst.executeUpdate();
//						conn.commit();
//						rs = pst.getGeneratedKeys();
//						while(rs.next())
//							idcabos = rs.getLong(1);
//							
//						} catch (SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}		
//						finally {
//							DB.closeResultSet(rs);
//							DB.closeStatement(pst);
//							DB.closeConnection();
//							
//						}
//
//							
//							
//							try {
//								conn = DB.getConnection();
//								conn.setAutoCommit(false);
//								pst = conn.prepareStatement("UPDATE tb_orcamento "
//																+ "SET cabos = ?"
//																+" WHERE "
//																+"(id = ?)");
//								
//								pst.setLong( 1, idcabos );
//								pst.setLong( 2, orcamento.getId() );
//								
//								pst.executeUpdate();
//								conn.commit();
//							
//							}catch(DbException | SQLException e) {
//								e.printStackTrace();
//								try {
//									conn.rollback();
//									throw new DbException("Transaction rolled back! Caused by: " + e.getMessage() );
//								}catch (SQLException e1) {
//									throw new DbException("Error trying to rollback! Caused by: \" + e1.getMessage()");
//								}
//								
//							}
//							finally {
//								DB.closeStatement(pst);
//								DB.closeConnection();
//								
//							}
//								
//					
//						
//						
//					}
//						
//				});
//				
//					
//			
//		});
				
//		
//		try {
//			for(Equipamento equipamento: list) {				
//			if(equipamento.getDataSaida() != null) {
//				pst = conn.prepareStatement("UPDATE tb_equipamento "
//												+ "SET dateChegada = ?" + ", "
//												+ "dateSaida = ?"
//												+ " WHERE "
//												+ "(id = ?)");
//				
//				pst.setDate( 1, Geral.dateParceString(equipamento.getDataChegada() ) );
//				pst.setDate(2, Geral.dateParceString( equipamento.getDataSaida() ) );			
//				pst.setLong( 3, equipamento.getId() );
//			}else {
//				pst = conn.prepareStatement("UPDATE tb_equipamento "
//						+ "SET dateChegada = ?"
//						+ " WHERE "
//						+ "(id = ?)");
//				
//				pst.setDate( 1, Geral.dateParceString(equipamento.getDataChegada() ) );			
//				pst.setLong( 2, equipamento.getId() );
//			}
//
//			
//			pst.executeUpdate();
//			}
				
//		}catch (SQLException e) {
//		System.out.println(e.getMessage());	
//		}
//		finally {
				
//			
//		}		
				
	}	
}

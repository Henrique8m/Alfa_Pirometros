package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
//@Table(name = "tb_equipamento")
public class Equipamento implements Serializable {	


	private static final long serialVersionUID = 1L;
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String empressaName;
	private String modelo;

	private int status;
	private String dataChegada;
	private String dataSaida;	
	private String ns;
	private String pat;
	private String ultimaCalib;
	private String certificado;
	private double valor;
	private boolean laboratorio;
	private String dataCal;
	@SuppressWarnings("unused")
	private String statusStr;
	
	private String relatorio;
	
	private Date dateChegada;
	private Date dateSaida;
	
	

	//@OneToOne(mappedBy = "motorista", fetch = FetchType.EAGER)
//	private EstoqueEletronicos estoqueEletronicos;
//	private EstoqueEletricos estoqueempressa;
//	private EstoqueEstetico estoqueEstetico;
//	private EstoqueSinal estoqueSinal;
	private Long empressa;
	
	private Long orcamento_id;
	private Long coletor_id;
	
	public Equipamento() {}
		

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpressaName() {
		return empressaName;
	}

	public void setEmpressaName(String empressaName) {
		this.empressaName = empressaName;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(String dataChegada) {
		this.dataChegada = dataChegada;
	}

	public String getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getPat() {
		return pat;
	}

	public void setPat(String pat) {
		this.pat = pat;
	}

	public String getUltimaCalib() {
		return ultimaCalib;
	}

	public void setUltimaCalib(String ultimaCalib) {
		this.ultimaCalib = ultimaCalib;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(boolean laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Long getEmpressa() {
		return empressa;
	}

	public void setEmpressa(Long empressa) {
		this.empressa = empressa;
	}

	public Long getOrcamento_id() {
		return orcamento_id;
	}

	public void setOrcamento_id(Long orcamento_id) {
		this.orcamento_id = orcamento_id;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getStatusStr() {
		switch (status) {
			case 1: return "Aguardando Orçamento";
			case 2:	return "Enviar Orçamento";
			case 3: return "Aguardando Aprovação";
			case 4: return "Aprovado, aquardando Reparo!";
			case 5: return "Liberado, aquardando Coleta!";
			case 6: return "Não Aprovado, aquardando coleta!";
			case 7: return "Coletado";
			case 8: return "Aprovado sem orcamento, aquardando reparo!";
			case 9: return "Liberado sem orcamento, aquardando coleta!";
			case 12:return "Coletado, enviar Orçamento";
			case 13: return "Coletado, aguardando Aprovação";
			
			
			default: return "";
		}
		
	}


	public String getDataCal() {
		return dataCal;
	}

	public void setDataCal(String dataCal) {
		this.dataCal = dataCal;
	}

	public Long getColetor_id() {
		return coletor_id;
	}

	public void setColetor_id(Long coletor_id) {
		this.coletor_id = coletor_id;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public Date getDateChegada() {
		return dateChegada;
	}

	public void setDateChegada(Date dateChegada) {
		this.dateChegada = dateChegada;
	}

	public Date getDateSaida() {
		return dateSaida;
	}

	public void setDateSaida(Date dateSaida) {
		this.dateSaida = dateSaida;
	}	
	
	public static Equipamento parseEquipamento(ResultSet rs) {
		Equipamento obj = new Equipamento();
		try {
			obj.setOrcamento_id( rs.getLong("orcamento_id"));
			obj.setId( rs.getLong("Id") );	
			obj.setEmpressaName( rs.getString("empressaName") );				
			obj.setModelo( rs.getString("modelo") );  
			obj.setStatus( rs.getInt("status") );
//			obj.setDataChegada( rs.getString("dataChegada") );
//			obj.setDataSaida( rs.getString("dataSaida") );
			obj.setNs(rs.getString("ns"));
			obj.setPat(rs.getString("pat"));
			obj.setUltimaCalib(rs.getString("ultimaCalib"));	
			obj.setCertificado(rs.getString("certificado") );
			obj.setValor( rs.getDouble("valor") );	
			obj.setEmpressa( rs.getLong( "empresa_id" ) );
			obj.setColetor_id( rs.getLong( "coletor_id" ) );
			obj.setRelatorio( rs.getString( "relatorio" ));
			obj.setDateChegada(rs.getDate("dateChegada"));
			obj.setDateSaida(rs.getDate("dateSaida"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return obj;		
	}
	
}

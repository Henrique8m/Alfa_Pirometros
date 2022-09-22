package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
//@Table(name = "tb_equipamento")
public class Equipamento implements Serializable {	


	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String empressaName;
	private String modelo;
	private String ns;
	private String pat;
	private Long empressa;	
	private Long orcamento_id;	
	private Boolean laboratorio;
	private Date ultimaCalibDate;	
	private Long certificado_id;
	
	private int status;
	private String certificado;	
	@SuppressWarnings("unused")
	private String statusStr;	
	private String relatorio;	
	private Date dateChegada;
	private Date dateSaida;
	
	public Equipamento() {}

	public Equipamento(String empressaName, String modelo, String ns, String pat, Long empressa) {
		super();
		this.empressaName = empressaName;
		this.modelo = modelo;
		this.ns = ns;
		this.pat = pat;
		this.empressa = empressa;
	}
	
	public static Equipamento parseEquipamentoDois(ResultSet rs) {
		Equipamento obj = new Equipamento();
		try {
			obj.setId( rs.getLong("Id") );	
			obj.setEmpressaName( rs.getString("empressaName") );				
			obj.setModelo( rs.getString("modelo") );  
			obj.setNs(rs.getString("ns"));
			obj.setPat(rs.getString("pat"));
			obj.setEmpressa( rs.getLong( "empresa_id" ) );
			obj.setOrcamento_id( rs.getLong("orcamento_id"));
			obj.setLaboratorio( rs.getBoolean( "laboratorio" ));
			if(rs.getDate( "ultimaCalibDate" )!= null)
				obj.setUltimaCalibDate( rs.getDate( "ultimaCalibDate" ));
			obj.setCertificado_id( rs.getLong( "certificado" ));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return obj;		
	}
	
	public void setOrcamento(Orcamento orcamento) {		
			setStatus( orcamento.getStatus() );
			setRelatorio( orcamento.getRelatorio() );	
			setDateChegada( orcamento.getData_chegada() );		
			if(orcamento.getData_saida()!= null)
				setDateSaida(orcamento.getData_saida());
	}
	
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

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
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

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public Date getDateChegada() {
		return dateChegada;
	}

//	@SuppressWarnings("deprecation")
	public void setDateChegada(Date dateChegada) {
//		int date = dateChegada.getDate() + 1;
//		dateChegada.setDate(date);
		this.dateChegada = dateChegada;
	}
	
	public Date getDateSaida() {
		return dateSaida;
	}
	
//	@SuppressWarnings("deprecation")
	public void setDateSaida(Date dateSaida) {
//		int date = dateSaida.getDate() + 1;
//		dateSaida.setDate(date);
		this.dateSaida = dateSaida;
	}
	
	public Date getUltimaCalibDate() {
		return ultimaCalibDate;
	}

	@SuppressWarnings("deprecation")
	public void setUltimaCalibDate(Date ultimaCalibDate) {
		int date = ultimaCalibDate.getDate() + 1;
		ultimaCalibDate.setDate(date);
		this.ultimaCalibDate = ultimaCalibDate ;
	}
	

	public Boolean getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Boolean laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Long getCertificado_id() {
		return certificado_id;
	}

	public void setCertificado_id(Long certificado_id) {
		this.certificado_id = certificado_id;
	}
	
}

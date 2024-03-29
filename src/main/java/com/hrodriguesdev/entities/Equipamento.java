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
	private String empresaName;
	private String modelo;
	private String ns;
	private String pat;
	private Long empresa;	
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
	
	private String fabricante;
	private String instrumento;
	
	public Equipamento() {}

	public Equipamento(String empresaName, String modelo, String ns, String pat, Long empresa, String fabricante, String instrumento) {
		super();
		this.empresaName = empresaName;
		this.modelo = modelo;
		this.ns = ns;
		this.pat = pat;
		this.empresa = empresa;
		this.fabricante = fabricante;
		this.instrumento = instrumento;
	}
	
	public static Equipamento parse(ResultSet rs) {
		Equipamento obj = new Equipamento();
		try {
			obj.setId( rs.getLong("Id") );	
			obj.setEmpresaName( rs.getString("empressaName") );				
			obj.setModelo( rs.getString("modelo") );  
			obj.setNs(rs.getString("ns"));
			obj.setPat(rs.getString("pat"));
			obj.setEmpresa( rs.getLong( "empresa_id" ) );
			obj.setOrcamento_id( rs.getLong("orcamento_id"));
			obj.setLaboratorio( rs.getBoolean( "laboratorio" ));
			if(rs.getDate( "ultimaCalibDate" )!= null)
				obj.ultimaCalibDate( rs.getDate( "ultimaCalibDate" ));
			obj.setCertificado_id( rs.getLong( "certificado" ));
			obj.setFabricante(rs.getString("fabricante"));
			obj.setInstrumento(rs.getString("instrumento"));
			
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
	
	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpresaName() {
		return empresaName;
	}

	public void setEmpresaName(String empresaName) {
		this.empresaName = empresaName;
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

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
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
			case 15: return "Manutenção na area, enviar Orçamento";
			case 16: return "Manutenção na area, aguardando Aprovação";
			
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

	public void setDateChegada(Date dateChegada) {
		this.dateChegada = dateChegada;
	}
	
	public Date getDateSaida() {
		return dateSaida;
	}
	
	public void setDateSaida(Date dateSaida) {
		this.dateSaida = dateSaida;
	}
	
	public Date getUltimaCalibDate() {
		return ultimaCalibDate;
	}

	public void setUltimaCalibDate(Date ultimaCalibDate) {
		this.ultimaCalibDate = ultimaCalibDate ;
	}
	
	@SuppressWarnings("deprecation")
	public void ultimaCalibDate(Date ultimaCalibDate) {
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

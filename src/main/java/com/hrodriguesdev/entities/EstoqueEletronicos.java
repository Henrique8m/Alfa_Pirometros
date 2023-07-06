package com.hrodriguesdev.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
//@Table(name = "tb_eletronicos")
public class EstoqueEletronicos implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	///@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private Long orcamento_id;
	private Boolean saida;
	private int nfe;
	private Integer sirene;
	private Integer PCIFIII;
	private Integer PCIFKal;
	private Integer DispFKal;
	private Integer FIII;
	private Integer Indicmax;
	private Integer CIFII;
	private Integer CIIndicmax;
	private Boolean entrada;
	
	public EstoqueEletronicos(Boolean entrada, Long orcamento_id, Boolean saida, int nfe, int sirene, int pCIFIII, int pCIFKal,
			int dispFKal, int fIII, int indicmax, int cIFII, int cIIndicmax) {
		super();
		this.orcamento_id = orcamento_id;
		this.saida = saida;
		this.nfe = nfe;
		this.sirene = sirene;
		PCIFIII = pCIFIII;
		PCIFKal = pCIFKal;
		DispFKal = dispFKal;
		FIII = fIII;
		Indicmax = indicmax;
		CIFII = cIFII;
		CIIndicmax = cIIndicmax;
		this.entrada = entrada;
	}
	
	public EstoqueEletronicos(ResultSet rs) {
		try {			
			this.id = rs.getLong("id");
			this.orcamento_id = rs.getLong("orcamento_id");	
			this.saida = rs.getBoolean("saida");
			this.nfe = rs.getInt("nfe");
			sirene = rs.getInt("sirene");
			PCIFIII = rs.getInt("pci_fiii");
			PCIFKal = rs.getInt("pci_fkal");	
			DispFKal = rs.getInt("disp_fkal");
			FIII = rs.getInt("fiii");	
			Indicmax = rs.getInt("indicmax");
			CIFII = rs.getInt("ci_fii");	
			CIIndicmax = rs.getInt("ci_indicmax");	
			this.entrada = rs.getBoolean("entrada");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	

	public void remove(ResultSet rs) {
		try {			

			sirene -= rs.getInt("sirene");
			PCIFIII -= rs.getInt("pci_fiii");
			PCIFKal -= rs.getInt("pci_fkal");	
			DispFKal -= rs.getInt("disp_fkal");
			FIII -= rs.getInt("fiii");	
			Indicmax -= rs.getInt("indicmax");
			CIFII -= rs.getInt("ci_fii");	
			CIIndicmax -= rs.getInt("ci_indicmax");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public void add(ResultSet rs) {
		try {			

			sirene += rs.getInt("sirene");
			PCIFIII += rs.getInt("pci_fiii");
			PCIFKal += rs.getInt("pci_fkal");	
			DispFKal += rs.getInt("disp_fkal");
			FIII += rs.getInt("fiii");	
			Indicmax += rs.getInt("indicmax");
			CIFII += rs.getInt("ci_fii");	
			CIIndicmax += rs.getInt("ci_indicmax");	
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public EstoqueEletronicos() {
		sirene=0;
		PCIFIII=0;
		PCIFKal=0;
		DispFKal=0;
		FIII=0;
		Indicmax=0;
		CIFII=0;
		CIIndicmax=0;
	}

//	public static List<Orcamento> orcamentoListEletronico(EstoqueEletronicos eletronicos) {
//		List<Orcamento> list = new ArrayList<>();
//		try {
//		if( eletronicos.getPCIFIII() > 0) 
//			list.add(new Orcamento("PCIFIII", eletronicos.getPCIFIII() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//		if( eletronicos.getPCIFKal() > 0) 
//			list.add(new Orcamento("PCIFKal", eletronicos.getPCIFKal() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//		if( eletronicos.getDispFKal() > 0) 
//			list.add(new Orcamento("DispFKal", eletronicos.getDispFKal() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//		if( eletronicos.getFIII() > 0) 
//			list.add(new Orcamento("FIII", eletronicos.getFIII())  );
//			
//		}catch(NullPointerException e) {}
//		try {
//		if( eletronicos.getIndicmax() > 0) 
//			list.add(new Orcamento("Indicmax", eletronicos.getIndicmax() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//		if( eletronicos.getCIFII() > 0) 
//			list.add(new Orcamento("CIFII", eletronicos.getCIFII() ) );
//			
//		}catch(NullPointerException e) {}
//		try {
//		
//		if( eletronicos.getCIIndicmax() > 0) 
//			list.add(new Orcamento("CIIndicmax", eletronicos.getCIIndicmax() ) );
//			
//		}catch(NullPointerException e) {}
//
//		try {
//		if( eletronicos.getSirene() > 0) 
//			list.add(new Orcamento("sirene", eletronicos.getSirene() ) );
//			
//		}catch(NullPointerException e) {}
//
//		
//
//
//		
//
//		
//		return list;
//	}
	
	@Override
	public String toString() {
		String list = "";

		if( sirene > 0) 
			list = list + sirene + " Sirene fim de medicao\n";
		
		if( PCIFIII > 0) 
			list = list + PCIFIII + " PCI Fornero III\n";
		
		if( PCIFKal > 0) 
			list = list + PCIFKal + " PCI Fornero Kal\n";
		
		if( DispFKal > 0) 
			list = list + DispFKal + " Display Fornero Kal\n";
		
		if( FIII > 0) 
			list = list + FIII + " Fornero III novo\n";
		
		if( Indicmax > 0) 
			list = list + Indicmax + " Indicmax novo\n";
		
		if( CIFII > 0) 
			list = list + CIFII + " CI microcontrolador Fornero II\n";
		
		if( CIIndicmax > 0) 
			list = list + CIIndicmax + " CI Indicmax\n";
		
		return list;
	}
	
	public String string() {
		String list = "Sirene fim de medicao, quantidade = " + sirene + "\n";
		
			list = list + "PCI Fornero III, quantidade = " + PCIFIII + "\n";
		
			list = list + "PCI Fornero Kal, quantidade = " + PCIFKal + "\n";
		
			list = list + "Display Fornero Kal, quantidade = " + DispFKal + "\n";
		
			list = list + "Fornero III novo, quantidade = " + FIII + "\n";
		
			list = list + "Indicmax novo, quantidade = " + Indicmax + "\n";
		
			list = list + "CI microcontrolador Fornero II, quantidade = " + CIFII + "\n";
		
			list = list + "CI Indicmax, quantidade = " + CIIndicmax + "\n";
		
		return list;
	}
		
	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrcamento_id() {
		return orcamento_id;
	}
	public void setOrcamento_id(Long orcamento_id) {
		this.orcamento_id = orcamento_id;
	}
	public Boolean getSaida() {
		return saida;
	}
	public void setSaida(Boolean saida) {
		this.saida = saida;
	}
	public int getNfe() {
		return nfe;
	}
	public void setNfe(int nfe) {
		this.nfe = nfe;
	}
	public int getSirene() {
		return sirene;
	}
	public void setSirene(int sirene) {
		this.sirene = sirene;
	}
	public int getPCIFIII() {
		return PCIFIII;
	}
	public void setPCIFIII(int pCIFIII) {
		PCIFIII = pCIFIII;
	}
	public int getPCIFKal() {
		return PCIFKal;
	}
	public void setPCIFKal(int pCIFKal) {
		PCIFKal = pCIFKal;
	}
	public int getDispFKal() {
		return DispFKal;
	}
	public void setDispFKal(int dispFKal) {
		DispFKal = dispFKal;
	}
	public int getFIII() {
		return FIII;
	}
	public void setFIII(int fIII) {
		FIII = fIII;
	}
	public int getIndicmax() {
		return Indicmax;
	}
	public void setIndicmax(int indicmax) {
		Indicmax = indicmax;
	}
	public int getCIFII() {
		return CIFII;
	}
	public void setCIFII(int cIFII) {
		CIFII = cIFII;
	}
	public int getCIIndicmax() {
		return CIIndicmax;
	}
	public void setCIIndicmax(int cIIndicmax) {
		CIIndicmax = cIIndicmax;
	}
	public Long getId() {
		return id;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	
	

}

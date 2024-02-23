package com.hrodriguesdev.dependency;

import com.hrodriguesdev.controller.CertificadoController;
import com.hrodriguesdev.controller.ColetorController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.gui.controller.server.CatalinaRunServer;
import com.hrodriguesdev.gui.controller.tabs.AllOrcamentoTabController;
import com.hrodriguesdev.gui.controller.tabs.CertificateExpiredTabController;
import com.hrodriguesdev.gui.controller.tabs.CertificateTabController;
import com.hrodriguesdev.gui.controller.tabs.CompanyTabController;
import com.hrodriguesdev.gui.controller.tabs.FindTabController;
import com.hrodriguesdev.gui.controller.tabs.MainTabController;


public class InjecaoDependency {
	public static final CatalinaRunServer CATALINA = new CatalinaRunServer();	
	public static final CertificadoController CERTIFICADO_CONTROLLER = new CertificadoController();	
	public static final EquipamentoController EQUIPAMENTO_CONTROLLER = new EquipamentoController();
	public static final OrcamentoController ORCAMENTO_CONTROLLER = new OrcamentoController();
	public static final ColetorController COLETOR_CONTROLLER = new ColetorController();
	public static final EmpresaController EMPRESA_CONTROLLER = new EmpresaController();	
	public static final EnsaiosController ENSAIO_CONTROLLER = new EnsaiosController();	
	
//	Tabs Main
	public static final MainTabController MAIN_TAB_CONTROLLER = new MainTabController();
	public static final FindTabController TAB_FIND_CONTROLLER = new FindTabController();
	public static final CertificateTabController TAB_CERTIFICATE_CONTROLLER = new CertificateTabController();
	public static final CompanyTabController TAB_COMPANY_CONTROLLER = new CompanyTabController();
	public static final AllOrcamentoTabController TAB_OS_CONTROLLER = new AllOrcamentoTabController();
	public static final CertificateExpiredTabController TAB_CERTIFICATE_EXPIRED_CONTROLLER = new CertificateExpiredTabController();
}

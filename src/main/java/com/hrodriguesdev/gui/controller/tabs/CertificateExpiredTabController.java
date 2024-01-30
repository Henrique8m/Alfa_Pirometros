package com.hrodriguesdev.gui.controller.tabs;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.CertificadoController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.DTO.CertificadoDTO;
import com.hrodriguesdev.utilitary.Format;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CertificateExpiredTabController implements Initializable{
	
	@FXML
	private ImageView certificadoImg;
	
	@FXML
	private Tab tabEmpresa;	
	
	@FXML
	private CheckBox tresMeses, seisMeses, noveMeses, doseMeses;
	


	@FXML
	private TableView<CertificadoDTO> tableCertificadoAll;
	private ObservableList<CertificadoDTO> obsbListCertificadoAll = FXCollections.observableArrayList();
	@FXML
	private TableColumn<CertificadoDTO, Date> dataCalibracao;
	@FXML
	private TableColumn<CertificadoDTO, Integer> numeroCertificado;
	@FXML
	private TableColumn<CertificadoDTO, String> empresaCertificado;
	@FXML
	private TableColumn<CertificadoDTO, String> modeloCertificado;
	@FXML
	private TableColumn<CertificadoDTO, String> nsCertificado;
	@FXML
	private TableColumn<CertificadoDTO, String> patCertificado;
	@FXML
	private TableColumn<CertificadoDTO, String> enderecoEmpresa;	
	@FXML
	private TableColumn<CertificadoDTO, String> cidadeEmpresa;	
	
	
	//@Autowired
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;
	protected CertificadoController certificadoController = InjecaoDependency.CERTIFICADO_CONTROLLER;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */		
		tabEmpresa.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				refreshTableCertificado();
			}else {
				
			}

		});
		
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() throws InterruptedException {
		    	startTable();
		    	imageInit();
		        return null;
		    }
		};
		new Thread(task).start();	
		
		
	}

	private void refreshTableCertificado() {
		
		List<CertificadoDTO> list = certificadoController.findExpiredDTO(6);			
		obsbListCertificadoAll = FXCollections.observableArrayList();
		obsbListCertificadoAll.addAll(list);
		obsbListCertificadoAll.sort( (a,b) -> {
			if(b.getDateCal().equals(a.getDateCal())) {
				Integer bn =  b.getNumero();
				return bn.compareTo(a.getNumero());
			}
			return b.getDateCal().compareTo(a.getDateCal()); 
		}   );
		tableCertificadoAll.setItems(obsbListCertificadoAll);
	}
	
	private void imageInit() {		
		Image image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-certificado.png").toString() );
		certificadoImg.setImage(image);
		
	}

	private void startTable() {		
		dataCalibracao.setCellValueFactory(new PropertyValueFactory<>("dateCal"));
		dataCalibracao.setCellFactory(cell -> {
			return new TableCell<CertificadoDTO, Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (!empty) {
						try {
							setText(Format.formatData.format(item));
						} catch (NullPointerException e) {
							setText("");
							setGraphic(null);
						}

					} else {
						setText("");
						setGraphic(null);
					}
				}
			};
		});
		numeroCertificado.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, Integer>("numero"));
		modeloCertificado.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("modelo"));
		nsCertificado.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("ns"));
		patCertificado.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("pat"));
		empresaCertificado.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("empresa"));
		enderecoEmpresa.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("endereco"));
		cidadeEmpresa.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("cidade"));
		
		
	}
	
}
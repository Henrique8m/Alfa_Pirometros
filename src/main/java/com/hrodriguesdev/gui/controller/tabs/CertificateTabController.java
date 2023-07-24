package com.hrodriguesdev.gui.controller.tabs;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.ExceptionAlfa;
import com.hrodriguesdev.controller.CertificadoController;
import com.hrodriguesdev.controller.EmpresaController;
import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.dao.db.DbException;
import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.entities.Certificado;
import com.hrodriguesdev.entities.Ensaios;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.DTO.CertificadoDTO;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.CertificadoOrcamentoEnsaio;
import com.hrodriguesdev.resources.file.FileEquipamento;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CertificateTabController implements Initializable {

	@FXML
	private ComboBox<String> textEmpresaCertificado;
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;
	private ObservableList<String> obsString = FXCollections.observableArrayList();

	@FXML
	private DatePicker dataCalibracaoNovo;

	@FXML
	private Tab tabCertificado;

	@FXML
	private ImageView certificadoImg;

	@FXML
	private TableView<Equipamento> tableEquipamentosCertificados;
	private ObservableList<Equipamento> osbListEquipamento = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Equipamento, String> empressaCertificado;
	@FXML
	private TableColumn<Equipamento, String> nsCertificado;
	@FXML
	private TableColumn<Equipamento, String> patCertificado;
	@FXML
	private TableColumn<Equipamento, String> modeloCertificado;

	@FXML
	private TableView<Certificado> tableCertificado;
	private ObservableList<Certificado> osbListCertificado = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Certificado, Date> dataCalibracao;
	@FXML
	private TableColumn<Certificado, Integer> numeroCertificado;

	@FXML
	private TableView<CertificadoDTO> tableCertificadoAll;
	private ObservableList<CertificadoDTO> obsbListCertificadoAll = FXCollections.observableArrayList();
	@FXML
	private TableColumn<CertificadoDTO, Date> dataCalibracaoAll;
	@FXML
	private TableColumn<CertificadoDTO, Integer> numeroCertificadoAll;
	@FXML
	private TableColumn<CertificadoDTO, String> empresaCertificadoAll;
	@FXML
	private TableColumn<CertificadoDTO, String> modeloCertificadoAll;
	@FXML
	private TableColumn<CertificadoDTO, String> nsCertificadoAll;
	@FXML
	private TableColumn<CertificadoDTO, String> patCertificadoAll;
	
	
	
	@FXML
	private TextArea certificadoText;

	@FXML
	private TextField refeVal1, refeVal2, refeVal3, aplicado1, aplicado2, aplicado3, sinalCalibr11, sinalCalibr12,
			sinalCalibr13, sinalCalibr21, sinalCalibr22, sinalCalibr23;

	@FXML
	private TextField textNsEquipCertificado, textPatEquipCertificado;

	@FXML
	private TextField nomeEmpressaClickCertificado, nsClickCertificado, patClickCertificado, modeloClickCertificado,
			numeroCertificadoNovo;

	// @Autowired
	protected EquipamentoController equipamentoController = InjecaoDependency.EQUIPAMENTO_CONTROLLER;
	protected CertificadoController certificadoController = InjecaoDependency.CERTIFICADO_CONTROLLER;
	protected EmpresaController empresaController = InjecaoDependency.EMPRESA_CONTROLLER;

	private Equipamento equipamento;
	private Alert alert;
	private EnsaiosController ensaiosController = new EnsaiosController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Adciona um listener do tipo Charge Listener, que seria um ouvinte das
		 * variaveis, caso a tab fique no foco, é ativo e aloca os valores do comboBox
		 */
		tabCertificado.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				addListener();
				if (MainTabController.comboBoxBusca != "") {
					if (MainTabController.comboBoxBusca != "") {
						textEmpresaCertificado.setValue(MainTabController.comboBoxBusca);
						buscar();
					}
				}
			} else
				removeListener();
		});

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				dataCalibracaoNovo.setValue(LocalDate.now());
				startTable();
				imageInit();
				List<CertificadoDTO> list = certificadoController.findAllDTO();
				list.sort((a,b) -> a.getDateCal().compareTo(b.getDateCal()));				
				obsbListCertificadoAll.addAll(list);
				return null;
			}
		};
		new Thread(task).start();

	}

	@FXML
	private void enter(KeyEvent event) {
		if (event.getTarget() == textEmpresaCertificado) {
			buscar();
		}
		if (event.getTarget() == textPatEquipCertificado) {
			if (event.getCode().equals(KeyCode.ENTER))
				System.out.println(textPatEquipCertificado.getText());
		}
		if (event.getTarget() == textNsEquipCertificado) {
			if (event.getCode().equals(KeyCode.ENTER))
				System.out.println(textNsEquipCertificado);
		}

	}

	@FXML
	protected void clickEquipamentos(MouseEvent event) throws SQLException {
		if (tableEquipamentosCertificados.getSelectionModel().getSelectedItem() != null) {
			equipamento = tableEquipamentosCertificados.getSelectionModel().getSelectedItem();

			tableCertUpdate();

			nomeEmpressaClickCertificado.setText(equipamento.getEmpresaName());
			if (equipamento.getNs() != null)
				nsClickCertificado.setText(equipamento.getNs());
			if (equipamento.getPat() != null)
				patClickCertificado.setText(equipamento.getPat());
			if (equipamento.getModelo() != null)
				modeloClickCertificado.setText(equipamento.getModelo());

		}
	}

	private void buscar() {
		Equipamento equipamento = new Equipamento();
		if (textEmpresaCertificado.getValue() != null)
			if (!textEmpresaCertificado.getValue().isEmpty()) {
				equipamento.setEmpresaName(textEmpresaCertificado.getValue());
			}
		if (!textNsEquipCertificado.getText().isEmpty()) {
			equipamento.setNs(textNsEquipCertificado.getText());
		}
		if (!textPatEquipCertificado.getText().isEmpty()) {
			equipamento.setPat(textPatEquipCertificado.getText());
		}

		ObservableList<Equipamento> obs = equipamentoController.findAll(equipamento);
		if (obs.size() > 0) {
			osbListEquipamento = obs;
			MainTabController.comboBoxBusca = textEmpresaCertificado.getValue();
		} else {
			osbListEquipamento = equipamentoController.findAllObs();
			MainTabController.comboBoxBusca = "";
		}

		tableEquipamentosCertificados.setItems(osbListEquipamento);

		removeListener();
		addListener();
	}

	@FXML
	private void relacionarCertificado(ActionEvent event) {
		Certificado certificado = tableCertificado.getSelectionModel().getSelectedItem();
		if (certificado != null)
			if (certificado.getEnsaio_id() == null || certificado.getEnsaio_id() == 0)
				NewView.getNewView("Controlador de certificado", "certificadoOrcamentoEnsaio",
						new CertificadoOrcamentoEnsaio(equipamento, certificado));

	}

	@FXML
	private void salvarCertificado(ActionEvent event) {
		Certificado certificado;
		if (!numeroCertificadoNovo.getText().isBlank()) {
			Long certificado_id = certificadoController.add(new Certificado(equipamento.getId(),
					Date.valueOf(dataCalibracaoNovo.getValue()), Integer.parseInt(numeroCertificadoNovo.getText())));

			if (equipamento.getUltimaCalibDate() == null
					|| equipamento.getUltimaCalibDate().before(Date.valueOf(dataCalibracaoNovo.getValue())))
				if (certificado_id != null) {
					certificadoController.updateEquipamento(certificado_id, equipamento.getId(),
							Date.valueOf(dataCalibracaoNovo.getValue()));
					certificado = certificadoController.findById(certificado_id);
					NewView.getNewView("Controlador de certificado", "certificadoOrcamentoEnsaio",
							new CertificadoOrcamentoEnsaio(equipamento, certificado));
				}
			tableCertUpdate();
		}

	}

	/*
	 * Logicaa para o delete de Certificado, tecla alt tem que estar apertada!!
	 */

	@FXML
	private void delete(KeyEvent event) {
		if (!tableCertificado.getSelectionModel().isEmpty() && event.isAltDown()
				&& event.getCode().toString() == "DELETE")
			showAlert("Deletar Certificado",
					"Deletara certificado de numero "
							+ tableCertificado.getSelectionModel().getSelectedItem().getNumero() + "?",
					"", AlertType.CONFIRMATION);
	}

	@FXML
	public void clickCertificado(MouseEvent e) {
		Ensaios ensaio = null;
		Long ensaio_id = 0l;
		try {
			ensaio_id = tableCertificado.getSelectionModel().getSelectedItem().getEnsaio_id();
		} catch (NullPointerException e1) {
			certificadoText.setText("Sem ensaio selecionado");
			throw new ExceptionAlfa("Sem ensaio selecionado");
		}
		if (ensaio_id != null && ensaio_id != 0) {
			ensaio = ensaiosController.findById(ensaio_id);
		} else
			clearValues();
		if (ensaio != null) {
			writeValues(ensaio);
		}

	}

	@FXML
	public void printCertificado(ActionEvent e) {

		Certificado certif = tableCertificado.getSelectionModel().getSelectedItem();
		if (certif == null) {
			certificadoText.setText("Selecionar um certificado");
			return;
		} else {
			certificadoText.setText("Emprimindo...\n");
			try {
				certificadoController.gerarCertificadoPDF(certif);
				certificadoText.insertText(certificadoText.getLength(), "Empresso");
			} catch (ExceptionAlfa | DbException | NullPointerException e1) {
				certificadoText.setText(e1.getMessage());
				e1.printStackTrace();
			}

		}

	}

//	Confirmar Deletar
	private void showAlert(String title, String header, String content, AlertType type) {
		if (alert != null)
			if (alert.isShowing())
				alert.close();
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
			public void handle(DialogEvent e) {
				if (alert.getResult().getButtonData().toString() == "OK_DONE") {
					if (deletarCertificado(tableCertificado.getSelectionModel().getSelectedItem()))
						Alerts.showAlert("Deletar Certificado", "Deletado com sucesso", "", AlertType.CONFIRMATION);
					tableCertUpdate();
				}
			}
		});
		alert.show();

	}

	private boolean deletarCertificado(Certificado certificado) {
		return certificadoController.delete(certificado);
	}

	private void writeValues(Ensaios ensaio) throws NullPointerException {
		String referencia = ensaio.getReferencia();
		String primeiro = ensaio.getPrimeiro();
		String segundo = ensaio.getSegundo();
		String terceiro = ensaio.getTerceiro();

		String[] ref = referencia.split("\n");
		String[] pri = primeiro.split("\n");
		String[] seg = segundo.split("\n");
		String[] ter = terceiro.split("\n");

		if (ref.length == 3)
			writeRef(ref);
		if (pri.length == 3)
			writeVal1(pri);
		if (seg.length == 3)
			writeVal2(seg);
		if (ter.length == 3)
			writeVal3(ter);

	}

	private void writeRef(String[] ensaiosRef) {
		refeVal1.setText(ensaiosRef[0]);
		refeVal2.setText(ensaiosRef[1]);
		refeVal3.setText(ensaiosRef[2]);

	}

	private void writeVal1(String[] value) {
		aplicado1.setText(value[0]);
		aplicado2.setText(value[1]);
		aplicado3.setText(value[2]);

	}

	private void writeVal2(String[] value) {
		sinalCalibr11.setText(value[0]);
		sinalCalibr12.setText(value[1]);
		sinalCalibr13.setText(value[2]);

	}

	private void writeVal3(String[] value) {
		sinalCalibr21.setText(value[0]);
		sinalCalibr22.setText(value[1]);
		sinalCalibr23.setText(value[2]);

	}

	protected void clearValues() {
		String referencia = "-\n-\n-";

		String[] ref = referencia.split("\n");

		if (ref.length == 3)
			writeRef(ref);
		writeVal1(ref);
		writeVal2(ref);
		writeVal3(ref);
	}

	@FXML
	private void referenciaEquipamento(ActionEvent e) {
		NewView.getNewView("Ensaios", "ensaioInserts", new FileEquipamento(new Equipamento(), new Orcamento()));

	}

	private void tableCertUpdate() {
		List<Certificado> certificado = certificadoController.findAllByEquipamento(equipamento.getId());
		ObservableList<Certificado> obs = FXCollections.observableArrayList();
		obs.addAll(certificado);
		tableCertificado.setItems(obs);
		tableCertificado.refresh();

	}

	private void addListener() {
		obsString = empresaController.findAllObs();
		filteredList = new FilteredList<>(obsString);
		inputFilter = new InputFilter<String>(textEmpresaCertificado, filteredList);
		textEmpresaCertificado.getEditor().textProperty().addListener(inputFilter);

	}

	private void removeListener() {
		textEmpresaCertificado.getEditor().textProperty().removeListener(inputFilter);
		textEmpresaCertificado.setValue("");

	}

	private void imageInit() {
		Image image = new Image(
				AlfaPirometrosApplication.class.getResource("gui/resources/icons-certificado.png").toString());
		certificadoImg.setImage(image);

	}

	private void startTable() {
		tableEquipamentosCertificados.setEditable(false);
		empressaCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("empresaName"));
		modeloCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("modelo"));
		nsCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("ns"));
		patCertificado.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("pat"));
		tableEquipamentosCertificados.setItems(osbListEquipamento);

		tableCertificado.setEditable(false);
		numeroCertificado.setCellValueFactory(new PropertyValueFactory<Certificado, Integer>("numero"));
		dataCalibracao.setCellValueFactory(new PropertyValueFactory<>("date_cal"));
		dataCalibracao.setCellFactory(cell -> {
			return new TableCell<Certificado, Date>() {
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
		tableCertificado.setItems(osbListCertificado);

		dataCalibracaoAll.setCellValueFactory(new PropertyValueFactory<>("dateCal"));
		dataCalibracaoAll.setCellFactory(cell -> {
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
		numeroCertificadoAll.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, Integer>("numero"));
		modeloCertificadoAll.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("modelo"));
		nsCertificadoAll.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("ns"));
		patCertificadoAll.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("pat"));
		empresaCertificadoAll.setCellValueFactory(new PropertyValueFactory<CertificadoDTO, String>("empresa"));
		
	}

}
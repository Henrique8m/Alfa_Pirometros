package com.hrodriguesdev.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.controller.EquipamentoController;
import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.controller.ProductsController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.DTO.OrcamentoDTOEquipamento;
import com.hrodriguesdev.entities.DTO.OrcamentoDTORelatorio;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.relatorio.RelatorioMateriais;
import com.hrodriguesdev.utilitary.Format;
import com.hrodriguesdev.utilitary.InputFilter;
import com.hrodriguesdev.utilitary.NewView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RelatoriosController implements Initializable {

	private OrcamentoController orcamentoController = new OrcamentoController();
	private ProductsController productsController = new ProductsController();
	private EquipamentoController equipamentoContoller = new EquipamentoController();
	private OSController osController = new OSController();
	

	@FXML
	protected DatePicker inicioDatePiker, finalDatePiker;

	@FXML
	protected ComboBox<String> produtoCombobox;
	
	private static ObservableList<String> obsString = FXCollections.observableArrayList();
	private FilteredList<String> filteredList;
	private InputFilter<String> inputFilter;

	@FXML
	private CheckBox manutencaoEmCurco, manutencaoRealizada, saidaMaterial, entradaMaterial;

	@FXML
	private ImageView cancelarImg, buscarImg, pdfImg, pdfImg1;

	@FXML
	private ProgressIndicator ProgressIndicator;

//	Tabela saida de material

	@FXML
	private TableView<OrcamentoDTORelatorio> MaterialOutTable;

	private ObservableList<OrcamentoDTORelatorio> obsOrcamento = FXCollections.observableArrayList();
	@FXML
	private TableColumn<OrcamentoDTORelatorio, Date> MaterialOutData;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, Integer> MaterialOutNfe;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String> MaterialOutEmpresa;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String> MaterialOutAuthor;
	@FXML
	private TableColumn<OrcamentoDTORelatorio, String> MaterialOutFinalidade;

//	Tabela Manutencao em equipamentos

	@FXML
	private TableView<OrcamentoDTOEquipamento> MaintenanceTable;

	private ObservableList<OrcamentoDTOEquipamento> obsMaintenance = FXCollections.observableArrayList();
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String> MaintenanceRelatorio;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, Date> MaintenanceDateIn;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, Date> MaintenanceDateOut;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String> MaintenanceSituation;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String> MaintenanceEmploye;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String> MaintenanceNS;
	@FXML
	private TableColumn<OrcamentoDTOEquipamento, String> MaintenancePat;

//	tabela materiais usados

	@FXML
	protected TableView<Product> productSelectedTable;
	protected ObservableList<Product> obsMateriais = FXCollections.observableArrayList();
	@FXML
	protected TableColumn<Product, String> productsSelected;
	@FXML
	protected TableColumn<Product, String> descriptionSelected;
	@FXML
	protected TableColumn<Product, String> unitMeasurementSelected;
	@FXML
	protected TableColumn<Product, Double> amountSelected;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startTable();
		imageInit();
		finalDatePiker.setValue(LocalDate.now());
		inicioDatePiker.setValue(LocalDate.of(2020, 1, 1));
		ProductsController prodController = new ProductsController();
		List<Product> listPro = prodController.findAll();
		List<String> listProStr = new ArrayList<>();
		listPro.forEach(prod -> {
			listProStr.add(prod.getName());
		});
		
		obsString.addAll(listProStr);
		addListener();
	}

//	Button
	@FXML
	private void voltar(ActionEvent event) throws IOException {
		NewView.addChildrenn((Node) NewView.loadFXML("estoque", new EstoqueController()));
	}

//	Button
	@FXML
	private void buscar(ActionEvent event) throws IOException {
		ProgressIndicator.setVisible(true);
		obsOrcamento = FXCollections.observableArrayList();
		obsMaintenance = FXCollections.observableArrayList();

		Boolean entrada = entradaMaterial.selectedProperty().getValue();
		Boolean saida = saidaMaterial.selectedProperty().getValue();
		Boolean mRealizada = manutencaoRealizada.selectedProperty().getValue();
		Boolean mCurso = manutencaoEmCurco.selectedProperty().getValue();

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				try {
					if(!produtoCombobox.getValue().isBlank()) {
						System.out.println("Entrou produto Combobox");
						popularTableComFiltroProd(entrada, saida, mRealizada, mCurso);
						ProgressIndicator.setVisible(false);
						return null;
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				if (entrada)
					obsOrcamento.addAll(orcamentoController.findAllDTORelatorio().stream()
							.filter(x -> x.getFinalidade().equals("Entrada")).collect(Collectors.toList()));
				System.out.println("3");
				if (saida)
					obsOrcamento.addAll(orcamentoController.findAllDTORelatorio().stream()
							.filter(x -> x.getFinalidade().equals("Saida")).collect(Collectors.toList()));
				System.out.println("4");
				if (mRealizada)
					obsMaintenance.addAll(orcamentoController.findAllDTOEquipamento().stream()
							.filter(x -> !x.isLaboratorio()).collect(Collectors.toList()));
				System.out.println("5");
				if (mCurso)
					obsMaintenance.addAll(orcamentoController.findAllDTOEquipamento().stream()
							.filter(x -> x.isLaboratorio()).collect(Collectors.toList()));

				System.out.println("6");
				
				if (inicioDatePiker.getValue() != null) {
					System.out.println("7");
					Date gettedDatePickerDateStart = Date.valueOf(inicioDatePiker.getValue());
					obsOrcamento = obsOrcamento.filtered(x -> {

//		    			int date = x.getData_chegada().getDate() -1 ;
//		    			Date data = x.getData_chegada();
//		    			data.setDate(date);
//		    			if(data.after(gettedDatePickerDateStart))
//		    					return true;
//		    			return false;

						if (x.getData_chegada().after(gettedDatePickerDateStart)
								|| x.getData_chegada().equals(gettedDatePickerDateStart))
							return true;
						return false;
					});
					obsMaintenance = obsMaintenance.filtered(x -> x.getData_chegada().after(gettedDatePickerDateStart));

				}
				if (finalDatePiker.getValue() != null) {
					Date gettedDatePickerDateFinal = Date.valueOf(finalDatePiker.getValue());
					obsOrcamento = obsOrcamento.filtered(x -> {
						if (x.getData_chegada().before(gettedDatePickerDateFinal)
								|| x.getData_chegada().equals(gettedDatePickerDateFinal))
							return true;
						return false;
					});
					obsMaintenance = obsMaintenance
							.filtered(x -> x.getData_chegada().before(gettedDatePickerDateFinal));
				}

				MaterialOutTable.setItems(obsOrcamento);
				MaintenanceTable.setItems(obsMaintenance);
				// updateProgress(100, 100);
				ProgressIndicator.setVisible(false);
				return null;
			}
		};

		ProgressIndicator.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
//		ProgressIndicator.setVisible(false);
	}
	
	@FXML
	public void relatorioReceptaculos() {
		relatorioReceptaculos(42l,  " \"S\"");
		relatorioReceptaculos(43l,  " \"SU\"");
		relatorioReceptaculos(45l,  " \"K\"");
	}
		

	public void relatorioReceptaculos(Long id, String modelo) {
//		42 receptaculo s
//		43 receptaculo su
//		45 receptaculo K
		
		RelatorioMateriais relPDF = new RelatorioMateriais();
		
		ObservableList<OrcamentoDTORelatorio> obsReceptaculo = FXCollections.observableArrayList();
		List<Orcamento> orcamentoList = orcamentoController.findAll();
		List<ProductsOs> productsOsList = osController.findAllOut();
		List<Equipamento> equipamentoList = equipamentoContoller.findAll();		
		List<Orcamento> orcamentoFiltrado = new ArrayList<>();
		
//		Orcamentos que contem receptaculos S entrando
		obsReceptaculo.addAll(orcamentoController.findAllDTORelatorio().stream()
				.filter(x -> {
					if(x.getFinalidade().equals("Entrada")) {
						List<Product> list = productsController.findAllByOrcamentoId(x.getId());
						list = list.stream().filter(y -> y.getId().equals(id)).collect(Collectors.toList());
						if(list.size()>0)
							return true;
						else return false;
					}
					else return false;
					
				}).collect(Collectors.toList()));
		
//		Pega a data da ultima entrada de Receptaculos
		obsReceptaculo.sort((a,b)-> b.getData_chegada().compareTo(a.getData_chegada()));
		Date lastS = obsReceptaculo.get(0).getData_chegada();
		Timestamp timeStamp = new Timestamp(lastS.getTime());
					
//		filtra todas as saidas de receptaculos depois da data de entrada
		productsOsList = productsOsList.stream().filter(y -> y.getProductId().equals(id)).filter(x -> x.getDate().after(timeStamp)).collect(Collectors.toList());
			
//		Coleta os orcamentos que consta as saidas de receptaculos
		productsOsList.forEach(pro -> {
			try {
				Orcamento orc = orcamentoList.stream().filter(x -> x.getId().equals(pro.getIdOrcamento())).findFirst().get();
				orc.setProducts(modelo +  " - " + pro.getQtde() + " PÇs");
				orc.setQuantidade(pro.getQtde());
				Date d = new Date(pro.getDate().getTime());
				orc.setData_saida(d);
				orcamentoFiltrado.add(orc);

			}catch (NullPointerException e) {
				System.out.println(pro.getId() + " NullPointerException RelatoriosController");
			}
		});
		
//		Faz o somatorio da quantidade gasto, e para qual empresa foi
		Double total = 0d;		
		for(Orcamento orc: orcamentoFiltrado) {
			total += orc.getQuantidade();
			orc.setEmpressa( equipamentoList.stream().filter(x -> x.getId().equals(orc.getEquipamento_id() )).findFirst().get().getEmpresaName()  );
		}
		
//		Gera o relatorio
		relPDF.relatorio(orcamentoFiltrado, total, modelo);
			
	}

	@FXML
	public void relatorioPdf() {
		List<List<Product>> listOfListOrcamento = new ArrayList<>();
		List<OrcamentoDTOEquipamento> listOrcamento = new ArrayList<>();
		obsOrcamento.forEach(orca -> {
			List<Product> listOrccamento = new ArrayList<>();
			listOrccamento.addAll( productsController
			.findAllByOrcamentoId(orca.getId()) );
			listOfListOrcamento.add(listOrccamento);
			
		});
		
		listOrcamento.addAll(listOrcamento);
		
		List<List<Product>> listOfListMaintenance = new ArrayList<>();
		List<OrcamentoDTOEquipamento> listMaintenance = new ArrayList<>();
		obsMaintenance.forEach(orca -> {
			List<Product> listMaiantenance = new ArrayList<>();
			listMaiantenance.addAll( productsController
			.findAllByOrcamentoId(orca.getId()) );
			listOfListMaintenance.add(listMaiantenance);
			
		});
		
		listMaintenance.addAll(obsMaintenance);
				
		
//		listOfListMaintenance.forEach(maintenance -> {
//			System.out.println(
//					"To string maintenance " 
//					+ maintenance.toString() + "\n");
//				
//			maintenance.forEach(prod -> {
//				System.out.println( " prod - " 
//						+ prod.getName()
//						);
//			});
//			
//		});

		
	}
	
//	Acao de click na tabela de saida de material e na tabela de manutencao em equipamentos
	@FXML
	public void clickMaterialOutTable(MouseEvent event) throws SQLException {
		MaintenanceTable.getSelectionModel().clearSelection();
		if (!MaterialOutTable.getSelectionModel().isEmpty()) {
			 selectMateriais(MaterialOutTable.getSelectionModel().getSelectedItem().getId() );
		}

	}

	@FXML
	public void clickMaintenanceTable(MouseEvent event) throws SQLException {
		MaterialOutTable.getSelectionModel().clearSelection();
		if (!MaintenanceTable.getSelectionModel().isEmpty()) {
			 selectMateriais(MaintenanceTable.getSelectionModel().getSelectedItem().getId() );
		}

	}
	
	private void selectMateriais(Long id) {
		obsMateriais = productsController
				.findAllByOrcamentoId(id);
		productSelectedTable.setItems(obsMateriais);
	}
	

	
	private void addListener() {
		filteredList = new FilteredList<>(obsString);  
		inputFilter = new InputFilter<String>( produtoCombobox, filteredList );	
		produtoCombobox.getEditor().textProperty().addListener(inputFilter);	

		produtoCombobox.hide();

	}	
				

	private void imageInit() {
		Image image = new Image(
				AlfaPirometrosApplication.class.getResource("gui/resources/icons-excluir.png").toString());
		cancelarImg.setImage(image);
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pesquisar.png").toString());
		buscarImg.setImage(image);
		
		image = new Image(AlfaPirometrosApplication.class.getResource("gui/resources/icons-pdf.png").toString());
		pdfImg.setImage(image);
		pdfImg1.setImage(image);

	}

	private void startTable() {
		MaterialOutTable.setEditable(false);
		MaterialOutData.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		MaterialOutData.setCellFactory(cell -> {
			return new TableCell<OrcamentoDTORelatorio, Date>() {
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

		MaterialOutNfe.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, Integer>("nfe"));
		MaterialOutEmpresa.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("empresa"));
		MaterialOutAuthor.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("author"));
		MaterialOutFinalidade
				.setCellValueFactory(new PropertyValueFactory<OrcamentoDTORelatorio, String>("finalidade"));
		MaterialOutTable.setItems(obsOrcamento);

		MaintenanceTable.setEditable(false);
		MaintenanceRelatorio
				.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("relatorio"));
		MaintenanceDateIn.setCellValueFactory(new PropertyValueFactory<>("data_chegada"));
		MaintenanceDateIn.setCellFactory(cell -> {
			return new TableCell<OrcamentoDTOEquipamento, Date>() {
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

		MaintenanceDateOut.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
		MaintenanceDateOut.setCellFactory(cell -> {
			return new TableCell<OrcamentoDTOEquipamento, Date>() {
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

	MaintenanceSituation
				.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("situation"));
		MaintenanceEmploye.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("empresa"));
		MaintenanceNS.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("ns"));
		MaintenancePat.setCellValueFactory(new PropertyValueFactory<OrcamentoDTOEquipamento, String>("pat"));
		MaintenanceTable.setItems(obsMaintenance);

		productsSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		descriptionSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("descricao"));
		unitMeasurementSelected.setCellValueFactory(new PropertyValueFactory<Product, String>("unidadeMedida"));
		amountSelected.setCellValueFactory(new PropertyValueFactory<Product, Double>("qtde"));
		productSelectedTable.setItems(obsMateriais);

	}

	private void popularTableComFiltroProd(Boolean entrada, Boolean saida, Boolean mRealizada, Boolean mCurso){
		String prod = produtoCombobox.getValue();
			if (entrada)
				obsOrcamento.addAll(orcamentoController.findAllDTORelatorioNew(prod).stream()
						.filter(x -> x.getFinalidade().equals("Entrada")).collect(Collectors.toList()));
			if (saida)
				obsOrcamento.addAll(orcamentoController.findAllDTORelatorioNew(prod).stream()
						.filter(x -> x.getFinalidade().equals("Saida")).collect(Collectors.toList()));

			if (mRealizada)
				obsMaintenance.addAll(orcamentoController.findAllDTOEquipamentoNew(prod).stream()
						.filter(x -> !x.isLaboratorio()).collect(Collectors.toList()));
			if (mCurso)
				obsMaintenance.addAll(orcamentoController.findAllDTOEquipamentoNew(prod).stream()
						.filter(x -> x.isLaboratorio()).collect(Collectors.toList()));

			
			
			if (inicioDatePiker.getValue() != null) {

				Date gettedDatePickerDateStart = Date.valueOf(inicioDatePiker.getValue());
				obsOrcamento = obsOrcamento.filtered(x -> {

//	    			int date = x.getData_chegada().getDate() -1 ;
//	    			Date data = x.getData_chegada();
//	    			data.setDate(date);
//	    			if(data.after(gettedDatePickerDateStart))
//	    					return true;
//	    			return false;

					if (x.getData_chegada().after(gettedDatePickerDateStart)
							|| x.getData_chegada().equals(gettedDatePickerDateStart))
						return true;
					return false;
				});
				obsMaintenance = obsMaintenance.filtered(x -> x.getData_chegada().after(gettedDatePickerDateStart));

			}
			if (finalDatePiker.getValue() != null) {
				Date gettedDatePickerDateFinal = Date.valueOf(finalDatePiker.getValue());
				obsOrcamento = obsOrcamento.filtered(x -> {
					if (x.getData_chegada().before(gettedDatePickerDateFinal)
							|| x.getData_chegada().equals(gettedDatePickerDateFinal))
						return true;
					return false;
				});
				obsMaintenance = obsMaintenance
						.filtered(x -> x.getData_chegada().before(gettedDatePickerDateFinal));
			}

			MaterialOutTable.setItems(obsOrcamento);
			MaintenanceTable.setItems(obsMaintenance);
	}
}

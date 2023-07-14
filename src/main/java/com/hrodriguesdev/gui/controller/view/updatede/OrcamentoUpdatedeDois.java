package com.hrodriguesdev.gui.controller.view.updatede;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.entities.Equipamento;
import com.hrodriguesdev.entities.Orcamento;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.products.ProductsOs;
import com.hrodriguesdev.gui.alert.Alerts;
import com.hrodriguesdev.gui.controller.view.insert.OrcamentoInsert;
import com.hrodriguesdev.utilitary.Log;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OrcamentoUpdatedeDois extends OrcamentoInsert implements Initializable {
	
	private Orcamento orcamento;
	private String obsSelectedText;
	
	public OrcamentoUpdatedeDois(Equipamento equipamento, Orcamento orcamento, ObservableList<Product> obsMateriais,
			String obsSelectedText) {
		super(equipamento, orcamento);
		super.obsMateriais = obsMateriais;
		this.orcamento = orcamento;
		this.obsSelectedText = obsSelectedText;
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
//		ProductsController productsController = new ProductsController(); 
//		super.obsMateriais = productsController.findAllByOrcamentoId(orcamento.getId());
		obsSelected.setText(obsSelectedText);
	}	
	
	@Override
	protected void cancelar(ActionEvent event) throws IOException {
		fecharView();
	}
	
	@Override
	protected void salvar(ActionEvent event) throws IOException {
		orcamentoId = orcamento.getId();
		
		List<ProductsOs> listProductsOs = new ArrayList<>();
		
		if(!obsSelected.getText().isBlank())
			orcamento.setItem(obsSelected.getText());
		if(obsMateriais.size()>0) {
			obsMateriais.forEach((product) -> {
				listProductsOs.add(new ProductsOs(orcamentoId, product.getId(), product.getQtde()));				
			});
		}else if(obsSelected.getText().isBlank())
			return ;
		
		OSController osController = new OSController();
		OrcamentoController orcamentoController = new OrcamentoController();
		if(osController.updatOS(listProductsOs) )
			if(orcamentoController.update(orcamento)) 
				fecharView();
			else {
				Log.logString("OrcamentoInsert", "Erro ao Atualizar o orcamento");
				System.out.println("Erro ao Atualizar o orcamento\nOrcamentoInsert\n");
			}
		else {
			Log.logString("OrcamentoInsert", "Erro Criar a lista da os");
			Alerts.showAlert("Error", "Contatar Adm", "", AlertType.ERROR);
			System.out.println("Erro Criar a lista da os\nOrcamentoInsert\n");
		}
	}
	
	private void fecharView() {
		Stage stage =(Stage) observacaoVbox.getScene().getWindow();
		stage.close();
	}
}

package com.hrodriguesdev.gui.controller.view.updatede;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.hrodriguesdev.controller.OSController;
import com.hrodriguesdev.controller.OrcamentoController;
import com.hrodriguesdev.dependency.InjecaoDependency;
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

public class OrcamentoUpdate extends OrcamentoInsert implements Initializable {

	private Orcamento orcamento;
	private String obsSelectedText;

	public OrcamentoUpdate(Equipamento equipamento, Orcamento orcamento, ObservableList<Product> obsMateriais,
			String obsSelectedText) {
		super(equipamento, orcamento);
		super.obsMateriais = obsMateriais;
		this.orcamento = orcamento;
		this.obsSelectedText = obsSelectedText;

	}

	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		obsSelected.setText(obsSelectedText);
		obsSelected.setEditable(true);

	}

	@Override
	protected void cancelar(ActionEvent event) throws IOException {
		fecharView();
	}

	@Override
	protected void salvar(ActionEvent event) throws IOException {
		orcamentoId = orcamento.getId();

		List<ProductsOs> listProductsOs = new ArrayList<>();

		if (!obsSelected.getText().isBlank())
			orcamento.setItem(obsSelected.getText());
		if (obsMateriais.size() > 0) {
			obsMateriais.forEach((product) -> {
				listProductsOs.add(new ProductsOs(orcamentoId, product.getId(), product.getQtde()));
			});
		} else if (obsSelected.getText().isBlank())
			return;

		OSController osController = new OSController();
		OrcamentoController orcamentoController = new OrcamentoController();

		if (!osController.isContentOs(orcamento.getId())) {
			if (!osController.createNewOS(listProductsOs)) {
				Log.logString("OrcamentoInsert", "Erro Criar a lista da os");
				Alerts.showAlert("Error", "Contatar Adm", "", AlertType.ERROR);
				System.out.println("Erro Criar a lista da os\nOrcamentoInsert\n");
				return;
			}

		} else if (osController.isContentOsOut(orcamentoId)) {
			Alerts.showAlert("Seguranca", "Não pode alterar um orcamento ja consolidado!",
					"Por motivos de seguranca, orcamentos se torna inalteraveis quando a já executado a manutenção!",
					AlertType.ERROR);
		} else if (!osController.updatOS(listProductsOs)) {
			Log.logString("OrcamentoInsert", "Erro Criar a lista da os");
			Alerts.showAlert("Error", "Contatar Adm", "", AlertType.ERROR);
			System.out.println("Erro Criar a lista da os\nOrcamentoInsert\n");
			return;
		}

		if (orcamentoController.update(orcamento))
			fecharView();
		else {
			Log.logString("OrcamentoInsert", "Erro ao Atualizar o orcamento");
			System.out.println("Erro ao Atualizar o orcamento\nOrcamentoInsert\n");
		}
		try {
			InjecaoDependency.TAB_FIND_CONTROLLER.clickOrcamento(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fecharView() {
		Stage stage = (Stage) observacaoVbox.getScene().getWindow();
		stage.close();
	}
}

package com.hrodriguesdev.gui.controller.view.itens;

import com.hrodriguesdev.controller.EnsaiosController;
import com.hrodriguesdev.entities.Ensaios;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GridPaneEnsaios {

	protected EnsaiosController ensaiosController = new EnsaiosController();
	
	@FXML
	private TextField refeVal1, refeVal2, refeVal3, aplicado1, aplicado2, aplicado3, sinalCalibr11, sinalCalibr12,
			sinalCalibr13, sinalCalibr21, sinalCalibr22, sinalCalibr23;
	
	protected void writeValues(Ensaios ensaio) throws NullPointerException {
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

	protected void clearValues() {
		String referencia = "-\n-\n-";
		String primeiro = "-\n-\n-";
		String segundo = "-\n-\n-";
		String terceiro = "-\n-\n-";

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
}

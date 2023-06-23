package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.AlfaPirometrosApplication;
import com.hrodriguesdev.dao.repository.ItensRepositoryFind;
import com.hrodriguesdev.dao.repository.ItensRepositoryUpdatede;
import com.hrodriguesdev.dao.repository.RepositoryCabos;
import com.hrodriguesdev.dao.repository.RepositoryConsumo;
import com.hrodriguesdev.dao.repository.RepositoryEletricos;
import com.hrodriguesdev.dao.repository.RepositoryEletronicos;
import com.hrodriguesdev.dao.repository.RepositoryEstetico;
import com.hrodriguesdev.dao.repository.RepositoryProducts;
import com.hrodriguesdev.dao.repository.RepositorySinal;
import com.hrodriguesdev.entities.EstoqueCabos;
import com.hrodriguesdev.entities.EstoqueConsumo;
import com.hrodriguesdev.entities.EstoqueEletricos;
import com.hrodriguesdev.entities.EstoqueEletronicos;
import com.hrodriguesdev.entities.EstoqueEstetico;
import com.hrodriguesdev.entities.EstoqueSinal;
import com.hrodriguesdev.entities.Product;
import com.hrodriguesdev.entities.UnidadeMedida;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
@SuppressWarnings(value = { "unused" })
public class EstoqueService {
	private RepositoryCabos cabosRep = new RepositoryCabos();
	private RepositoryConsumo consumoRep = new RepositoryConsumo();
	private RepositoryEletricos eletricosRep = new RepositoryEletricos();
	private RepositoryEletronicos eletronicosRep = new RepositoryEletronicos();
	private RepositoryEstetico esteticoRep = new RepositoryEstetico();
	private RepositorySinal sinalRep = new RepositorySinal();
	
	private RepositoryProducts repositoryProducts = new RepositoryProducts();
	private UnidadeMedidaService unidadeService = new UnidadeMedidaService();
	
	public String[] findAllNameProducts() {
		/*
		String[] cabos = cabosRep.copyNameAllColuns();
		String[] consumo = consumoRep.copyNameAllColuns();
		String[] eletricos = eletricosRep.copyNameAllColuns();
		String[] eletronicos = eletronicosRep.copyNameAllColuns();
		String[] estetico = esteticoRep.copyNameAllColuns();
		String[] sinal = sinalRep.copyNameAllColuns();	
		
		int length = 
				cabos.length + 
				consumo.length +
				eletricos.length +
				eletronicos.length +
				estetico.length +
				sinal.length;
		
		String[] nameAllProducts = new String[length];
		*/
		return null;
	}

	public boolean saidaDoEstoque(Long orcamento_id) {
		
		ItensRepositoryFind repository = new ItensRepositoryFind();
		
		EstoqueConsumo consumo = repository.consumoByOrcamentoId(orcamento_id);
		EstoqueEstetico estetico = repository.esteticoByOrcamentoId(orcamento_id);
		EstoqueEletricos eletrico = repository.eletricosByOrcamentoId(orcamento_id);
		EstoqueEletronicos eletronico = repository.eletronicosByOrcamentoId(orcamento_id);
		EstoqueSinal sinal = repository.sinalByOrcamentoId(orcamento_id);
		EstoqueCabos cabos = repository.cabosByOrcamentoId(orcamento_id);
		
		ItensRepositoryUpdatede repositoryUpdate = new ItensRepositoryUpdatede();
		
		consumo.setSaida(true);
		estetico.setSaida(true);
		eletrico.setSaida(true);
		eletronico.setSaida(true);
		sinal.setSaida(true);
		cabos.setSaida(true);
		
//		return repositoryUpdate.saidaDoEstoque(consumo, eletrico, eletronico, estetico, sinal, cabos);
		
		repositoryUpdate.updatedeConsumo(consumo);
		repositoryUpdate.updatedeEletricos(eletrico);
		repositoryUpdate.updatedeEletronicos(eletronico);
		repositoryUpdate.updatedeEstetico(estetico);
		repositoryUpdate.updatedeSinal(sinal);
		repositoryUpdate.updatedeCabos(cabos);
		
		 return true;
	}

	public void refreshObsProducts() {
		List<String> list = repositoryProducts.findAllNameProducts();
		AlfaPirometrosApplication.OBS_PRODUCTS.addAll(list);
		
	}

	public ObservableList<Product> findAllProductsObs() {
		ObservableList<Product> obs = FXCollections.observableArrayList();
		List<Product> list = repositoryProducts.findAllProducts();
		List<UnidadeMedida> listUnidade = unidadeService.findAll();
		
		list.forEach(produto -> {
			String unidadeStr= listUnidade.stream().filter(unidade -> unidade.getId() == produto.getUnidade_medida()).findFirst().get().getUnidade();
			produto.setUnidadeMedida(unidadeStr); 
		});
		
		obs.addAll(list);
		return obs;
	}
	

}

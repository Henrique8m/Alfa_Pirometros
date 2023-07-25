package com.hrodriguesdev.service;

import java.util.List;
import java.util.stream.Collectors;

import com.hrodriguesdev.dao.repository.OSRepository;
import com.hrodriguesdev.entities.products.ProductsOs;

public class OSService {

	private OSRepository repositoryOs = new OSRepository();
	
	public boolean isContentOs(Long idOrcamento) {
		if(repositoryOs.findAllByOrcamentoId(idOrcamento, "products_os ").isEmpty())
			return false;			
		return true;
	}
	
	public boolean isContentOsOut(Long idOrcamento) {
		if(repositoryOs.findAllByOrcamentoId(idOrcamento, "products_out ").isEmpty())
			return false;			
		return true;
	}	
	
	public boolean createNewOs(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_os ").isEmpty())
				return repositoryOs.createNewOs(list, "products_os ");
		return false;
	}
	
	public boolean createNewOSOut(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_out ").isEmpty())
				return repositoryOs.createNewOs(list, "products_out ");
			else {
				System.out.println("Ja tem saida para este orcamento - Classe OSService.java");
				return true;
			}
		return false;
	}
	
	public boolean createNewOSIn(List<ProductsOs> list) {
		if(list.size()>0)
			if(repositoryOs.findAllByOrcamentoId(list.get(0).getIdOrcamento(), "products_inp ").isEmpty())
				return repositoryOs.createNewOs(list, "products_inp ");
		return false;
	}
	
	public List<ProductsOs> findAll(){
		return repositoryOs.findAll("products_os ");
	}
	
	public List<ProductsOs> findAllIn() {
		return repositoryOs.findAll("products_inp ");
	}
	
	public List<ProductsOs> findAllOut() {
		return repositoryOs.findAll("products_out ");
	}

	public List<ProductsOs> findAllOsByOrcamentoId(Long id) {
		return repositoryOs.findAllByOrcamentoId(id, "products_os ");
	}
	
	public List<ProductsOs> findAllByOrcamentoId(Long id) {
		List<ProductsOs> list = repositoryOs.findAllByOrcamentoId(id, "products_out ");
		list.addAll(repositoryOs.findAllByOrcamentoId(id, "products_inp "));
		if(list.size()==0)
			return repositoryOs.findAllByOrcamentoId(id, "products_os ");		
		return list;
	}
	
	public boolean updatOS(List<ProductsOs> listProductsOs) {
		if(listProductsOs.size() > 0 ) {
			Long orcamentoId = listProductsOs.get(0).getIdOrcamento();
			List<ProductsOs> listOs = repositoryOs.findAllByOrcamentoId(orcamentoId, "products_os ");
			List<ProductsOs> listOut = repositoryOs.findAllByOrcamentoId(orcamentoId, "products_out ");
			
			if(listOs.size() > 0 ) {
				metodoUpdateOs(listProductsOs, listOs, "products_os ");
			}
			if(listOut.size() > 0) {
				metodoUpdateOs(listProductsOs, listOut, "products_out ");
			}
		}else {
			return false;
		}		
		 return true;
	}

	public void delete(Long id, String table) {
		repositoryOs.delete(id, table);
	}

	private void updateProd(String table, ProductsOs productOs) {
		repositoryOs.updateProd(table, productOs);
	}	
	
	private void metodoUpdateOs(List<ProductsOs> listNovosProductsOs, List<ProductsOs> listVindaBancoOs, String table) {		
//		vou filtrar a lista do banco de dados "listOs" e deixar somente as novas inserçoes que estao vinda na listProductsOs
		List<ProductsOs> listCriarNovo = listNovosProductsOs.stream().filter(prodNovo -> !listVindaBancoOs.contains(prodNovo)).collect(Collectors.toList());
		if(!listCriarNovo.isEmpty())
			repositoryOs.createNewOs(listCriarNovo, table);

		
//		vou filtrar a lista do banco de dados "listOs" e deixar somente os produtos que ja existe!
//		depois verificar a quantidade, se tiver alteração, iremos atulizar
		List<ProductsOs> listAtualizarQuantidade = listNovosProductsOs.stream().filter(prodNovo -> listVindaBancoOs.contains(prodNovo)).collect(Collectors.toList());
		listAtualizarQuantidade.forEach(atualizar -> {//					
			ProductsOs prodAtualizar = listVindaBancoOs.stream().filter(x -> x.getProductId().equals(atualizar.getProductId())).findFirst().get();
					if(!atualizar.getQtde().equals(prodAtualizar.getQtde())) {
//						A nova lista, nao contem o id do objeto no banco, entao alteramos o arquivo original do banco
						prodAtualizar.setQtde(atualizar.getQtde());
						updateProd(table, prodAtualizar);	
					}
//			
		});
		
//		vou filtrar a lista do banco de dados "listOs" e deixar somente os produtos que nao existe na nova lista
//		depois apagar no banco os que estao sobresalente				
		List<ProductsOs> listDelete = listVindaBancoOs.stream().filter(prodBanco -> {
			if(listNovosProductsOs.contains(prodBanco)) {
				listNovosProductsOs.remove(prodBanco);
				return false;
			}
			return true;
		}).collect(Collectors.toList());
		
		listDelete.forEach(x-> delete(x.getId(), table));
	}


}

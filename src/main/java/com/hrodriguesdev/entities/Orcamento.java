package com.hrodriguesdev.entities;

public class Orcamento {
	public Long getId() {
		return id;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	private Long id;
	private String Item;
	private Integer quantidade;
	
	public Orcamento(String item, Integer quantidade) {
		Item = item;
		this.quantidade = quantidade;
	}
	public Orcamento() {}

}

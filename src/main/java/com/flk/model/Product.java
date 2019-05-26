package com.flk.model;

public class Product {

	private String _name;
	private String _detail;
	private double _price;
	
	public Product(String name, String detail, double price) {
		this._name = name;
		this._detail = detail;
		this._price = price;
	}
	
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getDetail() {
		return _detail;
	}
	public void setDetail(String _detail) {
		this._detail = _detail;
	}
	public double getPrice() {
		return _price;
	}
	public void setPrice(double _price) {
		this._price = _price;
	}
	
}

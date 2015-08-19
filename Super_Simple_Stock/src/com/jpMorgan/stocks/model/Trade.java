package com.jpMorgan.stocks.model;

import java.util.Date;

public class Trade {
	
	private Date myDate = null;
	
	private String stock = null;
	
	private String indicator = null;
	
	private int quantityOfShares = 0;
	
	private double price = 0;
	
	
	public Trade(){
	}
	
	public Date getMyDate() {
		return myDate;
	}
	
	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}
	
	public int getQuantityOfShares() {
		return quantityOfShares;
	}
	
	public void setQuantityOfShares(int quantityOfShares) {
		this.quantityOfShares = quantityOfShares;
	}
	
	public String getIndicator() {
		return indicator;
	}
	
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getStock() {
		return stock;
	}
	
	public void setStock(String stock) {
		this.stock = stock;
	}
	
}

package com.jpMorgan.stocks;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jpMorgan.stocks.model.Stock;
import com.jpMorgan.stocks.model.Trade;

public class SuperSimpleStock {
	
	private static List<Stock> stocks = new ArrayList();
	private static List<Trade> trades = new ArrayList();
	
	public static void main(String []args){
		SuperSimpleStock simpleStock = new SuperSimpleStock();
		
		//Get all stocks stored in the stockValues.xml file
		simpleStock.getStocks();
		
		//Get all trades stored in the tradeValues.xml file
		simpleStock.getTrades();
		

     	SuperSimpleStockUtils stockUtils = new SuperSimpleStockUtils();
     	
     	
     	double allStockPrice = 0;
     	double countStocks = 0;
     	
     	try {
     		//Iterate stocks field, and calculate Dividend Yield, P/E Ratio and Volume Weighted Stock Price
	     	for(Stock stock: stocks){
	     		System.out.println("");
				System.out.println("********************** Stock: " + stock.getStockSymbol() + " **************************");
				System.out.println("");
	     		System.out.println("------------Calulating Dividend Yield------------");
				double divYield = stockUtils.getDividendYield(stock);
				
				
				System.out.println("------------Calulating P/E Ratio------------");
				stockUtils.getPERatio(stock, divYield);
				
				
				System.out.println("------------Calulating Volume Weighted Stock Price------------");
				double stockPrice = stockUtils.getStockPrice(stock, trades);
				System.out.println("Volume Weighted Stock Price: " + stockPrice);
				System.out.println("");
				System.out.println("");
				
				if(stockPrice > 0){
					if(countStocks==0)
						allStockPrice = stockPrice;
					else
						allStockPrice *= stockPrice;
					countStocks++;
				}
				
				
			}
	     	
	     	System.out.println("");
			System.out.println("****************************************************");
			System.out.println("");
	     	
			//Finally calculate Geometric Mean
	     	System.out.println("------------Calulating Geometric Mean------------");
			stockUtils.getGeometricMean(allStockPrice,countStocks);
			
			
     	}catch(Exception e){
     		System.out.println("Error SuperSimpleStock.main : " + e.getMessage());
     	}
		
	}
	
	//Method to obtain from stockValues.xml all stocks
	private List getStocks(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = (Document) dBuilder.parse((SuperSimpleStock.class.getResourceAsStream("/com/jpMorgan/stocks/conf/stockValues.xml")));
	    	doc.getDocumentElement().normalize();
	    			
	    	NodeList nList = doc.getElementsByTagName("stock");

	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node nNode = nList.item(temp);
	    				
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;
	    			
	    			Stock stock = new Stock();
	    			
	    			stock.setStockSymbol(eElement.getElementsByTagName("stockSymbol").item(0).getTextContent());
	    			stock.setType(eElement.getElementsByTagName("type").item(0).getTextContent());
	    			stock.setLastDividend(Double.parseDouble(eElement.getElementsByTagName("lastDividend").item(0).getTextContent()));
	    			stock.setFixedDividend(Double.parseDouble(eElement.getElementsByTagName("fixedDividend").item(0).getTextContent()));
	    			stock.setParValue(Double.parseDouble(eElement.getElementsByTagName("parValue").item(0).getTextContent()));
	    			stock.setMarketPrice(Double.parseDouble(eElement.getElementsByTagName("marketPrice").item(0).getTextContent()));
	    			
	    			stocks.add(stock);
	    		}
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error SuperSimpleStock.main : " + e.getMessage());
		}
		return stocks;
	}
	
	//Method to obtain from tradeValues.xml all trades
	private List getTrades(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = (Document) dBuilder.parse((SuperSimpleStock.class.getResourceAsStream("/com/jpMorgan/stocks/conf/tradeValues.xml")));
	    	doc.getDocumentElement().normalize();
	    			
	    	NodeList nList = doc.getElementsByTagName("trade");

	    	for (int temp = 0; temp < nList.getLength(); temp++) {

	    		Node nNode = nList.item(temp);
	    				
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;
	    			
	    			Trade trade = new Trade();
	    				    			
	    			trade.setMyDate(new Date());
	    			trade.setStock(eElement.getElementsByTagName("stockType").item(0).getTextContent());
	    			trade.setIndicator(eElement.getElementsByTagName("indicator").item(0).getTextContent());
	    			trade.setQuantityOfShares(Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent()));
	    			trade.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
	    			
	    			trades.add(trade);
	    		}
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error SuperSimpleStock.main : " + e.getMessage());
		}
		return stocks;
	}
}

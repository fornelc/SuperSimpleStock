package com.jpMorgan.stocks;

import java.util.Calendar;
import java.util.List;

import com.jpMorgan.stocks.model.Stock;
import com.jpMorgan.stocks.model.StockConstants;
import com.jpMorgan.stocks.model.Trade;

public class SuperSimpleStockUtils {
	
	//Method to get DividendYield
	public double getDividendYield(Stock stock){
		double divYield = 0;
		try{
			
			//Market Price must be greater than zero, because it will be divided
			if(stock.getMarketPrice() <= 0){
				throw new Exception("Stock '" + stock.getStockSymbol() + "' must have a Market Price greater than zero");
			}
			
			if( stock.getType().equals(StockConstants.PREFERRED)){
				divYield = (stock.getFixedDividend() * stock.getParValue()) / stock.getMarketPrice();
			}else{
				divYield = stock.getLastDividend() / stock.getMarketPrice();
			}
			
			System.out.println("Result Dividend Yield: " + divYield);
			System.out.println("");
			System.out.println("");
			
		}catch(Exception e){
			System.out.println("Stock: " + stock.getStockSymbol() + " - Error calculating dividendYield : " + e.getMessage());
			System.out.println("");
			System.out.println("");
		}
		return divYield;
	}

	//Method to get PERatio
	public double getPERatio(Stock stock, double divYield) throws Exception{
		double peRatio = 0;
		try{
			
			//Dividend Yield must be greater than zero, because it will be divided
			if(divYield <= 0){
				throw new Exception("Stock '" + stock.getStockSymbol() + "' must have a Dividend Yield greater than zero");
			}

			peRatio = stock.getMarketPrice() / divYield;

			System.out.println("Result P/E Ratio: " + peRatio);
			System.out.println("");
			System.out.println("");
			
		}catch(Exception e){
			System.out.println("Stock: " + stock.getStockSymbol() + " - Error calculating PERatio : " + e.getMessage());
			System.out.println("");
			System.out.println("");
		}
		
		return peRatio;
	}
	
	//Method to get StockPrice
	public double getStockPrice(Stock stock, List<Trade> trades) throws Exception{
		double tradeSumPrice = 0;
		double sumQuantity = 0;
		double stockPrice = 0;
		int minutes = 15;
		
		try{		
			
			//Iterate trades to calculate tradePrice and quantity sum
			for(Trade trade : trades){
				if(trade.getStock().equals(stock.getStockSymbol())){
					//Check if it is based on trades in past 15 minutes
					Calendar dateTime = Calendar.getInstance();
					dateTime.add(Calendar.MINUTE, -minutes);
				
					if(dateTime.getTime().compareTo(trade.getMyDate()) <= 0){
						//Calculate summation of price multiplied by quantity
						tradeSumPrice += trade.getPrice() * trade.getQuantityOfShares();
						
						//Calculate summation of quantity
						sumQuantity += trade.getQuantityOfShares();
					}
				}
			}
			
			//Calculate Stock Price
			if(sumQuantity > 0)
				stockPrice = tradeSumPrice / sumQuantity;
			
		}catch(Exception e){
			System.out.println("Stock: " + stock.getStockSymbol() + " - Error calculating Volume Weighted Stock Price : " + e.getMessage());
		}
		
		return stockPrice;
	}
	
	//Method to get GeometricMean
	public double getGeometricMean(double allStockPrice, double countStocks) throws Exception{
		double geometricMean = 0;
		try{			
			geometricMean = Math.pow(allStockPrice, (1/countStocks));
			
			System.out.println("Result Geometric Mean: " +geometricMean);
			
		}catch(Exception e){
			System.out.println("Error calculating Volume Weighted Stock Price : " + e.getMessage());
		}
		
		return geometricMean;
	}
}

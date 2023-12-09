package com.vcs.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyConversion {
	
	private Long id;
	private String from;
	private String to;
	private double conversionMultiple;
	private int quantity;
	private double calculatedAmount;
	private String environment;
}

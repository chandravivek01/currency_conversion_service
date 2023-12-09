package com.vcs.currencyconversionservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vcs.currencyconversionservice.model.CurrencyConversion;
import com.vcs.currencyconversionservice.service.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateExchangeAmount(@PathVariable String from, @PathVariable String to, 
				@PathVariable int quantity) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
											CurrencyConversion.class, uriVariables);
		
		CurrencyConversion currencyConversion = response.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setCalculatedAmount(quantity * currencyConversion.getConversionMultiple());
		currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " rest-template");
		return currencyConversion;
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateExchangeAmountFeign(@PathVariable String from, @PathVariable String to, 
				@PathVariable int quantity) {
	
		
		CurrencyConversion currencyConversion = proxy.retrieveExchange(from, to);
		currencyConversion.setQuantity(quantity);
		currencyConversion.setCalculatedAmount(quantity * currencyConversion.getConversionMultiple());
		currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " feign");
		return currencyConversion;
	}
	
	
}

package com.excel.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.excel.demo.bean.ExchangeRate;
import com.excel.demo.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

	@Autowired
	private ExchangeRateRepository repository; 
	
	public ExchangeRate createExchangeRate(Map<String, Object> rateMap) {
		
//		ExchangeRate rate = new ExchangeRate( rateMap.get("Ccy").toString(), rateMap.get("RelvtCcy").toString(), rateMap.get("Mid"), 
//				rateMap.get("Bid"), rateMap.get("Ask"), rateMap.get("FeedSource").toString(),(Timestamp)rateMap.get("LastDate"));
		
//		ExchangeRate rate = new ExchangeRate( rateMap.get("Ccy").toString(), rateMap.get("RelvtCcy").toString(), rateMap.get("Mid").toString(), 
//				rateMap.get("Bid").toString(), rateMap.get("Ask").toString(), rateMap.get("FeedSource").toString(),rateMap.get("LastDate").toString());
//		
//		repository.save(rate);
//		return rate;
		return null;
	}
	
	public ExchangeRate createExchangeRate(ExchangeRate rate) {
		repository.save(rate);
		return rate;
	}
	
	public void createExchangeRate(List<ExchangeRate> rates) {
		repository.saveAll(rates);
	}
	
	//@Cacheable(value = "CcyRateCache", keyGenerator = "wiselyKeyGenerator")
	public ExchangeRate getRateDetails(String as_Ccy, String as_RelvtCcy) {
		
		return repository.findAllByQuery(as_Ccy, as_RelvtCcy).get(0);
	}
	

	public List<ExchangeRate> getRateDetails() {
		return repository.findAll();
	}
	
	
}

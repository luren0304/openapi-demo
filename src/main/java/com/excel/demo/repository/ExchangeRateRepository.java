package com.excel.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.excel.demo.bean.ExchangeRate;

public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String> {
	
//	public ExchangeRate findByCcy(String as_Ccy, String as_RelvtCcy); 
//	
//	public List<ExchangeRate> findbyLastDate(Timestamp ats_LastDte);
//
//	public ExchangeRate findAll(String as_Ccy, String as_RelvtCcy);
	
	
	/**
	 * 
	 * 
	 * @param as_Ccy
	 * @param as_RelvtCcy
	 * @return
	 */
	//@Query("{'ccy':?#{[0]}, 'relvtCcy':?#{[1]}}")
	@Query(value="{'ccy':?#{[0]}, 'relvtCcy':?#{[1]}}", fields= "{'bid' : 1, 'ask' : 1, 'mid' : 1, 'feedSource': 1, 'lastDate' : 1}")
	public List<ExchangeRate> findAllByQuery(String as_Ccy, String as_RelvtCcy);
	
	
}



package com.excel.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.alibaba.fastjson.JSON;
import com.excel.demo.bean.ExchangeRate;
import com.excel.demo.service.ExchangeRateService;

@RestController
//@Controller
@RequestMapping("/exchangeRate")
public class ExchangeRateController {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

	@Autowired
	ExchangeRateService rateService;

	
	
	@RequestMapping(method = RequestMethod.POST, value = "/createExchangeRateObj")
//	@ResponseBody
	public Map<String, Object> createExchangeRate(@RequestBody ExchangeRate rate){
		logger.info("createExchangeRate rate start");
		rateService.createExchangeRate(rate);
		Map<String, Object> respone = new LinkedHashMap<String, Object>();
		respone.put("message", "Create Exchange Rate object successfully");
		respone.put("ExchangeRate", rate);
		logger.info("createExchangeRate rate End");
		return respone;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/RateCcy/{RateCcy}/RateRelvtCcy/{RateRelvtCcy}")
	@ResponseBody
	/*public ExchangeRate getRateDetails(@PathVariable("RateCcy") String as_Ccy, @PathVariable("RateRelvtCcy") String as_RelvtCcy) {
		logger.info("getRateDetails ccy = " + as_Ccy + " relvtCcy = " + as_RelvtCcy);
		return rateService.getRateDetails(as_Ccy, as_RelvtCcy);
		
	}*/
	
	public String getRateDetails(@PathVariable("RateCcy") String as_Ccy, @PathVariable("RateRelvtCcy") String as_RelvtCcy) {
		logger.info("getRateDetails ccy = " + as_Ccy + " relvtCcy = " + as_RelvtCcy);
		return rateService.getRateDetails(as_Ccy, as_RelvtCcy).toString();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
//	@ResponseBody
	public List<ExchangeRate> getRateDetails() {
		logger.info("getRateDetails all");
		return rateService.getRateDetails();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/createExchangeRateLst")
//	@ResponseBody
	public String createExchangeRate(@RequestBody List<ExchangeRate> rateLst){
		logger.info("createExchangeRate rateLst start");
		logger.info("createExchangeRate rateLst " + rateLst.get(0));
		if(rateLst.get(0) instanceof ExchangeRate  ) {
			logger.info("createExchangeRate rateLst true");
		}
//		logger.info("createExchangeRate rateLst " + rateLst.get(0));
//		logger.info("createExchangeRate " + JSON.toJSONString(rateLst));
		rateService.createExchangeRate(rateLst);
		//ExchangeRate rate = rateService.createExchangeRate(rateLst);
		//Map<String, Object> respone = new LinkedHashMap<String, Object>();
		//respone.put("message", "Create Exchange Rate object successfully");
//		respone.put("ExchangeRate", rate);
		logger.info("createExchangeRate rateLst End");
		return "Create Exchange Rate object successfully";
	}
}

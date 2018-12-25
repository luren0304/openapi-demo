package com.excel.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excel.demo.bean.RateInfo;
import com.excel.demo.utils.InterfaceFileProcess;

@RestController
@RequestMapping("/rateinfo")
public class RateInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(RateInfoController.class);
	@Autowired
	private InterfaceFileProcess interfaceFileProcess;
	
	
	
	@RequestMapping("/index.html")
	public String index() {
		return "hello world";
	}
	
	@RequestMapping("infor")
	@ResponseBody
	public Object getRateInfo() {
		RateInfo l_RateInfo = new RateInfo();
		l_RateInfo.setAsk(new BigDecimal(7.7500 + Math.random()).setScale(8, BigDecimal.ROUND_HALF_UP));
		l_RateInfo.setBid(new BigDecimal(7.7500 + Math.random()).setScale(8, BigDecimal.ROUND_HALF_UP));
		l_RateInfo.setBid(new BigDecimal(7.7500 + Math.random()).setScale(8, BigDecimal.ROUND_HALF_UP));
		l_RateInfo.setCcy_Cde("USD");
		l_RateInfo.setRelvt_Ccy_Cde("HKD");
		l_RateInfo.setFeed_Source("BLOOMBERG");
		//l_RateInfo.setLts_Last_Date(new Timestamp(new Date().getTime()));
		return l_RateInfo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/RateCcy/{RateCcy}/RateRelvtCcy/{RateRelvtCcy}")
	public List<RateInfo> getRateDetails(@PathVariable("RateCcy") String as_Ccy, @PathVariable("RateRelvtCcy") String as_RelvtCcy) {
		logger.info("getRateDetails ccy = " + as_Ccy + " relvtCcy = " + as_RelvtCcy);
		RateInfo rateInfo = new RateInfo();
		rateInfo.setCcy_Cde(as_Ccy);
		rateInfo.setRelvt_Ccy_Cde(as_RelvtCcy);
		return interfaceFileProcess.getDetails(rateInfo);
		
	}	
	
}

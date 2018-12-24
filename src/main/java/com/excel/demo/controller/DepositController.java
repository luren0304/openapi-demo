package com.excel.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excel.demo.bean.Deposit;
import com.excel.demo.service.DepositService;
import com.excel.demo.utils.RateProcess;

@RestController
@RequestMapping("/deposits")
public class DepositController {
	private static final Logger logger = LoggerFactory.getLogger(DepositController.class);
	
	@Autowired
	DepositService depositService;
	
	@Autowired
	private RateProcess rateProcess;
	
	@RequestMapping(method = RequestMethod.POST, value="/createObj")
	public boolean createObj(Deposit deposit) {
		logger.info("createObj start");
		depositService.createDeposit(deposit);
		logger.info("createObj End");
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/createObjList")
	public boolean createObjList(@RequestBody List<Deposit> depositLst) {
		logger.info("createObjList start");
		depositService.createDeposit(depositLst);
		logger.info("createObjList End");
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findone/prodid/{prodid}")
	public List<Deposit> findByProdId(@PathVariable("prodid") String as_ProdId) {
		logger.info("findByProdId" + as_ProdId);
//		return depositService.findByProdId(as_ProdId);
		Deposit deposit = new Deposit();
		deposit.setprodId(as_ProdId);
		deposit.setProduct("Deposits");
		return rateProcess.getDetails(deposit);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findall")
	public List<Deposit> findAll() {
		logger.info("findAll");
		return depositService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findProd")
	public List<Deposit> findAllProd() {
		logger.info("findAllProd");
//		return depositService.findAllProdId();
		return rateProcess.getProds("Deposits", new Deposit());
	}
}

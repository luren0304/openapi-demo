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

import com.excel.demo.bean.Loan;
import com.excel.demo.service.LoanService;
import com.excel.demo.utils.RateProcess;

@RestController
@RequestMapping("/loans")
public class LoanController {
	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
	@Autowired
	LoanService loanService;
	@Autowired
	private RateProcess rateProcess;
	
	@RequestMapping(method = RequestMethod.POST, value="/createObj")
	public boolean createObj(Loan Loan) {
		logger.info("createObj start");
		loanService.createLoan(Loan);
		logger.info("createObj End");
		return true;
	} 
	
	@RequestMapping(method = RequestMethod.POST, value="/createObjList")
	public boolean createObjList(@RequestBody List<Loan> LoanLst) {
		logger.info("createObjList start");
		loanService.createLoan(LoanLst);
		logger.info("createObjList End");
		return true;
	} 
	
	@RequestMapping(method = RequestMethod.GET, value="/findone/prodid/{prodid}")
	public List<Loan> findByProdId(@PathVariable("prodid") String as_ProdId) {
		logger.info("findByProdId" + as_ProdId);
//		return loanService.findByProdId(as_ProdId);
		
		Loan loan = new Loan();
		loan.setprodId(as_ProdId);
		loan.setProduct("Loans");
		return rateProcess.getDetails(loan);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findall")
	public List<Loan> findAll() {
		logger.info("findAll");
		return loanService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findProd")
	public List<Loan> findAllProd() {
		logger.info("findAllProd");
//		return loanService.findAllProdId();
		return rateProcess.getProds("Loans", new Loan());
	}
}

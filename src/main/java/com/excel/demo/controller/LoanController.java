package com.excel.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excel.demo.bean.ErrorMessage;
import com.excel.demo.bean.Loan;
import com.excel.demo.service.LoanService;
import com.excel.demo.utils.InterfaceFileProcess;
import com.jcraft.jsch.SftpException;

@RestController
@RequestMapping("/loans")
public class LoanController {
	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
	
	@Value("${data.mode:db}")
	private String dataMode;
	
	@Autowired
	LoanService loanService;
	@Autowired
	private InterfaceFileProcess interfaceFileProcess;
	
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
	public Object findByProdId(@PathVariable("prodid") String as_ProdId, @RequestHeader("tyk-conn-type") String as_ConnType) {
		logger.info("findByProdId" + as_ProdId);
//		return loanService.findByProdId(as_ProdId);
		logger.info("as_ConnType " + as_ConnType);
		if(as_ConnType !=null && !as_ConnType.equalsIgnoreCase("ftp")) {
			return loanService.findByProdId(as_ProdId);
		}else {
			Loan loan = new Loan();
			loan.setProdId(as_ProdId);
			loan.setProduct("Loans");
			try {
				return interfaceFileProcess.getDetails(loan);
			} catch (SftpException e) {
				ErrorMessage errorMessage = new ErrorMessage();
				errorMessage.setErrorCode(e.id);
				errorMessage.setErrorMsg(e.getMessage());
				return errorMessage;
			}
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findall")
	public List<Loan> findAll() {
		logger.info("findAll");
		return loanService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findProd")
	public Object findAllProd(@RequestHeader("tyk-conn-type") String as_ConnType) {
		logger.info("findAllProd");
		logger.info("as_ConnType " + as_ConnType);
		if(as_ConnType !=null && !as_ConnType.equalsIgnoreCase("ftp")) {
			return loanService.findAllProdId();
		}else {
			try {
				return interfaceFileProcess.getProds("Loans", new Loan());
			} catch (SftpException e) {
				ErrorMessage errorMessage = new ErrorMessage();
				errorMessage.setErrorCode(e.id);
				errorMessage.setErrorMsg(e.getMessage());
				return errorMessage;
			}
		}
	}
}

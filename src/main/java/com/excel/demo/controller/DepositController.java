package com.excel.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excel.demo.bean.Deposit;
import com.excel.demo.service.DepositService;
import com.excel.demo.utils.InterfaceFileProcess;
import com.jcraft.jsch.SftpException;

@RestController
@RequestMapping("/deposits")
public class DepositController {
	private static final Logger logger = LoggerFactory.getLogger(DepositController.class);
//
//	@Value("${data.mode:db}")
//	private String dataMode;
	
	@Autowired
	DepositService depositService;
	
	@Autowired
	private InterfaceFileProcess interfaceFileProcess;
	
	
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
	public Object findByProdId(@PathVariable("prodid") String as_ProdId, @RequestHeader("tyk-conn-type") String as_ConnType) {
		logger.info("findByProdId" + as_ProdId);
//		logger.info("dataMode " + dataMode);
		logger.info("as_ConnType " + as_ConnType);
		if(as_ConnType !=null && !as_ConnType.equalsIgnoreCase("ftp")) {
			return depositService.findByProdId(as_ProdId);
		}else {
			Deposit deposit = new Deposit();
			deposit.setProdId(as_ProdId);
			deposit.setProduct("Deposits");
			try {
				return interfaceFileProcess.getDetails(deposit);
			} catch (SftpException e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("error", e.getMessage());
				logger.error("findByProdId" +  e.getMessage());
				return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findall")
	public List<Deposit> findAll() {
		logger.info("findAll");
		return depositService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/findProd")
	public Object findAllProd(@RequestHeader("tyk-conn-type") String as_ConnType) {
		logger.info("findAllProd");
//		return depositService.findAllProdId();
//		logger.info("dataMode " + dataMode);
		logger.info("as_ConnType " + as_ConnType);
		if(as_ConnType !=null && !as_ConnType.equalsIgnoreCase("ftp")) {
			return depositService.findAllProdId();
		}else {
			try {
				return interfaceFileProcess.getProds("Deposits", new Deposit());
			} catch (SftpException e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("error", e.getMessage());
				logger.error("findByProdId" +  e.getMessage());
				return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	}
}

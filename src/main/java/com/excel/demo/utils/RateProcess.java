package com.excel.demo.utils;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.excel.demo.bean.Deposit;
import com.excel.demo.bean.Loan;
import com.excel.demo.bean.RateInfo;

@Configuration
public class RateProcess {
//	
//	@Value("${exchange.filename.prex}")
	private String exchageFileNamePrex ="exch";
	
	@Autowired
	private ExchangeRateFtpProcess exchangeRateFtpProcess;
	
//	@Autowired
	private CommonUtils commonUtils = new CommonUtils();
	
	private static final Logger logger = LoggerFactory.getLogger(RateProcess.class);
	
	

	/**
	 * get details
	 * 
	 * @param obj
	 * @return
	 */
	public List getDetails(Object obj) {
		List l_Details = new ArrayList();
		String ls_FileName=null;
		if(obj instanceof RateInfo   ) {
			logger.info("Filename Prex: " + exchageFileNamePrex);
			ls_FileName = commonUtils.getFileName(exchageFileNamePrex + "." + ((RateInfo)obj).getCcy_Cde() + "." + ((RateInfo)obj).getRelvt_Ccy_Cde())+".txt";
		}else if (obj instanceof Deposit) {
			ls_FileName =  commonUtils.getFileName("prod" + "." + ((Deposit)obj).getProduct() + "." + ((Deposit)obj).getprodId()) + ".txt";
		}else if (obj instanceof Loan) {
			ls_FileName =  commonUtils.getFileName("prod" + "." + ((Loan)obj).getProduct() + "." + ((Loan)obj).getprodId()) + ".txt";
		}
		
		boolean success = exchangeRateFtpProcess.generateFile(ls_FileName, obj );
		if(success) {
			// put out file
			exchangeRateFtpProcess.upload(ls_FileName);
			// wait 
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//get remote in file
			logger.info("download file start");
			if(exchangeRateFtpProcess.download(ls_FileName)) {
				logger.info("download file successfully");
				logger.info("get file details");
				exchangeRateFtpProcess.getDetailByFile(obj, ls_FileName, l_Details);
			}
			logger.info("download file end");
		}else {
			l_Details.add(obj);
		}
		
		return l_Details;
	}
	
	/**
	 * Get the product id
	 * 
	 * @param as_Prod
	 * @param obj
	 * @return
	 */
	public List getProds(String as_Prod, Object obj){
		String ls_FileName = null;
		String ls_Content = as_Prod;
		List l_PordLst = new ArrayList();
		ls_FileName = commonUtils.getFileName("prod"+ "." + as_Prod)  + ".txt";
		boolean success = exchangeRateFtpProcess.generateFile(ls_FileName,  ls_Content);
		if(success) {
			// put out file
			exchangeRateFtpProcess.upload(ls_FileName);
			// wait 
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//get remote in file
			logger.info("download file start");
			if(exchangeRateFtpProcess.download(ls_FileName)) {
				logger.info("download file successfully");
				logger.info("get file details");
				exchangeRateFtpProcess.getProdsByFile(l_PordLst, ls_FileName, obj);
			}
			logger.info("download file end");
		}
		return l_PordLst;
	}
	
}

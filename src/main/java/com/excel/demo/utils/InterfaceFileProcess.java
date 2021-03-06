package com.excel.demo.utils;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.excel.demo.bean.Deposit;
import com.excel.demo.bean.Loan;
import com.excel.demo.bean.RateInfo;
import com.jcraft.jsch.SftpException;

@Component
public class InterfaceFileProcess {
	
	@Value("${sftp.waitTime}")
	private long waitTime;
	
	@Autowired
	private InterfaceFileFtpProcess interfaceFileFtpProcess;
	
	@Autowired
	private ApplicationContext applicationContext; 
	
	@Autowired
	private CommonUtils commonUtils;
	private static final Logger logger = LoggerFactory.getLogger(InterfaceFileProcess.class);
	
	

	/**
	 * get details
	 * 
	 * @param obj
	 * @return
	 */
	public List getDetails(Object obj) throws SftpException{
		List l_Details = new ArrayList();
		String ls_FileName=null;
		if(obj instanceof RateInfo   ) {
			ls_FileName = commonUtils.getFileName("exch" + "." + ((RateInfo)obj).getCcy_Cde() + "." + ((RateInfo)obj).getRelvt_Ccy_Cde());
		}else if (obj instanceof Deposit) {
			ls_FileName =  commonUtils.getFileName("prod" + "." + ((Deposit)obj).getProduct() + "." + ((Deposit)obj).getProdId());
		}else if (obj instanceof Loan) {
			ls_FileName =  commonUtils.getFileName("prod" + "." + ((Loan)obj).getProduct() + "." + ((Loan)obj).getProdId());
		}
		
		boolean success = commonUtils.generateFile(ls_FileName, obj );
		if(success) {
			// put out file
			try {
				interfaceFileFtpProcess.upload(ls_FileName);
			} catch (Exception e) {
				throw commonUtils.handleErr(e);
			}
			//get remote in file
			logger.info("download file start");
			FtpFileEvent ftpFileEvent = new FtpFileEvent(this, ls_FileName);
			int loopCnt = 10;
			for(int i = 0; i < loopCnt; i ++) {
				try {
					applicationContext.publishEvent(ftpFileEvent);
					success = ftpFileEvent.isDownloadFlag();
					if(success)
						break;
					if(i != loopCnt - 1)
						Thread.sleep(waitTime/loopCnt);
				} catch (Exception e) {
					throw commonUtils.handleErr(e);
				}
			}
			if(success) {
				logger.info("download file successfully");
				logger.info("get file details");
				commonUtils.getDetailByFile(obj, ls_FileName, l_Details);
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
	public List getProds(String as_Prod, Object obj) throws SftpException{
		String ls_FileName = null;
		String ls_Content = as_Prod;
		List l_PordLst = new ArrayList();
		ls_FileName = commonUtils.getFileName("prod"+ "." + as_Prod);
		boolean success = commonUtils.generateFile(ls_FileName,  ls_Content);
		if(success) {
			// put out file
			try {
				interfaceFileFtpProcess.upload(ls_FileName);
			} catch (Exception e) {
				throw commonUtils.handleErr(e);
			}
			
			//get remote in file
			logger.info("download file start");
			FtpFileEvent ftpFileEvent = new FtpFileEvent(this, ls_FileName);
			int loopCnt = 10;
			for(int i = 0; i < loopCnt; i ++) {
				try {
					applicationContext.publishEvent(ftpFileEvent);
					success = ftpFileEvent.isDownloadFlag();
					if(success)
						break;
					if(i != loopCnt - 1)
						Thread.sleep(waitTime/loopCnt);
				} catch (Exception e) {
					throw commonUtils.handleErr(e);
				}
			}
			
			if(success) {
				logger.info("download file successfully");
				logger.info("get file details");
				commonUtils.getProdsByFile(l_PordLst, ls_FileName, obj);
			}
			logger.info("download file end");
		}
		return l_PordLst;
	}
}

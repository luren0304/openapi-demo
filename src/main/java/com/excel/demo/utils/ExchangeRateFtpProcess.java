package com.excel.demo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.excel.demo.bean.Deposit;
import com.excel.demo.bean.Loan;
import com.excel.demo.bean.RateInfo;

@Component
@ConfigurationProperties(prefix="sftp")
public class ExchangeRateFtpProcess {
	
	private String host;
	private String port;
	private String user;
	private String password;
	
	private String remoteInpath;
	private String localInpath;
	private String remoteOutpath;
	private String localOutpath;
	private static Logger LOGGER = LoggerFactory.getLogger(ExchangeRateFtpProcess.class);
	private SFTPClientHandler l_sftpClientHandler = null;

	/**
	 * Generate request file with content
	 * 
	 * @param as_FileName
	 * @param as_content
	 * @return
	 */
	public boolean generateFile(String as_FileName, String as_content) {
		LOGGER.info("as_FileName " + as_FileName);
		LOGGER.info("as_content " + as_content);
		if(as_FileName == null || as_content == null) {
			LOGGER.info("one of as_FileName and as_content is null" );
			return false;
		}
		String ls_context="FileName=" + as_FileName;
		LOGGER.info("as_FileName " + localOutpath +"/"+ as_FileName);
		PrintWriter lPw_Out = null;
		try{
			lPw_Out = new PrintWriter(new BufferedWriter(new FileWriter(localOutpath +"/"+ as_FileName, false)), true);
			lPw_Out.println(ls_context);
			ls_context = "Delimiter=,";
			lPw_Out.println(ls_context);
			ls_context = as_content;
			lPw_Out.println(ls_context);
			lPw_Out.flush();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			
			if(lPw_Out  !=null )
				lPw_Out.close(); 
		}
	}
	
	
	/**
	 * Generate request file by object
	 * 
	 * @param as_FileName
	 * @param obj
	 * @return
	 */
	public boolean generateFile(String as_FileName, Object obj) {
		
		LOGGER.info("as_FileName " + as_FileName);
		LOGGER.info("obj " + obj);
		if(as_FileName == null || obj == null) {
			LOGGER.info("one of as_FileName and obj is null" );
			return false;
		}
		String ls_context="FileName=" + as_FileName;
		LOGGER.info("as_FileName " + localOutpath +"/"+ as_FileName);
		PrintWriter lPw_Out = null;
		try{
			lPw_Out = new PrintWriter(new BufferedWriter(new FileWriter(localOutpath +"/"+ as_FileName, false)), true);
			lPw_Out.println(ls_context);
			ls_context = "Delimiter=,";
			lPw_Out.println(ls_context);
			if (obj instanceof RateInfo) {
				ls_context = ((RateInfo)obj).getCcy_Cde()+","+((RateInfo)obj).getRelvt_Ccy_Cde();
			}else if (obj instanceof Loan) {
				ls_context = ((Loan)obj).getProduct() + "," + ((Loan)obj).getprodId();
			}else if (obj instanceof Deposit) {
				ls_context = ((Deposit)obj).getProduct() + "," + ((Deposit)obj).getprodId();
			}
			lPw_Out.println(ls_context);
			lPw_Out.flush();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			
			if(lPw_Out  !=null )
				lPw_Out.close(); 
		}
	}
	
	/**
	 * 
	 * Upload request file to sftp server
	 * 
	 * @param as_FileName
	 */
	
	public void upload (String as_FileName) {
		try {
			if(l_sftpClientHandler == null) {
				l_sftpClientHandler = SFTPClientHandler.getInstance(host, Integer.parseInt(port));
			}
			l_sftpClientHandler.debugResponses(true);
			l_sftpClientHandler.login(user, password);
			l_sftpClientHandler.chdir(remoteInpath);
			l_sftpClientHandler.put(localOutpath + "/" + as_FileName, as_FileName);
		} catch (Exception e) {
			LOGGER.error("upload file failed. error message: " + e.getMessage() );
			e.printStackTrace();
		}finally {
			if(l_sftpClientHandler != null) {
				try {
					l_sftpClientHandler.quit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Download reply file from SFTP server
	 *
	 * @param as_FileName
	 * @return
	 */
	public boolean download(String as_FileName) {
		try {
			if(l_sftpClientHandler == null) {
				l_sftpClientHandler = SFTPClientHandler.getInstance(host, Integer.parseInt(port));
			}
			l_sftpClientHandler.debugResponses(true);
			l_sftpClientHandler.login(user, password);
			l_sftpClientHandler.chdir(remoteOutpath);
			String [] sFileList = l_sftpClientHandler.dir("*");
			// check file exist
			for (int li_Cnt = 0; li_Cnt < sFileList.length; li_Cnt++) {
				String tmpName = sFileList[li_Cnt];
				LOGGER.info("tmpName : " + tmpName);
				if(tmpName!=null && tmpName.equalsIgnoreCase(as_FileName)) {
					//file exist
					l_sftpClientHandler.get(localInpath + "/" + as_FileName, as_FileName);
					LOGGER.info("The remote file download successfully.");
					LOGGER.info("Rename the remote file start");
					l_sftpClientHandler.rename(as_FileName, as_FileName.substring(0, as_FileName.lastIndexOf(".")) + ".bak" );
					LOGGER.info("Rename the remote file successfully End");
					return true;
				}else if(li_Cnt==sFileList.length -1) {
					LOGGER.info("The remote file doesn't exist.");
					return false;
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("upload file failed. error message: " + e.getMessage() );
			e.printStackTrace();
		}finally {
			if(l_sftpClientHandler != null) {
				try {
					l_sftpClientHandler.quit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Decompose reply file for product ID
	 * 
	 * 
	 * @param a_ProdLst
	 * @param as_Filename
	 * @param obj
	 */
	public void getProdsByFile(List a_ProdLst, String as_Filename, Object obj) {
		BufferedReader l_br = null;
		try{
			l_br = new BufferedReader(new FileReader(localInpath + "/" + as_Filename));
			String ls_Line = l_br.readLine();
			while(ls_Line != null){
				LOGGER.info("ls_Line : " +ls_Line);
				String[] ls_prod = ls_Line.split(",");
				//info
				LOGGER.info("ls_prod.length : " +ls_prod.length);
				if(obj instanceof Loan) {
					((Loan) obj).setprodId(ls_prod[0]);
					a_ProdLst.add(obj);
					obj = new Loan();
				}else if (obj instanceof Deposit) {
					((Deposit) obj).setprodId(ls_prod[0]);
					a_ProdLst.add(obj);
					obj = new Deposit();
				}
				ls_Line = l_br.readLine();
			}
		}catch(FileNotFoundException ex) {
			LOGGER.error("File " + as_Filename +" not found!" );
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(l_br != null){
				try {
					l_br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}

	/**
	 * Decompose reply file for details
	 * 
	 * @param obj
	 * @param as_Filename
	 * @param a_Lst
	 */
	public void getDetailByFile(Object obj, String as_Filename, List a_Lst) {
		
		if(obj instanceof RateInfo   ) {
			getRateDetailByFile((RateInfo)obj, as_Filename, a_Lst);
		}else if(obj instanceof Loan) {
			getLoanDetailByFile((Loan)obj, as_Filename, a_Lst);
		}else if(obj instanceof Deposit) {
			getDepositDetailByFile((Deposit)obj, as_Filename, a_Lst);
		}

	}

	
	/**
	 * Decompose reply file for Deposit details
	 * 
	 * @param info
	 * @param as_Filename
	 * @param a_Lst
	 */
	public void getDepositDetailByFile(Deposit info, String as_Filename, List a_Lst) {
		
		BufferedReader l_br = null;
		try{
			l_br = new BufferedReader(new FileReader(localInpath + "/" + as_Filename));
			String ls_Line = l_br.readLine();
			while(ls_Line != null){
				LOGGER.info("ls_Line : " +ls_Line);
				String[] ls_Info = ls_Line.split(",");
				//info
				if(info.getprodId() == null)
						info.setprodId(ls_Info[0]);
				if (ls_Info.length > 2)
					info.setProduct(ls_Info[1]);
				if (ls_Info.length > 2)
					info.setType(ls_Info[2]);
				if (ls_Info.length > 3)
					info.setSubtype(ls_Info[3]);
				if (ls_Info.length > 4)
					info.setCurrency(ls_Info[4]);
				if (ls_Info.length > 5)
					info.setInterestRate(ls_Info[5]);
				if (ls_Info.length > 6)
					info.setMinamount(ls_Info[6]);
				if (ls_Info.length > 7)
					info.setFee(ls_Info[7]);
				if (ls_Info.length > 8)
					info.setRemark(ls_Info[8]);
				a_Lst.add(info);
				info = new Deposit();
				ls_Line = l_br.readLine();
			}
		}catch(FileNotFoundException ex) {
			LOGGER.error("File " + as_Filename +" not found!" );
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(l_br != null){
				try {
					l_br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Decompose reply file for Loan details
	 * 
	 * @param info
	 * @param as_Filename
	 * @param a_Lst
	 */
	public void getLoanDetailByFile(Loan info, String as_Filename, List a_Lst) {
		
		BufferedReader l_br = null;
		try{
			l_br = new BufferedReader(new FileReader(localInpath + "/" + as_Filename));
			String ls_Line = l_br.readLine();
			while(ls_Line != null){
				LOGGER.info("ls_Line : " +ls_Line);
				String[] ls_Info = ls_Line.split(",");
				if(info.getprodId() == null)
					info.setprodId(ls_Info[0]);
				if (ls_Info.length > 1)
					info.setProduct(ls_Info[1]);
				if (ls_Info.length > 2)	
					info.setType(ls_Info[2]);
				if (ls_Info.length > 3)					
					info.setSubtype(ls_Info[3]);
				if (ls_Info.length > 4)
					info.setInterestRate(ls_Info[4]);
				if (ls_Info.length > 5)
					info.setPrdinfo1(ls_Info[5]);
				if (ls_Info.length > 6)	
					info.setPrdinfo2(ls_Info[6]);
				if (ls_Info.length > 7)
					info.setPrdinfo3(ls_Info[7]);
				if (ls_Info.length > 8)
					info.setFee(ls_Info[8]);
				if (ls_Info.length > 9)
					info.setRemark(ls_Info[9]);
				a_Lst.add(info);
				info = new Loan();
				ls_Line = l_br.readLine();
			}
		}catch(FileNotFoundException ex) {
			LOGGER.error("File " + as_Filename +" not found!" );
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(l_br != null){
				try {
					l_br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Decompose reply file for Rate details
	 * 
	 * @param info
	 * @param as_Filename
	 * @param a_Lst
	 */
	public void getRateDetailByFile(RateInfo info, String as_Filename, List a_Lst) {
		
		BufferedReader l_br = null;
		try{
			l_br = new BufferedReader(new FileReader(localInpath + "/" + as_Filename));
			String ls_Line = l_br.readLine();
			String ls_Ccy = info.getCcy_Cde();
			String ls_RelvtCcy = info.getRelvt_Ccy_Cde();
			while(ls_Line != null){
				LOGGER.info("ls_Line : " +ls_Line);
				String[] rates = ls_Line.split(",");
				//info
					
				if(info.getCcy_Cde() == null) {
					info.setCcy_Cde(ls_Ccy);
					info.setRelvt_Ccy_Cde(ls_RelvtCcy);
				}
				if (rates.length > 1)
					info.setBid(new BigDecimal(rates[1]));
				if (rates.length > 2)
					info.setMid(new BigDecimal(rates[2]));
				if (rates.length > 3)
					info.setAsk(new BigDecimal(rates[3]));
				if (rates.length > 4)
					info.setFeed_Source(rates[4]);
				if (rates.length > 5)
					info.setLastDate(rates[5]);
				a_Lst.add(info);
				info = new RateInfo();
				ls_Line = l_br.readLine();
			}
		}catch(FileNotFoundException ex) {
			LOGGER.error("File " + as_Filename +" not found!" );
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(l_br != null){
				try {
					l_br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String getRemoteInpath() {
		return remoteInpath;
	}
	public void setRemoteInpath(String remoteInpath) {
		this.remoteInpath = remoteInpath;
	}
	public String getLocalInpath() {
		return localInpath;
	}
	public void setLocalInpath(String localInpath) {
		this.localInpath = localInpath;
	}
	public String getRemoteOutpath() {
		return remoteOutpath;
	}
	public void setRemoteOutpath(String remoteOutpath) {
		this.remoteOutpath = remoteOutpath;
	}
	public String getLocalOutpath() {
		return localOutpath;
	}
	public void setLocalOutpath(String localOutpath) {
		this.localOutpath = localOutpath;
	}
	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}

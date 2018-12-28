package com.excel.demo.utils;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;


@Component
@ConfigurationProperties(prefix="sftp")
public class InterfaceFileFtpProcess {
	
	private String host;
	private int port;
	private String user;
	private String password;
	
	private String remoteInpath;
	private String localInpath;
	private String remoteOutpath;
	private String localOutpath;
	
	@Value("${sftp.romteOut.file.conv:txt}")
	private String remoteOutFileConv;
	
	@Value("${sftp.remoteIn.file.conv:txt}")
	private String remoteInFileConv;
	
	private static Logger LOGGER = LoggerFactory.getLogger(InterfaceFileFtpProcess.class);
	private SFTPClientHandler l_sftpClientHandler = null;
	

	
	/**
	 * 
	 * Upload request file to sftp server
	 * 
	 * @param as_FileName
	 */
	
	public void upload (String as_FileName) throws Exception{
		try {
			as_FileName = as_FileName + "." + remoteOutFileConv;
			String ls_localOutpath = null;
			checkDirExist(localOutpath);
			if(!localOutpath.endsWith(File.separator)) {
				ls_localOutpath = localOutpath + File.separator + as_FileName;
			}else {
				ls_localOutpath = localOutpath + as_FileName ;
			}
			LOGGER.info("ls_localOutpath " + ls_localOutpath);
				
			if(l_sftpClientHandler == null) {
				l_sftpClientHandler = SFTPClientHandler.getInstance(host, port);
			}
			l_sftpClientHandler.debugResponses(true);
			l_sftpClientHandler.login(user, password);
			l_sftpClientHandler.chdir(remoteOutpath);
			l_sftpClientHandler.put(ls_localOutpath, as_FileName);
		} catch (Exception e) {
			LOGGER.error("upload file failed. error message: " + e.getMessage() );
			e.printStackTrace();
			throw e;
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
	public boolean download(String as_FileName) throws Exception{
		try {
			as_FileName = as_FileName + "." + remoteInFileConv;
			String ls_localInpath = null;
			checkDirExist(localInpath);
			if(!localInpath.endsWith(File.separator)) {
				ls_localInpath = localInpath + File.separator + as_FileName;
			}else {
				ls_localInpath = localInpath + as_FileName ;
			}
			LOGGER.info("ls_localInpath " + ls_localInpath);
			
			if(l_sftpClientHandler == null) {
				l_sftpClientHandler = SFTPClientHandler.getInstance(host, port);
			}
			l_sftpClientHandler.debugResponses(true);
			l_sftpClientHandler.login(user, password);
			l_sftpClientHandler.chdir(remoteInpath);
			String [] sFileList = l_sftpClientHandler.dir("*");
			// check file exist
			for (int li_Cnt = 0; li_Cnt < sFileList.length; li_Cnt++) {
				String tmpName = sFileList[li_Cnt];
				LOGGER.info("tmpName : " + tmpName);
				if(tmpName!=null && tmpName.equalsIgnoreCase(as_FileName)) {
					//file exist
					l_sftpClientHandler.get(ls_localInpath, as_FileName);
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
			throw e;
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
	 * check if directory exists, if not, create it.
	 */
	public void checkDirExist(String as_Dir) {
		LOGGER.info("as_Dir " + as_Dir);
		File filedir = new File(as_Dir);
		if(!filedir.exists()) {
			filedir.mkdirs();
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


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
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

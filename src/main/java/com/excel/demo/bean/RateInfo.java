package com.excel.demo.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RateInfo {
	
	private String ccy_Cde;
	private String relvt_Ccy_Cde;
	private BigDecimal Mid = null;
	private BigDecimal Bid = null;
	private BigDecimal Ask = null;
	private String feed_Source;
	private String lastDate;
	public String getCcy_Cde() {
		return ccy_Cde;
	}
	public void setCcy_Cde(String ccy_Cde) {
		this.ccy_Cde = ccy_Cde;
	}
	public String getRelvt_Ccy_Cde() {
		return relvt_Ccy_Cde;
	}
	public void setRelvt_Ccy_Cde(String relvt_Ccy_Cde) {
		this.relvt_Ccy_Cde = relvt_Ccy_Cde;
	}
	public BigDecimal getMid() {
		return Mid;
	}
	public void setMid(BigDecimal mid) {
		Mid = mid;
	}
	public BigDecimal getBid() {
		return Bid;
	}
	public void setBid(BigDecimal bid) {
		Bid = bid;
	}
	public BigDecimal getAsk() {
		return Ask;
	}
	public void setAsk(BigDecimal ask) {
		Ask = ask;
	}
	public String getFeed_Source() {
		return feed_Source;
	}
	public void setFeed_Source(String feed_Source) {
		this.feed_Source = feed_Source;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	
}

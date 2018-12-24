package com.excel.demo.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;

//import lombok.Data;

//@Data
public class ExchangeRate {

	//@Id
	//private String id;
	private String ccy;
	private String relvtCcy;
	private String mid = new String("0");
	private String bid = new String("0");
	private String ask = new String("0");
	private String feedSource;
	private String lastDate;
	private Timestamp lastDateTime;

	public Timestamp getLastDateTime() {
		return lastDateTime;
	}

	public void setLastDateTime(Timestamp lastDateTime) {
		this.lastDateTime = lastDateTime;
	}

	/*public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}*/

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getRelvtCcy() {
		return relvtCcy;
	}

	public void setRelvtCcy(String relvtCcy) {
		this.relvtCcy = relvtCcy;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getFeedSource() {
		return feedSource;
	}

	public void setFeedSource(String feedSource) {
		this.feedSource = feedSource;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	@Override
	public String toString() {

		/*
		 * return String.
		 * format("Data:[{id=%s, bid='%s', ask='%s', mid='%s',feedSource='%s',lastDate='%s'}]"
		 * , id, new BigDecimal(bid), new BigDecimal(ask), new BigDecimal(mid),
		 * feedSource, lastDate);
		 */
/*
		return String.format("Data:[{bid='%s', ask='%s', mid='%s',feedSource='%s',lastDate='%s'}]", new BigDecimal(bid),
				new BigDecimal(ask), new BigDecimal(mid), feedSource, lastDate);*/

		return String.format("{bid= %s , ask=%s, mid=%s,feedSource=%s,lastDate=%s}", new BigDecimal(bid),
				new BigDecimal(ask), new BigDecimal(mid), feedSource, lastDate);
	}
}

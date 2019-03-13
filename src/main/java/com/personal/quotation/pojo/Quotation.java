package com.personal.quotation.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Quotation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer currencyId;// 币种id
	private Integer legalId;// 法币id
	private BigDecimal startPrice;// 开始价格
	private BigDecimal endPrice;// 结束价格
	private BigDecimal highPrice;// 最高价
	private BigDecimal lowPrice;// 最低价
	private Date time;// 表示k线时刻
	private BigDecimal number;// 成交数量
	private BigDecimal volume;// 成交额
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Integer getLegalId() {
		return legalId;
	}

	public void setLegalId(Integer legalId) {
		this.legalId = legalId;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
package main.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReceiptVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6491776238907699316L;
	protected String id;//单据编号
	protected int receiptType;//单据类型，共11种
	protected Calendar makeTime;//创建时间
	protected Calendar reviseTime;//审批通过时间
	protected String creatorId;//2017/11/30增加，制定人员id

	public ReceiptVO(){}
	/**
	 * @param id
	 * @param receiptType
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 */
	public ReceiptVO(String id, int receiptType, Calendar makeTime, Calendar reviseTime, String creatorId) {
		super();
		this.id = id;
		this.receiptType = receiptType;
		this.makeTime = makeTime;
		this.reviseTime = reviseTime;
		this.creatorId = creatorId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}
	public Calendar getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(Calendar makeTime) {
		this.makeTime = makeTime;
	}
	public Calendar getReviseTime() {
		return reviseTime;
	}
	public void setReviseTime(Calendar reviseTime) {
		this.reviseTime = reviseTime;
	}
	public String getStringMakeTime(){
		if(makeTime==null){
			return "*";
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		return df.format(makeTime.getTime());
	}
	public String getStringReviseTime(){
		if(reviseTime==null){
			return "*";
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		return df.format(reviseTime.getTime());
	}
}

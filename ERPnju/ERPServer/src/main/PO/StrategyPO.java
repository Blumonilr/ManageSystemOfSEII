package main.PO;

import java.io.Serializable;
import java.util.Calendar;

public abstract class StrategyPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ID;
	private Calendar startTime;
    private Calendar endTime;
    private int type; //1.客户等级折让 2.客户等级赠品 3.客户等级代金券 4.总价折让 5.总价赠品 6.特价包
    
    public String getID() {
		return ID;
	}
	public Calendar getStartTime() {
		return startTime;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public int getType() {
		return type;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}

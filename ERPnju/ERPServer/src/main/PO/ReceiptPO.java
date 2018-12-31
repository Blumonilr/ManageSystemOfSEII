package main.PO;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

//所有单据爸爸
/**
 *
 * @author qyc
 *
 */
public class ReceiptPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1310042881646882878L;
	
	protected String creatorId;//2017/11/30日增加,制定单据的工作人员的id
	protected String id;//id
	protected int receiptType;//类型 同vo
	protected String createTime;//2017-11-21-16-33  年-月-日-时-分
	protected String reviseTime;

	public ReceiptPO(){}
	/**
	 * 带操作人员的构造函数，用这个！
	 * @param id
	 * @param receiptType
	 * @param createTime
	 * @param reviseTime
	 * @param creatorId
	 */
	public ReceiptPO(String id, int receiptType, String createTime, String reviseTime,String creatorId) {
		super();
		this.id = id;
		this.receiptType = receiptType;
		this.createTime = createTime;
		this.reviseTime = reviseTime;
		this.creatorId=creatorId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReviseTime() {
		return reviseTime;
	}
	public void setReviseTime(String reviseTime) {
		this.reviseTime = reviseTime;
	}
	public void setCreateTimebyCalendar(Calendar c){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		createTime=df.format(c.getTime());
	}
	public Calendar getCreateTimebyCalendar(){
		Calendar c=new GregorianCalendar();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		try {
			c.setTime(df.parse(createTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	public void setReviseTimebyCalendar(Calendar c){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		reviseTime=df.format(c.getTime());
	}
	public Calendar getReviseTimebyCalendar(){
		Calendar c=new GregorianCalendar();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		try {
			c.setTime(df.parse(createTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}

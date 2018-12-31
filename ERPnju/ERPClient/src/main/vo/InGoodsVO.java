package main.vo;

import java.io.Serializable;
import java.util.Calendar;

//ui to bl
public class InGoodsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516381931335399258L;
	private String id,name,xh,className,classId;//名字、编号、型号,分类名，分类编号
	private double inPrice,outPrice;//进价、售价
	private long iniNum,alarmNum;//初始库存、警报阈值
	private Calendar day;//出厂日期
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}

	public InGoodsVO(String name, String xh, String className, String classId, double inPrice,
			double outPrice, long iniNum, long alarmNum) {
		super();
		this.name = name;
		this.xh = xh;
		this.className = className;
		this.classId = classId;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.iniNum = iniNum;
		this.alarmNum = alarmNum;
	}

	public InGoodsVO(){}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}
	public double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(double outPrice) {
		this.outPrice = outPrice;
	}
	public long getIniNum() {
		return iniNum;
	}
	public void setIniNum(long iniNum) {
		this.iniNum = iniNum;
	}
	public long getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(long alarmNum) {
		this.alarmNum = alarmNum;
	}
}

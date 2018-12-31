package main.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//bl to ui
public class OutGoodsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8397748660194017031L;
	private String name,id,xh,className,classId;//名字、编号、型号,分类名，分类编号
	private double inPrice,outPrice,latestip,latestop;//进价、售价，最近进价，最近售价
	private long stockNum,alarmNum;//库存、警报阈值
	private Calendar day;//出厂日期
	
	/**
	 * 
	 * @param name
	 * @param id
	 * @param xh
	 * @param className
	 * @param classId
	 * @param inPrice
	 * @param outPrice
	 * @param latestip
	 * @param latestop
	 * @param stockNum
	 * @param alarmNum
	 * @param day 入库时间
	 */
	public OutGoodsVO(String name, String id, String xh, String className, String classId, double inPrice,
			double outPrice, double latestip, double latestop, long stockNum, long alarmNum, Calendar day) {
		super();
		this.name = name;
		this.id = id;
		this.xh = xh;
		this.className = className;
		this.classId = classId;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.latestip = latestip;
		this.latestop = latestop;
		this.stockNum = stockNum;
		this.alarmNum = alarmNum;
		this.day = day;
	}
	
	public OutGoodsVO(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
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
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		latestip=this.inPrice;
		this.inPrice = inPrice;
	}
	public double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(double outPrice) {
		latestop=this.outPrice;
		this.outPrice = outPrice;
	}
	public double getLatestip() {
		return latestip;
	}
	public void setLatestip(double latestip) {
		this.latestip = latestip;
	}
	public double getLatestop() {
		return latestop;
	}
	public void setLatestop(double latestop) {
		this.latestop = latestop;
	}
	public long getStockNum() {
		return stockNum;
	}
	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}
	public long getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(long alarmNum) {
		this.alarmNum = alarmNum;
	}
	public Calendar getDay() {
		return day;
	}
	public void setDay(Calendar day) {
		this.day = day;
	}
	
	public String getStringDay(){
		if(day==null)
			return null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return df.format(day.getTime());
	}
}

package main.PO;

import java.io.Serializable;
import java.util.Calendar;

//商品
public class GoodsPO implements Serializable{


	public GoodsPO(String id, String name, String xh, double inPrice, double lastInPrice, double outPrice,
			double lastOutPrice, long num, long alarmNum, String className, String classId, Calendar day) {
		super();
		this.id = id;
		this.name = name;
		this.xh = xh;
		this.inPrice = inPrice;
		this.lastInPrice = lastInPrice;
		this.outPrice = outPrice;
		this.lastOutPrice = lastOutPrice;
		this.num = num;
		this.alarmNum = alarmNum;
		this.className = className;
		this.classId = classId;
		this.day = day;
	}

	private final static long serialVersionUID = -6072898985430298865L;
	private String id;//编号
	private String name;//名称
	private String xh;//型号
	private double inPrice;//进价
	private double lastInPrice;//最近进价
	private double outPrice;//售价
	private double lastOutPrice;//最近售价
	private long num;//库存数量
	private long alarmNum;//警戒值
	private String className,classId;//商品分类、id
	private Calendar day;//出厂日期
	
	//此构造器用于测试
	public GoodsPO(String id,String name,String classId){
		this.id=id;
		this.name=name;
		this.classId=classId;
	}
	
	public GoodsPO(String name, String xh, double inPrice, long num, String className, String classId, Calendar day) {
		super();
		this.name = name;
		this.xh = xh;
		this.inPrice = inPrice;
		this.num = num;
		this.className = className;
		this.classId = classId;
		this.day = day;
	}

	public GoodsPO( String id, String name, String xh, double inPrice, 
			double outPrice,  long num, long alarmNum, String className, String classId,
			Calendar day) {
		super();
		this.id = id;
		this.name = name;
		this.xh = xh;
		this.lastInPrice=this.inPrice;
		this.lastOutPrice=this.outPrice;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.num = num;
		this.alarmNum = alarmNum;
		this.className = className;
		this.classId = classId;
		this.day = day;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		lastInPrice=this.inPrice;
		this.inPrice = inPrice;
	}

	public double getLastInPrice() {
		return lastInPrice;
	}

	public void setLastInPrice(double lastInPrice) {
		this.lastInPrice = lastInPrice;
	}

	public double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(double outPrice) {
		lastOutPrice=this.outPrice;
		this.outPrice = outPrice;
	}

	public double getLastOutPrice() {
		return lastOutPrice;
	}

	public void setLastOutPrice(double lastOutPrice) {
		this.lastOutPrice = lastOutPrice;
	}

	public long getNum() {
		return num;
	}

	public void changeNum(long num) {
		this.num += num;
	}

	public long getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(long alarmNum) {
		this.alarmNum = alarmNum;
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

//	public void setClassId(String classId) {
//		this.classId = classId;
//	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}
	
	
}

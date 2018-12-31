package main.vo;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author qyc
 * 
 * 此类用于多条件查找商品时记录筛选条件
 * 
 * 可根据商品名、售价区间、出厂日期区间、商品类型名查找
 */
public class GoodsQueryItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723737132711589241L;
	public boolean[]flags=new boolean[6];//分别代表商品名、商品类型名、售价区间、出厂日期区间是否有输入
	private String name="",className="";//商品名、商品类型名
	private String lower="",upper="";//售价区间
	private Calendar start,end;//出厂日期区间
	
	public void setName(String s){
		if(!s.equals("")){
			name=s;
			flags[0]=true;
		}
	}
	public void setClassName(String s){
		if(!s.equals("")){
			className=s;
			flags[1]=true;
		}
	}
	
	public void setLower(String l){
		if(!l.equals("")){
			lower=l;
			flags[2]=true;
		}
	}
	
	public void setUpper(String u){
		if(!u.equals("")){
			upper=u;
			flags[3]=true;
		}
	}
	
	public void setStart(Calendar s){
		if(s!=null){
			start=s;
			flags[4]=true;
		}
	}
	
	public void setEnd(Calendar e){
		if(e!=null){
			end=e;
			flags[5]=true;
		}
	}
	
	public String getName(){
		return name;
	}
	public String getClassName(){
		return className;
	}
	public String getLower() {
		return lower;
	}
	public String getUpper() {
		return upper;
	}
	public Calendar getStart() {
		return start;
	}
	public Calendar getEnd() {
		return end;
	}
	
	
}

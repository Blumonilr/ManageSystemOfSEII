package main.PO;

import java.io.Serializable;

public class UserPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4975004470234636194L;
	
	private int type;//1:库存管理人员; 2:进货销售人员; 3:财务人员; 4:总经理;
	private String id;//用户名
	private String password;//密码
	private int power;//等级从1到5
	boolean isDeleted=false;//是否被删除
	
	//构造
	public UserPO(int type,String id,String password,int power){
		this.type=type;
		this.id=id;
		this.password=password;
		this.power=power;
	}
	
	public int getType(){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		return this.type;
	}
	
	public String getId(){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		return this.id;
	}
	
	public String getPassword(){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		return this.password;
	}
	
	public int getPower(){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		return this.power;
	}
	
	public void setPassword(String password){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		this.password=password;
	}
	
	public void setPower(int power){
		if(isDeleted)
			throw new IllegalStateException("该用户不存在！");
		this.power=power;
	}

	public boolean has(String info) {
		if(info==null||info.equals("")){
			return true;
		}
		else{
			return this.id.contains(info);
		}
	}
	
	public String toString(){
		return type+" "+id+" "+password+" "+power;
	}
}

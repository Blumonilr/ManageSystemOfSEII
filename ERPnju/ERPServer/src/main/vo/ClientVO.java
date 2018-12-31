package main.vo;

import java.io.Serializable;

public class ClientVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3518163932973325906L;
	private String clientName;
	private String ID;
	private String clientType;/*1销售商，2供应商，3都是  分类*/

	private int level;
	private String phone;//电话
	private String address;
	private String email;
	private String postcode;
	private double collectTop;//应收额度
	private double collect;//应收
	private double pay;//应付
	private String defaultUserID;//默认业务员

	public ClientVO(String clientName, String ID, String clientType, int level, String tel, String address, String email, String postcode, double collectTop, double collect, double pay, String defaultUserID) {
		this.clientName = clientName;
		this.ID = ID;
		this.clientType = clientType;
		this.level = level;
		this.phone = tel;
		this.address = address;
		this.email = email;
		this.postcode = postcode;
		this.collectTop = collectTop;
		this.collect = collect;
		this.pay = pay;
		this.defaultUserID = defaultUserID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getClientName(){
		return clientName;
	}
	
	public String getID(){
		return ID;
	}
	
	public String getType(){
		return clientType;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPostcode(){
		return postcode;
	}
	
	/**
	 * 
	 * @param money 变化量
	 * @return true如果修改最大应收成功
	 */
	public boolean changeCollectTop(double money){
		if(collectTop+money>=0){
			collectTop+=money;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param money 变化量
	 * @return true如果修改应收成功
	 */
	public boolean changeCollect(double money){
		if(collect+money>=0){
			collect+=money;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param money 变化量
	 * @return true如果修改应付成功
	 */
	public boolean changePay(double money){
		if(pay+money>=0){
			pay+=money;
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return 最大应收
	 */
	public double getCollectTop(){
		return collectTop;
	}
	
	/**
	 * 
	 * @return 应收
	 */
	public double getCollect(){
		return collect;
	}
	
	/**
	 * 
	 * @return 应付
	 */
	public double getPay(){
		return pay;
	}
	
	public void setPhone(String t){
		phone=t;
	}
	
	public void setAddress(String t){
		address=t;
	}
	
	public void setType(String t){
		clientType=t;
	}
	
	public void setLevel(int t){
		level=t;
	}
	
	public void setEmail(String t){
		email=t;
	}
	
	public void setPostcode(String s){
		postcode=s;
	}

	public String getDefaultUserID() {
		return defaultUserID;
	}

	public void setDefaultUserID(String defaultUserID) {
		this.defaultUserID = defaultUserID;
	}
}

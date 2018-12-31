package main.PO;

import java.io.Serializable;

public class AccountPO implements Serializable{
	private static final long serialVersionUID = -9196707248382981942L;

	//id、nickname均要求唯一
	private boolean isDeleted;//是否已删除//0为未删除 1为已删除
	private String id;//账号
	private String password;
	private double balance;//余额
	private String nickname;
	
	/**
	 * @param isDeleted  是否删除
	 * @param id  账号
	 * @param password  密码
	 * @param balance  余额
	 * @param nickname  昵称
	 */
	public AccountPO(boolean isDeleted, String id, String password, double balance, String nickname) {
		super();
		this.isDeleted = isDeleted;
		this.id = id;
		this.password = password;
		this.balance = balance;
		this.nickname = nickname;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * 保存txt时调用的toString方法
	 * 一行为一个accountPO对象的记录 格式为：
	 * 是否删除*账号*密码*余额*昵称
	 * 例如：
	 * false*19980810*123456*9999.99*shazi
	 */
	public String toString(){
		String result="";
		result=this.isDeleted+"*"+this.id+"*"+this.password+"*"+this.balance+"*"+this.nickname;
		return result;
	}

}

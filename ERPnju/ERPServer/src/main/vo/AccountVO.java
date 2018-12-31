package main.vo;

import java.io.Serializable;

public class AccountVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847673613053536903L;
	private String nickname;
	private String id;//账号
	private String password;
	private double balance;//余额
	
	public AccountVO(String nickname,double balance,String id,String password){
		this.nickname=nickname;
		this.id=id;
		this.password=password;
		this.balance=balance;
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
	
	
}

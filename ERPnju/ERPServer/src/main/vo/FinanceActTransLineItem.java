package main.vo;

import java.io.Serializable;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/15 21:14
*/

public class FinanceActTransLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1230179657974627162L;
	private String bankAccount;//账号
	private double amount;
	private String note;
	/**
	 * @param bankAccount  使用的银行账户账号
	 * @param amount  金额
	 * @param note  备注
	 */
	public FinanceActTransLineItem(String bankAccount, double amount, String note) {
		super();
		this.bankAccount = bankAccount;
		this.amount = amount;
		this.note = note;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}

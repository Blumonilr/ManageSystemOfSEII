package main.PO;

import java.io.Serializable;
/**
 * toString方法待完善
 * @author qyc
 *
 */
public class ClientPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1473319401701392773L;
	
	private String id/*编号*/,name/*姓名*/,tel/*电话*/,addr/*地址*/,
	postNum/*邮编*/,email/*邮箱*/,counterman/*默认业务员*/;
	private int type/*1销售商，2供应商，3都是  分类*/,level/*1~5级别*/;
	private double Pay/*应付*/,collect/*应收*/,collectTop/*欠款额度*/;
	
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPostNum() {
		return postNum;
	}

	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCounterman() {
		return counterman;
	}

	public void setCounterman(String counterman) {
		this.counterman = counterman;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	

	public ClientPO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ClientPO(String id, String name, String tel, String addr, String postNum, String email, String counterman,
			int type, int level, double Pay, double collect, double collectTop) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.addr = addr;
		this.postNum = postNum;
		this.email = email;
		this.counterman = counterman;
		this.type = type;
		this.level = level;
		this.Pay = Pay;
		this.collect = collect;
		this.collectTop = collectTop;
	}
	
	public double getPay() {
		return Pay;
	}

	public void setPay(double pay) {
		Pay = pay;
	}

	public double getCollect() {
		return collect;
	}

	public void setCollect(double collect) {
		this.collect = collect;
	}

	public double getCollectTop() {
		return collectTop;
	}

	public void setCollectTop(double collectTop) {
		this.collectTop = collectTop;
	}

	public boolean equals(ClientPO obj){
		return id.equals(obj.getId());
	}
	
	
	public String toString(){
		return id;
	}
}

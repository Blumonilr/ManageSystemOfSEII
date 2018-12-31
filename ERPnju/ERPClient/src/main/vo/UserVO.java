package main.vo;

import java.io.Serializable;

public class UserVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1336129357640871295L;
	private String id;
    private String password;
    private int type;//1:库存管理人员; 2:进货销售人员; 3:财务人员; 4:总经理;
    private int power;//等级从1到2

    public UserVO(){
        id=null;
        password=null;
        type=0;
    }

    public UserVO(String id, String password, int type, int power) {
        this.id = id;
        this.password = password;
        this.type = type;
        this.power = power;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}

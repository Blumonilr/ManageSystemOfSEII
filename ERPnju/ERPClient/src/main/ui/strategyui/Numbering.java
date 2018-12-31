package main.ui.strategyui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Numbering {
	
	public Numbering(){
	}
	
	public String number(int type){
		String re="";
		Calendar c=Calendar.getInstance();
		if(type==1){
			re+="client"+new SimpleDateFormat("yyyyMMddHHmm").format(c.getTime());
		}
		else if(type==2){
			re+="total"+new SimpleDateFormat("yyyyMMddHHmm").format(c.getTime());
		}
		else if(type==3){
			re+="package"+new SimpleDateFormat("yyyyMMddHHmm").format(c.getTime());
		}
		return re;
	}
}

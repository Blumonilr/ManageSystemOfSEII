package main.businesslogic.stockbl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdAssigner {

	/**
	 * 
	 * @param className 商品的分类名
	 * @return 商品分类名+当前时间组成的id
	 */
	public static synchronized String assignId(String className){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String s=className+df.format(new Date());
		return s;
	}
	
}

package main.data.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 其他类统一从这个类获得数据库的连接
 * 此类应和期初建账有关！！！
 */

public class DBConnector {
	private static String database="info.db";
	private static String draftBase="draft.db";
	private static String temp=database;

	public static Connection getConnection(){
		//如果数据库不存在则不返回链接，也就是说第一次使用该系统必须进行期初建账
		if(!new File(System.getProperty("user.dir")+"\\account books\\"+database).exists())
			return null;

		Connection conn=null;
		try {
			String filePath = System.getProperty("user.dir") + "\\account books\\" + temp;
			conn = DriverManager.getConnection("jdbc:sqlite:"+ filePath);
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		return conn; 
	}
	public static void switchLink(){
		if(temp.equals(database))
			temp=draftBase;
		else if(temp.equals(draftBase))
			temp=database;
	}
}

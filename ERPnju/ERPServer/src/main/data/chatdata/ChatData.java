package main.data.chatdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.data.util.DBConnector;
import main.dataservice.chatdataservice.ChatDataService;

public class ChatData implements ChatDataService {

	@Override
	public boolean saveMsg(String userName, String msg) {

		PreparedStatement ps=null;
		Connection conn=DBConnector.getConnection();
		
		try {
			ps=conn.prepareStatement("insert into messages(userName,msg) values(?,?);");
			ps.setString(1, userName);
			ps.setString(2, msg);
			ps.addBatch();
			ps.executeBatch();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}

	@Override
	public ArrayList<String> readMsg(String userName) {

		ArrayList<String>msgs=new ArrayList<>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		
		try {
			ps=conn.prepareStatement("select msg from messages where userName ='"+userName+"';");
			rs=ps.executeQuery();
			
			if(!rs.isClosed()){
				while(rs.next()){
					msgs.add(rs.getString("msg"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		delMsg(userName);
		return msgs;
	}

	private void delMsg(String userName){
		Statement s=null;
		Connection conn=DBConnector.getConnection();
		
		try {
			s=conn.createStatement();
			String sql="delete from messages where userName = '"+userName+"';";
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

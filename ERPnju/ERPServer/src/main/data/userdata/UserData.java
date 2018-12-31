/*Author wly
 * 完成数据端对用户的操作
 * 
 */
package main.data.userdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.PO.UserPO;
import main.data.util.DBConnector;
import main.dataservice.userdataservice.UserDataService;

public class UserData implements UserDataService,UserDataInfo{

	@Override
	public boolean insert(UserPO User) {
		//查重
		if(find(User.getId())!=null)
			return false;
		//插入
		String sql="insert into users(id,User) values(?,?);";
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		Connection cn=DBConnector.getConnection();
		try{
			ps=cn.prepareStatement(sql);
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(User);
			oos.flush();
			byte[] buff=baos.toByteArray();
			ps.setString(1, User.getId());
			ps.setBytes(2, buff);
			ps.addBatch();
			ps.executeBatch();
		}catch(SQLException | IOException e){
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean update(UserPO User) {
		// TODO 自动生成的方法存根
		UserPO u=find(User.getId());
		if(u==null)
			return false;
		delete(u);
		insert(User);
		return true;
	}

	@Override
	public boolean delete(UserPO User) {
		Connection cn=DBConnector.getConnection();
		//查找是否存在
		if(find(User.getId())==null)
			return false;
		
		Statement s=null;
		try {
			s=cn.createStatement();
			String sql="delete from users where id = '"+User.getId()+"';";
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}finally{
			if(s!=null)
				try {
					s.close();
					cn.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					return false;
				}
		}
		return true;
	}

	@Override
	public UserPO find(String id) {
		// TODO 自动生成的方法存根
		Connection cn=DBConnector.getConnection();
		UserPO u=null;
		Statement s=null;
		ResultSet rs=null;
		try {
			s=cn.createStatement();
			String sql="select User from users where id = '"+id+"';";
			rs=s.executeQuery(sql);
			if(rs.isClosed())
				return null;
			byte[] buff=rs.getBytes("User");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			u=(UserPO)ois.readObject();
			ois.close();
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			return null;
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(s!=null)
					s.close();
				if(cn!=null)
					cn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return u;
	}

	@Override
	public ArrayList<UserPO> finds(String info) {
		Connection cn=DBConnector.getConnection();
		ArrayList<UserPO> list=new ArrayList<UserPO>();
		Statement s=null;
		ResultSet rs=null;
		UserPO u=null;
		try {
			s=cn.createStatement();
			rs=s.executeQuery("select User from users;");
			if(rs.isClosed())
				return list;
			while(rs.next()){
				byte[] buff=rs.getBytes("User");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				u=(UserPO)ois.readObject();
				if(info==null){
					list.add(u);//如果info是null表示返回所有用户信息
					continue;
				}
				if(u.has(info))
					list.add(u);
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				s.close();
				rs.close();
				cn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public boolean findUser(String id) {
		if(find(id)!=null)
			return true;
		return false;
	}

}

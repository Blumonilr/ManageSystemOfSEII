package main.data.revisedata;

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

import main.PO.ReceiptPO;
import main.data.util.DBConnector;
import main.dataservice.revisedataservice.ReviseDataService;

public class ReviseData implements ReviseDataService {

	@Override
	public ArrayList<ReceiptPO> finds() {
		Connection cn=DBConnector.getConnection();
		ArrayList<ReceiptPO> list=new ArrayList<ReceiptPO>();
		Statement s=null;
		ResultSet rs=null;
		ReceiptPO r=null;
		try {
			s=cn.createStatement();
			rs=s.executeQuery("select receipt from newreceipts;");
			if(rs.isClosed()||rs==null)
				return list;
			while(rs.next()){
				byte[] buff=rs.getBytes("receipt");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				r=(ReceiptPO)ois.readObject();
				list.add(r);
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			return null;
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
	public ReceiptPO find(String id) {
		// TODO 自动生成的方法存根
		Connection cn=DBConnector.getConnection();
		ReceiptPO r=null;
		Statement s=null;
		ResultSet rs=null;
		try {
			s=cn.createStatement();
			String sql="select receipt from newreceipts where id = '"+id+"';";
			rs=s.executeQuery(sql);
			if(rs.isClosed()||rs==null)
				return null;
			byte[] buff=rs.getBytes("receipt");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			r=(ReceiptPO)ois.readObject();
			ois.close();
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			return null;
		}finally{
			try {
				rs.close();
				s.close();
				cn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return r;
	}
	
	@Override
	public boolean delete(String id){
		if(find(id)==null)
			return false;
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		try {
			s=conn.createStatement();
			String sql="delete from newreceipts where id = '"+id+"';";
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
		
		return true;
	}

	@Override
	public boolean update(ReceiptPO r) {
		// TODO 自动生成的方法存根
		System.out.println(r.getId());
		ReceiptPO re=find(r.getId());
		if(re==null)
			return false;
		delete(r.getId());
		add(r);
		return true;
	}

	@Override
	public boolean add(ReceiptPO r) {
		//查重
		if(find(r.getId())!=null)
			return false;
		//插入
		String sql="insert into newreceipts(id,receipt) values(?,?);";
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		Connection cn=DBConnector.getConnection();
		try{
			ps=cn.prepareStatement(sql);
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(r);
			oos.flush();
			byte[] buff=baos.toByteArray();
			ps.setString(1, r.getId());
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


}

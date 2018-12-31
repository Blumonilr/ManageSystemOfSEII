package main.data.strategydata;

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

import main.PO.StrategyPO;
import main.data.util.DBConnector;
import main.dataservice.strategydataservice.StrategyDataService;

public class StrategyData implements StrategyDataService{


	@Override
	public boolean insert(StrategyPO strategy) {
		//查重
		if(find(strategy.getID())!=null)
			return false;
		//插入
		String sql="insert into strategys(id,strategy) values(?,?);";
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		Connection cn=DBConnector.getConnection();
		try{
			ps=cn.prepareStatement(sql);
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(strategy);
			oos.flush();
			byte[] buff=baos.toByteArray();
			ps.setString(1, strategy.getID());
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
	public boolean update(StrategyPO strategy) {
		StrategyPO s=find(strategy.getID());
		if(s==null)
			return false;
		delete(s);
		insert(strategy);
		return true;
	}

	@Override
	public boolean delete(StrategyPO strategy) {
		Connection cn=DBConnector.getConnection();
		//查找是否存在
		if(find(strategy.getID())==null)
			return false;
		
		Statement s=null;
		try {
			s=cn.createStatement();
			String sql="delete from strategys where id = '"+strategy.getID()+"';";
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
	public StrategyPO find(String id) {
		// TODO 自动生成的方法存根
		System.out.println("StrategyData: id: "+id);
		Connection cn=DBConnector.getConnection();
		StrategyPO st=null;
		Statement s=null;
		ResultSet rs=null;
		try {
			s=cn.createStatement();
			String sql="select strategy from strategys where id = '"+id+"';";
			rs=s.executeQuery(sql);
			if(rs.isClosed())
				return null;
			byte[] buff=rs.getBytes("strategy");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			st=(StrategyPO)ois.readObject();
			ois.close();
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
		return st;
	}

	@Override
	public ArrayList<StrategyPO> finds() {
		Connection cn=DBConnector.getConnection();
		ArrayList<StrategyPO> list=new ArrayList<StrategyPO>();
		Statement s=null;
		ResultSet rs=null;
		StrategyPO st=null;
		try {
			s=cn.createStatement();
			rs=s.executeQuery("select strategy from strategys;");
			if(rs.isClosed())
				return list;
			while(rs.next()){
				byte[] buff=rs.getBytes("strategy");
				System.out.println("strategy in db!");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				st=(StrategyPO)ois.readObject();
				list.add(st);
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				s.close();
//				rs.close();
				cn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return list;
	}

}

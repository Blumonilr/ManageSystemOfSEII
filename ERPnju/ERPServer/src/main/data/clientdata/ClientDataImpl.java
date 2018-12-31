package main.data.clientdata;

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

import main.PO.ClientPO;
import main.data.util.DBConnector;
import main.dataservice.clientdataservice.ClientDataService;

/**
 * 
 * @author qyc
 * 此类完成
 * 负责对客户信息的增删改查
 */
public class ClientDataImpl implements ClientDataService {

	
	@Override
	public boolean addClient(ClientPO obj) {
		// TODO Auto-generated method stub
		//查重
		if(showClient(obj.getId())!=null)
			return false;
		
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		Connection conn=DBConnector.getConnection();
		try {
			ps=conn.prepareStatement("insert into clients(id,obj) values(?,?);");
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			ps.setString(1, obj.getId());
			ps.setBytes(2, buff);
			ps.addBatch();
			ps.executeBatch();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				ps.close();
				baos.close();
				oos.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean deleteClient(String id) {
		// TODO Auto-generated method stub
		if(showClient(id)==null)
			return false;
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		try {
			s=conn.createStatement();
			String sql="delete from clients where id = '"+id+"';";
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
	public ArrayList<ClientPO> show() {
		// TODO Auto-generated method stub
		ArrayList<ClientPO>list=new ArrayList<>();
		
		Statement s=null;		
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			rs=s.executeQuery("select obj from clients;");
			if(rs.isClosed())
				return list;
			//将二进制数组反序列化
			while(rs.next()){
				byte[]buff=rs.getBytes("obj");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ClientPO c=(ClientPO)ois.readObject();
				list.add(c);
				ois.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				s.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return list;
	}

	@Override
	/*
	 * 根据id查找客户信息
	 * @see main.dataservice.clientdataservice.ClientDataService#showClient(java.lang.String)
	 */
	public ClientPO showClient(String id) {
		// TODO Auto-generated method stub
		ClientPO c=null;
		Statement s=null;
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			String sql="select obj from clients where id = '"+id+"';";
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
			byte[]buff=rs.getBytes("obj");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			c=(ClientPO)ois.readObject();
			ois.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
				rs.close();
				s.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return c;
	}

	@Override
	/**
	 * 调用此方法的前提是调用了showClient方法
	 */
	public boolean modifyClient(ClientPO obj) {
		// TODO Auto-generated method stub
		ClientPO c=showClient(obj.getId());
		if(c==null)
			return false;
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement ps=null;
		String sql="update clients set obj = ? where id = '"+obj.getId()+"';";
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=null;
		try {
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			
			ps=conn.prepareStatement(sql);
			ps.setBytes(1, buff);
			ps.addBatch();
			ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
}

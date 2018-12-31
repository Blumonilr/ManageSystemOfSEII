package main.data.stockdata;

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
import main.data.userdata.UserData;
import main.data.userdata.UserDataInfo;
import main.data.util.DBConnector;

/**
 * receipts表格(id text,type int,obj Blob)
 * @author qyc
 *
 */
public class StockDataHelper {

	public boolean writeReceipt(ReceiptPO obj) {
		// TODO Auto-generated method stub
		
		if(readReceipt(obj.getId())!=null)
			return false;
		
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="insert into receipts(id,type,obj) values(?,?,?);";
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=null;
		try {
			conn=DBConnector.getConnection();
			ps=conn.prepareStatement(sql);
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			ps.setString(1, obj.getId());
			ps.setInt(2, obj.getReceiptType());
			ps.setBytes(3, buff);
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
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public ArrayList<ReceiptPO>readReceipt(){
		ArrayList<ReceiptPO>list=new ArrayList<>();
		Connection conn=null;
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select * from receipts ;";
		try {
			conn=DBConnector.getConnection();
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return list;
			
			while(rs.next()){
				
				byte[]buff=rs.getBytes("obj");
				ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ReceiptPO r=(ReceiptPO)ois.readObject();
				list.add(r);
				ois.close();//必须关闭，否则会一直读入之前的对象
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

	public ReceiptPO readReceipt(String id) {
		// TODO Auto-generated method stub
		ReceiptPO r=null;
		Connection conn=null;
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select obj from receipts where id='"+id+"';";
		try {
			conn=DBConnector.getConnection();
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
			byte[]buff=rs.getBytes("obj");
			ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			r=(ReceiptPO)ois.readObject();
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
		return r;
	}

	public ArrayList<ReceiptPO> readReceipt(int type) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptPO>list=new ArrayList<>();
		Connection conn=null;
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select obj from receipts where type="+type+";";
		try {
			conn=DBConnector.getConnection();
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return list;
			
			while(rs.next()){
				
				byte[]buff=rs.getBytes("obj");
				ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ReceiptPO r=(ReceiptPO)ois.readObject();
				list.add(r);
				ois.close();//必须关闭，否则会一直读入之前的对象
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

	public ArrayList<ReceiptPO> readDraft(String userId) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptPO>list=new ArrayList<>();
		
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select obj from drafts where userId = '"+userId+"';";
		try {
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return list;
			
			while(rs.next()){
				byte[]buff=rs.getBytes("obj");
				ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ReceiptPO r=(ReceiptPO)ois.readObject();
				list.add(r);
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

	public ReceiptPO readDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		ReceiptPO r=null;
		
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select obj from drafts where userId = '"+userId
				+"' AND draftId = '"+draftId+"' ;";
		try {
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
				byte[]buff=rs.getBytes("obj");
				ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				r=(ReceiptPO)ois.readObject();
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
		return r;
	}

	/**
	 * 修改草稿的前提是读取草稿，每个人只能操作自己的草稿
	 * @param obj 单据草稿
	 * @param userId 用户id
	 * @return
	 */
	public boolean modifyDraft(ReceiptPO obj,String userId) {
		System.out.println("stock data helper");
		if(obj==null||userId==null)
			return false;
		//先判断user是否存在！！！
		UserDataInfo udi=new UserData();
		if(!udi.findUser(userId))
			return false;
		System.out.println("user exists");
		if(!delDraft(obj.getId(),userId))
			return false;
		System.out.println("delete draft successfully");
		addDraft(obj,userId);
		return true;
	}

	public boolean delDraft(String draftId, String userId) {
		// TODO Auto-generated method stub
		System.out.println("deleting draft id= "+draftId);
		if(readDraft(draftId,userId)==null)
			return false;
		
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		String sql="delete from drafts where draftId ='"
		+draftId+"' AND userId ='"+userId+"';";
		try {
			s=conn.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				s.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean addDraft(ReceiptPO obj, String userId) {
		// TODO Auto-generated method stub
		//查重
		if(readDraft(obj.getId(),userId)!=null){
			return false;
		}
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement ps=null;
		String sql="insert into drafts(draftId,userId,obj) values(?,?,?);";
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, obj.getId());
			ps.setString(2, userId);
			ps.setBytes(3, buff);
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

	/**
	 * 此方法只有总经理可以使用
	 * 调用此方法的前提是调用了readReceipt方法！！！
	 * 且无人能删除单据，不用担心单据不存在
	 * @param obj 单据
	 * @return
	 */
	public boolean modifyReceipt(ReceiptPO obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement ps=null;
		String sql="update receipts set obj=? where id = '"+obj.getId()+"';";
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

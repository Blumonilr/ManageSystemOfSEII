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
import main.PO.ClassPO;
import main.PO.ClientPO;
import main.PO.GoodsPO;
import main.data.util.DBConnector;

/**
 * 
 * @author qyc
 * 
 * classes表格式：id text,fatherId text,obj Blob
 */
public class ClassDataHelper {

	private GoodsDataHelper goods;
	
	public void setGDH(GoodsDataHelper gdh){
		goods=gdh;
	}
	
	public boolean addClass(ClassPO obj) {
		// TODO Auto-generated method stub
		//查重
		if(show(obj.getId())!=null)
			return false;
		
		if(!addSubClassToFather(obj.getFatherId(),obj.getId()))
			return false;
		
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		Connection conn=DBConnector.getConnection();
		try {
			ps=conn.prepareStatement("insert into classes(id,fatherId,obj) values(?,?,?);");
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			ps.setString(1, obj.getId());
			ps.setString(2, obj.getFatherId());
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

	/**
	 * 此方法用于添加分类时，往父分类的subclasses中添加子类id信息
	 * @param fatherId
	 * @param sonId
	 */
	private boolean addSubClassToFather(String fatherId,String sonId){
		if(fatherId==null)
			return true;
		ClassPO c=show(fatherId);
		if(c==null)
			return false;
		if(c.getSubgoods().size()>0)
			return false;
		c.addSubClasses(sonId);
		modifyClass(c);
		return true;
	}
	
	/**
	 * 此方法对外提供删除商品分类的服务，会删除目标分类下的子分类和子商品
	 * @param id
	 * @return
	 */
	public boolean delClass(String id) {
		// TODO Auto-generated method stub
		//查重
		ClassPO c=show(id);
		if(c==null)
			return false;
		
		delSubClassFromFather(c.getFatherId(),id);
		//删除目标分类下的子分类和子商品
		ArrayList<String>subclasses=c.getSubclasses();
		ArrayList<String>subgoods=c.getSubgoods();
		if(subclasses!=null){			
			for(String s:subclasses)
				delClass(s);
		}
		if(subgoods!=null){			
			for(String s:subgoods)
				goods.delGoods(s);
		}
		
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		try {
			s=conn.createStatement();
			String sql="delete from classes where id = '"+id+"';";
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

	/**
	 * 此方法用于删除商品分类时从父分类中删除目标分类的记录
	 * @param fatherId
	 * @param sonId
	 */
	private void delSubClassFromFather(String fatherId,String sonId){
		ClassPO c=show(fatherId);
		if(c!=null){
			c.delSubClasses(sonId);
			modifyClass(c);
		}
	}
	
	/**
	 * 此方法用于修改商品分类信息，不会删除目标分类下的子分类或者子商品
	 * @param id
	 */
	private void delClassOnly(String id){
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		try {
			s=conn.createStatement();
			String sql="delete from classes where id = '"+id+"';";
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
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public boolean modifyClass(ClassPO obj) {
		// TODO Auto-generated method stub
		//查重
		ClassPO c=show(obj.getId());
		if(c==null)
			return false;
		delClassOnly(obj.getId());
		
		c.setSubclasses(obj.getSubclasses());
		c.setSubgoods(obj.getSubgoods());
		c.setName(obj.getName());
		//更改类下商品的父类名称
		GoodsDataHelper gdh=new GoodsDataHelper();
		for(String gid:c.getSubgoods()){
			GoodsPO g=gdh.getGoods(gid);
			if(g!=null){
				g.setClassName(obj.getName());				
				gdh.modifyGoods(g);
			}
		}
		addClass(c);
		return true;
	}

	/**
	 * 返回商品分类列表
	 * @return
	 */
	public ArrayList<ClassPO> showClass() {
		// TODO Auto-generated method stub
		ArrayList<ClassPO>list=new ArrayList<>();
		
		Statement s=null;		
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			rs=s.executeQuery("select obj from classes;");
			if(rs.isClosed())
				return list;
			//将二进制数组反序列化
			while(rs.next()){
				byte[]buff=rs.getBytes("obj");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ClassPO c=(ClassPO)ois.readObject();
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

	public ArrayList<ClassPO> showClass(String fatherId) {
		// TODO Auto-generated method stub
		ArrayList<ClassPO>list=new ArrayList<>();
		
		Statement s=null;		
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			rs=s.executeQuery("select obj from classes where fatherId = '"+fatherId+"';");
			if(rs.isClosed())
				return list;
			//将二进制数组反序列化
			while(rs.next()){					
				byte[]buff=rs.getBytes("obj");
				ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				ClassPO c=(ClassPO)ois.readObject();
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

	public ClassPO show(String id) {
		// TODO Auto-generated method stub
		ClassPO c=null;
		Statement s=null;
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			String sql="select obj from classes where id = '"+id+"';";
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
			byte[]buff=rs.getBytes("obj");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			c=(ClassPO)ois.readObject();
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

}

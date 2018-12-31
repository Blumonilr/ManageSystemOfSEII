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
import main.PO.GoodsPO;
import main.data.util.DBConnector;
import main.vo.GoodsQueryItem;

public class GoodsDataHelper {

	private ClassDataHelper cdh;
	
	public void setCDH(ClassDataHelper cdh){
		this.cdh=cdh;
	}
	
	public boolean addGoods(GoodsPO obj) {
		// 商品分类不存在||商品分类下有子分类,添加失败
		ClassPO father=cdh.show(obj.getClassId());
		if(father==null||
				father.getSubclasses().size()>0)
			return false;
		//商品已存在(商品名称、型号、商品分类重复)
		GoodsPO gpo=getGoodsbyName(obj.getName());
		if(gpo!=null&&gpo.getXh().equals(obj.getXh())&&gpo.getClassId().equals(obj.getClassId()))
			return false;
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement ps=null;
		ByteArrayOutputStream baos=null;
		ObjectOutputStream oos=null;
		
		//向父类注册！！！
		father.addSubGoods(obj.getId());
		cdh.modifyClass(father);
		try{
			String sql="insert into goods(id,name,obj) values(?,?,?);";
			ps=conn.prepareStatement(sql);
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[]buff=baos.toByteArray();
			ps.setString(1, obj.getId());
			ps.setString(2, obj.getName());
			ps.setBytes(3, buff);
			ps.addBatch();
			ps.executeBatch();
		}catch (SQLException e) {
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

	public boolean delGoods(String id) {
		
		GoodsPO g=getGoods(id);
		if(g==null)
			return false;
		//向父类删除子类的记录
		ClassPO father=cdh.show(g.getClassId());
		if(father!=null){
			father.delSubGoods(id);
			cdh.modifyClass(father);
		}
		
		Connection conn=null;
		Statement s=null;
		String sql="delete from goods where id ='"+id+"';";
		try {
			conn=DBConnector.getConnection();
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

	public ArrayList<GoodsPO> getGoods() {
		// TODO Auto-generated method stub
		ArrayList<GoodsPO>list=new ArrayList<>();
		Connection conn=null;
		Statement s=null;
		ResultSet rs=null;
		ObjectInputStream ois=null;
		String sql="select obj from goods;";
		try {
			conn=DBConnector.getConnection();
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			if(rs.isClosed())
				return list;
			while(rs.next()){
				byte[]buff=rs.getBytes("obj");
				ois=new ObjectInputStream(new ByteArrayInputStream(buff));
				GoodsPO g=(GoodsPO)ois.readObject();
				list.add(g);
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

	public GoodsPO getGoods(String id) {
		// TODO Auto-generated method stub
		GoodsPO g=null;
		
		Statement s=null;
		ResultSet rs=null;
		Connection conn=DBConnector.getConnection();
		try {
			s=conn.createStatement();
			String sql="select obj from goods where id = '"+id+"';";
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
			byte[]buff=rs.getBytes("obj");
			ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			g=(GoodsPO)ois.readObject();
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
		
		return g;
	}

	public boolean modifyGoods(GoodsPO obj) {
		// TODO Auto-generated method stub
		if(obj==null||getGoodsbyName(obj.getName())==null)
			return false;
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement ps=null;
		String sql="update goods set obj = ? where name = '"+obj.getName()+"';";
		
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

	private GoodsPO getGoodsbyName(String name){
		GoodsPO g=null;
		Connection conn=DBConnector.getConnection();
		Statement s=null;
		String sql="select obj from goods where name ='"+name+"';";
		ResultSet rs=null;
		ObjectInputStream ois=null;
		try {
			s=conn.createStatement();
			rs=s.executeQuery(sql);
			
			if(rs.isClosed())
				return null;
			
			byte[]buff=rs.getBytes("obj");
			ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			g=(GoodsPO) ois.readObject();
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
				if(ois!=null)
					ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
				s.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return g;
	}

}

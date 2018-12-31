package main.data.accountdata;

import java.sql.*;
import java.util.ArrayList;

import main.PO.AccountPO;
import main.data.util.DBConnector;
import main.dataservice.accountdataservice.AccountDataService;

public class AccountData implements AccountDataService {

	@Override
	public boolean add(AccountPO account) {//done
		// TODO Auto-generated method stub
		String id=account.getId();
		String nickname=account.getNickname();
		//已存在
		if(this.findById(id)!=null){
			System.out.println("id已存在");
			return false;
		}
		if(this.findById(nickname)!=null){
			System.out.println("nickname已存在");
			return false;
		}
		String password=account.getPassword();
		double balance=account.getBalance();
		
		Connection conn=DBConnector.getConnection();
		PreparedStatement stmt;
		try{
			String sql="insert into accounts"+'\n'+"values(?,?,?,?,?);";
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, 0);
			stmt.setString(2, id);
			stmt.setString(3, password);
			stmt.setDouble(4, balance);
			stmt.setString(5, nickname);
			
			stmt.execute();
			System.out.println("添加成功");
			stmt.close();
			conn.close();
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String accountId) {//done
		// TODO Auto-generated method stub
		AccountPO accountPo=this.findById(accountId);
		if(accountPo==null){
			//不存在或已删除
			System.out.println("不存在或已删除");
			return false;
		}
		else{
			try{
				String sql="update accounts"+'\n'+"set isDeleted=1"+'\n'+"where id==?;";
				Connection conn=DBConnector.getConnection();
				PreparedStatement stmt=conn.prepareStatement(sql);
				stmt.setString(1,accountId);
				stmt.execute();
				System.out.println("已删除");
				stmt.close();
				conn.close();
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return false;
	}

	@Override
	public boolean updateAccount(AccountPO account) {//done
		// TODO Auto-generated method stub
		String accountId=account.getId();
		AccountPO accountPo=this.findById(accountId);
		if(accountPo==null){
			//不存在或已删除
			System.out.println("不存在或已删除");
			return false;
		}
		else{
			try{
				String password=account.getPassword();
				String nickname=account.getNickname();
				double balance=account.getBalance();
				
				String sql="update accounts set password=?,nickname=? where id==? AND isDeleted==0;";
				Connection conn=DBConnector.getConnection();
				PreparedStatement stmt=conn.prepareStatement(sql);
				stmt.setString(1,password);
				stmt.setString(2,nickname);
				stmt.setString(3,accountId);
				stmt.execute();
				System.out.println("已更新");
				stmt.close();
				conn.close();
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public AccountPO findById(String id) {//done
		// TODO Auto-generated method stub
		AccountPO accountPo=null;
		Connection conn=DBConnector.getConnection();
		PreparedStatement stmt=null;
		try{
			String sql="select * from accounts where id==?;";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			ResultSet res=stmt.executeQuery();
			if(stmt.isClosed()){
				System.out.println("找不到对象");
				conn.close();
				return null;
			}
			else{
				while(res.next()){
					int isDel=res.getInt("isDeleted");
					String password=res.getString("password");
					double balance=res.getDouble("balance");
					String nickname=res.getString("nickname");
					boolean isDeleted=false;
					if(isDel==1){
						isDeleted=true;
					}
					if(isDeleted){
						System.out.println("对象已删除");
					}
					else{
						accountPo=new AccountPO(isDeleted,id,password,balance,nickname);
						System.out.println(id+" "+password+" "+balance+" "+nickname);
						stmt.close();
						conn.close();
						return accountPo;
					}
				}
				stmt.close();
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return accountPo;
	}

	@Override
	public AccountPO findByNickname(String nickname) {//done
		// TODO Auto-generated method stub
		AccountPO accountPo=null;
		Connection conn=DBConnector.getConnection();
		PreparedStatement stmt=null;
		try{
			String sql="select * from accounts where nickname==?;";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,nickname);
			ResultSet res=stmt.executeQuery();
			if(stmt.isClosed()){
				System.out.println("找不到对象");
				stmt.close();
				conn.close();
				return null;
			}
			else{
				while(res.next()){
					String id=res.getString("id");
					int isDel=res.getInt("isDeleted");
					String password=res.getString("password");
					double balance=res.getDouble("balance");
					boolean isDeleted=false;
					if(isDel==1){
						isDeleted=true;
					}
					if(isDeleted){
						System.out.println("对象已删除");
					}
					else{
						accountPo=new AccountPO(isDeleted,id,password,balance,nickname);
						System.out.println(id+" "+password+" "+balance+" "+nickname);
						stmt.close();
						conn.close();
						return accountPo;
					}
				}
				stmt.close();
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return accountPo;
	}

	@Override
	public ArrayList<AccountPO> showAll() {//done
		// TODO Auto-generated method stub
		ArrayList<AccountPO> list=new ArrayList<AccountPO>();
		try{
			Connection conn=DBConnector.getConnection();
			String sql="select * from accounts where isDeleted==0;";
			PreparedStatement stmt=conn.prepareStatement(sql);
			ResultSet res=stmt.executeQuery();
			while(res.next()){
				String id=res.getString("id");
				String password=res.getString("password");
				double balance=res.getDouble("balance");
				String nickname=res.getString("nickname");
				AccountPO account=new AccountPO(false,id,password,balance,nickname);
				list.add(account);
			}
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateBalance(String accountId, double newBalance) {//done
		// TODO Auto-generated method stub
		//调用前已检查存在
		String sql="update accounts set balance=? where id==? AND isDeleted==0;";
		try{
			Connection conn=DBConnector.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(2, accountId);
			stmt.setDouble(1, newBalance);
			stmt.execute();
			System.out.println("update balance……");
			stmt.close();
			conn.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

}

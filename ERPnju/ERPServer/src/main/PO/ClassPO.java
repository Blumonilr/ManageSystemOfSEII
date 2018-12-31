package main.PO;

import java.io.Serializable;
import java.util.ArrayList;

//商品分类信息
/**
 * 
 * @author qyc
 *
 */
public class ClassPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2684650083465188271L;
	private String id;
	private String name;
//	private boolean isLeaf=true;//判断此分类是不是叶节点
	private String fatherId;//记录父节点id即可
	private ArrayList<String>subclasses=new ArrayList<>();//记录id即可
	private ArrayList<String>subgoods=new ArrayList<>();

	/**
	 * 
	 * @param i:商品分类编号
	 * @param n:商品分类
	 * @param f:父亲分类
	 */
	public ClassPO(String i,String n,String f){
		id=i;
		name=n;
		fatherId=f;
	}
	
	
	public ClassPO(String id, String name, String fatherId, ArrayList<String> subclasses,
			ArrayList<String> subgoods) {
		super();
		this.id = id;
		this.name = name;
//		this.isLeaf = isLeaf;
		this.fatherId = fatherId;
		this.subclasses = subclasses;
		this.subgoods = subgoods;
	}
	
//	public boolean isLeaf(){
//		return isLeaf;
//	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public boolean addSubClasses(String id){
		if(subgoods.size()>0||subclasses.contains(id))
			return false;
		subclasses.add(id);
		return true;
	}
	
	public boolean addSubGoods(String id){
		if(subclasses.size()>0||subgoods.contains(id))
			return false;
		subgoods.add(id);
		return true;
	}
	
	public boolean delSubClasses(String id){
		if(subclasses.contains(id)){
			subclasses.remove(id);
			return true;
		}
		return false;
	}
	
	public boolean delSubGoods(String id){
		if(subgoods.contains(id)){
			subgoods.remove(id);
			return true;
		}
		return false;
	}
	
	public void setSubclasses(ArrayList<String>arr){
		subclasses=arr;
	}
	
	public void setSubgoods(ArrayList<String>arr){
		subgoods=arr;
	}
	
	public ArrayList<String> getSubclasses() {
		return subclasses;
	}


	public ArrayList<String> getSubgoods() {
		return subgoods;
	}


	public String getName(){
		
		return name;
	}
	
	public String getId(){
		
		return id;
	}
	
	public String getFatherId(){
		
		return fatherId;
	}
	
	public String toString(){
		return id+"/"+name;
	}
}

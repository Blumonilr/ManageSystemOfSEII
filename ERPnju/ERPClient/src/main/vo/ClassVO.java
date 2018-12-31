package main.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author qyc
 *添加构造器，father变为string类型
 */
public class ClassVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 804104946857548207L;
	private String id;
	private String name;
	private String father;//father's id
	private ArrayList<String>subclasses=new ArrayList<>();//记录id即可
	private ArrayList<String>subgoods=new ArrayList<>();
	
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
	
	public ArrayList<String> getSubclasses() {
		return subclasses;
	}

	public void setSubclasses(ArrayList<String> subclasses) {
		this.subclasses = subclasses;
	}

	public ArrayList<String> getSubgoods() {
		return subgoods;
	}

	public void setSubgoods(ArrayList<String> subgoods) {
		this.subgoods = subgoods;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public ClassVO(String name,String father){
		this.name = name;
		this.father = father;
	}
	
	public ClassVO(String id, String name, String father, ArrayList<String> subclasses, ArrayList<String> subgoods) {
		super();
		this.id = id;
		this.name = name;
		this.father = father;
		this.subclasses = subclasses;
		this.subgoods = subgoods;
	}

	public ClassVO(String id,String name, String father) {
		super();
		this.id=id;
		this.name = name;
		this.father = father;
	}
}

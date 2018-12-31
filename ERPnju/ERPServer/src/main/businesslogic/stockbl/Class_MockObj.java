package main.businesslogic.stockbl;

import main.vo.ClassVO;

import java.util.ArrayList;
/**
 * 
 * @author qyc
 *
 */
public class Class_MockObj  {

	ArrayList<ClassVO>list;
	
	public Class_MockObj(){
		list=new ArrayList<>();
		ClassVO c1=new ClassVO("0001","总分类",null);
		list.add(c1);
		ClassVO c2=new ClassVO("0002","食品","总分类");
		list.add(c2);
		ClassVO c3=new ClassVO("0003","数码产品","总分类");
		list.add(c3);
		ClassVO c4=new ClassVO("0004","手机","数码产品");
		list.add(c4);
		ClassVO c5=new ClassVO("0005","蔬菜","食品");
		list.add(c5);
	}
	
	public boolean addClass(ClassVO obj){
		list.add(obj);
		return true;
	}
	
	public boolean delClass(String id){
		return true;
	}
	
	public boolean modifyClass(ClassVO obj){
		return true;
	}
	
	public ArrayList<ClassVO>showClass(){
		return list;
	}

}

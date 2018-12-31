package main.businesslogic.stockbl;

import main.vo.ClassVO;

import java.util.ArrayList;
/**
 * 
 * @author qyc
 *
 */
public class ClassInfo_MockObj implements ClassInfo {

	ArrayList<ClassVO>list;
	
	public ClassInfo_MockObj(){
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
	
	@Override
	public ArrayList<ClassVO> showClass() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public boolean addClass(ClassVO obj) {
		// TODO Auto-generated method stub
		list.add(obj);
		return true;
	}

}

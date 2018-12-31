package main.businesslogic.stockbl;

import java.util.ArrayList;

import main.PO.ClassPO;
import main.data.stockdata.StockDataServiceImpl;
import main.dataservice.stockdataservice.StockDataService;
import main.vo.ClassVO;

public class Classes implements ClassInfo {

	StockDataService sds;
	
	public Classes(){
		sds=new StockDataServiceImpl();
	}
	
	@Override
	public ArrayList<ClassVO> showClass() {
		// TODO Auto-generated method stub
		ArrayList<ClassPO>pList=sds.showClass();
		ArrayList<ClassVO>vList=new ArrayList<>();
		for(ClassPO c:pList){
			vList.add(po2vo(c));
		}
		return vList;
	}

	@Override
	public synchronized boolean addClass(ClassVO obj) {
		// TODO Auto-generated method stub
		return sds.addClass(vo2po(obj));
	}

	public synchronized boolean modifyClass(ClassVO obj) {
		
		//检查修改商品分类后商品分类名是否重复 
		ArrayList<ClassPO>list=sds.showClass();
		for(ClassPO cpo:list){
			if(cpo.getName().equals(obj.getName())&&!cpo.getId().equals(obj.getId()))
				return false;
		}
		return sds.modifyClass(vo2po(obj));
	}

	public synchronized boolean delClass(String id) {
		// TODO Auto-generated method stub
		return sds.delClass(id);
	}

	private ClassVO po2vo(ClassPO obj){
		if(obj==null)
			return null;
		ClassVO c=new ClassVO(obj.getId(), obj.getName(), obj.getFatherId(), obj.getSubclasses(), obj.getSubgoods());
		return c;
	}
	
	private ClassPO vo2po(ClassVO obj){
		if(obj==null)
			return null;
		ClassPO c=new ClassPO(obj.getId(), obj.getName(), obj.getFather(), obj.getSubclasses(), obj.getSubgoods());
		return c;
	}

	public ClassVO showClass(String id) {
		// TODO Auto-generated method stub
		ArrayList<ClassPO>pList=sds.showClass();
		for(ClassPO c:pList){
			if(c.getId().equals(id)){
				return po2vo(c);
			}
		}
		return null;
	}
}

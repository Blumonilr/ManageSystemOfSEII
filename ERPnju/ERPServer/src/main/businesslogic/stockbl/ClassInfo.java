package main.businesslogic.stockbl;

import main.vo.ClassVO;

import java.util.ArrayList;
/**
 * 
 * @author qyc
 *
 */
public interface ClassInfo {

	public ArrayList<ClassVO>showClass();
	public boolean addClass(ClassVO obj);
	
}

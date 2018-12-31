package test.test_bl.stock;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.businesslogic.stockbl.Classes;
import main.vo.ClassVO;

public class Test_bl_Classes {

	static Classes classes;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		classes=new Classes();
	}

	@Test
	public void test_show() {
		ArrayList<ClassVO>list=classes.showClass();
		for(ClassVO c:list){
			System.out.println(c.getId()+c.getName()+c.getFather());
		}
	}

	@Test
	public void test_addClass(){
		ClassVO c1=new ClassVO("id1", "classvo1", null);
		assertEquals(false,classes.addClass(c1));
		ClassVO c2=new ClassVO("id10", "classvo1", "id1");
		assertEquals(false,classes.addClass(c2));
		ClassVO c3=new ClassVO("id11", "classvo2", "id2");
		assertEquals(false,classes.addClass(c3));
	}
	
	@Test
	public void test_delClass(){
		ClassVO c=new ClassVO("id2", "classvo2", null);
		assertEquals(true,classes.addClass(c));
		assertEquals(true,classes.delClass("id2"));
		assertEquals(false,classes.delClass("id2"));
	}
	
	@Test
	public void test_modifyClass(){
		
	}
}

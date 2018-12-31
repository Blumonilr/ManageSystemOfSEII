package Test.stock;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.vo.ClassVO;

public class Test_Class {

	static StockblService sbs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sbs=RemoteHelper.getInstance().getStockblService();
	}

	@Test
	public void test_addClass() {
		ClassVO obj=new ClassVO("iiiiiid", "niiiiame", "father",new ArrayList<String>(),new ArrayList<String>());
		try {
			boolean flag=sbs.addClass(obj);
			assertEquals(true,flag);
			sbs.delClass("id");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

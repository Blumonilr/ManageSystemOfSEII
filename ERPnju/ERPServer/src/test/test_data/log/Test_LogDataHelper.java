package test.test_data.log;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import main.data.logdata.LogDataHelper;

public class Test_LogDataHelper {

	static LogDataHelper ldh;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ldh=new LogDataHelper();
	}

	//测试通过，测试输入文件已删除
	@Test
	public void testSaveLog() {
		File f1=new File("input1.txt");
		File f2=new File("input2.txt");
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos.writeObject(f1);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[]buff=baos.toByteArray();
		assertEquals(true,ldh.saveLog("user1", buff));
		try {
			baos.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		baos=new ByteArrayOutputStream();
		try {
			oos=new ObjectOutputStream(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos.writeObject(f2);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buff=baos.toByteArray();
		assertEquals(true,ldh.saveLog("user1", buff));
		try {
			baos.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testReadLog() {
		byte[]buff=ldh.readLog("user1");
		byte[]buff1=ldh.readLog("user2");
		byte[]buff2=ldh.readLog(null);
		
		assertNotNull(buff);
		assertNull(buff1);
		assertNull(buff2);
		
		ObjectInputStream ois=null;
		File f=null;
		try {
			ois=new ObjectInputStream(new ByteArrayInputStream(buff));
			f=(File) ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(f));
			String line=null;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

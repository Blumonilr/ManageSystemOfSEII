package main.data.logdata;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.dataservice.logdata.LogDataService;

public class LogDataHelper implements LogDataService {

	@Override
	public boolean saveLog(String userId, String content) {
		//每天新开一个文件
		Calendar c=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");	
		String fileName=userId+df.format(c.getTime())+".txt";
		FileWriter fw=null;
		File output=null;
		
		//开启输出文件
		output=new File(fileName);
		if(!output.exists()){
			try {
				output.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//输出
		try {
			fw=new FileWriter(output);
			fw.write(content);
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public String readLog(String userId) {
		// TODO Auto-generated method stub
		Calendar c=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");	
		String fileName=userId+df.format(c.getTime())+".txt";

		BufferedReader br=null;
		String content="";
		File f=new File(fileName);
		if(!f.exists())
			return null;
		
		try {
			br=new BufferedReader(new FileReader(f));
			String line=null;
			while((line=br.readLine())!=null){
				content+=line;
				content+=System.lineSeparator();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

}

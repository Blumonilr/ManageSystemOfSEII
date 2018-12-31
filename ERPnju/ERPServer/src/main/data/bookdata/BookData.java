package main.data.bookdata;

import main.PO.BookPO;
import main.businesslogic.bookbl.Book;
import main.data.util.DBConnector;
import main.dataservice.bookdataservice.BookDataService;
import main.vo.BookVO;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * 旧账存储在/account books/old books 旧账以账簿开始与结束的时间命名 yyyyMM-yyyyMM.db
 * 新帐直接存储在/account books 	文件名为info.db
 * 草稿存储在/account books 文件名为draft.db
 * 建新帐时删除草稿，草稿只能有一份
 *
 * 期初建账时将当前info.db的一份拷贝送入old books并更改文件名
 * 将当前info.db删除，将draft.db更名为info.db
 * 使用getnow方法得到当前bookVO转为PO存入firstBook表。
 */

public class BookData implements BookDataService {
    private String filePath=System.getProperty("user.dir")+"\\account books\\";
    private File database=new File(filePath+"info.db");
    private File draftBase=new File(filePath+"draft.db");
    private File tempDraft=new File(filePath+"temp.db");
    private DateFormat df=new SimpleDateFormat("yyyyMMdd");

    private final String pattern = "yyyy-MM-dd";
    private SimpleDateFormat format = new SimpleDateFormat(pattern);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    /**
     * 初始化账簿：拷贝数据库，删除原数据库，清空receipts表。初始化time和firstBook
     * 对草稿库更名，切回数据库链接
     * @param book 新帐的期初状态
     * @return 是否成功
     */
    @Override
    public boolean initBook(BookPO book) {
        Connection conn = DBConnector.getConnection();
        if(database.exists()) {
            //读取时间信息
            String oldTime = null;
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select time from time;");
                if(rs.isClosed()){
                    oldTime="19981125";
                }else {
                    rs.next();
                    oldTime = rs.getString("time");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String newTime = df.format(Calendar.getInstance().getTime());

            //copy文件
            File targetFile = new File(filePath + "\\old books\\" + oldTime + "-" + newTime + ".db");
            if(targetFile.exists())
                targetFile.delete();
            try {
                FileUtils.copyFile(database, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //删除原库
            database.delete();
            tempDraft.delete();
        }

        //清空表
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("delete from firstBook;");
            stmt.execute("delete from receipts;");
            stmt.execute("delete from time;");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //初始化
        book.setTime(Calendar.getInstance());
        try {
            Statement stmt=conn.createStatement();
            String time=df.format(book.getTime().getTime());
            stmt.execute("insert into time values("+time+");");
            PreparedStatement prep=conn.prepareStatement("insert into firstBook values(?)");
            ByteArrayOutputStream baos=null;
            ObjectOutputStream oos=null;
            baos=new ByteArrayOutputStream();
            oos=new ObjectOutputStream(baos);
            oos.writeObject(book);
            oos.flush();
            byte[]buff=baos.toByteArray();
            prep.setBytes(1, buff);
            prep.addBatch();
            prep.executeBatch();
            prep.close();
            stmt.close();
            conn.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        //草稿库更名
        draftBase.renameTo(database);

        //切回数据库链接
        DBConnector.switchLink();

        return true;
    }

    //本方法用于设置期初时间//后门
    private Calendar convert(LocalDate a){
        Calendar time=Calendar.getInstance();
        try {
            time.setTime(format.parse(formatter.format(a)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 返回当前账目的期初信息
     * @return 返回账目vo
     */
    @Override
    public BookVO showBook() {
        Connection conn=DBConnector.getConnection();
        BookPO book=null;
        try {
            Statement stmt=conn.createStatement();
            ResultSet resultSet=stmt.executeQuery("select BookPO from firstBook");
            resultSet.next();
            byte[] buff=resultSet.getBytes("BookPO");
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(buff));
            book=(BookPO)ois.readObject();
            ois.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if (book != null) {
            return new BookVO(book);
        }
        return null;
    }

    /**
     * 本方法无需实现，除非闲的蛋疼
     * 如果实现了此方法，还需额外做查看旧账目的功能
     * @return 返回旧账本的列表
     */
    @Override
    public String[] findBooks() {
        return null;
    }

    @Override
    public boolean saveDraft() {
        tempDraft.delete();
        DBConnector.switchLink();
        return true;
    }

    public void cancelChange(){
        draftBase.delete();
        tempDraft.renameTo(draftBase);
        DBConnector.switchLink();
    }

    /**
     * 进入建账状态（打开建账界面）时调用该方法
     * 如果原数据库存在，且草稿库不存在，则拷贝数据库至draft.db
     * 如果原数据库不存在，且草稿库不存在，则初始化草稿库
     * 切换数据库链接
     */
    @Override
    public void creatingBook() {
        //第一次创建
        //实际上是对草稿库的初始化
        if(!database.exists()){
            if(!draftBase.exists()) {
                Connection conn = DBConnector.getConnection();
                try {
                    Statement stmt = conn.createStatement();
                    String time = df.format(Calendar.getInstance().getTime());
                    stmt.execute("CREATE TABLE clients(id text,obj Blob);");
                    stmt.execute("CREATE TABLE classes(id text,fatherId text,obj Blob);");
                    stmt.execute("CREATE TABLE accounts(" +
                            "isDeleted int not null," +
                            "id text not null," +
                            "password text not null," +
                            "balance real not null," +
                            "nickname text not null" +
                            ");");
                    stmt.execute("CREATE TABLE receipts(id text,type int,obj Blob);");
                    stmt.execute("CREATE TABLE users(id text,User Blob);");
                    stmt.execute("CREATE TABLE goods(id text,name text,obj Blob);");
                    stmt.execute("CREATE TABLE time(time text);");
                    stmt.execute("CREATE TABLE firstBook(BookPO Blob);");
                    stmt.execute("CREATE TABLE messages(userName text,msg text);");
                    stmt.execute("CREATE TABLE newreceipts(id text,receipt Blob);");
                    stmt.execute("CREATE TABLE strategys(id text,strategy Blob);");
                    stmt.execute("CREATE TABLE drafts(draftId text,userId text,obj Blob);");
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else{
            if(!draftBase.exists()) {
                try {
                    FileUtils.copyFile(database, draftBase);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            FileUtils.copyFile(draftBase,tempDraft);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBConnector.switchLink();
    }
}

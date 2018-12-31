package main.businesslogic.util;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 维护单据编号的单键，仅供ReviseInfo使用
 * 对单据编号时使用，调用number方法，参数为单据类型，返回值为单据编号或其他，具体见方法注释
 *
 * 已测试！
 */
public  class Numbering {
    private int stockOverflowReceipt;
    private int stockUnderflowReceipt;
    private int stockGiftReceipt;
    private int financeCollectReceipt;
    private int financePayReceipt;
    private int financeCashReceipt;
    private int saleReceipt;
    private int saleReturnReceipt;
    private int purchaseReceipt;
    private int purchaseReturnReceipt;
    private int stockAlarmReceipt;
    private Calendar time;
    private String filepath=Numbering.class.getResource("").getPath()+"time.txt";
    private BufferedReader br;
    private BufferedWriter bw;
    private DateFormat df=new SimpleDateFormat("yyyyMMdd");

    private static Numbering ourInstance = new Numbering();

    public static Numbering getInstance() {
        return ourInstance;
    }

    /**
     *
     * @param type 单据类型
     * @return 单据编号
     *                max:当日单据量已达最大值
     *                error:参数错误(应该不会存在这种情况）
     */
    public synchronized String number(int type){
        time=Calendar.getInstance();

        try {
            br=new BufferedReader(new FileReader(new File(filepath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if(!br.readLine().split(" ")[0].equals(df.format(time.getTime()))){//时间不符
                init();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(type==11){
            if(stockOverflowReceipt==99999)
                return "max";
            stockOverflowReceipt++;
            write();
            return "KCBYD-"+df.format(time.getTime())+"-"+format(stockOverflowReceipt);
        }
        if(type==12){
            if(stockUnderflowReceipt==99999)
                return "max";
            stockUnderflowReceipt++;
            write();
            return "KCBSD-"+df.format(time.getTime())+"-"+format(stockUnderflowReceipt);
        }
        if(type==13){
            if(stockGiftReceipt==99999)
                return "max";
            stockGiftReceipt++;
            write();
            return "KCZSD-"+df.format(time.getTime())+"-"+format(stockGiftReceipt);
        }
        if(type==14){
            if(stockAlarmReceipt==99999)
                return "max";
            stockAlarmReceipt++;
            write();
            return "KCBJD-"+df.format(time.getTime())+"-"+format(stockAlarmReceipt);
        }
        if(type==21){
            if(financeCollectReceipt==99999)
                return "max";
            financeCollectReceipt++;
            write();
            return "SKD-"+df.format(time.getTime())+"-"+format(financeCollectReceipt);
        }
        if(type==22){
            if(financePayReceipt==99999)
                return "max";
            financePayReceipt++;
            write();
            return "FKD-"+df.format(time.getTime())+"-"+format(financePayReceipt);
        }
        if(type==23){
            if(financeCashReceipt==99999)
                return "max";
            financeCashReceipt++;
            write();
            return "XJFYD-"+df.format(time.getTime())+"-"+format(financeCashReceipt);
        }
        if(type==31){
            if(saleReceipt==99999)
                return "max";
            saleReceipt++;
            write();
            return "XSD-"+df.format(time.getTime())+"-"+format(saleReceipt);
        }
        if(type==32){
            if(saleReturnReceipt==99999)
                return "max";
            saleReturnReceipt++;
            write();
            return "XSTHD-"+df.format(time.getTime())+"-"+format(saleReturnReceipt);
        }
        if(type==41){
            if(purchaseReceipt==99999)
                return "max";
            purchaseReceipt++;
            write();
            return "JHD-"+df.format(time.getTime())+"-"+format(purchaseReceipt);
        }
        if(type==42){
            if(purchaseReturnReceipt==99999)
                return "max";
            purchaseReturnReceipt++;
            write();
            return "JHTHD-"+df.format(time.getTime())+"-"+format(purchaseReturnReceipt);
        }
        return "error";
    }

    public String format(int target){
        if(target>0&&target<=9)
            return "0000"+target;
        if(target>=10&&target<=99)
            return "000"+target;
        if(target>=100&&target<=999)
            return "00"+target;
        if(target>=1000&&target<=9999)
            return "0"+target;
        return ""+target;
    }

    private Numbering() {
        br=null;
        File file=new File(filepath);
        if(!file.exists())
            init();
        try {
            br=new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();//不存在的
        }

        //恢复服务器
        String[] numbers=null;
        try {
            numbers=br.readLine().split(" ");
            time=Calendar.getInstance();
            if(!numbers[0].equals(df.format(time.getTime()))){//文件存在但时间不符
                init();
            }else {//文件存在且时间相符
                stockOverflowReceipt = Integer.parseInt(numbers[1]);
                stockUnderflowReceipt = Integer.parseInt(numbers[2]);
                stockGiftReceipt = Integer.parseInt(numbers[3]);
                financeCollectReceipt = Integer.parseInt(numbers[4]);
                financePayReceipt = Integer.parseInt(numbers[5]);
                financeCashReceipt = Integer.parseInt(numbers[6]);
                saleReceipt = Integer.parseInt(numbers[7]);
                saleReturnReceipt = Integer.parseInt(numbers[8]);
                purchaseReceipt = Integer.parseInt(numbers[9]);
                purchaseReturnReceipt = Integer.parseInt(numbers[10]);
                stockAlarmReceipt=Integer.parseInt(numbers[11]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        stockOverflowReceipt = 0;
        stockUnderflowReceipt = 0;
        stockGiftReceipt = 0;
        financeCollectReceipt = 0;
        financePayReceipt = 0;
        financeCashReceipt = 0;
        saleReceipt = 0;
        saleReturnReceipt = 0;
        purchaseReceipt = 0;
        purchaseReturnReceipt = 0;
        stockAlarmReceipt=0;
        time=Calendar.getInstance();
        try {
            bw = new BufferedWriter(new FileWriter(new File(filepath)));
            bw.write(df.format(time.getTime()) +" " +stockOverflowReceipt +" "+
                    stockUnderflowReceipt +" "+
                    stockGiftReceipt +" "+
                    financeCollectReceipt +" "+
                    financePayReceipt +" "+
                    financeCashReceipt +" "+
                    saleReceipt +" "+
                    saleReturnReceipt +" "+
                    purchaseReceipt +" "+
                    purchaseReturnReceipt+" "+
                    stockAlarmReceipt);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(){
        try {
            bw = new BufferedWriter(new FileWriter(new File(filepath)));
            bw.write(df.format(time.getTime()) +" " +stockOverflowReceipt +" "+
                    stockUnderflowReceipt +" "+
                    stockGiftReceipt +" "+
                    financeCollectReceipt +" "+
                    financePayReceipt +" "+
                    financeCashReceipt +" "+
                    saleReceipt +" "+
                    saleReturnReceipt +" "+
                    purchaseReceipt +" "+
                    purchaseReturnReceipt+" "+
                    stockAlarmReceipt);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

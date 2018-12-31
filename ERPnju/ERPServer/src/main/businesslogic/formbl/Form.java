package main.businesslogic.formbl;

import main.businesslogic.revisebl.Revise;
import main.businesslogic.revisebl.ReviseInfo;
import main.businesslogic.stockbl.Goods;
import main.businesslogic.stockbl.GoodsInfo;
import main.businesslogic.stockbl.ReceiptInfo;
import main.businesslogic.stockbl.Stock;
import main.vo.*;

import java.util.ArrayList;
import java.util.Calendar;

public class Form {
    private ReceiptInfo receiptInfo=new Stock();
    private ReviseInfo reviseInfo=new Revise();

    /**
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param condition 格式为商品名 客户 业务员 仓库，以空格隔开，条件不限则以"null"代替
     * @return 符合要求的列表
     */
    public ArrayList<SaleFormLineItem> saleList(Calendar beginTime,Calendar endTime,String condition) {
        ArrayList<SaleReceiptVO> list = forceConvertSale(receiptInfo.readReceipt(31, beginTime, endTime));
        String[] conditions = condition.split(" ");
        if (!conditions[1].equals("null"))
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getClientId().equals(conditions[1])) {
                    list.remove(i);
                    i--;
                }
            }
        if (!conditions[2].equals("null"))
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getClerkId().equals(conditions[2])){
                    list.remove(i);
                    i--;
                }
            }
        if (!conditions[3].equals("null"))
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getStockId().equals(conditions[3])){
                    list.remove(i);
                    i--;
                }
            }
        ArrayList<SaleFormLineItem> saleList = new ArrayList<>();
        ArrayList<SaleReceiptBothVOLineItem> temp;
        for(int i=0;i<list.size();i++){
            temp=list.get(i).getItemList();
            for(int j=0;j<temp.size();j++){
                saleList.add(new SaleFormLineItem(list.get(i).getMakeTime(),temp.get(j).getGoodsName(),temp.get(j).getGoodsXh(),temp.get(j).getGoodsAmount(),temp.get(j).getGoodsPrice()));
            }
        }
        if(!conditions[0].equals("null"))
        for(int i=0;i<saleList.size();i++){
            if(!saleList.get(i).getGoodName().equals(conditions[0])){
                saleList.remove(i);
                i--;
            }
        }
        return saleList;
    }

    /**
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param condition 格式为单据类型  客户 业务员 仓库，以空格隔开，条件不限则以“null”代替
     *                  单据类型：1.销售类单据 2.进货类单据 3.财务类单据 4.库存类单据
     * @return 符合要求的列表
     */
    public ArrayList<ReceiptVO> recordList(Calendar beginTime,Calendar endTime,String condition) {
        String[] conditions = condition.split(" ");
        switch (conditions[0]){
            case "1":
                ArrayList<ReceiptVO> saleRecord=receiptInfo.readReceipt(31,beginTime,endTime);
                saleRecord.addAll(receiptInfo.readReceipt(32,beginTime,endTime));
                return saleRecord;
            case "2":
                ArrayList<ReceiptVO> purchaseRecord=receiptInfo.readReceipt(41,beginTime,endTime);
                purchaseRecord.addAll(receiptInfo.readReceipt(42,beginTime,endTime));
                return purchaseRecord;
            case "3":
                ArrayList<ReceiptVO> financeRecord=receiptInfo.readReceipt(21,beginTime,endTime);
                financeRecord.addAll(receiptInfo.readReceipt(22,beginTime,endTime));
                financeRecord.addAll(receiptInfo.readReceipt(23,beginTime,endTime));
                return financeRecord;
            case "4":
                ArrayList<ReceiptVO> stockRecord=receiptInfo.readReceipt(11,beginTime,endTime);
                stockRecord.addAll(receiptInfo.readReceipt(12,beginTime,endTime));
                stockRecord.addAll(receiptInfo.readReceipt(13,beginTime,endTime));
                return stockRecord;
        }
        return null;
    }

    public ReceiptVO showDetailReceipt(String id) {
        return receiptInfo.readReceipt(id);
    }

    public boolean offset(String id) {
    	ReceiptVO receipt=receiptInfo.readReceipt(id);
    	switch(receipt.getReceiptType()){
    	case 21:
    		FinancePayReceiptVO fpr=new FinancePayReceiptVO();
    		fpr.setClient(((FinancePayReceiptVO)receipt).getClient());
    		fpr.setCreatorId(((FinancePayReceiptVO)receipt).getCreatorId());
    		fpr.setMakeTime(((FinancePayReceiptVO)receipt).getMakeTime());
    		ArrayList<FinanceActTransLineItem> list=((FinancePayReceiptVO)receipt).getItemList();
    		ArrayList<FinanceActTransLineItem> newList=new ArrayList<>();
    		for(FinanceActTransLineItem item:list){
    			newList.add(new FinanceActTransLineItem(item.getBankAccount(), -item.getAmount(), "红冲操作"));
    		}
    		fpr.setItemList(newList);
    		return reviseInfo.insertReceipt(fpr);
    	case 22:
    		FinanceCollectReceiptVO fcr=new FinanceCollectReceiptVO();
    		fcr.setClient(((FinancePayReceiptVO)receipt).getClient());
    		fcr.setCreatorId(((FinancePayReceiptVO)receipt).getCreatorId());
    		fcr.setMakeTime(((FinancePayReceiptVO)receipt).getMakeTime());
    		ArrayList<FinanceActTransLineItem> flist=((FinancePayReceiptVO)receipt).getItemList();
    		ArrayList<FinanceActTransLineItem> newFlist=new ArrayList<>();
    		for(FinanceActTransLineItem item:flist){
    			newFlist.add(new FinanceActTransLineItem(item.getBankAccount(), -item.getAmount(), "红冲操作"));
    		}
    		fcr.setItemList(newFlist);
    		return reviseInfo.insertReceipt(fcr);
    	case 23:
    		FinanceCashReceiptVO fcar=new FinanceCashReceiptVO();
    		fcar.setCreatorId(receipt.getCreatorId());
    		fcar.setMakeTime(receipt.getMakeTime());
    		fcar.setAccountName(((FinanceCashReceiptVO)receipt).getAccountName());
    		ArrayList<FinanceCashReceiptLineItem> clist=((FinanceCashReceiptVO)receipt).getItemList();
    		ArrayList<FinanceCashReceiptLineItem> newClist=new ArrayList<>();
    		for(FinanceCashReceiptLineItem item:clist){
    			newClist.add(new FinanceCashReceiptLineItem(item.getItemName(), -item.getAmount(), "红冲操作"));
    		}
    		fcar.setItemList(newClist);
    		return reviseInfo.insertReceipt(fcar);
    	}
		return false;
    }

    public boolean offsetAndCopy(ReceiptVO offSetReceipt) {
        return reviseInfo.insertReceipt(offSetReceipt);
    }

    /**
     *
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @return double数组：销售退货差价(已减去折让额)，代金券总额，折让总额，报溢收入，总收入，
     *                                  进货退货差价，报损支出，现金费用支出，商品赠送支出，总支出，总利润
     *                                  共11项
     */
    public double[] businessList(Calendar beginTime,Calendar endTime) {
        double income=0;//总收入
        double expense=0;//总支出
        double saleIncome=0;//折让前总量
        double saleIncomedis=0;//折让后总量
        double antiSaleExpense=0;//销售退货支出
        double overflowIncome=0;//报溢收入
        double purchaseExpense=0;//进货支出
        double antiPurchaseIncome=0;//进货退货收入
        double voucherExpense=0;//代金券支出
        double underFlowExpense=0;//报损支出
        double giftExpense=0;//赠送支出
        double cashExpense=0;//现金费用支出

        //---------------------
        ArrayList<SaleReceiptVO> saleList=forceConvertSale(receiptInfo.readReceipt(31,beginTime,endTime));
        ArrayList<SaleReturnReceiptVO> antiSaleList=forceConvertAntisale(receiptInfo.readReceipt(32,beginTime,endTime));
        ArrayList<StockOverflowReceiptVO> overflowList=forceConvertOverFlow(receiptInfo.readReceipt(11,beginTime,endTime));
        ArrayList<PurchaseReceiptVO> purchaseList=forceConvertPurchase(receiptInfo.readReceipt(41,beginTime,endTime));
        ArrayList<PurchaseReturnReceiptVO> antiPurchaseList=forceConvertAntipurchase(receiptInfo.readReceipt(42,beginTime,endTime));
        ArrayList<StockUnderflowReceiptVO> underflowList=forceConvertUnderFlow(receiptInfo.readReceipt(12,beginTime,endTime));
        ArrayList<StockGiftReceiptVO> giftList=forceConvertGift(receiptInfo.readReceipt(13,beginTime,endTime));
        ArrayList<FinanceCashReceiptVO> cashList=forceConvertCash(receiptInfo.readReceipt(23,beginTime,endTime));

        //计算折让前后总量
        //计算代金券支出
        for(int i=0;i<saleList.size();i++){
            //如果用的是代金券，就把该单据的代金券前价格加在折让前、后总量里以抵消代金券的影响
            if(saleList.get(i).getDiscount()!=1.0) {
                saleIncome += saleList.get(i).getTotalBeforeDiscountOrVoucher();
                saleIncomedis += saleList.get(i).getTotalAfterDiscount();
            }
            if(saleList.get(i).getValueOfVoucher()!=0){
                saleIncome += saleList.get(i).getTotalBeforeDiscountOrVoucher();
                saleIncomedis += saleList.get(i).getTotalBeforeDiscountOrVoucher();
                voucherExpense+=saleList.get(i).getValueOfVoucher();
            }
        }
        //计算销售退货支出
        for(int i=0;i<antiSaleList.size();i++){
            antiSaleExpense+=antiSaleList.get(i).getTotalBeforeDiscountOrVoucher();
        }
        //计算报溢收入
        GoodsInfo goodsInfo=new Goods();
        for(int i=0;i<overflowList.size();i++){
            overflowIncome+=goodsInfo.getGoods(overflowList.get(i).getGoodsId()).getLatestip()*overflowList.get(i).getAmount();
        }
        //计算进货支出
        for(int i=0;i<purchaseList.size();i++){
            purchaseExpense+=purchaseList.get(i).getTotalValue();
        }
        //计算进货退货收入
        for(int i=0;i<antiPurchaseList.size();i++){
            antiPurchaseIncome+=antiPurchaseList.get(i).getTotalValue();
        }
        //计算报损支出
        for(int i=0;i<underflowList.size();i++){
            underFlowExpense+=goodsInfo.getGoods(underflowList.get(i).getGoodsId()).getLatestip()*underflowList.get(i).getAmount();
        }
        //计算赠送支出
        ArrayList<StockGiftReceiptVOLineItem> gifts=null;
        for(int i=0;i<giftList.size();i++){
            gifts=giftList.get(i).getGoodsList();
            giftExpense+=giftList.get(i).getTotal();
        }
        //计算现金费用支出
        for(int i=0;i<cashList.size();i++){
            cashExpense+=cashList.get(i).getTotalAmount();
        }
        income=saleIncomedis-antiSaleExpense+overflowIncome;
        expense=purchaseExpense-antiPurchaseIncome+cashExpense+giftExpense+voucherExpense;
        return new double[]{saleIncomedis-antiSaleExpense,voucherExpense,saleIncome-saleIncomedis,overflowIncome,income,purchaseExpense-antiPurchaseIncome,underFlowExpense,cashExpense,giftExpense,expense,income-expense};
    }

    //以下方法用于强制转换单据（列表）类型
    public ArrayList<StockOverflowReceiptVO> forceConvertOverFlow(ArrayList<ReceiptVO> list){
        ArrayList<StockOverflowReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((StockOverflowReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<StockUnderflowReceiptVO> forceConvertUnderFlow(ArrayList<ReceiptVO> list){
        ArrayList<StockUnderflowReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((StockUnderflowReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<StockGiftReceiptVO> forceConvertGift(ArrayList<ReceiptVO> list){
        ArrayList<StockGiftReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((StockGiftReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<FinanceCollectReceiptVO> forceConvertCollect(ArrayList<ReceiptVO> list){
        ArrayList<FinanceCollectReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((FinanceCollectReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<FinancePayReceiptVO> forceConvertPay(ArrayList<ReceiptVO> list){
        ArrayList<FinancePayReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((FinancePayReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<FinanceCashReceiptVO> forceConvertCash(ArrayList<ReceiptVO> list){
        ArrayList<FinanceCashReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((FinanceCashReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<SaleReceiptVO> forceConvertSale(ArrayList<ReceiptVO> list){
        ArrayList<SaleReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((SaleReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<SaleReturnReceiptVO> forceConvertAntisale(ArrayList<ReceiptVO> list){
        ArrayList<SaleReturnReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((SaleReturnReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<PurchaseReceiptVO> forceConvertPurchase(ArrayList<ReceiptVO> list){
        ArrayList<PurchaseReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((PurchaseReceiptVO)list.get(i));
        }
        return result;
    }
    public ArrayList<PurchaseReturnReceiptVO> forceConvertAntipurchase(ArrayList<ReceiptVO> list){
        ArrayList<PurchaseReturnReceiptVO> result=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            result.add((PurchaseReturnReceiptVO)list.get(i));
        }
        return result;
    }
}

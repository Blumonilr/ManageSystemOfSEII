package main.businesslogic.bookbl;

import main.PO.AccountPO;
import main.PO.ClassPO;
import main.PO.ClientPO;
import main.PO.GoodsPO;
import main.vo.*;

/**
 * 期初建账专用
 */
public class ObjectsTransformer {
    public ClientPO toClientPO(ClientVO obj){
        if(obj==null)
            return null;
        int type=1;
        if(obj.getType().equals("供应商")){
            type=2;
        }
        ClientPO c=new ClientPO(obj.getID(), obj.getClientName(), obj.getPhone(),
                obj.getAddress(), obj.getPostcode(), obj.getEmail(), obj.getDefaultUserID(),
                type, obj.getLevel(), obj.getPay(),
                obj.getCollect(), obj.getCollectTop());
        return c;
    }

    public ClientVO toClientVO(ClientPO obj){
        if(obj==null)
            return null;
        String type="销售商";
        if(obj.getType()==2){
            type="供应商";
        }
        ClientVO c=new ClientVO(obj.getName(), obj.getId(),type,
                obj.getLevel(), obj.getTel(), obj.getAddr(), obj.getEmail(),
                obj.getPostNum(), obj.getCollectTop(), obj.getCollect(),
                obj.getPay(), obj.getCounterman());
        return c;
    }

    public GoodsPO toGoodsPO(InGoodsVO obj){
        if(obj==null)
            return null;
        //lastinPrice\lastoutPrice应该在构造器里自动赋值
        return new GoodsPO( obj.getId(), obj.getName(), obj.getXh(), obj.getInPrice(),
                obj.getOutPrice(), obj.getIniNum(), obj.getAlarmNum(),
                obj.getClassName(), obj.getClassId(), obj.getDay());
    }

    public InGoodsVO toGoodsVO(GoodsPO obj){
        if(obj==null)
            return null;
        return new InGoodsVO(obj.getName(),obj.getXh(),obj.getClassName(),
                obj.getClassId(),obj.getLastInPrice(),obj.getLastOutPrice(),obj.getNum(),obj.getAlarmNum());
    }
    public InGoodsVO toInGoodsVO(OutGoodsVO obj){
        if(obj==null)
            return null;
        InGoodsVO middle=new InGoodsVO(obj.getName(),obj.getXh(),obj.getClassName(),obj.getClassId(),obj.getInPrice(),obj.getOutPrice(),obj.getStockNum(),obj.getAlarmNum());
        middle.setId(null);
        middle.setDay(null);
        return middle;
    }

    public ClassPO toClassPO(ClassVO obj){
        if(obj==null)
            return null;
        return new ClassPO(obj.getId(), obj.getName(), obj.getFather(), obj.getSubclasses(), obj.getSubgoods());
    }

    public ClassVO toClassVO(ClassPO obj){
        if(obj==null)
            return null;
        return new ClassVO(null, obj.getName(), obj.getFatherId(), obj.getSubclasses(), obj.getSubgoods());
    }

    public AccountPO toAccountPO(AccountVO vo){
        return new AccountPO(false,vo.getId(),vo.getPassword(),vo.getBalance(),vo.getNickname());
    }

    public AccountVO toAccountVO(AccountPO po){
        return new AccountVO(po.getNickname(),po.getBalance(),po.getId(),po.getPassword());
    }
}

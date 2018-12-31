package main.ui.stockui.stockui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class StockInventoryController {

	@FXML public TextField findbyClass;
	@FXML public TextField findbyGoods;
	@FXML public TableView<OutGoodsVO>table;
	@FXML public Label goodsName;
	@FXML public Label goodsId;
	@FXML public Label className;
	@FXML public Label classId;
	@FXML public Label xh;
	@FXML public Label inPrice;
	@FXML public Label outPrice;
	@FXML public Label stockNum;
	@FXML public Label alarmNum;
	
	private StockblService stock;
	
	public void stockInventory() {
		stock=RemoteHelper.getInstance().getStockblService();
		
		ArrayList<OutGoodsVO>goods=null;
		try {
			goods=stock.getGoods();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(goods!=null)
			initTable(goods);
		ClientRunner.getLogger().record("库存盘点");
		
		//为输入框添加输入监听
		findbyClass.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				onFind();
			}
			
		});
		
		findbyGoods.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				onFind();
			}
			
		});
	}
	
	private void initTable(ArrayList<OutGoodsVO>goods){
		table.getColumns().clear();
			
		//设置点击事件
		table.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)){
				OutGoodsVO obj=null;
				obj=table.getSelectionModel().getSelectedItem();
				if(obj!=null){
					goodsName.setText(obj.getName());
					goodsId.setText(obj.getId());
					className.setText(obj.getClassName());
					classId.setText(obj.getClassId());
					xh.setText(obj.getXh());
					inPrice.setText(Double.toString(obj.getInPrice()));
					outPrice.setText(Double.toString(obj.getOutPrice()));
					stockNum.setText(Long.toString(obj.getStockNum()));
					alarmNum.setText(Long.toString(obj.getAlarmNum()));
				}
			}
		});
		
		TableColumn<OutGoodsVO,String>nameC=new TableColumn<>("商品名");
		TableColumn<OutGoodsVO,String>xhC=new TableColumn<>("型号");
		TableColumn<OutGoodsVO,Long>numC=new TableColumn<>("库存数量");
		TableColumn<OutGoodsVO,Double>opC=new TableColumn<>("售价");
		TableColumn<OutGoodsVO,String>dayC=new TableColumn<>("日期");
		TableColumn<OutGoodsVO,Number>rowC=new TableColumn<>("行号");
		
		nameC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,String>("name"));
		xhC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,String>("xh"));
		numC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,Long>("stockNum"));
		opC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,Double>("outPrice"));
		dayC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String>p)->
			new ReadOnlyStringWrapper(p.getValue().getStringDay()));
		//rowC将不在排序标准之列
		rowC.setSortable(false);
		//自动标号
		rowC.setCellValueFactory(column->
			new ReadOnlyObjectWrapper<Number>(table.getItems().indexOf(column.getValue())));
		
		table.getColumns().addAll(rowC,nameC,xhC,numC,opC,dayC);
		table.setItems(FXCollections.observableArrayList(goods));
	}
	
	public void onFind(){
		String className=findbyClass.getText(),goodsName=findbyGoods.getText();
		if(className==null)
			className="";
		if(goodsName==null)
			goodsName="";
		
		GoodsQueryItem query=new GoodsQueryItem();
		query.setClassName(className);
		query.setName(goodsName);
		ArrayList<OutGoodsVO> list=null;
		try {
			list = stock.findGoods(query);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null)
			initTable(list);
	}

	@FXML
	public void onClear(){
		findbyClass.setText(null);
		findbyGoods.setText(null);
		className.setText(null);
		classId.setText(null);
		goodsName.setText(null);
		goodsId.setText(null);
		xh.setText(null);
		inPrice.setText(null);
		outPrice.setText(null);
		stockNum.setText(null);
		alarmNum.setText(null);
	}
	
	@FXML
	public void onExport(){
		//以PDF格式导出库存数量记录
		ArrayList<OutGoodsVO>list=null;
		try {
			list=stock.getGoods();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//由用户选择记录的保存路径
		FileChooser chooser=new FileChooser();
		chooser.setTitle("选择导出路径");
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("XLS files(*.xls)","*.xls");
		chooser.getExtensionFilters().add(filter);
		File file=chooser.showSaveDialog(new Stage());//file即为保存数据的目标对象
		
		if(file!=null){
			try {
				WritableWorkbook wwb=Workbook.createWorkbook(new FileOutputStream(file));
				WritableSheet sheet=wwb.createSheet("库存盘点", 0);
				jxl.write.Label nameC=new jxl.write.Label(0,0,"商品名");
				jxl.write.Label xhC=new jxl.write.Label(1,0,"型号");
				jxl.write.Label stockNumC=new jxl.write.Label(2,0,"库存数量");
				jxl.write.Label opC=new jxl.write.Label(3,0,"售价");
				jxl.write.Label dayC=new jxl.write.Label(4,0,"出厂日期");
				sheet.addCell(nameC);
				sheet.addCell(xhC);
				sheet.addCell(stockNumC);
				sheet.addCell(opC);
				sheet.addCell(dayC);
				
				if(list!=null)
					for(int i=0;i<list.size();i++){
						jxl.write.Label name=new jxl.write.Label(0,i+1,list.get(i).getName());
						jxl.write.Label xh=new jxl.write.Label(1,i+1,list.get(i).getXh());
						jxl.write.Number stockNum=new jxl.write.Number(2,i+1,list.get(i).getStockNum());
						jxl.write.Number op=new jxl.write.Number(3,i+1,list.get(i).getOutPrice());
						jxl.write.Label day=new jxl.write.Label(4,i+1,list.get(i).getStringDay());
						
						sheet.addCell(name);
						sheet.addCell(xh);
						sheet.addCell(stockNum);
						sheet.addCell(op);
						sheet.addCell(day);
					}
				
				wwb.write();
				wwb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//打开文件
			Desktop desktop=Desktop.getDesktop();
			try {
				desktop.open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ClientRunner.getLogger().record("导出库存盘点记录"+file.getPath());
		}
	}
}

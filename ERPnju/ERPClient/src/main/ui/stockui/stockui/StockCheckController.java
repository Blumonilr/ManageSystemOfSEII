package main.ui.stockui.stockui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.stockui.goodsui.GoodsUIController;
import main.ui.stockui.goodsui.ShowGoodsController;
import main.ui.util.ShowReceiptUIController;
import main.vo.OutGoodsVO;
import main.vo.ReceiptVO;
import main.vo.StockCheckResultVO;
import main.vo.StockRecordVO;

public class StockCheckController {

	@FXML public JFXDatePicker startTime;
	@FXML public JFXDatePicker endTime;
	@FXML public TableView<StockRecordVO> table;
	@FXML public JFXTextField goodsId;
	@FXML public Label totalNum;
	@FXML public Label showId;
	@FXML public Label showName;
	@FXML public AnchorPane abstractPane;
	
	private StockblService stock;
	
	@FXML
	public void onFind(){
		String id=goodsId.getText();
		String t1=startTime.getEditor().getText();
		String t2=endTime.getEditor().getText();
		if(id==null)
			id="";
		if(t1==null)
			t1="";
		if(t2==null)
			t2="";
		
		Calendar start=new GregorianCalendar(),end=new GregorianCalendar();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		StockCheckResultVO result=null;
		
		try {
			start.setTime(df.parse(t1));
		} catch (ParseException e1) {
			//e1.printStackTrace();
			start=null;
		}
		try {
			end.setTime(df.parse(t2));
		} catch (ParseException e1) {
			//e1.printStackTrace();
			end=null;
		}

		if(id!=null){			
			try {
				result=stock.stockCheck(id, start, end);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result!=null){
			initTableView(result.getList());
			showName.setText(result.getGoodsName());
			showId.setText(result.getGoodsId());
			totalNum.setText(Long.toString(result.getTotalNum()));
			ClientRunner.getLogger().record("库存查看 "+result.getGoodsName());
		}else{
			ClientRunner.getLogger().record("库存查看未找到记录 ");
			initTableView(new ArrayList<StockRecordVO>());
			
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("找不到相应的库存记录！");
			alert.show();
		}
	}
	
	@FXML
	public void onRefresh(){
		startTime.getEditor().setText(null);
		endTime.getEditor().setText(null);
		goodsId.setText(null);
		totalNum.setText(null);
		showId.setText(null);
		showName.setText(null);
		table.getItems().clear();
	}

	@FXML
	/**
	 * 显示商品列表以供选择
	 */
	public void onSelectGoods(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(StockCheckController.class.getResource("GoodsListUI.fxml"));
		try {
			AnchorPane layout = loader.load();
			GoodsListUIController listui = (GoodsListUIController)loader.getController();
			Scene scene=new Scene(layout);
			Stage stage=new Stage();
			stage.setTitle("请选择一个商品");
			stage.setScene(scene);
			listui.init(stage,this);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stockCheck() {
		stock=RemoteHelper.getInstance().getStockblService();
		abstractPane.setOnMouseClicked((MouseEvent me)->{
			if(me.getClickCount()==2&&me.getButton().equals(MouseButton.PRIMARY))
				showGoods();
		});
		initTableView(new ArrayList<StockRecordVO>());
	}
	
	private void initTableView(ArrayList<StockRecordVO>list){
		
		table.getColumns().clear();
		
		for(StockRecordVO sr:list){
			System.out.println();
		}
		
		//设置双击事件
		table.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				StockRecordVO vo=table.getSelectionModel().getSelectedItem();
				if(vo!=null){
					showReceipt(vo.getReceiptId());
				}
			}
		});
		
		//设置每一列，显示单据类型、编号、创建者、创建时间、审批时间
		TableColumn<StockRecordVO,String>typeC=new TableColumn<>("类型");
		TableColumn<StockRecordVO,String>idC=new TableColumn<>("单据ID");
		TableColumn<StockRecordVO,Long>numC=new TableColumn<>("库存数量");
		TableColumn<StockRecordVO,String>timeC=new TableColumn<>("时间");
		TableColumn<StockRecordVO,Double>priceC=new TableColumn<>("总价");
		
		//设置映射
		typeC.setCellValueFactory(new PropertyValueFactory<StockRecordVO, String>("type"));
		idC.setCellValueFactory(new PropertyValueFactory<StockRecordVO,String>("receiptId"));
		numC.setCellValueFactory(new PropertyValueFactory<StockRecordVO,Long>("num"));
		timeC.setCellValueFactory(new PropertyValueFactory<StockRecordVO,String>("time"));
		priceC.setCellValueFactory(new PropertyValueFactory<StockRecordVO,Double>("price"));
		
		//强制开始时间早于结束时间
        final Callback<DatePicker, DateCell> endCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (startTime.getValue()!=null&&item.isBefore(startTime.getValue().plusDays(1)) ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };


        final Callback<DatePicker, DateCell> beginCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (endTime.getValue()!=null&&item.isAfter( endTime.getValue().minusDays(1)) ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        startTime.setDayCellFactory(beginCellFactory);
        endTime.setDayCellFactory(endCellFactory);
		
		
		
		table.setItems(FXCollections.observableArrayList(list));
		table.getColumns().addAll(timeC,typeC,idC,numC,priceC);
	}
	
	private void showGoods(){
		OutGoodsVO goods=null;
		try {
			goods=stock.getGoods(showId.getText());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(goods!=null){
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(GoodsUIController.class.getResource("ShowGoods.fxml"));
			try {
				AnchorPane ap=(AnchorPane)loader.load();
				ShowGoodsController sgc=(ShowGoodsController)loader.getController();
				Scene scene=new Scene(ap);
				Stage s=new Stage();
				s.setScene(scene);
				sgc.init(s, goods);
				s.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void showReceipt(String receiptId){
		ReceiptVO receipt=null;
		try {
			receipt=stock.stockCheckDetail(receiptId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		ShowReceiptUIController contr=new ShowReceiptUIController();
		contr.showReceipt(receipt);
	}
}

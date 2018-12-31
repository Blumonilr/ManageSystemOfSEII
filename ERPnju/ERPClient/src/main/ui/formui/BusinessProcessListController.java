package main.ui.formui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import main.businesslogicservice.bookblservice.BookblService;
import main.businesslogicservice.formblservice.FormblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.financeui.detail.CashDetailController;
import main.ui.financeui.detail.CollectAndPayDetailController;
import main.ui.financeui.edit.EditCashController;
import main.ui.financeui.edit.EditCollectAndPayController;
import main.ui.reviseui.ModifyCollectAndPayController;
import main.ui.reviseui.Receipt;
import main.ui.util.ShowReceiptUIController;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceReceiptVO;
import main.vo.ReceiptVO;

public class BusinessProcessListController implements Initializable{
	@FXML private JFXDatePicker beginTimePicker;
	@FXML private JFXDatePicker endTimePicker;
	@FXML private Button query;
	@FXML TableColumn<FormReceipt,String> id;
	@FXML TableColumn<FormReceipt,String> time;
	@FXML TableColumn<FormReceipt,String> type;
	@FXML TableColumn<FormReceipt,String> counter;
	@FXML TableColumn<FormReceipt,String> client;
	@FXML TableColumn<FormReceipt,String> stock;
	@FXML TableView<FormReceipt> record;
	@FXML private MenuButton receipttype;
	@FXML MenuItem saleReceipt;
	@FXML MenuItem purchaseReceipt;
	@FXML MenuItem financeReceipt;
	@FXML MenuItem stockReceipt;
	@FXML private JFXTextField counterSearch;
	@FXML private JFXTextField clientSearch;
	@FXML private JFXTextField stockSearch;
	@FXML private Button output;

	private FormblService formblService;
	private BookblService bookblService;
	private final String pattern = "yyyy-MM-dd";
	private SimpleDateFormat format;
	private StringConverter<LocalDate> converter;
	ObservableList<FormReceipt> receiptsList=FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		formblService=RemoteHelper.getInstance().getFormblService();
	    bookblService=RemoteHelper.getInstance().getBookblService();
	    record.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				FormReceipt vo=record.getSelectionModel().getSelectedItem();
				if(vo!=null){
					showReceipt(vo.getId());
				}
			}
		});
	    format=new SimpleDateFormat(pattern);
	    converter=new StringConverter<LocalDate>() {
	        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
	        @Override
	        public String toString(LocalDate date) {
	            if (date != null) {
	                return formatter.format(date);
	            } else {
	                return "";
	            }
	        }
	        @Override
	        public LocalDate fromString(String string) {
	            if (string != null && !string.isEmpty()) {
	                return LocalDate.parse(string, formatter);
	            } else {
	                return null;
	            }
	        }
	    };
	    //设置格式转换器
	    beginTimePicker.setConverter(converter);
	    endTimePicker.setConverter(converter);

	    //获得当前账簿起始时间
	    DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
	    LocalDate firstBook=null;
	    try {
	        firstBook=LocalDate.parse(format.format(bookblService.showBook().getTime().getTime()), formatter);
	    } catch (RemoteException e) {
	        e.printStackTrace();
	    }
	    LocalDate finalFirstBook = firstBook;

	    //设置单元格工厂
	    final Callback<DatePicker, DateCell> endCellFactory =
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                            if (item.isBefore(beginTimePicker.getValue()==null?finalFirstBook:beginTimePicker.getValue().plusDays(1))||item.isAfter(LocalDate.now().plusDays(1))) {
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
	                            if (item.isAfter(endTimePicker.getValue()==null?LocalDate.now():endTimePicker.getValue().minusDays(1))||item.isBefore(finalFirstBook)) {
	                                setDisable(true);
	                                setStyle("-fx-background-color: #ffc0cb;");
	                            }
	                        }
	                    };
	                }
	            };
	    beginTimePicker.setDayCellFactory(beginCellFactory);
	    endTimePicker.setDayCellFactory(endCellFactory);
	    endTimePicker.setValue(LocalDate.now().plusDays(1));
	    
	    id.setCellValueFactory(new PropertyValueFactory<>("id"));
	    time.setCellValueFactory(new PropertyValueFactory<>("time"));
	    type.setCellValueFactory(new PropertyValueFactory<>("type"));
	    counter.setCellValueFactory(new PropertyValueFactory<>("counter"));
	    client.setCellValueFactory(new PropertyValueFactory<>("client"));
	    stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
	    record.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	private void showReceipt(String id) {
		// TODO 自动生成的方法存根
		ReceiptVO vo=null;
		try {
			vo=RemoteHelper.getInstance().getFormblService().showDetailReceipt(id);
		} catch (RemoteException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		int type=vo.getReceiptType();
		if(type==31||type==32||type==41||type==42||type==11||type==12||type==13||type==14){
			ShowReceiptUIController show=new ShowReceiptUIController();
			show.showReceipt(vo);
		}else if(type==21||type==22){
			CollectAndPayDetailController cap=new CollectAndPayDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(CollectAndPayDetailController.class.getResource("CollectAndPayDetail.fxml"));
			try {
				pane=loader.load();
				cap=(CollectAndPayDetailController)loader.getController();
				cap.init((FinanceReceiptVO)vo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}else if(type==23){
			CashDetailController ca=new CashDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(CashDetailController.class.getResource("CashDetail.fxml"));
			try {
				pane=loader.load();
				ca=(CashDetailController)loader.getController();
				ca.init((FinanceCashReceiptVO)vo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
	}

	@FXML public void setSale(){receipttype.setText("销售类单据");}
	@FXML public void setPurchase(){receipttype.setText("进货类单据");}
	@FXML public void setFinance(){receipttype.setText("财务类单据");}
	@FXML public void setStock(){receipttype.setText("库存类单据");}
	
	@FXML
	public void query(){
		if(beginTimePicker.getValue()!=null&&endTimePicker.getValue()!=null) {
	        String begin = beginTimePicker.getValue().toString();
	        String end = endTimePicker.getValue().toString();
	        Calendar beginCal = Calendar.getInstance();
	        Calendar endCal = Calendar.getInstance();
	        try {
	            beginCal.setTime(format.parse(begin));
	            endCal.setTime(format.parse(end));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        try {
	        	int i=0;
	        	if(receipttype.getText().equals("销售类单据"))
	        		i=1;
	        	else if(receipttype.getText().equals("进货类单据"))
	        		i=2;
	        	else if(receipttype.getText().equals("财务类单据"))
	        		i=3;
	        	else
	        		i=4;
	        	String condition=(i+" ")+(counterSearch.getText().equals("")?"null ":(counterSearch.getText()+" "))+(clientSearch.getText().equals("")?"null ":(clientSearch.getText()+" "))+(stockSearch.getText().equals("")?"null":stockSearch.getText());
	            ArrayList<ReceiptVO> list=formblService.recordList(beginCal, endCal, condition);
	            if(list.isEmpty()){
	            	System.out.println("wrong");
	            	return;
	            }
	            receiptsList.clear();
	            for(ReceiptVO r:list)
	            	receiptsList.add(new FormReceipt(r));
	            record.setItems(receiptsList);
	        } catch (RemoteException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	@FXML
	public void export(){
		ArrayList<FormReceipt> list=new ArrayList<FormReceipt>();
		if(record.getSelectionModel().getSelectedItems().isEmpty()){
			Alert alert=new Alert(Alert.AlertType.WARNING,"未选定任何信息");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		else
			for(FormReceipt r:record.getSelectionModel().getSelectedItems())
				list.add(r);
		
		//由用户选择记录的保存路径
		FileChooser chooser=new FileChooser();
		chooser.setTitle("选择导出路径");
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("XLS files(*.xls)","*.xls");
		chooser.getExtensionFilters().add(filter);
		File file=chooser.showSaveDialog(new Stage());//file即为保存数据的目标对象
		
		if(file!=null){
			try {
				WritableWorkbook wwb=Workbook.createWorkbook(new FileOutputStream(file));
				WritableSheet sheet=wwb.createSheet("经营历程表", 0);
				jxl.write.Label idC=new jxl.write.Label(0,0,"单据编号");
				jxl.write.Label timeC=new jxl.write.Label(1,0,"时间");
				jxl.write.Label typeC=new jxl.write.Label(2,0,"类型");
				jxl.write.Label clientC=new jxl.write.Label(3,0,"客户");
				jxl.write.Label counterC=new jxl.write.Label(4,0,"业务员");
				jxl.write.Label stockC=new jxl.write.Label(5,0,"仓库");
				sheet.addCell(idC);
				sheet.addCell(timeC);
				sheet.addCell(typeC);
				sheet.addCell(clientC);
				sheet.addCell(counterC);
				sheet.addCell(stockC);
				
				if(list!=null)
					for(int i=0;i<list.size();i++){
						jxl.write.Label id=new jxl.write.Label(0,i+1,list.get(i).getId());
						jxl.write.Label time=new jxl.write.Label(1,i+1,list.get(i).getTime());
						jxl.write.Label type=new jxl.write.Label(2,i+1,list.get(i).getType());
						jxl.write.Label client=new jxl.write.Label(3,i+1,list.get(i).getClient());
						jxl.write.Label counter=new jxl.write.Label(4,i+1,list.get(i).getCounter());
						jxl.write.Label stock=new jxl.write.Label(5,i+1,list.get(i).getStock());
						
						sheet.addCell(id);
						sheet.addCell(time);
						sheet.addCell(type);
						sheet.addCell(client);
						sheet.addCell(counter);
						sheet.addCell(stock);
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
			
			ClientRunner.getLogger().record("导出经营历程表"+file.getPath());
		}
	}
	
	@FXML
	public void offset(){
		FormReceipt r=record.getSelectionModel().getSelectedItem();
		if(r.getType().equals("现金费用单")||r.getType().equals("收款单")||r.getType().equals("付款单")){
			try {
				if(RemoteHelper.getInstance().getFormblService().offset(r.getId())){
					Alert success=new Alert(Alert.AlertType.INFORMATION,"红冲成功!");
					success.setHeaderText(null);
					success.showAndWait();
				}
				else{
					Alert fail=new Alert(Alert.AlertType.INFORMATION,"红冲失败!");
					fail.setHeaderText(null);
					fail.showAndWait();
					return;
				}
			} catch (RemoteException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else{
			Alert fail=new Alert(Alert.AlertType.INFORMATION,"此单据不可被红冲!");
			fail.setHeaderText(null);
			fail.showAndWait();
			return;
		}
	}
	
	@FXML
	public void offsetAndCopy(){
//		if(ClientRunner.getUserLevel()==1){
//			Alert fail=new Alert(Alert.AlertType.INFORMATION,"权限不足!");
//			fail.setHeaderText(null);
//			fail.showAndWait();
//			return;
//		}
		FormReceipt r=record.getSelectionModel().getSelectedItem();
		ReceiptVO receipt=null;
		try {
			receipt = formblService.showDetailReceipt(r.getId());
		} catch (RemoteException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		if(r.getType().equals("收款单")||r.getType().equals("付款单")){
			OffsetAndCopyFinanceController ocfc=new OffsetAndCopyFinanceController();
			AnchorPane pane1=null;
			FXMLLoader loader1=new FXMLLoader();
			loader1.setLocation(OffsetAndCopyFinanceController.class.getResource("offsetAndCopyFinanceUI.fxml"));
			try {
				pane1=loader1.load();
				ocfc=(OffsetAndCopyFinanceController)loader1.getController();
				ocfc.init((FinanceReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage1=new Stage();
			Scene scene1=new Scene(pane1);
			stage1.setScene(scene1);
			stage1.show();
		}
		else if(r.getType().equals("现金费用单")){
			OffsetAndCopyCashController ocfc=new OffsetAndCopyCashController();
			AnchorPane pane1=null;
			FXMLLoader loader1=new FXMLLoader();
			loader1.setLocation(OffsetAndCopyCashController.class.getResource("offsetAndCopyCashUI.fxml"));
			try {
				pane1=loader1.load();
				ocfc=(OffsetAndCopyCashController)loader1.getController();
				ocfc.init((FinanceCashReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage1=new Stage();
			Scene scene1=new Scene(pane1);
			stage1.setScene(scene1);
			stage1.show();
		}
		else{
			Alert fail=new Alert(Alert.AlertType.INFORMATION,"此单据不可被红冲!");
			fail.setHeaderText(null);
			fail.showAndWait();
			return;
		}
	}
}

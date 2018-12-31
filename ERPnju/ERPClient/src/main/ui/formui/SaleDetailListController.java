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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import main.vo.OutGoodsVO;
import main.vo.SaleFormLineItem;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;


public class SaleDetailListController implements Initializable{
    @FXML private JFXDatePicker beginTimePicker;
    @FXML private JFXDatePicker endTimePicker;
    @FXML private Button query;
    @FXML TableColumn<SaleGoods,String> name;
    @FXML TableColumn<SaleGoods,String> time;
    @FXML TableColumn<SaleGoods,String> xh;
    @FXML TableColumn<SaleGoods,Double> num;
    @FXML TableColumn<SaleGoods,Double> price;
    @FXML TableColumn<SaleGoods,Double> total;
    @FXML TableView<SaleGoods> saleList;
    @FXML private JFXTextField goodsname;
    @FXML private JFXTextField counter;
    @FXML private JFXTextField client;
    @FXML private JFXTextField stock;
    @FXML private Button output;

	private FormblService formblService;
	private BookblService bookblService;
	private final String pattern = "yyyy-MM-dd";
	private SimpleDateFormat format;
	private StringConverter<LocalDate> converter;
	ObservableList<SaleGoods> goodsList=FXCollections.observableArrayList();
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		formblService=RemoteHelper.getInstance().getFormblService();
	    bookblService=RemoteHelper.getInstance().getBookblService();
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
		
	    name.setCellValueFactory(new PropertyValueFactory<SaleGoods,String>("name"));
	    time.setCellValueFactory(new PropertyValueFactory<SaleGoods,String>("time"));
	    xh.setCellValueFactory(new PropertyValueFactory<SaleGoods,String>("xh"));
	    num.setCellValueFactory(new PropertyValueFactory<SaleGoods,Double>("num"));
	    price.setCellValueFactory(new PropertyValueFactory<SaleGoods,Double>("price"));
	    total.setCellValueFactory(new PropertyValueFactory<SaleGoods,Double>("total"));
	    
	    saleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

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
	        	String condition=(goodsname.getText().equals("")?"null ":(goodsname.getText()+" "))+(counter.getText().equals("")?"null ":(counter.getText()+" "))+(client.getText().equals("")?"null ":(client.getText()+" "))+(stock.getText().equals("")?"null":stock.getText());
	        	ArrayList<SaleFormLineItem> list= formblService.saleList(beginCal, endCal, condition);
	        	goodsList.clear();
	        	for(SaleFormLineItem s:list){
	        		goodsList.add(new SaleGoods(s));
	        		System.out.println(s.getGoodName());
	        	}
	        	saleList.setItems(goodsList);
	        } catch (RemoteException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	@FXML
	public void export(){
		ArrayList<SaleGoods> list=new ArrayList<SaleGoods>();
		if(saleList.getSelectionModel().getSelectedItems().isEmpty()){
			Alert alert=new Alert(Alert.AlertType.WARNING,"未选定任何信息");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		else
			for(SaleGoods s:saleList.getSelectionModel().getSelectedItems())
				list.add(s);
		
		//由用户选择记录的保存路径
		FileChooser chooser=new FileChooser();
		chooser.setTitle("选择导出路径");
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("XLS files(*.xls)","*.xls");
		chooser.getExtensionFilters().add(filter);
		File file=chooser.showSaveDialog(new Stage());//file即为保存数据的目标对象
		
		if(file!=null){
			try {
				WritableWorkbook wwb=Workbook.createWorkbook(new FileOutputStream(file));
				WritableSheet sheet=wwb.createSheet("销售明细表", 0);
				jxl.write.Label nameC=new jxl.write.Label(0,0,"商品名");
				jxl.write.Label timeC=new jxl.write.Label(1,0,"变动时间");
				jxl.write.Label xhC=new jxl.write.Label(2,0,"型号");
				jxl.write.Label numC=new jxl.write.Label(3,0,"变动数量");
				jxl.write.Label priceC=new jxl.write.Label(4,0,"售价");
				jxl.write.Label totalC=new jxl.write.Label(5,0,"总额");
				sheet.addCell(nameC);
				sheet.addCell(timeC);
				sheet.addCell(xhC);
				sheet.addCell(numC);
				sheet.addCell(priceC);
				sheet.addCell(totalC);
				
				if(list!=null)
					for(int i=0;i<list.size();i++){
						jxl.write.Label name=new jxl.write.Label(0,i+1,list.get(i).getName());
						jxl.write.Label time=new jxl.write.Label(1,i+1,list.get(i).getTime());
						jxl.write.Label xh=new jxl.write.Label(2,i+1,list.get(i).getXh());
						jxl.write.Number num=new jxl.write.Number(3,i+1,list.get(i).getNum());
						jxl.write.Number price=new jxl.write.Number(4,i+1,list.get(i).getPrice());
						jxl.write.Number total=new jxl.write.Number(5,i+1,list.get(i).getTotal());
						
						sheet.addCell(name);
						sheet.addCell(time);
						sheet.addCell(xh);
						sheet.addCell(num);
						sheet.addCell(price);
						sheet.addCell(total);
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
			
			ClientRunner.getLogger().record("导出销售明细表"+file.getPath());
		}
	}
	
	@FXML
	public void detailPie(){
		ObservableList<SaleGoods> list=saleList.getSelectionModel().getSelectedItems();
		if(list.isEmpty())
			return;
		else{
			PieChart pie=new PieChart();
			ObservableList<Data> answer=FXCollections.observableArrayList();
			for(SaleGoods g:list){
				answer.add(new Data(g.getName(),g.getNum()));
			}
			pie.setData(answer);
			Stage stage=new Stage();
			Scene scene=new Scene(pie);
			stage.setScene(scene);
			stage.setTitle("详细信息");
			stage.show();
		}
	}
}

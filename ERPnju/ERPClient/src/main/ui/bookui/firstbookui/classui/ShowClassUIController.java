package main.ui.bookui.firstbookui.classui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.bookui.firstbookui.goodsui.GoodsUIController;
import main.ui.bookui.firstbookui.goodsui.ShowGoodsController;
import main.vo.ClassVO;
import main.vo.OutGoodsVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ShowClassUIController {

	@FXML public Label className,classId,sub,father;
	@FXML public TableView<Node<String>> subList;
	@FXML public TableColumn<Node<String>, Integer> row;
	@FXML public TableColumn<Node<String>, String> id;
	
	private Stage stage;
	private ClassVO obj;
	private ClassUIController classcontr;
	private StockblService sbs=RemoteHelper.getInstance().getStockblService();
	
	public void init(Stage s,ClassVO c){
		stage=s;
		obj=c;
		//显示分类id、分类名、父类id
		if(obj==null){
			return;
		}
		className.setText(obj.getName());
		classId.setText(obj.getId());
		father.setText(obj.getFather());
		
		//显示子分类列表或者类下商品列表
		row.setCellValueFactory(new PropertyValueFactory<Node<String>,Integer>("row"));
		id.setCellValueFactory(new PropertyValueFactory<Node<String>,String>("name"));
		
		if(obj.getSubgoods()==null||obj.getSubgoods().size()==0){
			sub.setText("子分类");
			if(obj.getSubclasses()!=null){
				ArrayList<Node<String>>nodes=new ArrayList<>();
				for(int i=0;i<obj.getSubclasses().size();i++){
					ClassVO cvo;
					try {
						cvo = sbs.showClass(obj.getSubclasses().get(i));
						Node<String> n=new Node<>(obj.getSubclasses().get(i),cvo.getName(),i+1);
						nodes.add(n);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				ObservableList<Node<String>>list=FXCollections.observableArrayList(nodes);
				subList.setItems(list);
			}
		}else{
			sub.setText("类下商品");
			if(obj.getSubgoods()!=null){
				ArrayList<Node<String>>nodes=new ArrayList<>();
				for(int i=0;i<obj.getSubgoods().size();i++){
					OutGoodsVO gvo;
					try {
						gvo = sbs.getGoods(obj.getSubgoods().get(i));
						Node<String> n=new Node<>(obj.getSubgoods().get(i),gvo.getName(),i+1);
						nodes.add(n);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				ObservableList<Node<String>>list=FXCollections.observableArrayList(nodes);
				subList.setItems(list);
			}
		}
		
		//给列表添加鼠标双击事件
		subList.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)//左键点击2次
					&&me.getClickCount()==2){
				Node<String> n=subList.getSelectionModel().getSelectedItem();
				if(sub.getText().equals("子分类")){
					//显示子分类
					ClassVO cvo=null;
					try {
						if(n!=null)
							cvo=RemoteHelper.getInstance().getStockblService().showClass(n.getId());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					if(cvo!=null){
						classcontr.loadShowClassUI(cvo);
						ClientRunner.getLogger().record("查看商品分类"+cvo.getName());
					}
				}else{
					//显示商品
					OutGoodsVO gvo=null;
					if(n!=null)
						try {
							gvo=RemoteHelper.getInstance().getStockblService().getGoods(n.getId());
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if(gvo!=null){
						showGoods(gvo);
						ClientRunner.getLogger().record("查看商品"+gvo.getName());
					}
				}
			}
		});
	}
	
	/**
	 * 
	 * @param obj 要显示的商品
	 */
	private void showGoods(OutGoodsVO obj){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(GoodsUIController.class.getResource("ShowGoods.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			ShowGoodsController sgc=(ShowGoodsController)loader.getController();
			Scene scene=new Scene(ap);
			Stage s=new Stage();
			s.setScene(scene);
			sgc.init(s, obj);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onOK(){
		stage.close();
	}
	
	public void setClassContr(ClassUIController con){
		classcontr=con;
	}
	
	/**
	 * 列表节点
	 * 不包装起来无法显示在table view中！
	 * 
	 * 往Table View中添加数据时，应用ObservableList，这样可以实现数据的实时更新；然后，ObservableList应该装的是Bean，
	 * Bean与column建立映射，因此，column的名字应该与Bean的属性一致！！！
	 * 
	 * @author 17678
	 *
	 * @param <T>
	 */
	public class Node<T>{
		private T id;
		private int row=0;
		private T name;
		public Node(T t,T n,int i){
			id=t;
			name=n;
			row=i;
		}
		public void setId(T t){
			id=t;
		}
		public T getId(){
			return id;
		}
		public void setRow(int i){
			row=i;
		}
		public int getRow(){
			return row;
		}
		public void setName(T t){
			name=t;
		}
		public T getName(){
			return name;
		}
	}
}

package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Entity.Cars;
import sample.Entity.CatalogOfModels;
import sample.Entity.Employee;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CarsTabController implements Initializable {

    @FXML
    private TextField carIDTextField;
    @FXML
    private ComboBox<Long> IDKMComboBox;
    @FXML
    private ComboBox<String> localizationComboBox;
    @FXML
    Button insertButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    @FXML
    Button refreshButton;
    @FXML
    TableView<Cars> tvCars;
    @FXML
    TableColumn<Cars,Long> colCarID;
    @FXML
    TableColumn<Cars,Long> colIDKM;
    @FXML
    TableColumn<Cars,String> colLocOfCar;
    @FXML
    TableColumn<Cars,String> colModel;


    ObservableList<String> localizationList = FXCollections.observableArrayList("WRO","KRK");

    //Controllers
    private DBController dbController;

    public void injectDBController(DBController dbController){
        this.dbController = dbController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localizationComboBox.setItems(localizationList);
    }

    @FXML
    public void insertRecord(){
        String query = "insert into V_EGZEMPLARZE(ID_KM, LOKALIZACJA_EGZEMPLARZA) values("+IDKMComboBox.getSelectionModel().getSelectedItem()+",'"+localizationComboBox.getSelectionModel().getSelectedItem()+"')";
        dbController.executeQuery(query);
        showCars();
    }

    @FXML
    public void updateRecord(){
        String query = "update V_EGZEMPLARZE set ID_KM ="+IDKMComboBox.getSelectionModel().getSelectedItem()+",LOKALIZACJA_EGZEMPLARZA='"+localizationComboBox.getSelectionModel().getSelectedItem()+"' "
                +" where ID_EGZEMPLARZA="+carIDTextField.getText()+"";
        dbController.executeQuery(query);
        showCars();
    }

    @FXML
    public void deleteRecord(){
        String query = "delete from v_egzemplarze where ID_EGZEMPLARZA="+carIDTextField.getText()+" ";
        dbController.executeQuery(query);
        showCars();
    }

    public void refreshOnClick(){
        dbController.executeQuery("commit");
        showCars();
        localizationComboBox.setItems(localizationList);
        IDKMComboBox.setItems(getIDKMindexes());
        carIDTextField.setText("");
        localizationComboBox.getSelectionModel().clearSelection();
    }
    
    public ObservableList<Cars> getCarList(){
        ObservableList<Cars> carList = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT v_Egzemplarze.ID_EGZEMPLARZA,Katalog_modeli.MODEL, v_Egzemplarze.ID_KM , v_Egzemplarze.LOKALIZACJA_EGZEMPLARZA FROM v_Egzemplarze INNER JOIN Katalog_modeli ON v_Egzemplarze.ID_KM=Katalog_modeli.ID_KM";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Cars car;
            while (rs.next()){
                car = new Cars(rs.getLong("ID_EGZEMPLARZA"),rs.getString("MODEL"),rs.getLong("ID_KM"),rs.getString("LOKALIZACJA_EGZEMPLARZA"));
                carList.add(car);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return carList;
    }

    public ObservableList<Long> getIDKMindexes(){
        ObservableList<Long> idkmIndexes = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT ID_KM FROM KATALOG_MODELI";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                idkmIndexes.add(rs.getLong("ID_KM"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return idkmIndexes;
    }


    public void showCars(){
        ObservableList<Cars> list = getCarList();
        colCarID.setCellValueFactory(new PropertyValueFactory<Cars,Long>("carID"));
        colModel.setCellValueFactory(new PropertyValueFactory<Cars,String>("carModel"));
        colIDKM.setCellValueFactory(new PropertyValueFactory<Cars,Long>("ID_KM"));
        colLocOfCar.setCellValueFactory(new PropertyValueFactory<Cars,String>("locOfCar"));
        tvCars.setItems(list);

    }
    
    @FXML
    private void handleMouseAction(){
        Cars car = tvCars.getSelectionModel().getSelectedItem();
        String loc = car.getLocOfCar();
        Long idkm = car.getID_KM();
        carIDTextField.setText(car.getCarID().toString());
        localizationComboBox.getSelectionModel().select(loc);
        IDKMComboBox.getSelectionModel().select(idkm);
    }
    
    
}

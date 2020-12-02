package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Entity.Cars;
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
    private ComboBox<?> IDKMComboBox;
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

    public void refreshOnClick(){
        dbController.executeQuery("commit");
        showCars();
    }
    
    public ObservableList<Cars> getCarList(){
        ObservableList<Cars> carList = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT Egzemplarze.ID_EGZEMPLARZA,Katalog_modeli.MODEL, Egzemplarze.ID_KM , Egzemplarze.LOKALIZACJA_EGZEMPLARZA FROM Egzemplarze INNER JOIN Katalog_modeli ON Egzemplarze.ID_KM=Katalog_modeli.ID_KM";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(1));
            System.out.println(rsmd.getColumnName(2));
            System.out.println(rsmd.getColumnName(3));
            System.out.println(rsmd.getColumnName(4));
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


    public void showCars(){
        ObservableList<Cars> list = getCarList();
        colCarID.setCellValueFactory(new PropertyValueFactory<Cars,Long>("carID"));
        colModel.setCellValueFactory(new PropertyValueFactory<Cars,String>("carModel"));
        colIDKM.setCellValueFactory(new PropertyValueFactory<Cars,Long>("ID_KM"));
        colLocOfCar.setCellValueFactory(new PropertyValueFactory<Cars,String>("locOfCar"));
        tvCars.setItems(list);

    }
    
    
}

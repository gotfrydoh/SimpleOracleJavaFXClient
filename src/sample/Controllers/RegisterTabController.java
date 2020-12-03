package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Entity.Cars;
import sample.Entity.Register;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class RegisterTabController implements Initializable {

    @FXML
    private TextField regIdTextField;
    @FXML
    private ComboBox<Long> peselComboBox;
    @FXML
    private ComboBox<Long> empComboBox;
    @FXML
    private ComboBox<Long> carComboBox;
    @FXML
    private DatePicker dateFromDP;
    @FXML
    private DatePicker dateToDP;
    @FXML
    private ComboBox<String> locComboBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private TableView<Register> tvRegisters;
    @FXML
    private TableColumn<Register,Long> colId;
    @FXML
    private TableColumn<Register,Long> colPesel;
    @FXML
    private TableColumn<Register,Long> colEmp;
    @FXML
    private TableColumn<Register,Long> colCar;
    @FXML
    private TableColumn<Register, String> colDateFrom;
    @FXML
    private TableColumn<Register, String> colDateTo;
    @FXML
    private TableColumn<Register,String> colLoc;
    @FXML
    private TableColumn<Register,Long> colPrice;
    @FXML
    private Button refreshButton;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;


    //controllers
    private DBController dbController;

    public void injectDBController(DBController dbController){
        this.dbController = dbController;
    }

    ObservableList<String> localizationList = FXCollections.observableArrayList("WRO","KRK");
    ObservableList<Long> peselList;
    ObservableList<Long> empIdList;
    ObservableList<Long> carIdList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locComboBox.setItems(localizationList);
    }

    @FXML
    public void insertRecord(){
        String query = "insert into v_rezerwacja(PESEL,ID_PRACOWNIKA, ID_EGZEMPLARZA,DATA_OD,DATA_DO,LOKALIZACJA_ODBIORU,CENA_CALKOWITA) values("+peselComboBox.getSelectionModel().getSelectedItem()+","+empComboBox.getSelectionModel().getSelectedItem()+
                ","+carComboBox.getSelectionModel().getSelectedItem()+",'"+dateFromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"','"+dateToDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"','"+
                locComboBox.getSelectionModel().getSelectedItem()+"',"+priceTextField.getText()+") ";
        dbController.executeQuery(query);
        showRegisters();
    }

    @FXML
    public void updateRecord(){
        String query = "update V_REZERWACJA set PESEL="+peselComboBox.getSelectionModel().getSelectedItem()+",ID_PRACOWNIKA="+empComboBox.getSelectionModel().getSelectedItem()+",ID_EGZEMPLARZA="+carComboBox.getSelectionModel().getSelectedItem()+
                ",DATA_OD='"+dateFromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"',DATA_DO='"+dateToDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"',LOKALIZACJA_ODBIORU='"+locComboBox.getSelectionModel().getSelectedItem()+"',CENA_CALKOWITA="+priceTextField.getText()+"" +
                "where ID_REZERWACJI="+regIdTextField.getText()+" ";
        dbController.executeQuery(query);
        showRegisters();
    }


    @FXML
    public void deleteRecord(){
        String query = "delete from v_rezerwacja where ID_REZERWACJI="+regIdTextField.getText()+" ";
        dbController.executeQuery(query);
        showRegisters();
    }


    public void refreshOnClick(){
        dbController.executeQuery("commit");
        peselList=getIndexes("PESEL","V_KLIENT");
        empIdList=getIndexes("ID_PRACOWNIK","PRACOWNIK");
        carIdList=getIndexes("ID_EGZEMPLARZA","V_EGZEMPLARZE");
        peselComboBox.setItems(peselList);
        empComboBox.setItems(empIdList);
        carComboBox.setItems(carIdList);
        regIdTextField.setText("");
        priceTextField.setText("");
        dateFromDP.setValue(null);
        dateToDP.setValue(null);
        peselComboBox.getSelectionModel().clearSelection();
        empComboBox.getSelectionModel().clearSelection();
        carComboBox.getSelectionModel().clearSelection();
        locComboBox.getSelectionModel().clearSelection();
        showRegisters();

    }

    public void handleTotalPriceClick(){
        Long carId = carComboBox.getSelectionModel().getSelectedItem();
        Long pricePerDay=0L;
        String query = "SELECT Katalog_modeli.cena_za_dobe " +
                "FROM v_egzemplarze " +
                "INNER JOIN Katalog_modeli ON v_Egzemplarze.ID_KM=Katalog_modeli.ID_KM " +
                "where v_egzemplarze.id_egzemplarza="+carId;
        Connection conn = dbController.getConnection();
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                pricePerDay = rs.getLong("CENA_ZA_DOBE");
            }
            String dateStart = dateFromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String dateStop = dateToDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            priceTextField.setText(Long.toString(countTotalPrize(dateStart,dateStop,pricePerDay)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Long countTotalPrize(String dateFrom, String dateTo, Long pricePerDay){
        LocalDate localDateFrom = LocalDate.parse(dateFrom);
        LocalDate localDateTo = LocalDate.parse(dateTo);
        Period period = Period.between(localDateFrom,localDateTo);
        int days = period.getDays();
        if(days>0){
            return (days*pricePerDay);
        }else{
            return 0L;
        }
    }

    @FXML
    private void handleMouseAction(){
        Register reg = tvRegisters.getSelectionModel().getSelectedItem();
        regIdTextField.setText(reg.getRegId().toString());
        priceTextField.setText(reg.getTotalPrice().toString());
        peselComboBox.getSelectionModel().select(reg.getPesel());
        empComboBox.getSelectionModel().select(reg.getEmpId());
        carComboBox.getSelectionModel().select(reg.getCarId());
        locComboBox.getSelectionModel().select(reg.getPickupLoc());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(reg.getDateFrom(), formatter);
        LocalDate date2 = LocalDate.parse(reg.getDateTo(), formatter);
        dateFromDP.setValue(date1);
        dateToDP.setValue(date2);

    }



    public ObservableList<Register> getRegisterList(){
        ObservableList<Register> registerList = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT * FROM V_REZERWACJA";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Register reg;
            while (rs.next()){
                reg = new Register(rs.getLong("ID_REZERWACJI"),rs.getLong("PESEL"),rs.getLong("ID_PRACOWNIKA"), rs.getLong("ID_EGZEMPLARZA"),rs.getString("DATA_OD").substring(0,10),rs.getString("DATA_DO").substring(0,10),rs.getString("LOKALIZACJA_ODBIORU"),rs.getLong("CENA_CALKOWITA"));
                registerList.add(reg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return registerList;
    }

    public void showRegisters(){
        ObservableList<Register> list = getRegisterList();
        colId.setCellValueFactory(new PropertyValueFactory<Register,Long>("regId"));
        colPesel.setCellValueFactory(new PropertyValueFactory<Register,Long>("pesel"));
        colEmp.setCellValueFactory(new PropertyValueFactory<Register,Long>("empId"));
        colCar.setCellValueFactory(new PropertyValueFactory<Register,Long>("carId"));
        colDateFrom.setCellValueFactory(new PropertyValueFactory<Register, String>("dateFrom"));
        colDateTo.setCellValueFactory(new PropertyValueFactory<Register,String>("dateTo"));
        colLoc.setCellValueFactory(new PropertyValueFactory<Register,String>("pickupLoc"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Register,Long>("totalPrice"));
        tvRegisters.setItems(list);

    }

    public ObservableList<Long> getIndexes(String column, String table){
        ObservableList<Long> indexes = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT "+column+" FROM "+table;
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                indexes.add(rs.getLong(column));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return indexes;
    }

}

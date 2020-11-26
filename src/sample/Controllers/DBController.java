package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.events.MouseEvent;
import sample.Entity.Customer;
import sample.Entity.DBUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBController {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TextField peselTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField adressTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField localizationTextField;
    @FXML
    private TableView<Customer> tvCustomers;
    @FXML
    private TableColumn<Customer,Long> colPesel;
    @FXML
    private TableColumn<Customer,String> colName;
    @FXML
    private TableColumn<Customer,String> colLastName;
    @FXML
    private TableColumn<Customer,Long> colPhone;
    @FXML
    private TableColumn<Customer,String> colAdress;
    @FXML
    private TableColumn<Customer,String> colZipCode;
    @FXML
    private TableColumn<Customer,String> colCountry;
    @FXML
    private TableColumn<Customer,String> colLocalization;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private AnchorPane employeeTab;

    //injecting controllers
    @FXML
    private EmployeeController employeeTabController;


    public void initialize(){
        employeeTabController.injectDBController(this);
    }


    @FXML
    private void insertRecord(){
        String query = "insert into V_KLIENT values ("+peselTextField.getText()+",'"+nameTextField.getText()+"','"+lastNameTextField.getText()+
                "',"+phoneTextField.getText()+",'"+adressTextField.getText()+"','"+zipCodeTextField.getText()+"','"+countryTextField.getText()+"','"+localizationTextField.getText()+"')";
        executeQuery(query);
        showCustomers();
    }


    @FXML
    private void updateRecord(){
        String query = "update V_KLIENT set IMIE ='"+nameTextField.getText()+"',NAZWISKO='"+lastNameTextField.getText()+"', NR_TEL="+phoneTextField.getText()+", ADRES='"+
                adressTextField.getText()+"', KOD_POCZTOWY='"+zipCodeTextField.getText()+"', PANSTWO='"+countryTextField.getText()+"',LOKALIZACJA_REJESTRACJI='"+localizationTextField.getText()+
                "' where PESEL="+peselTextField.getText()+"";
        executeQuery(query);
        showCustomers();
    }

    @FXML
    private void deleteRecord(){
        String query = "delete from v_klient where PESEL="+peselTextField.getText()+" ";
        executeQuery(query);
        showCustomers();
    }

    @FXML
    private void handleMouseAction(){
        Customer customer = tvCustomers.getSelectionModel().getSelectedItem();
        peselTextField.setText(customer.getPESEL().toString());
        nameTextField.setText(customer.getName());
        lastNameTextField.setText(customer.getLastName());
        phoneTextField.setText(customer.getPhoneNumber().toString());
        adressTextField.setText(customer.getAdress());
        zipCodeTextField.setText(customer.getZipCode());
        countryTextField.setText(customer.getCountry());
        localizationTextField.setText(customer.getLocalizationOfRegistration());
    }

    @FXML
    public void refreshOnClick(){
        executeQuery("commit");
        showCustomers();
        peselTextField.setText("");
        nameTextField.setText("");
        lastNameTextField.setText("");
        phoneTextField.setText("");
        adressTextField.setText("");
        zipCodeTextField.setText("");
        countryTextField.setText("");
        localizationTextField.setText("");
    }


    public Connection getConnection(){
        Connection conn;
        Stage stage = (Stage) insertButton.getScene().getWindow();
        DBUser u = (DBUser) stage.getUserData();
        String username = u.getUsername();
        String password = u.getPassword();
        String sid = u.getSid();
        String port = u.getPort();
        String hostname =  u.getHostname();

        try{
            conn = DriverManager.getConnection("jdbc:oracle:thin:@"+hostname+":"+port+":"+sid,username,password);
            return conn;
        }catch(Exception e){
            return null;
        }
}

    public ObservableList<Customer> getCustomersList(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM V_KLIENT";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Customer customer;
            while (rs.next()){
                customer = new Customer(rs.getLong("PESEL"),rs.getString("IMIE"),rs.getString("NAZWISKO"), rs.getLong("NR_TEL"),
                        rs.getString("ADRES"), rs.getString("KOD_POCZTOWY"), rs.getString("PANSTWO"), rs.getString("LOKALIZACJA_REJESTRACJI"));
                customerList.add(customer);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return customerList;
    }

    public void showCustomers(){
        ObservableList<Customer> list = getCustomersList();
        colPesel.setCellValueFactory(new PropertyValueFactory<Customer,Long>("PESEL"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Customer,Long>("phoneNumber"));
        colAdress.setCellValueFactory(new PropertyValueFactory<Customer,String>("adress"));
        colZipCode.setCellValueFactory(new PropertyValueFactory<Customer,String>("zipCode"));
        colCountry.setCellValueFactory(new PropertyValueFactory<Customer,String>("country"));
        colLocalization.setCellValueFactory(new PropertyValueFactory<Customer,String>("localizationOfRegistration"));

        tvCustomers.setItems(list);
    }


    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeQuery(query);
            st.executeQuery("commit");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}

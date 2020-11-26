package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Entity.Customer;
import sample.Entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeController {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TextField idEmployeeTextField;
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
    private TextField localizationOfWorkTextField;
    @FXML
    private TableView<Employee> tvEmployee;
    @FXML
    private TableColumn<Employee,Long> colId;
    @FXML
    private TableColumn<Employee,String> colName;
    @FXML
    private TableColumn<Employee,String> colLastName;
    @FXML
    private TableColumn<Employee,Long> colPhone;
    @FXML
    private TableColumn<Employee,String> colAdress;
    @FXML
    private TableColumn<Employee,String> colZipCode;
    @FXML
    private TableColumn<Employee,String> colLocOfWork;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;

    //Controllers
    private DBController dbController;

    public void injectDBController(DBController dbController){
        this.dbController = dbController;
    }

    @FXML
    public void insertRecord(){
        String query = "INSERT INTO PRACOWNIK(IMIE, NAZWISKO,NR_TEL,ADRES,KOD_POCZTOWY, LOKALIZACJA_ZATRUDNIENIA) VALUES('"+nameTextField.getText()+"','"+lastNameTextField.getText()+
                "',"+phoneTextField.getText()+",'"+adressTextField.getText()+"','"+zipCodeTextField.getText()+"','"+localizationOfWorkTextField.getText()+"')";
        dbController.executeQuery(query);
        showEmployees();
    }

    @FXML
    public void updateRecord(){
        String query = "update PRACOWNIK set IMIE ='"+nameTextField.getText()+"',NAZWISKO='"+lastNameTextField.getText()+"', NR_TEL="+phoneTextField.getText()+", ADRES='"+
                adressTextField.getText()+"', KOD_POCZTOWY='"+zipCodeTextField.getText()+"',LOKALIZACJA_ZATRUDNIENIA='"+localizationOfWorkTextField.getText()+
                "' where ID_PRACOWNIK="+idEmployeeTextField.getText()+"";
        dbController.executeQuery(query);
        showEmployees();
    }

    @FXML
    public void deleteRecord(){
        String query = "delete from pracownik where ID_PRACOWNIK="+idEmployeeTextField.getText()+" ";
        dbController.executeQuery(query);
        showEmployees();
    }


    @FXML
    public void refreshOnClick(){
        dbController.executeQuery("commit");
        showEmployees();
        idEmployeeTextField.setText("");
        nameTextField.setText("");
        lastNameTextField.setText("");
        phoneTextField.setText("");
        adressTextField.setText("");
        zipCodeTextField.setText("");
        localizationOfWorkTextField.setText("");
    }

    @FXML
    private void handleMouseAction(){
        Employee employee = tvEmployee.getSelectionModel().getSelectedItem();
        idEmployeeTextField.setText(employee.getId().toString());
        nameTextField.setText(employee.getName());
        lastNameTextField.setText(employee.getLastName());
        phoneTextField.setText(employee.getPhoneNumber().toString());
        adressTextField.setText(employee.getAdress());
        zipCodeTextField.setText(employee.getZipCode());
        localizationOfWorkTextField.setText(employee.getLocalizationOfWork());
    }

    public ObservableList<Employee> getEmployeeList(){
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT * FROM PRACOWNIK";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Employee employee;
            while (rs.next()){
                employee = new Employee(rs.getLong("ID_PRACOWNIK"),rs.getString("IMIE"),rs.getString("NAZWISKO"), rs.getLong("NR_TEL"),
                        rs.getString("ADRES"), rs.getString("KOD_POCZTOWY"), rs.getString("LOKALIZACJA_ZATRUDNIENIA"));
                employeeList.add(employee);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return employeeList;
    }

    public void showEmployees(){
        ObservableList<Employee> list = getEmployeeList();
        colId.setCellValueFactory(new PropertyValueFactory<Employee,Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Employee,String>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Employee,Long>("phoneNumber"));
        colAdress.setCellValueFactory(new PropertyValueFactory<Employee,String>("adress"));
        colZipCode.setCellValueFactory(new PropertyValueFactory<Employee,String>("zipCode"));
        colLocOfWork.setCellValueFactory(new PropertyValueFactory<Employee,String>("localizationOfWork"));

        tvEmployee.setItems(list);

    }





}

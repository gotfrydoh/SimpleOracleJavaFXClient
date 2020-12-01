package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Entity.CatalogOfModels;
import sample.Entity.Customer;
import sample.Entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CatalogOfModelsController {

    @FXML
    private TextField idKMTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField dmcTextField;
    @FXML
    private TextField seatsTextField;
    @FXML
    private TextField specificInfoTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TableView<CatalogOfModels> tvCatalog;
    @FXML
    private TableColumn<CatalogOfModels,Long> colIDKM;
    @FXML
    private TableColumn<CatalogOfModels,String> colModel;
    @FXML
    private TableColumn<CatalogOfModels,String> colBrand;
    @FXML
    private TableColumn<CatalogOfModels,String> colCategory;
    @FXML
    private TableColumn<CatalogOfModels,Integer> colDMC;
    @FXML
    private TableColumn<CatalogOfModels,Integer> colSeats;
    @FXML
    private TableColumn<CatalogOfModels,String> colSpecificInfo;
    @FXML
    private TableColumn<CatalogOfModels,Integer> colPrice;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;


    //Controllers
    private DBController dbController;

    public void injectDBController(DBController dbController){
        this.dbController = dbController;
    }

    @FXML
    public void insertRecord(){
        String query = "insert into KATALOG_MODELI(MODEL, MARKA, KATEGORIA, DMC, LICZBA_OSOB, DANE_SZCZEGOLOWE, CENA_ZA_DOBE) values('"+modelTextField.getText()+"','"+brandTextField.getText()+
                "','"+categoryTextField.getText()+"',"+dmcTextField.getText()+","+seatsTextField.getText()+",'"+specificInfoTextField.getText()+"',"+priceTextField.getText()+")";
        dbController.executeQuery(query);
        showCatalog();
    }

    @FXML
    public void updateRecord(){
        String query = "update KATALOG_MODELI set MODEL ='"+modelTextField.getText()+"',MARKA='"+brandTextField.getText()+"', KATEGORIA='"+categoryTextField.getText()+"', DMC="+
                dmcTextField.getText()+", LICZBA_OSOB="+seatsTextField.getText()+",DANE_SZCZEGOLOWE='"+specificInfoTextField.getText()+"',CENA_ZA_DOBE="+priceTextField.getText()+
                " where ID_KM="+idKMTextField.getText()+"";
        dbController.executeQuery(query);
        showCatalog();
    }

    @FXML
    public void deleteRecord(){
        String query = "delete from katalog_modeli where ID_KM="+idKMTextField.getText()+" ";
        dbController.executeQuery(query);
        showCatalog();
    }


    public ObservableList<CatalogOfModels> getCatalogOfModelsList(){
        ObservableList<CatalogOfModels> catalogList = FXCollections.observableArrayList();
        Connection conn = dbController.getConnection();
        String query = "SELECT * FROM KATALOG_MODELI";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            CatalogOfModels catalog;
            while (rs.next()){
                catalog = new CatalogOfModels(rs.getLong("ID_KM"),rs.getString("MODEL"),rs.getString("MARKA"), rs.getString("KATEGORIA"),
                        rs.getInt("DMC"), rs.getInt("LICZBA_OSOB"), rs.getString("DANE_SZCZEGOLOWE"),rs.getInt("CENA_ZA_DOBE"));
                catalogList.add(catalog);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return catalogList;
    }


    public void showCatalog(){
        ObservableList<CatalogOfModels> list = getCatalogOfModelsList();
        colIDKM.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,Long>("idKM"));
        colModel.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,String>("model"));
        colBrand.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,String>("brand"));
        colCategory.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,String>("category"));
        colDMC.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,Integer>("dmc"));
        colSeats.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,Integer>("seats"));
        colSpecificInfo.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,String>("specificInfo"));
        colPrice.setCellValueFactory(new PropertyValueFactory<CatalogOfModels,Integer>("price"));
        tvCatalog.setItems(list);

    }


    public void refreshOnClick(){
        dbController.executeQuery("commit");
        showCatalog();
        idKMTextField.setText("");
        modelTextField.setText("");
        brandTextField.setText("");
        categoryTextField.setText("");
        dmcTextField.setText("");
        seatsTextField.setText("");
        specificInfoTextField.setText("");
        priceTextField.setText("");
    }

    @FXML
    private void handleMouseAction(){
        CatalogOfModels catalog = tvCatalog.getSelectionModel().getSelectedItem();
        idKMTextField.setText(catalog.getIdKM().toString());
        modelTextField.setText(catalog.getModel());
        brandTextField.setText(catalog.getBrand());
        categoryTextField.setText(catalog.getCategory());
        dmcTextField.setText(Integer.toString(catalog.getDmc()));
        seatsTextField.setText(Integer.toString(catalog.getSeats()));
        specificInfoTextField.setText(catalog.getSpecificInfo());
        priceTextField.setText(Integer.toString(catalog.getPrice()));
    }


}

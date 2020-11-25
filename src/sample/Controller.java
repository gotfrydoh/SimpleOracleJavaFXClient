package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Entity.DBUser;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField sidTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private TextField hostnameTextField;

    @FXML
    private Button connectButton;
    @FXML
    private AnchorPane sampleAnchorPane;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    //not used
    public boolean openDBWindow(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DBWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,785,515));
            stage.setTitle("Oracle Database Window");
            stage.show();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    public void connectOnClickMethod(ActionEvent event){

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String port = portTextField.getText();
        String sid = sidTextField.getText();
        String hostname = hostnameTextField.getText();

        if(username.equals("") || password.equals("") || port.equals("") || sid.equals("") || hostname.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Check the fields!");
            alert.showAndWait();
        }else{

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@"+hostname+":"+port+":"+sid,username,password);

                pst = con.prepareStatement("SELECT * FROM all_users where username=?");
                pst.setString(1,username.toUpperCase());
                rs = pst.executeQuery();

                if(rs.next()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Connection established!");
                    alert.showAndWait();

                    //passing user data to connect to database to DBController
                    DBUser userToPass = new DBUser();
                    userToPass.setUsername(username);
                    userToPass.setPassword(password);
                    userToPass.setHostname(hostname);
                    userToPass.setPort(port);
                    userToPass.setSid(sid);

                    Stage stage = (Stage) connectButton.getScene().getWindow();
                    stage.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DBWindow.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        stage.setUserData(userToPass);
                        Scene scene = new Scene(root1,1107,539);
                        stage.setScene(scene);
                        stage.setTitle("Oracle Database Window");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else if(!rs.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong!");
                    alert.showAndWait();
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong classnotfound-exc!");
                alert.showAndWait();
            }catch(SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong sql-exception!");
                alert.showAndWait();
            }

        }

    }


}


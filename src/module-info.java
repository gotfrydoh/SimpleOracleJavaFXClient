module OracleDBClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens sample;
    opens sample.Entity;
    opens sample.Controllers;
}
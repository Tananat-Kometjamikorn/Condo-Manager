package condo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class StartController {
    @FXML
    private Button btn_adminLogin, btn_officerLogin, btn_credit, btn_instruct;

    @FXML public void handleFuncOnAction(ActionEvent event) throws IOException {
        if (event.getSource() == btn_adminLogin) {
            Button a = (Button) event.getSource();
            Stage stage = (Stage) a.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/admin_login.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();

        } else if (event.getSource() == btn_officerLogin) {
            Button o = (Button) event.getSource();
            Stage stage = (Stage) o.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/officer_login.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();

        } else if (event.getSource() == btn_credit) {
            Button c = (Button) event.getSource();
            Stage stage = (Stage) c.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/credit.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();

        } else if (event.getSource() == btn_instruct){
            Button c = (Button) event.getSource();
            Stage stage = (Stage) c.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/instruction.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();
        }
    }

}

package condo.controller;

import condo.model.Officer;
import condo.model.User;
import condo.service.UserFileDataSource;
import condo.service.OfficerFileDataSource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OfficerLoginController {

    private String username,password;
    private UserFileDataSource dataSource;
    private Officer officer;
    private Alert alert;

    @FXML private Button btn_back,btn_signIn;
    @FXML private TextField off_username;
    @FXML private PasswordField off_password;

    @FXML public void initialize(){
        Platform.runLater(() -> {
            dataSource = new OfficerFileDataSource("data", "officers.csv");
        });
    }

    @FXML public void handleLoginOnAction (ActionEvent event) throws IOException {
        if (event.getSource() == btn_back) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/start.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();
        }
        else if (event.getSource() == btn_signIn) {
            username = off_username.getText();
            password = off_password.getText();
            officer = (Officer) dataSource.checkLoginAccount(username, password);
            if (officer != null) {
                ((OfficerFileDataSource)dataSource).writeLoginTime(officer);
                Button login = (Button) event.getSource();
                Stage stage = (Stage) login.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/officer_page.fxml")
                );
                stage.setScene((new Scene(loader.load(), 800, 600)));
                OfficerPageController officerPageController = loader.getController();
                officerPageController.setOfficer(officer);
                stage.show();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("ผิดพลาด");
                alert.setHeaderText(null);
                alert.setContentText("โปรดกรอกบัญชีผู้ใช้และรหัสผ่านให้ถูกต้อง");
                off_username.clear();
                off_password.clear();
                alert.showAndWait();
            }
        }
    }
}

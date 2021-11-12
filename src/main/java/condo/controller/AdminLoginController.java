package condo.controller;

import condo.model.User;
import condo.service.AdminFileDataSource;
import condo.service.UserFileDataSource;
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

public class AdminLoginController {

    private String username,password;
    private UserFileDataSource dataSource;
    private User admin;
    private Alert alert;

    @FXML private Button btn_back,btn_signIn;
    @FXML private TextField adm_username;
    @FXML private PasswordField adm_password;

    @FXML public void initialize(){
        Platform.runLater(() -> dataSource = new AdminFileDataSource("data", "admins.csv"));
    }

    @FXML public void handleLoginPageOnAction (ActionEvent event) throws IOException {
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
            username = adm_username.getText();
            password = adm_password.getText();
            admin = (User) dataSource.checkLoginAccount(username, password);
            if (admin != null){
                Button login = (Button) event.getSource();
                Stage stage = (Stage) login.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/admin_page.fxml")
                );
                stage.setScene((new Scene(loader.load(), 800, 600)));
                AdminPageController adminPageController = loader.getController();
                adminPageController.setAdmin(admin);
                stage.show();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("ผิดพลาด");
                alert.setHeaderText(null);
                alert.setContentText("โปรดกรอกบัญชีผู้ใช้และรหัสผ่านให้ถูกต้อง");
                adm_username.clear();
                adm_password.clear();
                alert.showAndWait();
            }
        }
    }
}

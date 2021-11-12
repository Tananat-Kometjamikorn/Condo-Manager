package condo.controller;

import condo.model.Officer;
import condo.model.OfficerList;
import condo.model.User;
import condo.service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminPageController {

    private Label currentDisable;
    private Label nextDisable;
    private Alert alert;

    //main
    private User admin;
    private UserFileDataSource adminUserFileDataSource;
    @FXML private Label btn_checkLog,btn_createAcc,btn_changePass,lab_welcome;
    @FXML private Button btn_signOut;
    @FXML private Pane checkLogTime,createAccount,changePassword;

    //officer login time
    private UserFileDataSource officerUserFileDataSource;
    private ObservableList<Officer> loginTimeObservableList;
    private OfficerList officerList;
    @FXML private TableView<Officer> table_offLogin;
    @FXML private TableColumn<Officer,String> log_username,log_name,log_date,log_time;

    //create officer account page
    private String officerName,officerUsername,officerPassword,officerConfirmPassword;
    @FXML private TextField fil_offName,fil_offUsername;
    @FXML private PasswordField fil_offPassword,fil_offConPassword;
    @FXML private Button btn_acceptCreateAcc;

    //change password page
    private String currentPassword,newPassword,confirmNewPassword;
    @FXML private Button btn_acceptChangePass;
    @FXML private PasswordField fil_curPass,fil_newPass,fil_conPass;

    @FXML public void initialize(){
        Platform.runLater(() -> {
            lab_welcome.setText("ผู้ดูแลระบบ : "+ admin.getName());
            checkLogTime.toFront();
            currentDisable = btn_checkLog;
            disableTab(currentDisable);

            adminUserFileDataSource = new AdminFileDataSource("data","admins.csv");
            officerUserFileDataSource = new OfficerFileDataSource("data","officers.csv");
            table_offLogin.setPlaceholder(new Label("ยังไม่มีเจ้าหน้าที่ส่วนกลาง"));
            officerList = ((OfficerFileDataSource)officerUserFileDataSource).getOfficerData();
            showLoginTime();
        });
    }

    private void showLoginTime(){
        loginTimeObservableList = FXCollections.observableList(officerList.toList());
        log_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        log_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        log_date.setCellValueFactory(new PropertyValueFactory<>("lastLoginDate"));
        log_time.setCellValueFactory(new PropertyValueFactory<>("lastLoginTime"));
        table_offLogin.setItems(loginTimeObservableList);
        table_offLogin.getSortOrder().add(log_date);
        table_offLogin.getSortOrder().add(log_time);
        table_offLogin.sort();
        table_offLogin.refresh();
    }

    @FXML public void handleTabMouseClick(MouseEvent event) throws IOException{
        if (event.getSource() == btn_checkLog) {
            nextDisable = btn_checkLog;
            enableTab(currentDisable);
            disableTab(nextDisable);
            currentDisable = nextDisable;
            checkLogTime.toFront();
            table_offLogin.getSelectionModel().clearSelection();
            table_offLogin.getSortOrder().add(log_date);
            table_offLogin.getSortOrder().add(log_time);
            table_offLogin.sort();
        }
        if (event.getSource() == btn_createAcc) {
            clearAllTextField();
            nextDisable = btn_createAcc;
            enableTab(currentDisable);
            disableTab(nextDisable);
            currentDisable = nextDisable;
            createAccount.toFront();
            btn_acceptCreateAcc.setOnAction(this::handleCreateAccount);
        }
        if (event.getSource() == btn_changePass) {
            clearAllTextField();
            nextDisable = btn_changePass;
            enableTab(currentDisable);
            disableTab(nextDisable);
            currentDisable = nextDisable;
            changePassword.toFront();
            btn_acceptChangePass.setOnAction(this::handleChangePassword);
        }
        if (event.getSource() == btn_signOut){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/start.fxml")
            );
            stage.setScene((new Scene(loader.load(), 800, 600)));
            loader.getController();
            stage.show();
        }
    }

    private void handleChangePassword(ActionEvent event1) {
        currentPassword = fil_curPass.getText();
        newPassword = fil_newPass.getText();
        confirmNewPassword = fil_conPass.getText();
        try {
            if (currentPassword.isEmpty()||newPassword.isEmpty()||confirmNewPassword.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("ผิดพลาด");
                alert.setHeaderText(null);
                alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
                alert.showAndWait();
            }
            else if (!newPassword.equals(confirmNewPassword)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("ผิดพลาด");
                alert.setHeaderText(null);
                alert.setContentText("ช่องรหัสผ่านใหม่กับช่องยืนยันรหัสผ่านใหม่ไม่ตรงกัน");
                alert.showAndWait();
            }
            else if(adminUserFileDataSource.changePassword(admin, currentPassword, newPassword, confirmNewPassword)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("สำเร็จ");
                alert.setHeaderText(null);
                alert.setContentText("เปลี่ยนรหัสผ่านสำเร็จ");
                alert.showAndWait();
                nextDisable = btn_checkLog;
                enableTab(currentDisable);
                disableTab(nextDisable);
                currentDisable = nextDisable;
                fil_curPass.clear();
                fil_newPass.clear();
                fil_conPass.clear();
                Button a = (Button) event1.getSource();
                Stage stage = (Stage) a.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/admin_login.fxml")
                );
                stage.setScene((new Scene(loader.load(), 800, 600)));
                loader.getController();
                stage.show();
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("ผิดพลาด");
                alert.setHeaderText(null);
                alert.setContentText("# ใส่รหัสผ่านปัจจุบันไม่ถูกต้อง\n# รหัสผ่านใหม่ซ้ำกับรหัสผ่านปัจจุบัน");
                fil_curPass.clear();
                fil_newPass.clear();
                fil_conPass.clear();
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateAccount(ActionEvent event1) {
        officerName = fil_offName.getText();
        officerUsername = fil_offUsername.getText();
        officerPassword = fil_offPassword.getText();
        officerConfirmPassword = fil_offConPassword.getText();
        if (officerName.isEmpty()||officerUsername.isEmpty()||
                officerPassword.isEmpty()||officerConfirmPassword.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("ผิดพลาด");
            alert.setHeaderText(null);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.showAndWait();
        }
        else if (!officerPassword.equals(officerConfirmPassword)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("ผิดพลาด");
            alert.setHeaderText(null);
            alert.setContentText("ช่องรหัสผ่านกับช่องยืนยันรหัสผ่านไม่ตรงกัน");
            fil_offPassword.clear();
            fil_offConPassword.clear();
            alert.showAndWait();
        }
        else {
            try {
                if (((OfficerFileDataSource) officerUserFileDataSource).createOfficerAccount
                        (officerUsername, officerName, officerPassword)) {
                    refreshOfficerLoginTable();
                    nextDisable = btn_checkLog;
                    enableTab(currentDisable);
                    disableTab(nextDisable);
                    currentDisable = nextDisable;
                    checkLogTime.toFront();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("ผิดพลาด");
                    alert.setHeaderText(null);
                    alert.setContentText("มีชื่อบัญชีผู้ใช้นี้แล้ว");
                    fil_offUsername.clear();
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearAllTextField();
        }
    }

    private void refreshOfficerLoginTable() {
        loginTimeObservableList.add(
                new Officer(officerUsername,officerName,officerPassword,"-","-"));
        table_offLogin.sort();
        table_offLogin.refresh();
    }

    private void clearAllTextField(){
        fil_offName.clear();
        fil_offUsername.clear();
        fil_offPassword.clear();
        fil_offConPassword.clear();

        fil_curPass.clear();
        fil_newPass.clear();
        fil_conPass.clear();
    }
    private void disableTab(Label tab){
        tab.setStyle("-fx-background-color: #33adff;");
        tab.setDisable(true);
    }
    private void enableTab(Label tab){
        tab.setStyle("-fx-background-color: #0033cc;");
        tab.setDisable(false);
    }
    public void setAdmin(User admin){
        this.admin = admin;
    }
}

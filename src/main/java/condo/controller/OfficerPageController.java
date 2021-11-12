package condo.controller;

import condo.model.*;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OfficerPageController {


    //main
    private Label currentDisable,nextDisable;
    private User officer;
    private UserFileDataSource officerFileDataSource;
    private Alert alert;
    @FXML private Label lab_welcome;
    @FXML private Label btn_roomList,btn_addRoom,btn_changePass,btn_mailList,btn_addMailInfo;
    @FXML private Button btn_signOut;
    @FXML private Pane allRoomInfo,addRoom,changePassword;
    @FXML private TabPane mailList,addMail;

    //room information list
    private RoomList roomList;
    private RoomFileDataSource roomFileDataSource;
    private ObservableList<Room> roomListObservableList;
    @FXML private TableView<Room> table_roomList;
    @FXML private TableColumn<Room,String> info_roomNum,info_roomType,info_floor,
                                            info_condoNum,info_resident1,info_resident2;

    //add room information
    private ObservableList<String> condoNumChoice,floorChoice,roomTypeChoice,roomNumChoice;
    private String condoNum,roomFloor,roomNum,roomType,resident1,resident2;
    @FXML private ComboBox<String> box_addCondoNum,box_addRoomFloor,box_addRoomNum,box_addRoomType;
    @FXML private Button btn_acceptAddRoom;
    @FXML private TextField fil_resName1,fil_resName2;
    @FXML private Label lab_resName1,lab_resName2;

    //add mail information
    private String staff,receiver,sender,room,time,date,size,urgency,trackNum,carrier,width,length,height;
    private LocalTime localTime;
    private LocalDate localDate;
    @FXML private TextField letter_receiver,doc_receiver,parcel_receiver,letter_sender,doc_sender,parcel_sender,
                            parcel_trackNum,letter_roomNum,doc_roomNum,parcel_roomNum,
                            letter_width,letter_length,doc_width,doc_length,parcel_width,parcel_length,parcel_height;
    @FXML private ComboBox<String> doc_urgency,parcel_carrier;
    @FXML private Button btn_acceptAddLetter,btn_acceptAddDoc,btn_acceptAddParcel;

    //mail information list
    private MailBoxFileDataSource letterFile, docFile, parcelFile;
    private MailList letterBox,documentBox,parcelBox;
    private ObservableList<Mail> letterObservableList,documentObservableList,parcelObservableList;
    private Mail selectedMail;
    @FXML TableView<Mail> table_letterList,table_docList,table_parcelList;
    @FXML TableColumn<Mail,String> letter_roomCol,letter_receiverCol,letter_dateCol,letter_timeCol,
                                    doc_roomCol,doc_receiverCol,doc_dateCol,doc_timeCol,
                                    parcel_roomCol,parcel_receiverCol,parcel_dateCol,parcel_timeCol;
    @FXML Label letter_showSize,letter_showSender,
                doc_showSize,doc_showSender,doc_showUrgency,
                parcel_showSize,parcel_showSender,parcel_showCarrier,parcel_showTrackNum;
    @FXML Button btn_delLetter,btn_delDoc,btn_delParcel;

    //change password page
    private String currentPassword,newPassword,confirmNewPassword;
    @FXML private Button btn_acceptChangePass;
    @FXML private PasswordField fil_curPass,fil_newPass,fil_conPass;

    @FXML public void initialize(){
        Platform.runLater(() -> {
            lab_welcome.setText("เจ้าหน้าที่ส่วนกลาง : "+ officer.getName());
            currentDisable = btn_roomList;
            disableTab(currentDisable);
            allRoomInfo.toFront();

            officerFileDataSource = new OfficerFileDataSource("data","officers.csv");
            roomFileDataSource = new RoomFileDataSource("data","rooms.csv");
            letterFile = new MailFileDataSource("data","letters.csv");
            docFile = new DocumentFileDataSource("data","documents.csv");
            parcelFile = new ParcelFileDataSource("data","parcels.csv");

            roomList = roomFileDataSource.getRoomList();
            condoNumChoice = FXCollections.observableArrayList("1");
            floorChoice = FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
            roomTypeChoice = FXCollections.observableArrayList("1 bedroom","2 bedrooms");
            box_addCondoNum.getItems().addAll(condoNumChoice);
            box_addRoomFloor.getItems().addAll(floorChoice);
            box_addRoomType.getItems().addAll(roomTypeChoice);
            roomNumChoice = FXCollections.observableArrayList();
            table_roomList.setPlaceholder(new Label("ยังไม่มีผู้เข้าพักอาศัย"));
            showRoomInfo();

            doc_urgency.getItems().addAll("ปกติ","ลับ","ด่วนมาก");
            parcel_carrier.getItems().addAll("Kerry","J&T","Flash","Thailand Post","EMS");

            table_letterList.setPlaceholder(new Label("ยังไม่มีจดหมายในระบบ"));
            table_docList.setPlaceholder(new Label("ยังไม่มีเอกสารในระบบ"));
            table_parcelList.setPlaceholder(new Label("ยังไม่มีพัสดุในระบบ"));

            letterBox = letterFile.getMailData();
            showLetterShowInfo();
            documentBox = docFile.getMailData();
            showDocumentShowInfo();
            parcelBox = parcelFile.getMailData();
            showParcelShowInfo();

            table_letterList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedMail = newValue;
                    showSelectedLetter(selectedMail);
                }
            });
            table_docList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedMail = newValue;
                    showSelectedDoc(selectedMail);
                }
            });
            table_parcelList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null){
                    selectedMail = newValue;
                    showSelectedParcel(selectedMail);
                }
            });
        });
    }

    @FXML public void handleTabMouseClick(MouseEvent event) throws IOException{
        if (event.getSource() == btn_roomList) {
           nextDisable = btn_roomList;
           disableTab(nextDisable);
           enableTab(currentDisable);
           currentDisable = nextDisable;
           allRoomInfo.toFront();
        }
        if (event.getSource() == btn_addRoom) {
            nextDisable = btn_addRoom;
            disableTab(nextDisable);
            enableTab(currentDisable);
            currentDisable = nextDisable;
            addRoom.toFront();
            clearAddRoomChoice();
            box_addRoomNum.setDisable(true);
            lab_resName1.setVisible(false);
            lab_resName2.setVisible(false);
            fil_resName1.setVisible(false);
            fil_resName2.setVisible(false);
            btn_acceptAddRoom.setOnMouseClicked(this::handleAddRoomInfo);
        }
        if (event.getSource() == btn_mailList) {
            nextDisable = btn_mailList;
            disableTab(nextDisable);
            enableTab(currentDisable);
            currentDisable = nextDisable;
            mailList.toFront();
            btn_delLetter.setOnAction(this::handleDelLetterInfo);
            btn_delDoc.setOnAction(this::handleDelDocInfo);
            btn_delParcel.setOnAction(this::handleDelParcelInfo);

        }
        if (event.getSource() == btn_addMailInfo) {
            nextDisable = btn_addMailInfo;
            disableTab(nextDisable);
            enableTab(currentDisable);
            currentDisable = nextDisable;
            addMail.toFront();
            clearAddMailField();
            btn_acceptAddLetter.setOnAction(this::handleAddLetterInfo);
            btn_acceptAddDoc.setOnAction(this::handleAddDocInfo);
            btn_acceptAddParcel.setOnAction(this::handleAddParcelInfo);

        }
        if (event.getSource() == btn_changePass) {
            nextDisable = btn_changePass;
            disableTab(nextDisable);
            enableTab(currentDisable);
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

    private void showRoomInfo(){
        roomListObservableList = FXCollections.observableList(roomList.toList());
        info_roomNum.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        info_roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        info_floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        info_condoNum.setCellValueFactory(new PropertyValueFactory<>("condoNumber"));
        info_resident1.setCellValueFactory(new PropertyValueFactory<>("residentName1"));
        info_resident2.setCellValueFactory(new PropertyValueFactory<>("residentName2"));
        table_roomList.setItems(roomListObservableList);
        table_roomList.getSortOrder().add(info_roomNum);
        table_roomList.sort();
    }
    private void showLetterShowInfo(){
        letterObservableList = FXCollections.observableList(letterBox.toList());
        letter_roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        letter_receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        letter_dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        letter_timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_letterList.setItems(letterObservableList);

    }
    private void showDocumentShowInfo(){
        documentObservableList = FXCollections.observableList(documentBox.toList());
        doc_roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        doc_receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        doc_dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        doc_timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_docList.setItems(documentObservableList);
    }
    private void showParcelShowInfo(){
        parcelObservableList = FXCollections.observableList(parcelBox.toList());
        parcel_roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        parcel_receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        parcel_dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        parcel_timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_parcelList.setItems(parcelObservableList);
    }

    private void showSelectedLetter(Mail mail){
        selectedMail = mail;
        letter_showSender.setText("ชื่อผู้ส่ง : " + "\n" + mail.getSender());
        letter_showSize.setText("ขนาด : " +"\n" + mail.getSize());
    }

    private void showSelectedDoc(Mail mail){
        selectedMail = mail;
        doc_showSender.setText("ชื่อผู้ส่ง : "+ "\n" +mail.getSender());
        doc_showSize.setText("ขนาด : "+ "\n" +mail.getSize());
        doc_showUrgency.setText("ความสำคัญ : "+ "\n" +((Document)mail).getUrgency());
    }

    private void showSelectedParcel(Mail mail){
        selectedMail = mail;
        parcel_showSender.setText("ชื่อผู้ส่ง : "+ "\n" + mail.getSender());
        parcel_showSize.setText("ขนาด : "+ "\n" + mail.getSize());
        parcel_showCarrier.setText("บริษัทขนส่ง : "+ "\n" + ((Parcel)mail).getCarrier());
        parcel_showTrackNum.setText("เลขพัสดุ : "+ "\n" + ((Parcel)mail).getTrackNumber());
    }

    private void handleDelLetterInfo(ActionEvent event){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ยืนยันการนำจดหมายออกจากระบบ");
        alert.setHeaderText(null);
        alert.setContentText("ต้องการนำจดหมายออก ใช่หรือไม่?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                letterFile.removeMail(selectedMail);
                refreshDelLetterInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDelDocInfo(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ยืนยันการนำเอกสารออกจากระบบ");
        alert.setHeaderText(null);
        alert.setContentText("ต้องการนำเอกสารออก ใช่หรือไม่?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                docFile.removeMail(selectedMail);
                refreshDelDocInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDelParcelInfo(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ยืนยันการนำพัสดุออกจากระบบ");
        alert.setHeaderText(null);
        alert.setContentText("ต้องการนำพัสดุออก ใช่หรือไม่?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                parcelFile.removeMail(selectedMail);
                refreshDelParcelInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAddRoomInfo(MouseEvent event1) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("ยืนยันการเพิ่มข้อมูลห้องพัก");
        alert.setHeaderText(null);
        alert.setContentText("ต้องการเพิ่มข้อมูลห้องพัก ใช่หรือไม่?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            setAddRoomVariable();
            try {
                if (!box_addCondoNum.getSelectionModel().isEmpty() && !box_addRoomFloor.getSelectionModel().isEmpty() &&
                        !box_addRoomType.getSelectionModel().isEmpty() && !box_addRoomNum.getSelectionModel().isEmpty()&&
                        !fil_resName1.getText().isEmpty()) {
                    if (roomFileDataSource.createRoom(condoNum, roomFloor, roomNum, roomType, resident1, resident2)) {
                        refreshRoomInfo();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("สำเร็จ");
                        alert.setHeaderText(null);
                        alert.setContentText("เพิ่มข้อมูลห้องพักสำเร็จ");
                        alert.showAndWait();
                        nextDisable = btn_roomList;
                        disableTab(nextDisable);
                        enableTab(currentDisable);
                        currentDisable = nextDisable;
                        allRoomInfo.toFront();
                    }
                    else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("ผิดพลาด");
                        alert.setHeaderText(null);
                        alert.setContentText("ห้องพักนี้มีผู้พักอาศัยแล้ว");
                        alert.showAndWait();
                    }
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("ผิดพลาด");
                    alert.setHeaderText(null);
                    alert.setContentText("#โปรดกรอกข้อมูลให้ครบถ้วน\n#ต้องมีผู้พักอาศัยอย่างน้อย 1 คน");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearAddRoomChoice();
        }
    }

    private void handleAddLetterInfo(ActionEvent event){
        setAddLetterVariable();
        if (staff.isEmpty()||receiver.isEmpty()||sender.isEmpty()||width.isEmpty()||length.isEmpty()||time.isEmpty()||date.isEmpty()
                || room.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("ผิดพลาด");
            alert.setHeaderText(null);
            clearAddMailField();
            alert.setContentText("#โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.showAndWait();
        }
        else {
            try {
                if (((MailFileDataSource) letterFile).addMailInfo(staff, receiver, sender, size, time, date, room, roomList)) {
                    refreshAddLetterInfo();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("สำเร็จ");
                    alert.setHeaderText(null);
                    alert.setContentText("เพิ่มข้อมูลจดหมายสำเร็จ");
                    alert.showAndWait();
                    clearAddMailField();
                    nextDisable = btn_mailList;
                    disableTab(nextDisable);
                    enableTab(currentDisable);
                    currentDisable = nextDisable;
                    mailList.toFront();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("ผิดพลาด");
                    alert.setHeaderText(null);
                    clearAddMailField();
                    alert.setContentText("#ภายในห้องพักไม่มีผู้พักอาศัยดังชื่อที่กรอกเข้ามา");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAddDocInfo(ActionEvent event) {
        setAddDocVariable();
        if (staff.isEmpty()||receiver.isEmpty()||sender.isEmpty()||width.isEmpty()||length.isEmpty()||time.isEmpty()||date.isEmpty()
                || room.isEmpty() || urgency.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("ผิดพลาด");
            alert.setHeaderText(null);
            clearAddMailField();
            alert.setContentText("#โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.showAndWait();
        }
        else {
            try {
                if (((DocumentFileDataSource) docFile).addMailInfo(staff, receiver, sender, size, time, date, room,
                        urgency, roomList)) {
                    refreshAddDocInfo();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("สำเร็จ");
                    alert.setHeaderText(null);
                    alert.setContentText("เพิ่มข้อมูลเอกสารสำเร็จ");
                    alert.showAndWait();
                    clearAddMailField();
                    nextDisable = btn_mailList;
                    disableTab(nextDisable);
                    enableTab(currentDisable);
                    currentDisable = nextDisable;
                    mailList.toFront();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("ผิดพลาด");
                    alert.setHeaderText(null);
                    clearAddMailField();
                    alert.setContentText("#ภายในห้องพักไม่มีผู้พักอาศัยดังชื่อที่กรอกเข้ามา");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleAddParcelInfo(ActionEvent event) {
        setAddParcelVariable();
        if (staff.isEmpty()||receiver.isEmpty()||sender.isEmpty()||width.isEmpty()||length.isEmpty()||height.isEmpty() ||time.isEmpty()||date.isEmpty()
                || room.isEmpty() || trackNum.isEmpty() || carrier.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("ผิดพลาด");
            alert.setHeaderText(null);
            clearAddMailField();
            alert.setContentText("#โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.showAndWait();
        }
        else {
            try {
                if (((ParcelFileDataSource) parcelFile).addMailInfo(staff, receiver, sender, size, time, date, room, trackNum, carrier, roomList)) {
                    refreshAddParcelInfo();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("สำเร็จ");
                    alert.setHeaderText(null);
                    alert.setContentText("เพิ่มข้อมูลพัสดุสำเร็จ");
                    alert.showAndWait();
                    clearAddMailField();
                    nextDisable = btn_mailList;
                    disableTab(nextDisable);
                    enableTab(currentDisable);
                    currentDisable = nextDisable;
                    mailList.toFront();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("ผิดพลาด");
                    alert.setHeaderText(null);
                    clearAddMailField();
                    alert.setContentText("#ภายในห้องพักไม่มีผู้พักอาศัยดังชื่อที่กรอกเข้ามา");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleChangePassword(ActionEvent event) {
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
            else if(officerFileDataSource.changePassword(officer, currentPassword, newPassword, confirmNewPassword)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("สำเร็จ");
                alert.setHeaderText(null);
                alert.setContentText("เปลี่ยนรหัสผ่านสำเร็จ");
                alert.showAndWait();
                nextDisable = btn_roomList;
                enableTab(currentDisable);
                disableTab(nextDisable);
                currentDisable = nextDisable;
                fil_curPass.clear();
                fil_newPass.clear();
                fil_conPass.clear();
                Button o = (Button) event.getSource();
                Stage stage = (Stage) o.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/officer_login.fxml")
                );
                stage.setScene((new Scene(loader.load(), 800, 600)));
                loader.getController();
                stage.show();
            }
            else{
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

    private void setAddLetterVariable() {
        staff = officer.getUsername();
        receiver = letter_receiver.getText();
        room = letter_roomNum.getText();
        sender = letter_sender.getText();
        localTime = LocalTime.now();
        localDate = LocalDate.now();
        width = letter_width.getText();
        length = letter_length.getText();
        date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        size = width + "x" + length;
    }

    private void setAddDocVariable(){
        staff = officer.getUsername();
        receiver = doc_receiver.getText();
        room = doc_roomNum.getText();
        localTime = LocalTime.now();
        localDate = LocalDate.now();
        sender = doc_sender.getText();
        width = doc_width.getText();
        length = doc_length.getText();
        date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        urgency = doc_urgency.getValue();
        size = width + "x" + length;
    }
    private void setAddParcelVariable(){
        staff = officer.getUsername();
        receiver = parcel_receiver.getText();
        room = parcel_roomNum.getText();
        sender = parcel_sender.getText();
        localTime = LocalTime.now();
        localDate = LocalDate.now();
        width = parcel_width.getText();
        length = parcel_length.getText();
        height = parcel_height.getText();
        date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        trackNum = parcel_trackNum.getText();
        carrier = parcel_carrier.getValue();
        size = width + "x" + length + "x" + height;
    }

    public void setAddRoomChoiceOnAction(ActionEvent event){
        boolean a = box_addCondoNum.getSelectionModel().isEmpty();
        boolean b = box_addRoomFloor.getSelectionModel().isEmpty();
        boolean c = box_addRoomType.getSelectionModel().isEmpty();
        if(!a&&!b&&!c){
            condoNum = box_addCondoNum.getValue();
            roomFloor = box_addRoomFloor.getValue();
            roomType = box_addRoomType.getValue();
            box_addRoomNum.getItems().clear();
            box_addRoomNum.setDisable(false);
            setAddRoomChoice();
            box_addRoomNum.getItems().addAll(roomNumChoice);
        }
    }

    private void setAddRoomChoice(){
        roomNumChoice.clear();
        if (roomType.equals("1 bedroom")){
            for (int i=0; i<5; i++){
                String r = Integer.toString(i);
                roomNumChoice.add(condoNum+roomFloor+"0"+r);
            }
            lab_resName1.setVisible(true);
            lab_resName2.setVisible(false);
            fil_resName1.setVisible(true);
            fil_resName2.setVisible(false);
        }
        else{
            for (int i=5; i<10; i++){
                String r = Integer.toString(i);
                roomNumChoice.add(condoNum+roomFloor+"0"+r);
            }
            lab_resName1.setVisible(true);
            lab_resName2.setVisible(true);
            fil_resName1.setVisible(true);
            fil_resName2.setVisible(true);
        }
        fil_resName1.clear();
        fil_resName2.clear();
    }

    private void setAddRoomVariable(){
        condoNum = box_addCondoNum.getValue();
        roomFloor = box_addRoomFloor.getValue();
        roomNum = box_addRoomNum.getValue();
        roomType = box_addRoomType.getValue();
        resident1 = fil_resName1.getText();
        resident2 = fil_resName2.getText();
        if (resident2.isEmpty()) resident2 = "-";
    }

    private void clearAddRoomChoice(){
        box_addCondoNum.setValue(null);
        box_addRoomFloor.setValue(null);
        box_addRoomNum.getItems().clear();
        box_addRoomType.setValue(null);
        fil_resName1.clear();
        fil_resName2.clear();
    }

    private void clearAddMailField(){
        letter_receiver.clear();
        letter_sender.clear();
        letter_roomNum.clear();
        letter_length.clear();
        letter_width.clear();

        doc_receiver.clear();
        doc_sender.clear();
        doc_roomNum.clear();
        doc_urgency.setValue(null);
        doc_length.clear();
        doc_width.clear();

        parcel_receiver.clear();
        parcel_sender.clear();
        parcel_roomNum.clear();
        parcel_trackNum.clear();
        parcel_carrier.setValue(null);
        parcel_length.clear();
        parcel_width.clear();
        parcel_height.clear();
    }

    private void refreshRoomInfo(){
        boolean create = true;
        for (Room r : roomListObservableList){
            if (r.getRoomNumber().equals(roomNum)){
                if (!r.getResidentName1().equals(resident1)||!r.getResidentName2().equals(resident2)){
                    r.setResidentName1(resident1);
                    r.setResidentName2(resident2);
                    create = false;
                }
            }
        }
        if(create) {
            roomListObservableList.add(new Room(condoNum, roomFloor, roomNum, roomType, resident1, resident2));
        }
        table_roomList.sort();
        table_roomList.refresh();
    }

    private void refreshAddLetterInfo(){
        letterObservableList.add(new Mail(officer.getUsername(),receiver,sender,size,time,date,
                                            room));
        table_letterList.refresh();
    }

    private void refreshAddDocInfo() {
        documentObservableList.add(new Document(officer.getUsername(),receiver,sender,size,time,date,
                room,urgency));
        table_docList.refresh();
    }

    private void refreshAddParcelInfo() {
        parcelObservableList.add(new Parcel(officer.getUsername(),receiver,sender,size,time,date,
                room,trackNum,carrier));
        table_parcelList.refresh();
    }

    private void refreshDelLetterInfo(){
        table_letterList.getItems().remove(selectedMail);
        clearSelectedMail();
        table_letterList.refresh();
        table_letterList.getSelectionModel().clearSelection();
    }
    private void refreshDelDocInfo(){
        table_docList.getItems().remove(selectedMail);
        clearSelectedMail();
        table_docList.refresh();
        table_docList.getSelectionModel().clearSelection();

    }
    private void refreshDelParcelInfo(){
        table_parcelList.getItems().remove(selectedMail);
        clearSelectedMail();
        table_parcelList.refresh();
        table_parcelList.getSelectionModel().clearSelection();
    }

    private void clearSelectedMail(){
        selectedMail = null;
        letter_showSender.setText(null);
        letter_showSize.setText(null);
        doc_showSender.setText(null);
        doc_showSize.setText(null);
        doc_showUrgency.setText(null);
        parcel_showSender.setText(null);
        parcel_showSize.setText(null);
        parcel_showCarrier.setText(null);
        parcel_showTrackNum.setText(null);
    }

    private void disableTab(Label tab){
        tab.setStyle("-fx-background-color:#00b33c;");
        tab.setDisable(true);
    }
    private void enableTab(Label tab){
        tab.setStyle("-fx-background-color: #006600;");
        tab.setDisable(false);
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }
}

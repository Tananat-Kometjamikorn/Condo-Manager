package condo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionController {

    @FXML private Button btn_back;

    public void handleBackOnAction(ActionEvent event) throws IOException {
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

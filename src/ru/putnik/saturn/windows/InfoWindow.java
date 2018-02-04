package ru.putnik.saturn.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class InfoWindow extends AbstractWindow {
    public static Stage infoStage;

    @FXML
    private TextArea infoArea;
    @FXML
    private Button exitButton;

    public InfoWindow(){

    }

    @Override
    public String getPathToLayout() {
        return "/ru/putnik/saturn/resources/layouts/InfoWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return infoStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setOnAction(event -> infoStage.close());
    }
}

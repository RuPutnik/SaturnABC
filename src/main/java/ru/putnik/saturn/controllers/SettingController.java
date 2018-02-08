package ru.putnik.saturn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class SettingController extends AbstractController {
    public static Stage settingStage;

    @FXML
    private Label addressTableLabel;
    @FXML
    private RadioButton onLoggingRButton;
    @FXML
    private RadioButton offLoggingRButton;
    @FXML
    private RadioButton onCryptSpacingRButton;
    @FXML
    private RadioButton offCryptSpacingRButton;
    @FXML
    private CheckBox actionPlayerTableCheckBox;
    @FXML
    private Button findFileButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button defaultButton;
    @FXML
    private Button cancelButton;
    public SettingController(){}
    public SettingController(Stage stage){
        settingStage=stage;
    }

    @Override
    public String getPathToLayout() {
        return "layouts/SettingWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return settingStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Настройки");

    }
}

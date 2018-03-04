package ru.putnik.saturn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.main.LogMachine;
import ru.putnik.saturn.models.SettingModel;
import ru.putnik.saturn.pojo.Settings;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class SettingController extends AbstractController {
    private SettingModel model;
    public static Stage settingStage;
    private ToggleGroup loggingGroup=new ToggleGroup();
    private ToggleGroup cryptSpacingGroup=new ToggleGroup();

    @FXML
    private TextField addressTableField;
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

    //Если какая-либо кнопка или виджет были нажаты и это сохранилось в настройках, то устанавливаем нужные квиджеты в нажатое состояние
    private void useGraphicPartOfSettings(Settings settings){
        if (settings!=null) {
            if (settings.isPlayerTableIndexs()) {
                actionPlayerTableCheckBox.setSelected(true);
            } else {
                actionPlayerTableCheckBox.setSelected(false);
            }
            if (settings.isCryptoSpacing()) {
                onCryptSpacingRButton.setSelected(true);
                offCryptSpacingRButton.setSelected(false);
            } else {
                onCryptSpacingRButton.setSelected(false);
                offCryptSpacingRButton.setSelected(true);
            }
            if (settings.isLogging()) {
                onLoggingRButton.setSelected(true);
                offLoggingRButton.setSelected(false);
            } else {
                onLoggingRButton.setSelected(false);
                offLoggingRButton.setSelected(true);
            }
            addressTableField.setText(settings.getPathToPlayerTable());
            addressTableField.setFocusTraversable(false);
        }
        LogMachine.log(Level.INFO,"Set the graphics settings widgets");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model=new SettingModel();

        settingStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Настройки");
        onLoggingRButton.setToggleGroup(loggingGroup);
        offLoggingRButton.setToggleGroup(loggingGroup);
        onCryptSpacingRButton.setToggleGroup(cryptSpacingGroup);
        offCryptSpacingRButton.setToggleGroup(cryptSpacingGroup);

        offLoggingRButton.setSelected(true);
        onCryptSpacingRButton.setSelected(true);

        useGraphicPartOfSettings(model.getSettingsProgram());

        onLoggingRButton.setOnAction(model.getOnLoggingAction());
        offLoggingRButton.setOnAction(model.getOffLoggingAction());
        onCryptSpacingRButton.setOnAction(model.getOnCryptSpacing());
        offCryptSpacingRButton.setOnAction(model.getOffCryptSpacing());
        actionPlayerTableCheckBox.setOnAction(model.getPlayerTableAction());
        findFileButton.setOnAction(model.getFindFile(addressTableField));
        saveButton.setOnAction(model.getSaveSettings());
        defaultButton.setOnAction(runnable->{
            useGraphicPartOfSettings(model.installDefaultSettings());
        });
        cancelButton.setOnAction(runnable->settingStage.close());
    }
}

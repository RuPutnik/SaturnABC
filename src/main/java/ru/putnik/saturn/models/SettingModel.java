package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.controllers.SettingController;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.main.LogMachine;
import ru.putnik.saturn.pojo.DefaultSettings;
import ru.putnik.saturn.pojo.Settings;
import ru.putnik.saturn.serialization.SerializationMachine;

import java.io.*;

/**
 * Created by My Computer on 02.02.2018.
 */
public class SettingModel {
    private Settings settingsProgram=new Settings();
    public static final String PATH_TO_FILE_SETTINGS="C:\\SaturnABC\\settings.st";
    private static final String PATH_TO_DIRECTORY_SETTINGS="C:\\SaturnABC\\";

    public SettingModel(){
        createDirectorySettings();
        Settings settings=SerializationMachine.deserializationSettings(new File(PATH_TO_FILE_SETTINGS));
        if(settings!=null) {
            settingsProgram.setPlayerTableIndex(settings.isPlayerTableIndexs());
            settingsProgram.setPathToPlayerTable(settings.getPathToPlayerTable());
            settingsProgram.setLogging(settings.isLogging());
            settingsProgram.setCryptoSpacing(settings.isCryptoSpacing());
            LogMachine.log(Level.INFO,"Receiving the configuration program");
        }else {
            installDefaultSettings();
            LogMachine.log(Level.WARN,"Receiving standard program configuration");
        }
    }

    public Settings getSettingsProgram() {
        return settingsProgram;
    }
    public OnLoggingAction getOnLoggingAction(){
        return new OnLoggingAction();
    }
    public OffLoggingAction getOffLoggingAction(){
        return new OffLoggingAction();
    }
    public OnCryptSpacing getOnCryptSpacing(){
        return new OnCryptSpacing();
    }
    public OffCryptSpacing getOffCryptSpacing(){
        return new OffCryptSpacing();
    }
    public PlayerTableAction getPlayerTableAction(){
        return new PlayerTableAction();
    }
    public FindFile getFindFile(TextField pathLabel){
        return new FindFile(pathLabel);
    }
    public SaveSettings getSaveSettings(){
        return new SaveSettings();
    }
    //Сериализация настроек

    //Создание папки настроек при ее отсутствии
    private void createDirectorySettings(){
        File settingsDirection=new File(PATH_TO_DIRECTORY_SETTINGS);
        if(!settingsDirection.exists()){
            settingsDirection.mkdir();
        }
        LogMachine.log(Level.INFO,"Created folder for settings");
    }
    //Установка дефолтных настроек
    public Settings installDefaultSettings(){
        settingsProgram.setCryptoSpacing(DefaultSettings.CRYPTO_SPACING);
        settingsProgram.setLogging(DefaultSettings.LOGGING);
        settingsProgram.setPathToPlayerTable(DefaultSettings.PATH_TO_PLAYER_TABLE);
        settingsProgram.setPlayerTableIndex(DefaultSettings.PLAYER_TABLE_INDEX);
        return settingsProgram;
    }

    /* Далее обработчики нажатий на определенные виджеты управления настройками приложения */

    private class OnLoggingAction implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            settingsProgram.setLogging(true);
        }
    }
    private class OffLoggingAction implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            settingsProgram.setLogging(false);
        }
    }
    private class OnCryptSpacing implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            settingsProgram.setCryptoSpacing(true);
        }
    }
    private class OffCryptSpacing implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            settingsProgram.setCryptoSpacing(false);
        }
    }
    private class PlayerTableAction implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            settingsProgram.setPlayerTableIndex(!settingsProgram.isPlayerTableIndexs());
        }
    }

    //Обработчик нажатия на кнопку поиска файла пользовательской таблицы индексов
    private class FindFile implements EventHandler<ActionEvent>{
        TextField pathField;
        FindFile(TextField pathField){
            this.pathField=pathField;
        }

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser=new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
            try {
                LogMachine.log(Level.INFO,"Selecting a file with a custom index table");
                File playerTable = fileChooser.showOpenDialog(new Stage());
                settingsProgram.setPathToPlayerTable(playerTable.getAbsolutePath());
                pathField.setText(playerTable.getAbsolutePath());

            }catch (NullPointerException e){
                e.printStackTrace();
                settingsProgram.setPathToPlayerTable("");
                pathField.setText("");
                LogMachine.log(Level.ERROR,"Selecting a file with a custom index table failed");
            }
        }
    }
    //Обработчик нажатия на кнопку сохранения настроек
    private class SaveSettings implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            LogMachine.log(Level.INFO,"Saving settings");
            createDirectorySettings();
            SerializationMachine.serializationSettings(settingsProgram,PATH_TO_FILE_SETTINGS);
            SettingController.close();
        }
    }
}

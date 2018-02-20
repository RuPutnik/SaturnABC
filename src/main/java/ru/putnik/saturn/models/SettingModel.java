package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.putnik.saturn.controllers.SettingController;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.pojo.DefaultSettings;
import ru.putnik.saturn.pojo.Settings;

import java.io.*;

/**
 * Created by My Computer on 02.02.2018.
 */
public class SettingModel {
    private Settings settingsProgram=new Settings();
    private static final String PATH_TO_FILE_SETTINGS="C:\\SaturnABC\\settings.st";
    private static final String PATH_TO_DIRECTORY_SETTINGS="C:\\SaturnABC\\";

    public SettingModel(){
        createDirectorySettings();
        Settings settings=deserializationSettings(new File(PATH_TO_FILE_SETTINGS));
        if(settings!=null) {
            settingsProgram.setPlayerTableIndex(settings.isPlayerTableIndexs());
            settingsProgram.setPathToPlayerTable(settings.getPathToPlayerTable());
            settingsProgram.setLogging(settings.isLogging());
            settingsProgram.setCryptoSpacing(settings.isCryptoSpacing());
        }else {
            installDefaultSettings();
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
    public FindFile getFindFile(Label pathLabel){
        return new FindFile(pathLabel);
    }
    public SaveSettings getSaveSettings(){
        return new SaveSettings();
    }
    //Сериализация настроек
    public void serializationSettings(Settings settings,String pathSettingFile){
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(pathSettingFile))) {
            oos.writeObject(settings);
            oos.flush();
            oos.close();
        } catch (IOException e){
            CreationAlerts.showErrorAlert("Ошибка","Ошибка сохранения настроек",
                    "При сохранении настроек приложения возникла ошибка!",false);
            e.printStackTrace();
        }
    }
    //Десериализация настроек
    public Settings deserializationSettings(File fileSettings){
        Settings settings=null;
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileSettings))){
            settings=(Settings)ois.readObject();
            ois.close();
        } catch (IOException e) {
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки натроек",
                    "При загрузке настроек приложения возникла ошибка! Возможно файл поврежден.",false);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            CreationAlerts.showWarningAlert("Ошибка","Ошибка загрузки натроек",
                    "При загрузке настроек приложения возникла ошибка! Возможно файл не существует. Будут использоваться стандартные настройки.",false);
            e.printStackTrace();
        }
        return settings;
    }
    //Создание папки настроек при ее отсутствии
    private void createDirectorySettings(){
        File settingsDirection=new File(PATH_TO_DIRECTORY_SETTINGS);
        if(!settingsDirection.exists()){
            settingsDirection.mkdir();
        }
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
        Label pathLabel;
        FindFile(Label pathLabel){
            this.pathLabel=pathLabel;
        }

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser=new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
            try {
                File playerTable = fileChooser.showOpenDialog(new Stage());
                settingsProgram.setPathToPlayerTable(playerTable.getAbsolutePath());
                pathLabel.setText(playerTable.getAbsolutePath());
            }catch (NullPointerException e){
                e.printStackTrace();
                settingsProgram.setPathToPlayerTable("");
                pathLabel.setText("");
            }
        }
    }
    //Обработчик нажатия на кнопку сохранения настроек
    private class SaveSettings implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            createDirectorySettings();
            serializationSettings(settingsProgram,PATH_TO_FILE_SETTINGS);
            SettingController.close();
        }
    }
}

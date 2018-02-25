package ru.putnik.saturn.serialization;

import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.pojo.DefaultSettings;
import ru.putnik.saturn.pojo.Settings;

import java.io.*;

public class SerializationMachine {
    private SerializationMachine(){}
    public static void serializationSettings(Settings settings, String pathSettingFile){
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
    public static Settings deserializationSettings(File fileSettings){
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
            settings=DefaultSettings.getSettings();
        }
        return settings;
    }
}

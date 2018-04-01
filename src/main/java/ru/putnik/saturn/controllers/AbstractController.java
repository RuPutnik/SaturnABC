package ru.putnik.saturn.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.main.LogMachine;
import ru.putnik.saturn.main.PathToLayout;

import java.io.IOException;

/**
 * Создано 13.07.16
 */
public abstract class AbstractController extends Application implements PathToLayout,Initializable {
    @Override
    public void start(Stage stage) throws Exception {}

    static final String NAME_PROGRAM="SaturnABC";
    static final String VERSION_PROGRAM="version: 0.9.2";
    private static final String LOGO_PATH="images/iconSaturn.png";
    private static Stage stage;
    //Отрисовываем окно программы
    public void renderWindow(Stage stage,int width,int height){
        AbstractController.stage=stage;
        Parent parent=null;
        try {
            parent=FXMLLoader.load(getClass().getClassLoader().getResource(getPathToLayout()));
            LogMachine.log(Level.INFO,"Loading a layout "+getPathToLayout());
        } catch (IOException|NullPointerException e) {
            LogMachine.log(Level.ERROR,"Layout "+getPathToLayout()+" not found or generated error!");
            e.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка отрисовки окна","Одно или несколько окон программы не смогло сгенерироваться и запущено не будет!",true);
        }
        stage.setResizable(true);
        stage.setScene(new Scene(parent));
        //Проверка на наличие иконки программы
        try{
            Image generalIcon=new Image(LOGO_PATH);
            stage.getIcons().add(generalIcon);
            LogMachine.log(Level.INFO,"Loading a icon");
        }catch (IllegalArgumentException|NullPointerException e){
            LogMachine.log(Level.ERROR,"The program icon is not found at this address!");
            e.printStackTrace();
            //Демонстрация сообщения об ошибке в отдельном окне
            CreationAlerts.showErrorAlert("Ошибка","Ошибка","Ошибка загрузки изображения!",false);
        }
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);
        stage.show();
    }

    public abstract Stage getStage();
    //Метод для закрытия окна программы(при условии что оно было открыто)
    public static void close(){
        if(stage!=null) {
            stage.close();
        }else {
            LogMachine.log(Level.WARN,"You cannot close the window until it is created!");
        }
    }
}

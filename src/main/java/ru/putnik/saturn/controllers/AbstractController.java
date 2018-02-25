package ru.putnik.saturn.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.main.PathToLayout;

import java.io.IOException;

/**
 * Создано 13.07.16
 */
public abstract class AbstractController extends Application implements PathToLayout,Initializable {
    @Override
    public void start(Stage stage) throws Exception {}

    static final String NAME_PROGRAM="SaturnABC";
    static final String VERSION_PROGRAM="version: 0.8";
    private static final String LOGO_PATH="images/iconSaturn.png";
    private static Stage stage;
    //Отрисовываем окно программы
    public void renderWindow(Stage stage,int width,int height){
        AbstractController.stage=stage;
        Parent parent=null;
        try {
            parent=FXMLLoader.load(getClass().getClassLoader().getResource(getPathToLayout()));
        } catch (IOException|NullPointerException e) {
            System.out.println("Layout "+getPathToLayout()+" not found or generated error!");
            e.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка отрисовки окна","Одно или несколько окон программы не смогло сгенерироваться и запущено не будет!",true);
        }
        stage.setResizable(true);
        stage.setScene(new Scene(parent));
        //Проверка на наличие иконки программы
        try{
            Image generalIcon=new Image(LOGO_PATH);
            stage.getIcons().add(generalIcon);
        }catch (IllegalArgumentException|NullPointerException e){
            System.out.println("Иконка программы не обнаружена по данному адресу!");
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
            System.out.println("Вы не можете закрыть окно до его создания!");
        }
    }
}

package ru.putnik.saturn.windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.putnik.saturn.main.PathToLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * Создано 13.07.16
 */
public abstract class AbstractWindow extends Application implements PathToLayout,Initializable {
    @Override
    public void start(Stage stage) throws Exception {}

    public static final String NAME_PROGRAM="SaturnABC";
    public static final String VERSION_PROGRAM="0.2";
    private static final String LOGO_PATH="/ru/putnik/saturn/resources/images/iconSaturn.png";
    //Отрисовываем окно программы
    public void renderWindow(Stage mainStage,int width,int height){
        Parent parent=null;
        try {
            parent= FXMLLoader.load(getClass().getResource(getPathToLayout()));
        } catch (IOException e) {
            System.out.println("Layout "+getPathToLayout()+" not found or generated error!");
            e.printStackTrace();
        }
        mainStage.setResizable(true);
        mainStage.setScene(new Scene(parent));
        mainStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM);
        InputStream stream=getClass().getResourceAsStream(LOGO_PATH);
        //Проверка на наличие иконки программы
        if(stream!=null) {
            Image generalIcon=new Image(stream);
            mainStage.getIcons().add(generalIcon);
        }
        mainStage.setMaxWidth(width);
        mainStage.setMaxHeight(height);
        mainStage.show();
    }

    public abstract Stage getStage();
}

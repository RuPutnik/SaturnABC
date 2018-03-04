package ru.putnik.saturn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.main.LogMachine;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class InfoController extends AbstractController {
    public static Stage infoStage;
    public final String pathHelpFile="texts/infoSaturnABC.txt";

    @FXML
    private TextArea infoArea;
    @FXML
    private Button exitButton;
    public InfoController(){}
    public InfoController(Stage stage){
        infoStage=stage;
    }

    @Override
    public String getPathToLayout() {
        return "layouts/InfoWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return infoStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Помощь");
        //Заполняем текстовое поле информацией из файла
        printHelpInfo();
        //Устанавливаем каретку на 0 символ
        infoArea.positionCaret(0);
        exitButton.setOnAction(event ->close());
    }
    //Чтение текста справки из файла, расположенного в resources
    public void printHelpInfo(){
        try {
            LogMachine.log(Level.INFO,"Download help file");
            String realPath=getClass().getClassLoader().getResource(pathHelpFile).getFile();
            InputStreamReader fileReader=new InputStreamReader(new FileInputStream(realPath));
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String lineText;
            while ((lineText=bufferedReader.readLine())!=null){
                infoArea.appendText(lineText+"\n");
            }
            LogMachine.log(Level.INFO,"Help file loaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки справки",
           "Текст справки не смог загрузиться. Возможно файл справки не существует или поврежден.",false);
            LogMachine.log(Level.ERROR,"Error loading help file");
        }
    }
}

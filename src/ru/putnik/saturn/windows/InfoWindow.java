package ru.putnik.saturn.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class InfoWindow extends AbstractWindow {
    public static Stage infoStage;
    public final String pathHelpFile="../resources/texts/infoSaturnABC.txt";

    @FXML
    private TextArea infoArea;
    @FXML
    private Button exitButton;
    public InfoWindow(){}
    public InfoWindow(Stage stage){
        infoStage=stage;
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
        printHelpInfo();
        infoArea.positionCaret(0);
        exitButton.setOnAction(event ->close());
    }
    public void printHelpInfo(){
        try {
            InputStreamReader fileReader=new InputStreamReader(getClass().getResourceAsStream(pathHelpFile));
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String lineText;
            while ((lineText=bufferedReader.readLine())!=null){
                infoArea.appendText(lineText+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

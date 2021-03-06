package ru.putnik.saturn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.main.LogMachine;
import ru.putnik.saturn.models.MainModel;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class MainController extends AbstractController {
    public static Stage mainStage;
    private CryptoListController cryptoListWindow;
    private SettingController settingWindow;
    private InfoController infoWindow;
    private MainModel mainModel;

    @FXML
    private MenuItem windowCryptsMenuItem;
    @FXML
    private MenuItem settingsMenuItem;
    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem openEncryptedFileMenuItem;
    @FXML
    private MenuItem saveFileMenuItem;
    @FXML
    private MenuItem saveEncryptedFileMenuItem;
    @FXML
    private MenuItem infoMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Label typeCryptLabel;
    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private Button clearLeftWindowButton;
    @FXML
    private Button clearRightWindowButton;
    @FXML
    private Button clearKeyFieldButton;
    @FXML
    private TextField keyTextField;
    @FXML
    private Button generateKeyButton;
    @FXML
    private TextArea decryptedTextArea;
    @FXML
    private TextArea encryptedTextArea;

    public static void start(){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        mainStage=stage;
        LogMachine.log(Level.INFO,"Render Main Window");
        renderWindow(getStage(),910,520);
    }

    @Override
    public String getPathToLayout() {
        return "layouts/MainWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cryptoListWindow=new CryptoListController(new Stage(),typeCryptLabel);
        settingWindow=new SettingController(new Stage());
        infoWindow=new InfoController(new Stage());
        mainModel=new MainModel(decryptedTextArea,encryptedTextArea,keyTextField);

        mainStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Главное окно");
        decryptedTextArea.setFont(Font.font("Times New Roman", FontWeight.findByWeight(50), FontPosture.REGULAR,15));
        encryptedTextArea.setFont(Font.font("Times New Roman", FontWeight.findByWeight(50), FontPosture.REGULAR,15));

        windowCryptsMenuItem.setOnAction(event -> cryptoListWindow.renderWindow(cryptoListWindow.getStage(),630,540));
        settingsMenuItem.setOnAction(event -> settingWindow.renderWindow(settingWindow.getStage(),463,235));
        openFileMenuItem.setOnAction(mainModel.getOpenFile(decryptedTextArea));
        openEncryptedFileMenuItem.setOnAction(mainModel.getOpenFile(encryptedTextArea));
        saveFileMenuItem.setOnAction(mainModel.getSaveFile(decryptedTextArea));
        saveEncryptedFileMenuItem.setOnAction(mainModel.getSaveFile(encryptedTextArea));
        infoMenuItem.setOnAction(event -> infoWindow.renderWindow(infoWindow.getStage(),425,490));
        exitMenuItem.setOnAction(event -> {LogMachine.log(Level.INFO,"Exit the program"); System.exit(0);});
        clearLeftWindowButton.setOnAction(new MainModel.CleanText<>(decryptedTextArea));
        clearRightWindowButton.setOnAction(new MainModel.CleanText<>(encryptedTextArea));
        clearKeyFieldButton.setOnAction(new MainModel.CleanText<>(keyTextField));
        encryptButton.setOnAction(mainModel.getCryptoOperation(1));
        decryptButton.setOnAction(mainModel.getCryptoOperation(-1));
        generateKeyButton.setOnAction(mainModel.getGeneratorKey());
        //Указываем слушателя события, происходящего при нажатии на крестик(закрытии главного окна программы)
        mainStage.setOnCloseRequest(event -> {LogMachine.log(Level.INFO,"Exit the program"); System.exit(0);});
    }

}

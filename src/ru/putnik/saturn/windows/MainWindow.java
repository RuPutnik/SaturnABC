package ru.putnik.saturn.windows;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.putnik.saturn.models.MainModel;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class MainWindow extends AbstractWindow{
    public static Stage mainStage;
    private CryptoListWindow cryptoListWindow;
    private SettingWindow settingWindow;
    private InfoWindow infoWindow;

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
        renderWindow(getStage(),910,520);
    }

    @Override
    public String getPathToLayout() {
        return "/ru/putnik/saturn/resources/layouts/MainWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return mainStage;
    }

    public Label getTypeCryptLabel() {
        return typeCryptLabel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cryptoListWindow=new CryptoListWindow(new Stage(),typeCryptLabel);
        settingWindow=new SettingWindow(new Stage());
        infoWindow=new InfoWindow(new Stage());

        windowCryptsMenuItem.setOnAction(event -> cryptoListWindow.renderWindow(cryptoListWindow.getStage(),630,540));
        settingsMenuItem.setOnAction(event -> settingWindow.renderWindow(settingWindow.getStage(),457,235));
        openFileMenuItem.setOnAction(new MainModel.OpenFile(decryptedTextArea));
        openEncryptedFileMenuItem.setOnAction(new MainModel.OpenFile(encryptedTextArea));
        saveFileMenuItem.setOnAction(new MainModel.SaveFile(decryptedTextArea));
        saveEncryptedFileMenuItem.setOnAction(new MainModel.SaveFile(encryptedTextArea));
        infoMenuItem.setOnAction(event -> infoWindow.renderWindow(infoWindow.getStage(),425,490));
        exitMenuItem.setOnAction(event -> System.exit(0));
        clearLeftWindowButton.setOnAction(new MainModel.CleanText<>(decryptedTextArea));
        clearRightWindowButton.setOnAction(new MainModel.CleanText<>(encryptedTextArea));
        clearKeyFieldButton.setOnAction(new MainModel.CleanText<>(keyTextField));
        encryptButton.setOnAction(new MainModel.CryptOperation(decryptedTextArea.getText(),1));
        decryptButton.setOnAction(new MainModel.CryptOperation(encryptedTextArea.getText(),-1));
        generateKeyButton.setOnAction(new MainModel.GeneratorRandomKey());
        mainStage.setOnCloseRequest(event -> System.exit(0));
    }
}

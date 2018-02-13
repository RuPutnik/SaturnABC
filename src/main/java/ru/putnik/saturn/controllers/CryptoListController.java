package ru.putnik.saturn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.putnik.saturn.models.CryptoListModel;
import ru.putnik.saturn.models.MainModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class CryptoListController extends AbstractController {
    public static Stage cryptoListStage;
    public static Label mainCryptLabel;
    private CryptoListModel model;
    @FXML
    private TextArea infoCryptTextArea;
    @FXML
    private ListView listCryptsListView;
    @FXML
    private Label selectedCryptLabel;
    @FXML
    private Button saveCryptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button connectModuleButton;
    @FXML
    private Button disconnectModuleButton;
    public CryptoListController(){}
    public CryptoListController(Stage stage, Label label){
        cryptoListStage=stage;
        mainCryptLabel=label;
    }

    @Override
    public String getPathToLayout() {
        return "layouts/CryptoListWindow.fxml";
    }

    @Override
    public Stage getStage() {
        return cryptoListStage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        cryptoListStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Выбор шифра");
        model=new CryptoListModel(selectedCryptLabel,infoCryptTextArea);
        //Подготавливаем список шифров
        model.prepareList();
        //Записываем номер и имя уже выбранного шифра(При запуске программы шифр не выбран и его номер number=0)
        model.setTempNameSelectedCrypt(MainModel.nameSelectedCrypt);
        model.setTempNumberSelectedCrypt(MainModel.numberSelectedCrypt);
        model.printCipherInfo(model.getNameInfoFileForNumber(MainModel.numberSelectedCrypt));
        listCryptsListView.setItems(model.getCryptsList());

        //Записываем имя выбранного шифра на специальный виджет в окне
        selectedCryptLabel.setText(MainModel.nameSelectedCrypt);

        saveCryptButton.setOnAction(model.saveButton);
        cancelButton.setOnAction(model.cancelButton);
        connectModuleButton.setOnAction(model.connectModuleButton);
        disconnectModuleButton.setOnAction(model.disconnectModuleButton);
        //Указываем обработчика события, происходящего при выборе шифра из списка (ListView)
        listCryptsListView.getSelectionModel().selectedItemProperty().addListener(model.selectedTypeCrypt);
    }
}

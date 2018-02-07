package ru.putnik.saturn.windows;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.putnik.saturn.models.CryptoListModel;
import ru.putnik.saturn.models.MainModel;
import ru.putnik.saturn.pojo.Crypt;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by My Computer on 30.01.2018.
 */
public class CryptoListWindow extends AbstractWindow{
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
    public CryptoListWindow(){}
    public CryptoListWindow(Stage stage,Label label){

        cryptoListStage=stage;
        mainCryptLabel=label;
    }

    @Override
    public String getPathToLayout() {
        return "/ru/putnik/saturn/resources/layouts/CryptoListWindow.fxml";
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
        model.prepareList();
        model.setTempNameSelectedCrypt(MainModel.nameSelectedCrypt);
        model.setTempNumberSelectedCrypt(MainModel.numberSelectedCrypt);
        model.selectedCipherInfo(MainModel.numberSelectedCrypt);
        listCryptsListView.setItems(model.getCryptsList());

        selectedCryptLabel.setText(MainModel.nameSelectedCrypt);

        saveCryptButton.setOnAction(model.saveButton);
        cancelButton.setOnAction(model.cancelButton);
        connectModuleButton.setOnAction(model.connectModuleButton);
        disconnectModuleButton.setOnAction(model.disconnectModuleButton);
        listCryptsListView.getSelectionModel().selectedItemProperty().addListener(model.selectedTypeCrypt);
    }
}

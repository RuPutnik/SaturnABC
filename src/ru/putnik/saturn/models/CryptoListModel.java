package ru.putnik.saturn.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.putnik.saturn.pojo.Crypt;
import ru.putnik.saturn.windows.CryptoListWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by My Computer on 02.02.2018.
 */
public class CryptoListModel {
    public SaveButton saveButton=new SaveButton();
    public CancelButton cancelButton=new CancelButton();
    public ConnectModuleButton connectModuleButton=new ConnectModuleButton();
    public DisconnectModuleButton disconnectModuleButton=new DisconnectModuleButton();
    public SelectedTypeCrypt selectedTypeCrypt=new SelectedTypeCrypt();
    private ObservableList<Crypt> cryptsList=FXCollections.observableArrayList();
    private String tempNameSelectedCrypt;
    private int tempNumberSelectedCrypt;
    private Label selectedCryptLabel;
    private TextArea infoCryptTextArea;
    public CryptoListModel(Label label,TextArea area){
        selectedCryptLabel=label;
        infoCryptTextArea=area;
    }
    public ObservableList<Crypt> prepareList(){
        cryptsList.add(new Crypt("Шифр Цезаря(Сдвиговый)",1));
        cryptsList.add(new Crypt("Шифр кодовым словом",2));
        cryptsList.add(new Crypt("Шифр блочный",3));
        cryptsList.add(new Crypt("Шифр Тритемиуса",4));
        cryptsList.add(new Crypt("Шифр расширения словом",5));
        return cryptsList;
    }

    public void selectedCipherInfo(int numberCipher){
        switch (numberCipher){
            case 0:{
                infoCryptTextArea.setText("Для получения справки выберите шифр.");
                break;
            }
            case 1:{
                printCipherInfo("caesarCipher.txt");
                break;
            }
            case 2:{
                printCipherInfo("codeWordCipher.txt");
                break;
            }
            case 3:{
                printCipherInfo("blockCipher.txt");
                break;
            }
            case 4:{
                printCipherInfo("tritemiusCipher.txt");
                break;
            }
            case 5:{
                printCipherInfo("extensionCipher.txt");
                break;
            }
            default:{
                infoCryptTextArea.setText("Ошибка выбора шифра! Данный шифр не существует\nили информационная справка о нём недоступна!");
                break;
            }
        }
    }
    public void printCipherInfo(String nameFile){
        InputStreamReader streamReader=new InputStreamReader(getClass().getResourceAsStream("../resources/texts/textsCrypts/"+nameFile));
        BufferedReader bufferedReader=new BufferedReader(streamReader);
        String infoLine;
        infoCryptTextArea.setText("");
        try {
            while ((infoLine = bufferedReader.readLine())!= null) {
                infoCryptTextArea.appendText(infoLine+"\n");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public ObservableList<Crypt> getCryptsList() {
        return cryptsList;
    }

    public String getTempNameSelectedCrypt() {
        return tempNameSelectedCrypt;
    }

    public int getTempNumberSelectedCrypt() {
        return tempNumberSelectedCrypt;
    }

    public void setTempNameSelectedCrypt(String nameSelectedCrypt) {
        this.tempNameSelectedCrypt = nameSelectedCrypt;
    }

    public void setTempNumberSelectedCrypt(int numberSelectedCrypt) {
        this.tempNumberSelectedCrypt = numberSelectedCrypt;
    }

    public class SelectedTypeCrypt implements ChangeListener<Crypt>{
        @Override
        public void changed(ObservableValue<? extends Crypt> observable, Crypt oldValue, Crypt newValue) {
            tempNameSelectedCrypt=newValue.getNameCrypt();
            tempNumberSelectedCrypt=newValue.getNumberCrypt();
            selectedCryptLabel.setText(tempNameSelectedCrypt);
            System.out.println(tempNumberSelectedCrypt);
            selectedCipherInfo(tempNumberSelectedCrypt);
        }
    }
    public class SaveButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            MainModel.nameSelectedCrypt=tempNameSelectedCrypt;
            MainModel.numberSelectedCrypt=tempNumberSelectedCrypt;
            CryptoListWindow.close();
            CryptoListWindow.mainCryptLabel.setText("    Тип шифрования: "+MainModel.nameSelectedCrypt);
        }
    }
    public class CancelButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            CryptoListWindow.close();
        }
    }
    public class ConnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class DisconnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}

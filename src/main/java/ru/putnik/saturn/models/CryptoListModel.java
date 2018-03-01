package ru.putnik.saturn.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.putnik.saturn.ciphers.*;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.controllers.CryptoListController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by My Computer on 02.02.2018.
 */
public class CryptoListModel {
    public static ObservableList<Cipher> cryptsList;
    public SaveButton saveButton=new SaveButton();
    public CancelButton cancelButton=new CancelButton();
    public ConnectModuleButton connectModuleButton=new ConnectModuleButton();
    public DisconnectModuleButton disconnectModuleButton=new DisconnectModuleButton();
    public SelectedTypeCrypt selectedTypeCrypt=new SelectedTypeCrypt();

    private String tempNameSelectedCrypt;
    private int tempNumberSelectedCrypt;
    private Label selectedCryptLabel;
    private TextArea infoCryptTextArea;
    public CryptoListModel(Label label,TextArea area){
        selectedCryptLabel=label;
        infoCryptTextArea=area;
    }
    //Добавление существующих шифров в список
    public ObservableList<Cipher> prepareList(){
        cryptsList=FXCollections.observableArrayList();
        cryptsList.add(new CaesarCipher(1));
        cryptsList.add(new CodeWordCipher(2));
        cryptsList.add(new BlockCodeWordCipher(3));
        cryptsList.add(new TrytCipher(4));
        cryptsList.add(new ExpandCipher(5));
        return cryptsList;
    }
    //Вывод справки о выбранном шифре
    public void printCipherInfo(String nameFile){
        try {
        String realPaths=getClass().getClassLoader().getResource("texts/textsCrypts/"+nameFile).getFile();
        InputStreamReader streamReader=new InputStreamReader(new FileInputStream(realPaths));
        BufferedReader bufferedReader=new BufferedReader(streamReader);
        String infoLine;
        infoCryptTextArea.setText("");
            while ((infoLine = bufferedReader.readLine())!= null) {
                infoCryptTextArea.appendText(infoLine+"\n");
            }
        }catch (IOException ex){
            ex.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки справки по шрифту",
           "Информация о выбранном шрфите не была загружена. Возможно файл справки поврежден или не существует.",
           false);
        }
    }
    //Возвращает имя файла помощи по номеру шифра
    public String getNameInfoFileForNumber(int numberCipher){
        if(numberCipher==0){
            return "notSelected.txt";
        }else {
            for (int a = 0; a < cryptsList.size(); a++) {
                if (cryptsList.get(a).numberCipher == numberCipher)
                    return cryptsList.get(a).nameFileInfo;
            }
            return "";
        }
    }

    public ObservableList<Cipher> getCryptsList() {
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


    public class SelectedTypeCrypt implements ChangeListener<Cipher>{
        @Override
        public void changed(ObservableValue<? extends Cipher> observable, Cipher oldValue, Cipher newValue) {
            tempNameSelectedCrypt=newValue.getNameCipher();
            tempNumberSelectedCrypt=newValue.getNumberCipher();
            selectedCryptLabel.setText(tempNameSelectedCrypt);

           // selectedCipherInfo(tempNumberSelectedCrypt);
            printCipherInfo(newValue.getNameFileInfo());

        }
    }
    //Обработчик события. Сохраняем информацию и выбранном шифре и выводим её на нужные виджеты Label
    private class SaveButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            MainModel.nameSelectedCrypt=tempNameSelectedCrypt;
            MainModel.numberSelectedCrypt=tempNumberSelectedCrypt;
            CryptoListController.close();
            CryptoListController.mainCryptLabel.setText("    Тип шифрования: "+MainModel.nameSelectedCrypt);
        }
    }
    //Обработчик события. Ничего не выбираем и не сохраняем, закрываем окно
    private class CancelButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            CryptoListController.close();
        }
    }
    //Обработчик события нажатия на кнопку подключения внешнего модуля шифра
    private class ConnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    //Обработчик события нажатия на кнопку отключения внешнего модуля шифра
    private class DisconnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}

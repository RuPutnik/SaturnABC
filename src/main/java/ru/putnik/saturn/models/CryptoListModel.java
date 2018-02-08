package ru.putnik.saturn.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.pojo.Crypt;
import ru.putnik.saturn.controllers.CryptoListController;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
    //Добавление существующих шифров в список
    public ObservableList<Crypt> prepareList(){
        cryptsList.add(new Crypt("Шифр Цезаря(Сдвиговый)",1));
        cryptsList.add(new Crypt("Шифр кодовым словом",2));
        cryptsList.add(new Crypt("Шифр блочный",3));
        cryptsList.add(new Crypt("Шифр Тритемиуса",4));
        cryptsList.add(new Crypt("Шифр расширения словом",5));
        return cryptsList;
    }
    //Определение файла справки для выбранного шифра по его номеру
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
            selectedCipherInfo(tempNumberSelectedCrypt);
        }
    }
    //Обработчик события. Сохраняем информацию и выбранном шифре и выводим её на нужные виджеты Label
    public class SaveButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            MainModel.nameSelectedCrypt=tempNameSelectedCrypt;
            MainModel.numberSelectedCrypt=tempNumberSelectedCrypt;
            CryptoListController.close();
            CryptoListController.mainCryptLabel.setText("    Тип шифрования: "+MainModel.nameSelectedCrypt);
        }
    }
    //Обработчик события. Ничего не выбираем и не сохраняем, закрываем окно
    public class CancelButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            CryptoListController.close();
        }
    }
    //Обработчик события нажатия на кнопку подключения внешнего модуля шифра
    public class ConnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    //Обработчик события нажатия на кнопку отключения внешнего модуля шифра
    public class DisconnectModuleButton implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}
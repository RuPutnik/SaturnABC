package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;


/**
 * Created by My Computer on 31.01.2018.
 */
public class MainModel {

    public static String nameSelectedCrypt="Не выбран";
    public static int numberSelectedCrypt=0;
    private TextArea decryptTextArea;
    private TextArea encryptTextArea;
    private TextField keyField;
    public MainModel(TextArea decryptTextArea,TextArea encryptTextArea,TextField keyField){
        this.decryptTextArea=decryptTextArea;
        this.encryptTextArea=encryptTextArea;
        this.keyField=keyField;
    }

    public CryptOperation getCryptoOperation(int direction){
        return new CryptOperation(direction);
    }

    private String decrypt(int numberCipher,String text,String key){
        String decryptedText;
        decryptedText=CryptoListModel.cryptsList.get(numberCipher-1).crypt(text,key,-1);
        return decryptedText;
    }
    private String encrypt(int numberCipher,String text,String key){
        String encryptedText;
        encryptedText=CryptoListModel.cryptsList.get(numberCipher-1).crypt(text,key,1);
        return encryptedText;
    }

    //Обработчик событий, отвечающий за очистку левого(правого) окна или поля для ключа при нажатии на кнопку
    public static class CleanText<T extends TextInputControl> implements EventHandler<ActionEvent>{
        T textComponent;
        public CleanText(T textComponent){
            this.textComponent=textComponent;
        }
        @Override
        public void handle(ActionEvent event) {
            textComponent.clear();
        }
    }
    //Обработчик событий нажатия на кнопки шифрации и дешифрации
    public class CryptOperation implements EventHandler<ActionEvent>{
        int direction;
        CryptOperation(int directionCrypt){
            this.direction=directionCrypt;
        }

        @Override
        public void handle(ActionEvent event) {
            if(direction==1){
                String encryptedText=encrypt(numberSelectedCrypt,decryptTextArea.getText(),keyField.getText());
                if(!encryptedText.equals("")||decryptTextArea.getText().equals("")) {
                    encryptTextArea.setText(encryptedText);
                }
            }else if(direction==-1){
                String decryptedText=decrypt(numberSelectedCrypt,encryptTextArea.getText(),keyField.getText());
                if(!decryptedText.equals("")||encryptTextArea.getText().equals("")) {
                    decryptTextArea.setText(decryptedText);
                }
            }
        }
    }
    //Обработчик событий нажатия на кнопку генерации случайного ключа
    public static class GeneratorRandomKey implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    //Обработчик нажатия на пункт меню открытия файла
    public static class OpenFile implements EventHandler<ActionEvent>{
        TextArea area;
        public OpenFile(TextArea area){
            this.area=area;
        }
        @Override
        public void handle(ActionEvent event) {

        }
    }
    //Обработчик нажатия на пункт меню сохранения файла
    public static class SaveFile implements EventHandler<ActionEvent>{
        TextArea area;
        public SaveFile(TextArea area){
            this.area=area;
        }
        @Override
        public void handle(ActionEvent event) {

        }
    }
}

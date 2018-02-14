package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import ru.putnik.saturn.ciphers.Ciphers;
import ru.putnik.saturn.main.CreationAlerts;

/**
 * Created by My Computer on 31.01.2018.
 */
public class MainModel {
    private Ciphers ciphers=new Ciphers();
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
        String decryptedText="";
            switch (numberCipher) {
                case 0: {
                    //Ничего не делаем, так как 0-номер не выбранного шифра
                    break;
                }
                case 1: {
                    //Проверяем ключ, используемый для дешифрации
                    if(checkCaesarKey(key)) {
                        return ciphers.cryptCaesar(text, key, -1);
                    }
                    break;
                }
                case 2: {

                    break;
                }
                case 3: {

                    break;
                }
                case 4: {

                    break;
                }
                case 5: {

                    break;
                }

            }
        return decryptedText;
    }
    private String encrypt(int numberCipher,String text,String key){
        String encryptedText="";
        switch (numberCipher) {
            case 0: {
                //Ничего не делаем, так как 0-номер не выбранного шифра
                break;
            }
            case 1: {
                //Проверяем ключ, используемый для шифрации
                if(checkCaesarKey(key)) {
                    return ciphers.cryptCaesar(text, key, 1);
                }
                break;
            }
            case 2: {

                break;
            }
            case 3: {

                break;
            }
            case 4: {

                break;
            }
            case 5: {

                break;
            }
        }
        return encryptedText;
    }
    private boolean checkCaesarKey(String key){
        if(key==null||key.equals("")){
            CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрования","Поля ключа пусто!",false);
           return false;
        }
        try {
            int testingKey=Integer.parseInt(key);
            return true;
        }catch (NumberFormatException e){
            e.printStackTrace();
            CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрования","Используемый ключ содержит недопустимые символы для данного шифра!",false);
            return false;
        }
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
                encryptTextArea.setText(encrypt(numberSelectedCrypt,decryptTextArea.getText(),keyField.getText()));
            }else if(direction==-1){
                decryptTextArea.setText(decrypt(numberSelectedCrypt,encryptTextArea.getText(),keyField.getText()));
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

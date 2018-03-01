package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.putnik.saturn.main.CreationAlerts;

import java.io.*;

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

    public GeneratorRandomKey getGeneratorKey(){
        return new GeneratorRandomKey();
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

    public OpenFile getOpenFile(TextArea area) {
        return new OpenFile(area);
    }

    public SaveFile getSaveFile(TextArea area){
        return new SaveFile(area);
    }

    //Используем стандартную компоненту для выбора текстового файла
    private File openFile(){
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File("C:\\"));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("TXT","*.txt"));
        return chooser.showOpenDialog(new Stage());
    }
    //Читаем текст из файла и загружаем его в TextArea
    private void fillTextAreaForAFile(TextArea fillingArea,File textFile){
        try {
            //Кодировку можно сделать выбираемой
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(textFile),"UTF-8"));
            String line;
            fillingArea.setText("");
            while ((line=bufferedReader.readLine())!=null){
                fillingArea.appendText(line+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка чтения файла","Выбранный файл не существует или недоступен",false);
        }catch (IOException ioe){
            ioe.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка чтения файла","Ошибка чтения выбранного файла",false);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
    //Выбираем файл, в который будем сохранять текст и(или) создаем этот файл
    private File chooseFileForSave(){
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File("C:\\"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        return chooser.showSaveDialog(new Stage());
    }
    //Сохраняем текст из TextArea в выбранный ранее файл
    private void saveFile(TextArea textArea,File textFile){
        if(textFile!=null) {
            try {
                FileWriter writer = new FileWriter(textFile);
                writer.write(textArea.getText());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private class CryptOperation implements EventHandler<ActionEvent>{
        int direction;
        CryptOperation(int directionCrypt){
            this.direction=directionCrypt;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
            if(MainModel.numberSelectedCrypt!=0) {
                if (direction == 1) {
                    String encryptedText = encrypt(numberSelectedCrypt, decryptTextArea.getText(), keyField.getText());
                    if (!encryptedText.equals("") || decryptTextArea.getText().equals("")) {
                        encryptTextArea.setText(encryptedText);
                    }
                } else if (direction == -1) {
                    String decryptedText = decrypt(numberSelectedCrypt, encryptTextArea.getText(), keyField.getText());
                    if (!decryptedText.equals("") || encryptTextArea.getText().equals("")) {
                        decryptTextArea.setText(decryptedText);
                    }
                }
            }else{
                CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрации",
                        "Шифр не выбран",false);
            }
            }catch (Exception ex){
                CreationAlerts.showErrorAlert("Ошибка","Ошибка шифрации",
                        "При выполнении шифрования произошла ошибка. Свяжитесь с разработчиком для её исправления.",true);
                ex.printStackTrace();
            }
        }
    }
    //Обработчик событий нажатия на кнопку генерации случайного ключа
    public class GeneratorRandomKey implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            keyField.setText(CryptoListModel.cryptsList.get(numberSelectedCrypt).generateKey());
        }
    }
    //Обработчик нажатия на пункт меню открытия файла
    private class OpenFile implements EventHandler<ActionEvent>{
        TextArea area;
        OpenFile(TextArea area){
            this.area=area;
        }
        @Override
        public void handle(ActionEvent event) {
            File openedFile=openFile();
            fillTextAreaForAFile(area,openedFile);
        }
    }
    //Обработчик нажатия на пункт меню сохранения файла
    private class SaveFile implements EventHandler<ActionEvent>{
        TextArea area;
        SaveFile(TextArea area){
            this.area=area;
        }
        @Override
        public void handle(ActionEvent event) {
            File savedFile=chooseFileForSave();
            saveFile(area,savedFile);
        }
    }
}

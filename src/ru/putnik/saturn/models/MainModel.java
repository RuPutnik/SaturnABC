package ru.putnik.saturn.models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

/**
 * Created by My Computer on 31.01.2018.
 */
public class MainModel {
    //Обработчик, отвечающий за очистку левого(правого) окна или поля для ключа при нажатии на кнопку
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
    public static class CryptOperation implements EventHandler<ActionEvent>{
        String text;
        int direction;
        public CryptOperation(String text,int directionCrypt){
            this.text=text;
            this.direction=directionCrypt;
        }

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public static class GeneratorRandomKey implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public static class OpenFile implements EventHandler<ActionEvent>{
        TextArea area;
        public OpenFile(TextArea area){
            this.area=area;
        }
        @Override
        public void handle(ActionEvent event) {

        }
    }
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

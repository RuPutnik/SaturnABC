package ru.putnik.saturn.models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import ru.putnik.saturn.pojo.Crypt;

/**
 * Created by My Computer on 02.02.2018.
 */
public class CryptoListModel {
    private ObservableList<Crypt> cryptsList=FXCollections.observableArrayList();
    public CryptoListModel(){


    }
    public ObservableList<Crypt> prepareList(){
        cryptsList.add(new Crypt("Шифр Цезаря(Сдвиговый)"));
        cryptsList.add(new Crypt("Шифр кодовым словом"));
        cryptsList.add(new Crypt("Шифр блочный"));
        cryptsList.add(new Crypt("Шифр Тритемиуса"));
        cryptsList.add(new Crypt("Шифр расширения словом"));
        return cryptsList;
    }

    public ObservableList<Crypt> getCryptsList() {
        return cryptsList;
    }
    public static class SelectedTypeCrypt<Crypt> implements ChangeListener<Crypt>{
        @Override
        public void changed(ObservableValue<? extends Crypt> observable, Crypt oldValue, Crypt newValue) {
            System.out.println(newValue.toString());
        }
    }
}

package ru.putnik.saturn.pojo;

/**
 * Created by My Computer on 02.02.2018.
 */
public class Crypt {
    //Класс отвечает за структуризацию отдельного шифра при добавлении в ObservableList в классе CryptoListModel
    private String nameCrypt;
    private int numberCrypt;

    public Crypt(String nameCrypt, int numberCrypt){
        this.nameCrypt=nameCrypt;
        this.numberCrypt=numberCrypt;
    }

    public String getNameCrypt() {
        return nameCrypt;
    }
    public int getNumberCrypt(){
        return numberCrypt;
    }
    public void setNameCrypt(String nameCrypt) {
        this.nameCrypt = nameCrypt;
    }
    public void setNumberCrypt(int numberCrypt){
        this.numberCrypt=numberCrypt;
    }
    @Override
    public String toString() {
        return nameCrypt;
    }
}

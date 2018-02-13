package ru.putnik.saturn.pojo;

/**
 * Created by My Computer on 02.02.2018.
 */
public class Crypt {
    //Класс отвечает за структуризацию отдельного шифра при добавлении в ObservableList в классе CryptoListModel
    private String nameCrypt;
    private int numberCrypt;
    private String nameInfoFile;

    public Crypt(String nameCrypt, int numberCrypt,String nameInfoFile){
        this.nameCrypt=nameCrypt;
        this.numberCrypt=numberCrypt;
        this.nameInfoFile=nameInfoFile;
    }

    public String getNameCrypt() {
        return nameCrypt;
    }
    public int getNumberCrypt(){
        return numberCrypt;
    }
    public String getNameInfoFile() {
        return nameInfoFile;
    }

    public void setNameCrypt(String nameCrypt) {
        this.nameCrypt = nameCrypt;
    }
    public void setNumberCrypt(int numberCrypt){
        this.numberCrypt=numberCrypt;
    }
    public void setNameInfoFile(String nameInfoFile) {
        this.nameInfoFile = nameInfoFile;
    }

    @Override
    public String toString() {
        return nameCrypt;
    }
}

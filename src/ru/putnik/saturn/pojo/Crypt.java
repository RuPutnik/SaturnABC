package ru.putnik.saturn.pojo;

/**
 * Created by My Computer on 02.02.2018.
 */
public class Crypt {
    private String nameCrypt;

    public Crypt(String nameCrypt){
        this.nameCrypt=nameCrypt;
    }
    public String getNameCrypt() {
        return nameCrypt;
    }

    public void setNameCrypt(String nameCrypt) {
        this.nameCrypt = nameCrypt;
    }

    @Override
    public String toString() {
        return nameCrypt;
    }
}

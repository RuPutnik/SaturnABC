package ru.putnik.saturn.ciphers;

public abstract class Cipher {
    public String nameCipher;
    public String nameFileInfo;
    public int numberCipher;

    public abstract String crypt(String text,String key,int direction);
    public abstract boolean checkKey(String key);
    public abstract String generateKey();

    protected char cryptSymbol(char symbol,int key,int direction){
        return (char)((int)symbol+key*direction);
    }

    public String getNameCipher() {
        return nameCipher;
    }

    public String getNameFileInfo() {
        return nameFileInfo;
    }

    public int getNumberCipher() {
        return numberCipher;
    }

    @Override
    public String toString() {
        return nameCipher;
    }
}

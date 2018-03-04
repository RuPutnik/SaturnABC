package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.PlayerIndexTable;
import ru.putnik.saturn.models.SettingModel;
import ru.putnik.saturn.pojo.Settings;
import ru.putnik.saturn.serialization.SerializationMachine;

import java.io.File;

public abstract class Cipher {
    private Settings settings=SerializationMachine.deserializationSettings(new File(SettingModel.PATH_TO_FILE_SETTINGS));
    private PlayerIndexTable table = PlayerIndexTable.INSTANCE;

    public String nameCipher;
    public String nameFileInfo;
    public int numberCipher;

    Cipher(int numberCipher){
        this.numberCipher=numberCipher;
    }

    Cipher() {}

    public abstract String crypt(String text,String key,int direction);
    public abstract boolean checkKey(String key);
    public abstract String generateKey();

    char cryptSymbol(char symbol, int keyOffset, int direction){

        if(settings.isPlayerTableIndexs()) {
           int key=table.getIndexForSymbol((char)keyOffset);
            if (symbol == ' ' && !settings.isCryptoSpacing()) {
                return symbol;
            } else {
                return table.getSymbolForIndex(table.getIndexForSymbol(symbol)+(key*direction));
            }
        }else {
            if (symbol == ' ' && !settings.isCryptoSpacing()) {
                return symbol;
            } else {
                int iv=(int) symbol + keyOffset * direction;//integer value
                return (char) (iv);
            }
        }
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

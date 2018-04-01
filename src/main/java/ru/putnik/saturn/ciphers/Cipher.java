package ru.putnik.saturn.ciphers;

import classes.ICipher;
import ru.putnik.saturn.main.PlayerIndexTable;
import ru.putnik.saturn.models.SettingModel;
import ru.putnik.saturn.pojo.Settings;
import ru.putnik.saturn.serialization.SerializationMachine;

import java.io.File;

public abstract class Cipher implements ICipher {
    private Settings settings=SerializationMachine.deserializationSettings(new File(SettingModel.PATH_TO_FILE_SETTINGS));
    private PlayerIndexTable table = PlayerIndexTable.INSTANCE;

    String nameCipher;
    String nameFileInfo;
    private int numberCipher;

    Cipher(int numberCipher){
        this.numberCipher=numberCipher;
    }

    Cipher() {}
    //шифрование отдельного символа
    char cryptSymbol(char symbol, int keyOffset, int direction){
        if(settings.isPlayerTableIndexs()) {
           int key=table.getIndexForSymbol((char)keyOffset);
           //Если символ-пробел, то при соответствующей настройке он не шифруется
            if (symbol == ' ' && !settings.isCryptoSpacing()) {
                return symbol;
            } else {
                //Если активирована пользовательская таблица индексов, то коды символов получаем из неё
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
    @Override
    public String getNameCipher() {
        return nameCipher;
    }
    @Override
    public String getNameFileInfo() {
        return nameFileInfo;
    }
    @Override
    public int getNumberCipher() {
        return numberCipher;
    }

    @Override
    public void setNumberCipher(int numberCipher){
        this.numberCipher=numberCipher;
    }

    @Override
    public String toString() {
        return nameCipher;
    }
}

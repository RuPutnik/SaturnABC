package ru.putnik.saturn.pojo;

import java.io.Serializable;

//Данный класс используется для сериализации настроек
public class Settings implements Serializable {
    boolean logging;
    boolean cryptoSpacing;
    boolean playerTableIndexs;
    String pathToPlayerTable;

    public boolean isLogging() {
        return logging;
    }

    public boolean isCryptoSpacing() {
        return cryptoSpacing;
    }

    public boolean isPlayerTableIndexs() {
        return playerTableIndexs;
    }

    public String getPathToPlayerTable() {
        return pathToPlayerTable;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public void setCryptoSpacing(boolean cryptoSpacing) {
        this.cryptoSpacing = cryptoSpacing;
    }

    public void setPlayerTableIndexs(boolean playerTableIndexs) {
        this.playerTableIndexs = playerTableIndexs;
    }

    public void setPathToPlayerTable(String pathToPlayerTable) {
        this.pathToPlayerTable = pathToPlayerTable;
    }
}

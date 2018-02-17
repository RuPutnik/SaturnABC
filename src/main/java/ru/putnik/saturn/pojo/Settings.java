package ru.putnik.saturn.pojo;

import java.io.Serializable;

//Данный класс используется для сериализации настроек
public class Settings implements Serializable {
    private boolean logging;
    private boolean cryptoSpacing;
    private boolean playerTableIndex;
    private String pathToPlayerTable;

    public boolean isLogging() {
        return logging;
    }

    public boolean isCryptoSpacing() {
        return cryptoSpacing;
    }

    public boolean isPlayerTableIndexs() {
        return playerTableIndex;
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

    public void setPlayerTableIndex(boolean playerTableIndex) {
        this.playerTableIndex = playerTableIndex;
    }

    public void setPathToPlayerTable(String pathToPlayerTable) {
        this.pathToPlayerTable = pathToPlayerTable;
    }
}

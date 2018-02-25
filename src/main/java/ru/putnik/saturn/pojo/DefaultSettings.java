package ru.putnik.saturn.pojo;

public class DefaultSettings{
    public static boolean LOGGING=false;
    public static final boolean CRYPTO_SPACING=true;
    public static final boolean PLAYER_TABLE_INDEX=false;
    public static final String PATH_TO_PLAYER_TABLE="";
    public static Settings getSettings(){
        Settings settings=new Settings();
        settings.setCryptoSpacing(CRYPTO_SPACING);
        settings.setLogging(LOGGING);
        settings.setPathToPlayerTable(PATH_TO_PLAYER_TABLE);
        settings.setPlayerTableIndex(PLAYER_TABLE_INDEX);
        return settings;
    }
}

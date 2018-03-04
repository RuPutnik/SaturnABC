package ru.putnik.saturn.main;

import org.apache.logging.log4j.Level;
import ru.putnik.saturn.models.SettingModel;
import ru.putnik.saturn.pojo.Settings;
import ru.putnik.saturn.serialization.SerializationMachine;

import java.io.*;
import java.util.HashMap;

public class PlayerIndexTable {
    public static final PlayerIndexTable INSTANCE=new PlayerIndexTable();
    private Settings settings=SerializationMachine.deserializationSettings(new File(SettingModel.PATH_TO_FILE_SETTINGS));
    private HashMap<Integer,Character> mapSymbols=new HashMap<>();
    private HashMap<Character,Integer> mapIndex=new HashMap<>();
    private boolean error;
    private PlayerIndexTable(){
        //Если пользовательская таблица активирована и адрес к ней не пустой, загружаем данные из неё в два HashMap`а
        if(settings.isPlayerTableIndexs()&&!settings.getPathToPlayerTable().equals("")) {
            File tableFile = new File(SerializationMachine.deserializationSettings(
                    new File(SettingModel.PATH_TO_FILE_SETTINGS)).getPathToPlayerTable());
            BufferedReader tableReader;
            String line;
            error = false;
            try {
                LogMachine.log(Level.INFO,"Loading the download of user table indexes");
                tableReader = new BufferedReader(new InputStreamReader(new FileInputStream(tableFile), "Windows-1251"));
                while ((line = tableReader.readLine()) != null) {
                    //Получаем строки с индексами и их значениями
                    String[] keyAndValue = line.split(":=");//Формат Индекс:=Символ
                    //Используем для разных HashMap для двухстороннего поиска (Индекс=>Символ и Символ=>Индекс)
                    mapSymbols.put(Integer.parseInt(keyAndValue[0]), keyAndValue[1].charAt(0));
                    mapIndex.put(keyAndValue[1].charAt(0), Integer.parseInt(keyAndValue[0]));
                }
                LogMachine.log(Level.INFO,"Loading the table completed successfully");
            } catch (IOException e) {
                CreationAlerts.showErrorAlert("Ошибка", "Ошибка обработки файла таблицы", "При обработке файла с таблицей " +
                        "пользовательских индексов символов возникла ошибка. Возможно поврежден файл или его структура.", false);
                e.printStackTrace();
                error = true;
                LogMachine.log(Level.ERROR,"Table loading failed");
            }
        }
    }
    //Получаем символ по индексу
    public char getSymbolForIndex(int index){
        char symbol;
        if(mapSymbols.containsKey(index)&&!error){
            symbol=mapSymbols.get(index);
        }else {
            symbol=(char)index;
        }
        return symbol;
    }
    //получаем индекс по символу
    public int getIndexForSymbol(char symbol){
        int index;
        if(mapIndex.containsKey(symbol)&&!error){
            index=mapIndex.get(symbol);
        }else {
            index=(int)symbol;
        }
        return index;
    }

}

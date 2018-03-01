package ru.putnik.saturn.main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.putnik.saturn.models.SettingModel;
import ru.putnik.saturn.serialization.SerializationMachine;

import java.io.File;

public class LogMachine {
    private static Logger logger= LogManager.getLogger();
    private LogMachine(){}
    public static void log(Level level,String text){
    if(SerializationMachine.deserializationSettings(new File(SettingModel.PATH_TO_FILE_SETTINGS)).isLogging()){
        logger.log(level,text);
    }
}
}

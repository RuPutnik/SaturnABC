package ru.putnik.saturn.main;

import org.apache.logging.log4j.Level;
import ru.putnik.saturn.controllers.MainController;

/**
 * Created by My Computer on 30.01.2018.
 */
public class Launcher{
    //Точка входа программы
    public static void main(String[] args) {
        LogMachine.log(Level.INFO,"Start program");
        MainController.start();
    }
}

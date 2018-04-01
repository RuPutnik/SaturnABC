package ru.putnik.saturn.models;

import classes.ICipher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import ru.putnik.saturn.ciphers.*;
import ru.putnik.saturn.main.CreationAlerts;
import ru.putnik.saturn.controllers.CryptoListController;
import ru.putnik.saturn.main.JarArchiver;
import ru.putnik.saturn.main.LogMachine;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by My Computer on 02.02.2018.
 */
public class CryptoListModel {
    private JarArchiver archiver=new JarArchiver();
    public static ObservableList<ICipher> cryptsList;
    public SaveButton saveButton=new SaveButton();
    public CancelButton cancelButton=new CancelButton();
    public ConnectModuleButton connectModuleButton=new ConnectModuleButton();
    public DisconnectModuleButton disconnectModuleButton=new DisconnectModuleButton();
    public SelectedTypeCrypt selectedTypeCrypt=new SelectedTypeCrypt();

    private String tempNameSelectedCrypt;
    private int tempNumberSelectedCrypt;
    private Label selectedCryptLabel;
    private TextArea infoCryptTextArea;
    public CryptoListModel(Label label,TextArea area){
        selectedCryptLabel=label;
        infoCryptTextArea=area;
    }
    //Добавление существующих шифров в список
    public ObservableList<ICipher> prepareList(){
        LogMachine.log(Level.INFO,"Preparation of the list of ciphers");
        cryptsList=FXCollections.observableArrayList();
        cryptsList.add(new CaesarCipher(1));
        cryptsList.add(new CodeWordCipher(2));
        cryptsList.add(new BlockCodeWordCipher(3));
        cryptsList.add(new TrytCipher(4));
        cryptsList.add(new ExpandCipher(5));
        loadingModules(cryptsList);
        return cryptsList;
    }
    //Вывод справки о выбранном шифре
    public void printCipherInfo(String nameFile){
        try {
        LogMachine.log(Level.INFO,"File download information on the cipher "+nameFile);
        String validPath=getValidAddressToInfoFile(nameFile);
        InputStreamReader streamReader=new InputStreamReader(new FileInputStream(validPath));
        BufferedReader bufferedReader=new BufferedReader(streamReader);
        String infoLine;
        infoCryptTextArea.setText("");
            while ((infoLine = bufferedReader.readLine())!= null) {
                infoCryptTextArea.appendText(infoLine+"\n");
            }
        LogMachine.log(Level.INFO,"The download of the cipher info file completed successfully");
        }catch (NullPointerException|IOException ex){
            ex.printStackTrace();
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки справки по шрифту",
           "Информация о выбранном шрифте не была загружена. Возможно файл справки поврежден или не существует.",
           false);
            LogMachine.log(Level.ERROR,"The download of the cipher info file failed");
        }
    }
    //Получение итогового адреса к файлу помощи. Если класс модуля наследуется от Cipher, то значит этот шифр- из ядра, и справку ищем в самой программе,
    //Иначе справка будет находиться в папке модулей программы
    private String getValidAddressToInfoFile(String fileName){
        String validAddress;
        String folder;
        try {
            if (tempNumberSelectedCrypt==0||Cipher.class.isAssignableFrom(cryptsList.get(tempNumberSelectedCrypt-1).getClass())) {
                folder = "texts/textsCrypts/";
                //Путь до файла из ядра программы
                validAddress = getClass().getClassLoader().getResource(folder + fileName).getFile();
            } else {
                folder = "C:\\SaturnABC\\arch\\";
                validAddress = folder + fileName;
            }
        }catch (Exception ex){
            throw ex;
        }
        //если шифр-из ядра то
        return validAddress;
    }
    //Возвращает имя файла помощи по номеру шифра
    public String getNameInfoFileForNumber(int numberCipher){
        if(numberCipher==0){
            return "notSelected.txt";
        }else {
            for (ICipher aCryptsList : cryptsList) {
                if (aCryptsList.getNumberCipher() == numberCipher)
                    return aCryptsList.getNameFileInfo();
            }
            return "";
        }
    }
    //Метод подключения модуля шифра к программе
    private void connectionModuleCiphers(){
        LogMachine.log(Level.INFO,"The module is connected...");
        FileChooser chooserModule=new FileChooser();
        chooserModule.setInitialDirectory(new File("C:\\"));
        chooserModule.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR","*.jar"));
        File fileModule=chooserModule.showOpenDialog(new Stage());
        if(fileModule!=null) {
            Class[] modulesClasses = archiver.extractFiles(fileModule);
            boolean existValidClass = false;
            //Анализируем каждый из полученных class-ов на соблюдение различных требований. Если class их соблюдает, добавлем в программу, иначе - удаляем
            for (Class modulesClass : modulesClasses) {
                if (analysisClass(modulesClass)) {
                    cryptsList.add(getNumberCipherAlphabetically(cryptsList,modulesClass.getName()),gettingCipherModule(modulesClass));
                    LogMachine.log(Level.INFO, "The module "+modulesClass.getName()+" is connected successfully");
                    existValidClass = true;
                }else {
                    archiver.deleteFile(modulesClass.getName(),"class");
                }
            }
            //В некоторых случаях при добавлении модуля может возникнуть проблема с внешними и внутренними индексами модуля
            if(existValidClass){
                //Если добавили модуль, на всякий случай исправляем номера индексов
                for (int a=1;a<cryptsList.size()+1;a++){
                    //Согласуем номера элементов в ObservableList (всегда на 1 меньше) с номерами, содержащимися в объекте
                    cryptsList.get(a-1).setNumberCipher(a);

                }
                //Изменяем номер и имя выбранного(но не утвержденного) шифра на тот, что находится в главном окне слева вверху(уже утвержденный)
                changeNumberSelectedCipher(MainModel.nameSelectedCrypt);
            }else {
                CreationAlerts.showWarningAlert("Ошибка", "Ошибка подключения модуля", "Выбраный модуль не содержит классов шифра, " +
                        "удовлетворяющих заданному шаблону, или данный модуль поврежден.", false);
                LogMachine.log(Level.WARN, "The selected module is not valid");

            }
        }
    }
    //Загрузка уставновленных модулей из папки при старте программы
    private void loadingModules(ObservableList<ICipher> listModules){
        Class[] modulesClasses=archiver.gettingUnpackedClasses();
        if(modulesClasses!=null) {
            for (Class modulesClass : modulesClasses) {
                //Анализируем class-ы. При несоблюдении условий-удаляем
                if (analysisClass(modulesClass)) {
                    listModules.add(gettingCipherModule(modulesClass));
                    LogMachine.log(Level.INFO, "The module"+modulesClass.getName()+"is connected successfully");
                }else {
                    archiver.deleteFile(modulesClass.getName(),"class");
                }
            }
        }
    }
    //Получение номера расположения по алфавиту названия загружаемого шифра (Используется для предотвращениея возникновения ошибки, т.к.
    //ПРи запуске программы шифры расставляются в списке шифров и инициализируются в алфавитном порядке имени class-ов модуля,
    //а при загрузке шифра - по очереди. Т.о. загруженный шифр при перезагрузке программы(или перезапуске окна выбора шифра)
    //может оказаться на другом месте в спике шифров
    private int getNumberCipherAlphabetically(List<ICipher> list,String nameClass){
        int index=list.size();
        try {
            ArrayList<String> namesClasses = new ArrayList<>();

            //Добавляем имена class-ов в анализируемый arraylist
            for (int a = 5; a < list.size(); a++) {
                namesClasses.add(list.get(a).getClass().getName());
            }
            namesClasses.add(nameClass);
            Collections.sort(namesClasses);//Сортируем имена классов по алфавиту

            index = 5+namesClasses.indexOf(nameClass);//Получаем номер имени по алфавиту со смещением на 5( т.к. первые 5 шифров- из ядра)
        }catch (Exception ex){
            index=list.size();
            ex.printStackTrace();
        }

        return index;
    }
    //Метод анализирования файла, и проверки, соответсвует ли он условиям модуля
    private boolean analysisClass(Class moduleClass){
        boolean validClass=false;
        //Нужно проверить, является ли интерфейс ICipher в модуле шифра тем же интерфейсом, что и здесь
        if(ICipher.class.isAssignableFrom(moduleClass)){
            ICipher checkingModule=gettingCipherModule(moduleClass);
            try {
                if (!checkingModule.getNameCipher().equals("")) {
                    validClass = true;

                    Iterator iterator = cryptsList.iterator();
                    //При наличии в List данного шифра отключаем его, что бы далее заново подключить.
                    //Защита от дублирования+удалении старой версии класса при подгрузке новой
                    while (iterator.hasNext()) {
                        ICipher element = (ICipher)iterator.next();
                        if(element.getClass().getName().equals(moduleClass.getName())){
                            //При наличии в List данного шифра отключаем его, что бы далее заново подключить.
                            //Защита от дублирования+удалении старой версии класса при подгрузке новой
                            iterator.remove();
                            validClass=true;
                            //Данный код нужен здесь, т.к. при обновлении модуля старая строчка удалится, а обновленная появится в конце списка, соответствеенно,
                            //шифры которые будут находиться после старой удаленной строки получат неверное смещение своего номера на 1
                            for (int a=1;a<cryptsList.size()+1;a++){
                                //Согласуем номера элементов в ObservableList (всегда на 1 меньше) с номерами, содержащимися в объекте
                                cryptsList.get(a-1).setNumberCipher(a);
                            }
                            //Изменяем номер и имя выбранного(но не утвержденного) шифра на тот, что находится в главном окне слева вверху(уже утвержденный)
                          changeNumberSelectedCipher(MainModel.nameSelectedCrypt);
                        }
                    }
                } else {
                    validClass = false;
                    CreationAlerts.showWarningAlert("Ошибка", "Ошибка инициализации шифра", "Выбранный модуль шифра не имеет имени. Загрузка невозможна.", false);
                    LogMachine.log(Level.WARN, "The cipher plug-in you select does not have a name. Loading is not possiblee");
                }
            }catch (Exception ex){
                ex.printStackTrace();
                validClass=false;
            }
        }
        return validClass;
    }
    //С помощью reflection получаем объект того класса, который был получен при загрузке модуля
    private ICipher gettingCipherModule(Class nameModule){
        ICipher moduleCipher=null;

        try {
            //Используем полиморфизм, т.к. мы не имеем в самой программе класса выбранного модуля, но знаем,
            //что он точно должен реализовывать интерфейс ICipher
            moduleCipher=(ICipher)(nameModule.getConstructor(int.class).newInstance(cryptsList.size()+1));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            CreationAlerts.showErrorAlert("Ошибка", "Ошибка инициализации шифра", "При инициализации шифра в программе возникла ошибка", false);
            LogMachine.log(Level.ERROR, "An error occurred while initializing the cipher in the program");
            e.printStackTrace();
        }

        return moduleCipher;
    }
    //Отключение модуля(и удаление из папки модулей
    private void disconnectionModuleCiphers(int numberCipher){
        if(numberCipher>4){
            archiver.deleteFile(cryptsList.get(numberCipher-1).getClass().getName(),"class");
            cryptsList.remove(numberCipher-1);
            //Начинаем с 6, т.к. все шифры номера ниже-содержатся в ядре и их номер не изменится в любом случае

            for (int a=1;a<cryptsList.size()+1;a++){
                //Согласуем номера элементов в ObservableList (всегда на 1 меньше) с номерами, содержащимися в объекте
                cryptsList.get(a-1).setNumberCipher(a);
            }

            //установить номер выбранного шифра на 0
            changeNumberSelectedCipher(MainModel.nameSelectedCrypt);
        }else {
            if(numberCipher==0){
                CreationAlerts.showWarningAlert("Ошибка", "Ошибка удаления шифра", "Удаляемый шифр не выбран", false);
            }else {
                CreationAlerts.showWarningAlert("Ошибка", "Ошибка удаления шифра", "Нельзя удалить шифры, входящие в ядро", false);
            }
        }
    }
    //Сбрасывает все номера выбранного шифра при удалении\обновлении модуля
    private void changeNumberSelectedCipher(String nameSelectedCipher){
        //В начале проверить cryptList на наличие указанного шифра из MainModel по названию
        //Если он остлся(удалялся не он), то передать новые данные по этому шифру(новый номер)
        //Если был удален именно он, то установить тип:не выбран
        boolean exitCipherInList=false;
        int newNumberCipher=0;
        for (ICipher cipher:cryptsList){
            if(cipher.getNameCipher().equals(nameSelectedCipher)){
                exitCipherInList=true;
                newNumberCipher=cipher.getNumberCipher();
                break;
            }
        }
        if(exitCipherInList){
            tempNameSelectedCrypt = nameSelectedCipher;
            tempNumberSelectedCrypt = newNumberCipher;
        }else {
            tempNameSelectedCrypt = "Не выбран";
            tempNumberSelectedCrypt = 0;
        }

        selectedCryptLabel.setText(tempNameSelectedCrypt);
        MainModel.nameSelectedCrypt = tempNameSelectedCrypt;
        MainModel.numberSelectedCrypt = tempNumberSelectedCrypt;
        CryptoListController.mainCryptLabel.setText("    Тип шифрования: " + MainModel.nameSelectedCrypt);
    }

    public ObservableList<ICipher> getCryptsList() {
        return cryptsList;
    }

    public void setTempNameSelectedCrypt(String nameSelectedCrypt) {
        this.tempNameSelectedCrypt = nameSelectedCrypt;
    }

    public void setTempNumberSelectedCrypt(int numberSelectedCrypt) {
        this.tempNumberSelectedCrypt = numberSelectedCrypt;
    }

    //Обработчик события. Записываем данные о выбранном из списка шифре
    public class SelectedTypeCrypt implements ChangeListener<ICipher>{
        @Override
        public void changed(ObservableValue<? extends ICipher> observable, ICipher oldValue, ICipher newValue) {
            tempNameSelectedCrypt=newValue.getNameCipher();
            tempNumberSelectedCrypt=newValue.getNumberCipher();
            selectedCryptLabel.setText(tempNameSelectedCrypt);
            LogMachine.log(Level.INFO,"The selected cipher "+newValue.getNameFileInfo());
            printCipherInfo(newValue.getNameFileInfo());//Выводим справку о шифре на экран в левой части окна
        }
    }
    //Обработчик события. Сохраняем информацию и выбранном шифре и выводим её на нужные виджеты Label
    private class SaveButton implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            MainModel.nameSelectedCrypt=tempNameSelectedCrypt;
            MainModel.numberSelectedCrypt=tempNumberSelectedCrypt;
            CryptoListController.close();
            CryptoListController.mainCryptLabel.setText("    Тип шифрования: "+MainModel.nameSelectedCrypt);
        }
    }
    //Обработчик события. Ничего не выбираем и не сохраняем, закрываем окно
    private class CancelButton implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            CryptoListController.close();
        }
    }
    //Обработчик события нажатия на кнопку подключения внешнего модуля шифра
    private class ConnectModuleButton implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            connectionModuleCiphers();
        }
    }
    //Обработчик события нажатия на кнопку отключения внешнего модуля шифра
    private class DisconnectModuleButton implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            disconnectionModuleCiphers(tempNumberSelectedCrypt);
        }
    }
}

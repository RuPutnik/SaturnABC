package ru.putnik.saturn.main;

import org.apache.logging.log4j.Level;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarArchiver {
    private JarFile file;
    private Enumeration<JarEntry> entries;
    private Vector<JarEntry> v;
    private File directoryArchiver;
    public JarArchiver(){
        directoryArchiver=new File("C:\\SaturnABC\\arch\\");
        //Если директории модулей не существует, создать её
        if(!(directoryArchiver.exists()||directoryArchiver.isDirectory())){
            directoryArchiver.mkdir();
        }
    }
    //Удаление файлов из директории загруженных модулей программы
    public void deleteFile(String nameClass,String extension){
        String nameFile=directoryArchiver+"\\"+nameClass+"."+extension;
        File file=new File(nameFile);
        try {
            if (file.exists())
                file.delete();
        }catch (Exception ex){
            LogMachine.log(Level.ERROR,"File deletion error");
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки модуля","При автоудалении невалидного модуля возникла ошибка.",false);
        }
    }
    //Получаем class файлы из jar, используется при загрузке уже установленных модулей
    public Class[] gettingUnpackedClasses(){
        ArrayList<Class> classes=new ArrayList<>();
        Class[] classesArray=null;
        try {
            URL url = null;
            url = directoryArchiver.toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
                //Загружаем class-ы из папки модулей. Они уже были установлены ранее. В папке находятся уже разархевированные файлы
                for (int a = 0; a < Objects.requireNonNull(directoryArchiver.listFiles()).length; a++) {
                    String nameAndExtension = Objects.requireNonNull(directoryArchiver.listFiles())[a].getName();
                    String name = nameAndExtension.split("\\.")[0];
                    String extension = nameAndExtension.split("\\.")[1];
                    //Если файл имеет расширение class, добавляем его в arraylist, если он txt, то ничего не делаем, иначе удаляем файл
                    if (extension.equals("class")) {
                        Class cls = cl.loadClass(name);
                        classes.add(cls);
                    }else if(!extension.equals("txt")){
                        deleteFile(name,extension);
                    }
            }
            } catch(Exception e){
                e.printStackTrace();
            }
            classesArray=new Class[classes.size()];
            classesArray=classes.toArray(classesArray);

        return classesArray;
    }
    //Получаем class файлы из jar
    public Class[] extractFiles(File jarFile){
        Class[] classesModule=null;
        try {
            file=new JarFile(jarFile);
            v=new Vector<>();
            entries=file.entries();
            while (entries.hasMoreElements()){
                v.add(entries.nextElement());
            }
            URL url = directoryArchiver.toURL();
            URL[] urls = new URL[]{url};
            //Настраиваем Class loader
            ClassLoader cl = new URLClassLoader(urls);
            //Получаем class файлы из архива
            File[] moduleClassesFiles=extract();
            if(moduleClassesFiles!=null) {
                //Если массив class файлов не пустой, то начинаем заполнять в цикле массив классов, используемых далее с помощью reflection
                classesModule = new Class[moduleClassesFiles.length];
                for (int a = 0; a < moduleClassesFiles.length; a++) {
                    String nameAndExtension = moduleClassesFiles[a].getName();
                    String name = nameAndExtension.split("\\.")[0];
                        Class cls = cl.loadClass(name);
                        classesModule[a] = cls;
                }
            }else {
                LogMachine.log(Level.WARN,"Class-file extraction error. Perhaps they do not exist or have damage.");
                CreationAlerts.showWarningAlert("Ошибка","Ошибка загрузки модуля","Class файлы в данном модуле не обнаружены. " +
                        "Возможно Jar-архив поврежден или имеет неправильную структуру.",false);
            }
        } catch (Exception e) {
            LogMachine.log(Level.ERROR,"Class-file extraction error. Maybe they're damaged.");
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки модуля","Ошибка обработки классов модуля.",false);
            e.printStackTrace();
        }
        return classesModule;
    }
    //Извлекаем файлы с расширение .class
    private File[] extract()
    {
        File[] classesArray;
        ArrayList<File> listFiles=new ArrayList<>();
        File tmpfile;  // создаём временный объект, который будет создавать каталоги
        JarEntry tmpentry;  //создаём временную ссылку на файл в архиве
        FileOutputStream out;
        InputStream in;
        int t;  /* переменная для копирования файла */
        try
        {
            for(int i=0;i<v.size();i++)  //создаём цикл для извлечения файлов из архива.
            {
                tmpentry=v.get(i);  //берём из вектора имя очередного файла или каталога
                tmpfile=new File(directoryArchiver.getPath()+"\\"+tmpentry.getName());
                //Если мы уже распаковывали данный jar, то удаляем файлы, полученынные после прошлой распаковки. Таким образом обновляем модуль
                if(tmpfile.exists()) {
                   tmpfile.delete();
                }
                if (!tmpentry.isDirectory()){  //если не каталог
                    in = file.getInputStream(new JarEntry(tmpentry.getName()));
                    out = new FileOutputStream(tmpfile);
                    while ((t = in.read()) != -1)
                        out.write(t);
                    out.close();
                    in.close();
                    String name=tmpfile.getName();
                    String extension=name.split("\\.")[1];
                    //если файл имеет расширение .class, то добавить в массив
                    if(extension.equals("class")) {
                        listFiles.add(tmpfile);
                    }
                }
            }

            classesArray=listFiles.toArray(new File[0]);
            return classesArray;
        }
        catch(Exception e)
        {
            LogMachine.log(Level.ERROR,"Error unpacking jar files. It may be damaged.");
            CreationAlerts.showErrorAlert("Ошибка","Ошибка загрузки модуля","Ошибка загрузки элементов модуля. Возможно, модуль поврежден",false);
            e.printStackTrace();
            return null;
        }

    }

}

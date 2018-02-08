package ru.putnik.saturn.main;

import javafx.scene.control.Alert;

public class CreationAlerts {
    //Статический метод вызова диалогового окна с заданными свойствами
    public static void showErrorAlert(String title,String head,String content,boolean wait){
        Alert errorAlert=new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(head);
        errorAlert.setContentText(content);
        if(wait){
            errorAlert.showAndWait();
        }else {
            errorAlert.show();
        }
    }
}

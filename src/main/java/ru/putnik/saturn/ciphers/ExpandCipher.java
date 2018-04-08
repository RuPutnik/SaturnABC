package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.CreationAlerts;

public class ExpandCipher extends Cipher{
    public ExpandCipher(int numberCipher){
        super(numberCipher);
        nameCipher="Шифр расширения словом";
        nameFileInfo="extensionCipher.txt";
    }
    @Override
    public String crypt(String text, String key, int direction) {
        CodeWordCipher cipher=new CodeWordCipher();
        StringBuilder resultText= new StringBuilder();
        StringBuilder clearText=new StringBuilder();
        StringBuilder temp1=new StringBuilder();//для цифер
        StringBuilder temp2=new StringBuilder();//для букв
        int g=1;
        int args[]=null;
        int lineArg=0;
        int d=0;
        for(int l=0;l<key.length();l++){
            if(key.charAt(l)>=((int)('0'))&&key.charAt(l)<=((int)('9'))){
                temp1.append(key.charAt(l));
            }else{
                temp2.append(key.charAt(l));
            }
        }
        key=temp2.toString();
        if(temp1.length()!=0){
            lineArg=temp1.length();
            args=new int[lineArg];
            for(int k=0;k<lineArg;k++){
                args[k]=Integer.parseInt(String.valueOf(temp1.toString().charAt(k)));
            }

            g=Integer.parseInt(String.valueOf(temp1.toString().charAt(lineArg-1)));
        }
        if(direction==1){
            String cryptWordText=cipher.crypt(text,key,direction);
            for(int a=0;a<cryptWordText.length();a++){
                resultText.append(cryptWordText.charAt(a));
                for(int k=0;k<lineArg-1;k++){
                    if(((int)cryptWordText.charAt(a))%args[k]==0){
                        resultText.append(cryptWordText.charAt(a/g+args[k]+d));
                        d++;
                        break;
                    }
                }
            }
        }else if(direction==-1){
            for(int a=0;a<text.length();a++){
                clearText.append(text.charAt(a));
                for(int k=0;k<lineArg-1;k++){
                    if((int)text.charAt(a)%args[k]==0){
                        a++;
                        break;
                    }
                }
            }
            resultText = new StringBuilder(cipher.crypt(clearText.toString(), key, direction));
        }
        return resultText.toString();
    }

    @Override
    public boolean checkKey(String key) {
        if(key==null||key.equals("")){
            CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрования","Поля ключа пусто!",false);
            return false;
        }else
            return true;
    }

    @Override
    public String generateKey() {
        return null;
    }
}

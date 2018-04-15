package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.CreationAlerts;

import java.util.Random;

public class CodeWordCipher extends Cipher {
    public CodeWordCipher(int numberCipher){
        super(numberCipher);
        nameCipher="Шифр кодовым словом";
        nameFileInfo="codeWordCipher.txt";
    }
    public CodeWordCipher(){}
    @Override
    public String crypt(String text, String key, int direction) {
        StringBuilder resultText= new StringBuilder();
        int j=0;

        for(int a=0;a<text.length();a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
            }else {
                if (j == key.length()) j = 0;
                resultText.append(cryptSymbol(text.charAt(a), (byte) (key.charAt(j)) + 70, direction));
                j++;
            }

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
        Random random=new Random();
        int lengthCodeWord=2+random.nextInt(8);
        StringBuilder codeWord=new StringBuilder();

        for (int a=0;a<lengthCodeWord;a++){
         codeWord.append((char)(48+random.nextInt(74)));
        }
        return codeWord.toString();
    }
}

package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.CreationAlerts;

import java.util.Random;

public class CaesarCipher extends Cipher {
    public CaesarCipher(int numberCipher){
        super(numberCipher);
      nameCipher="Шифр Цезаря(Сдвиговый)";
      nameFileInfo="caesarCipher.txt";
    }
    @Override
    public String crypt(String text, String key, int direction) {
        if(checkKey(key)) {
            StringBuilder resultText = new StringBuilder();
            int digitKey = Integer.parseInt(key);

            System.out.println(text);
            System.out.println(text.length());
            for (int a=0;a<text.length();a++){
                System.out.println(a+")"+text.charAt(a));
            }

            for (int a = 0; a < text.length(); a++) {
                if (String.valueOf(text.charAt(a)).equals("\n")) {
                    resultText.append("\n");
                }else {
                    resultText.append(cryptSymbol(text.charAt(a), digitKey, direction));
                }

            }
            return resultText.toString();
        }else
            return "";
    }

    @Override
    public boolean checkKey(String key) {
        if(key==null||key.equals("")){
            return false;
        }
        try {
            int testingKey=Integer.parseInt(key);
            return true;
        }catch (NumberFormatException e){
            e.printStackTrace();
            CreationAlerts.showWarningAlert("Ошибка","Ошибка шифрования","Используемый ключ содержит недопустимые символы для данного шифра!",false);
            return false;
        }
    }

    @Override
    public String generateKey() {
        int randomKey=new Random().nextInt(20);
        return String.valueOf(randomKey);
    }
}

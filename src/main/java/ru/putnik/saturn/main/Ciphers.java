package ru.putnik.saturn.main;

import java.util.Date;
import java.util.Timer;

public class Ciphers {
    public String cryptCaesar(String text,String key,int direction){
        StringBuilder resultText= new StringBuilder();
        int digitKey=Integer.parseInt(key);

        for (int a=0;a<text.length()-1;a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
                a++;
            }
         resultText.append(cryptSymbol(text.charAt(a), digitKey, direction));
        }
        return resultText.toString();
    }
    public String cryptTryt(String text,String key,int direction){
        StringBuilder resultText= new StringBuilder();
        int digitKey=Integer.parseInt(key);
        int b=0;
        int c=0;

        for (int a=0;a<text.length()-1;a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
                a++;
            }

            if(c>30) b=0;
            b=(int)Math.sqrt(c);
            resultText.append(cryptSymbol(text.charAt(a),digitKey+b,direction));
            c++;
        }

        return resultText.toString();
    }
    private char cryptSymbol(char symbol,int key,int direction){
        return (char)((int)symbol+key*direction);
    }
}

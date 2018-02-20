package ru.putnik.saturn.main;

import java.util.Date;
import java.util.Timer;

public class Ciphers {
    //Метод шфира тритемиуса (модифицированного)
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
    //Метод шифра кодовым словом
    public String cryptCodeWord(String text,String keyWord,int direction){
        StringBuilder resultText= new StringBuilder();
        int j=0;
        for(int a=0;a<text.length()-1;a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
                a++;
            }

            if(j==keyWord.length()) j=0;
            resultText.append(cryptSymbol(text.charAt(a),(int)(keyWord.charAt(j)),direction));
            j++;
        }
        return resultText.toString();
    }
    private char cryptSymbol(char symbol,int key,int direction){
        return (char)((int)symbol+key*direction);
    }
}

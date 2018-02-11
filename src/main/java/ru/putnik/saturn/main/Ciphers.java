package ru.putnik.saturn.main;

public class Ciphers {
    public String cryptCaesar(String text,String key,int direction){
        StringBuilder resultText= new StringBuilder();
        int digitKey=Integer.parseInt(key);

        for (int a=0;a<text.length();a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
                a++;
            }
         resultText.append(cryptSymbol(text.charAt(a), digitKey, direction));
        }
        return resultText.toString();
    }
    private char cryptSymbol(char symbol,int key,int direction){
        return (char)((int)symbol+key*direction);
    }
}

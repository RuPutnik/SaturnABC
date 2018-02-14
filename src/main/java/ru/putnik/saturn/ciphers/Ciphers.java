package ru.putnik.saturn.ciphers;

public abstract class Ciphers {
    protected String nameCipher;
    protected String nameFileInfo;
    public int numberCipher;

    public abstract String crypt(String text,String key,int direction);
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

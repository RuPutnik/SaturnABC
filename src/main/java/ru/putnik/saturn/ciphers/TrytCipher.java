package ru.putnik.saturn.ciphers;

public class TrytCipher extends Cipher {
    public TrytCipher(int numberCipher){
        super(numberCipher);
        nameCipher="Шифр Тритемиуса";
        nameFileInfo="tritemiusCipher.txt";
    }
    @Override
    public String crypt(String text, String key, int direction) {
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

    @Override
    public boolean checkKey(String key) {
        return false;
    }

    @Override
    public String generateKey() {
        return null;
    }
}

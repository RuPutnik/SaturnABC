package ru.putnik.saturn.ciphers;

public class CodeWordCipher extends Cipher {
    public CodeWordCipher(int numberCipher){
        super(numberCipher);
        nameCipher="Шифр кодовым словом";
        nameFileInfo="codeWordCipher.txt";
    }
    @Override
    public String crypt(String text, String key, int direction) {
        StringBuilder resultText= new StringBuilder();
        int j=0;
        for(int a=0;a<text.length()-1;a++){
            if(String.valueOf(text.charAt(a)).equals("\n")){
                resultText.append("\n");
                a++;
            }

            if(j==key.length()) j=0;
            resultText.append(cryptSymbol(text.charAt(a),(int)(key.charAt(j)),direction));
            j++;
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

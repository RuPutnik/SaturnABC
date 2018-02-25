package ru.putnik.saturn.ciphers;

import ru.putnik.saturn.main.CreationAlerts;

public class BlockCodeWordCipher extends Cipher{
    public BlockCodeWordCipher(int numberCipher) {
        super(numberCipher);
        nameCipher="Шифр блочный";
        nameFileInfo="blockCipher.txt";
    }

    @Override
    public String crypt(String text, String key, int direction) {
        StringBuilder stringBuilder=new StringBuilder();
        CodeWordCipher codeWordCipher=new CodeWordCipher();
        int lengthWord=key.length();
        int lengthText=text.length();
        int countBlock=lengthWord;
        int k=0;
        int integerPart=lengthText/lengthWord;//Длина всех целых блоков
        int a=0;

        String arrayLines[]=new String[((text.length() + integerPart - 1) / integerPart)];

        //Разбиваем исходный текст на подстроки равной длины(кроме, возможно, последней)
        for (int start = 0; start < text.length(); start += integerPart) {
            arrayLines[a]=(text.substring(start, Math.min(text.length(), start + integerPart)));
            a++;
        }

        for(int c=0;c<countBlock;c++){
            stringBuilder.append(codeWordCipher.crypt(arrayLines[c],editCodeWord(key,(int)key.charAt(c),k),direction));
        }
        return stringBuilder.toString();
    }
    //Метод генерации кодовых слов для отдельных блоков
    //Алгоритм генерации: исходное кодовое слово для каждого блока
    //шифруется сдвигом каждого символа кодового слова на номер блока, для которого оно генерируется.
    private String editCodeWord(String word,int coefficient,int additionalCoefficient){
        StringBuilder result= new StringBuilder();
        for(int c=0;c<word.length();c++){
            result.append((char) (Math.abs((int) word.charAt(c) + coefficient - additionalCoefficient)));
        }
        return result.toString();
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
